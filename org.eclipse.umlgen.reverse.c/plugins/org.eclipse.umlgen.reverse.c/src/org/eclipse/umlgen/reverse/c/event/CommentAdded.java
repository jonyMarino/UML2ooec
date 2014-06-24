/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.AnnotationUtil;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.reverse.c.util.ASTUtil;

/**
 * Event related to addition of a comment.
 *
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class CommentAdded extends CommentEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		IASTNode parent = getParent();
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		Element element = null;

		if (parent instanceof IASTTranslationUnit) {
			element = matchingClassifier;
		} else if (parent instanceof IASTFunctionDefinition) {
			String name = ((IASTFunctionDefinition)parent).getDeclarator().getName().toString();
			element = matchingClassifier.getOwnedMember(name, false, UMLPackage.Literals.OPAQUE_BEHAVIOR);
		} else if (parent instanceof IASTFunctionDeclarator) {
			String name = ((IASTFunctionDeclarator)parent).getName().toString();
			element = matchingClassifier.getOwnedMember(name, false, UMLPackage.Literals.OPERATION);
		} else if (parent instanceof IASTSimpleDeclaration) {
			IASTSimpleDeclaration simpleDeclaration = (IASTSimpleDeclaration)parent;
			IASTDeclSpecifier specifier = simpleDeclaration.getDeclSpecifier();

			String name = null;
			if (specifier instanceof IASTEnumerationSpecifier) {
				name = ModelUtil.computeAnonymousTypeName(getUnitName(),
						name = ((IASTEnumerationSpecifier)specifier).getName().toString(), parent);
			} else if (specifier instanceof IASTCompositeTypeSpecifier) {
				name = ModelUtil.computeAnonymousTypeName(getUnitName(),
						((IASTCompositeTypeSpecifier)specifier).getName().toString(), parent);
			} else if (specifier instanceof IASTElaboratedTypeSpecifier) {
				name = ((IASTElaboratedTypeSpecifier)specifier).getName().toString();
			} else if (simpleDeclaration.getDeclarators().length > 0) {
				IASTDeclarator declarator = simpleDeclaration.getDeclarators()[0];
				if (declarator instanceof IASTFunctionDeclarator) {
					// case for function pointer and operation decl
					IASTDeclarator nestedDeclarator = declarator.getNestedDeclarator();
					name = nestedDeclarator != null && nestedDeclarator.getName() != null ? nestedDeclarator
							.getName().toString() : declarator.getName().toString();
				} else {
					name = ASTUtil.computeName(declarator);
				}
			}

			// expect a data type (struct, enum, typedef)
			element = matchingClassifier.getOwnedMember(name, false, UMLPackage.Literals.DATA_TYPE);

			if (element == null) {
				// expect a property or an operation
				element = matchingClassifier.getFeature(name);
			}
		} else if (parent instanceof IASTParameterDeclaration) {
			IASTNode functionOrOperation = parent.getParent();
			IASTParameterDeclaration myParameter = (IASTParameterDeclaration)parent;
			if (functionOrOperation instanceof IASTFunctionDeclarator) {
				// this is the method
				IASTFunctionDeclarator declarator = (IASTFunctionDeclarator)functionOrOperation;
				if (declarator.getParent() instanceof IASTFunctionDefinition) {
					IASTFunctionDefinition function = (IASTFunctionDefinition)declarator.getParent();
					Operation fct = (Operation)((Class)matchingClassifier).getOperation(function
							.getDeclarator().getName().toString(), null, null);
					// The comment may concern a function that is declared in
					// the .h but not added in this to the model
					if (fct != null) {
						for (Parameter parameter : fct.getOwnedParameters()) {
							if (parameter.getName().equals(myParameter.getDeclarator().getName().toString())) {
								element = parameter;
								break;
							}
						}
					}
				} else {
					// the declaration of an operation
					for (Element ownedElement : matchingClassifier.getOwnedElements()) {
						if (ownedElement instanceof Operation) {
							if (((Operation)ownedElement).getName().equals(declarator.getName().toString())) {
								for (Parameter parameter : ((Operation)ownedElement).getOwnedParameters()) {
									if (parameter.getName().equals(
											myParameter.getDeclarator().getName().toString())) {
										element = parameter;
										break;
									}
								}
							}
						}
					}
				}
			}
		} else if (parent instanceof IASTPreprocessorIncludeStatement) {
			String elementName = ((IASTPreprocessorIncludeStatement)parent).getName().toString();
			elementName = elementName.substring(0, elementName.length() - 2);
			EList<DirectedRelationship> list = matchingClassifier.getSourceDirectedRelationships();
			for (DirectedRelationship usage : list) {
				for (Element anElement : usage.getTargets()) {
					if (anElement instanceof Classifier) {
						if (((Classifier)anElement).getName().compareTo(elementName) == 0) {
							element = usage;
							break;
						}
					}
				}
			}
		} else if (parent instanceof IASTPreprocessorMacroDefinition) {
			String elementName = ((IASTPreprocessorMacroDefinition)parent).getName().toString();
			element = matchingClassifier.getFeature(elementName);
		} else if (parent instanceof IASTEnumerationSpecifier) {
			element = matchingClassifier.getOwnedMember(((IASTEnumerationSpecifier)parent).getName()
					.toString(), false, UMLPackage.Literals.ENUMERATION);
		} else if (parent instanceof IASTEnumerator) {
			IASTEnumerationSpecifier enumSpec = (IASTEnumerationSpecifier)parent.getParent();
			String enumName = ModelUtil.computeAnonymousTypeName(matchingClassifier.getName(), enumSpec
					.getName().toString(), enumSpec);
			Enumeration enumeration = ModelUtil.findEnumerationInClassifier(matchingClassifier, enumName);
			element = enumeration
					.getOwnedLiteral(((IASTEnumerator)parent).getName().toString(), false, false);
		} else if (parent instanceof IASTArrayDeclarator) {
			element = matchingClassifier.getAttribute(((IASTArrayDeclarator)parent).getName().toString(),
					null, false, UMLPackage.Literals.PROPERTY);
		}

		if (element != null) {
			EAnnotation documentationEAnnotation = AnnotationUtil.getDocumentationAnnotation(element);
			documentationEAnnotation.getDetails().put(getKey(), cleanInvalidXmlChars(getBody()));
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<CommentAdded> builder() {
		return new Builder<CommentAdded>() {
			private CommentAdded event = new CommentAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.CommentEvent.Builder#getEvent()
			 */
			@Override
			protected CommentAdded getEvent() {
				return event;
			}
		};
	}
}
