/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Christophe LE CAMUS (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Thierry NAULEAU (CS-SI) - Improvements and bug fixes
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;
import org.eclipse.umlgen.reverse.c.util.ASTUtil;

public class UnionAdded extends StructEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		DataType myTypeDef = ModelUtil.findDataTypeInClassifier(matchingClassifier, getCurrentName());

		if (myTypeDef == null) {
			if (matchingClassifier instanceof Class) {
				myTypeDef = (DataType)((Class)matchingClassifier).getNestedClassifier(getCurrentName(),
						false, UMLPackage.Literals.DATA_TYPE, true);
			} else if (matchingClassifier instanceof Interface) {
				myTypeDef = (DataType)((Interface)matchingClassifier).getNestedClassifier(getCurrentName(),
						false, UMLPackage.Literals.DATA_TYPE, true);
			}

		} else {
			/* On supprime tous les attributs avant de les reconstruire */
			myTypeDef.getOwnedAttributes().clear();
		}

		for (IASTDeclaration declaration : getDeclarations()) {
			String typeName = "";
			String attributeName = "";

			if (declaration instanceof IASTSimpleDeclaration) {
				IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration)declaration;
				attributeName = ASTUtil.computeName(simpleDecl.getDeclarators()[0]);
				typeName = ASTUtil.computeType(simpleDecl);
			}

			Type myType = manager.getDataType(typeName);
			if (matchingClassifier.getAttribute(getCurrentName(), myType) == null) {
				myTypeDef.createOwnedAttribute(attributeName, myType);
			}
		}

		ModelUtil.setVisibility(myTypeDef, getTranslationUnit(), EventType.ADD);
		UML2Util.getEAnnotation(myTypeDef, "UNION", true);
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<UnionAdded> builder() {
		return new Builder<UnionAdded>() {
			private final UnionAdded event = new UnionAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.StructEvent#getEvent()
			 */
			@Override
			protected UnionAdded getEvent() {
				return event;
			}
		};
	}
}
