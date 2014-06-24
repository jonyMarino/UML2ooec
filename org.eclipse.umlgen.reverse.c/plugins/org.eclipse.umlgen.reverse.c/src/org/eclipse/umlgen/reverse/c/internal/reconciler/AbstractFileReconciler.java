/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     	Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.IEnumeration;
import org.eclipse.cdt.core.model.IFunctionDeclaration;
import org.eclipse.cdt.core.model.IStructure;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.ITypeDef;
import org.eclipse.cdt.core.model.IVariable;
import org.eclipse.cdt.core.model.IVariableDeclaration;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;
import org.eclipse.umlgen.reverse.c.event.EnumerationAdded;
import org.eclipse.umlgen.reverse.c.event.EnumerationRemoved;
import org.eclipse.umlgen.reverse.c.event.FunctionDeclarationAdded;
import org.eclipse.umlgen.reverse.c.event.FunctionDeclarationRemoved;
import org.eclipse.umlgen.reverse.c.event.IncludeAdded;
import org.eclipse.umlgen.reverse.c.event.IncludeRemoved;
import org.eclipse.umlgen.reverse.c.event.MacroAdded;
import org.eclipse.umlgen.reverse.c.event.MacroRemoved;
import org.eclipse.umlgen.reverse.c.event.StructAdded;
import org.eclipse.umlgen.reverse.c.event.StructRemoved;
import org.eclipse.umlgen.reverse.c.event.TypeDefAdded;
import org.eclipse.umlgen.reverse.c.event.TypeDefArrayAdded;
import org.eclipse.umlgen.reverse.c.event.TypeDefArrayRemoved;
import org.eclipse.umlgen.reverse.c.event.TypeDefEnumerationAdded;
import org.eclipse.umlgen.reverse.c.event.TypeDefEnumerationRemoved;
import org.eclipse.umlgen.reverse.c.event.TypeDefFunctionDeclarationAdded;
import org.eclipse.umlgen.reverse.c.event.TypeDefFunctionDeclarationRemoved;
import org.eclipse.umlgen.reverse.c.event.TypeDefRemoved;
import org.eclipse.umlgen.reverse.c.event.TypeDefStructAdded;
import org.eclipse.umlgen.reverse.c.event.TypeDefStructRemoved;
import org.eclipse.umlgen.reverse.c.event.UnionAdded;
import org.eclipse.umlgen.reverse.c.event.VariableDeclarationAdded;
import org.eclipse.umlgen.reverse.c.event.VariableDeclarationRemoved;
import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.util.ASTUtil;

/**
 * The abstract reconciler having the shared behavior betwwen C and H units.<br>
 * Creation : 10 september 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @see {@link CFileReconciler}
 * @see {@link HFileReconciler}
 */
public abstract class AbstractFileReconciler implements IFileReconciler {

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier,
	 *      org.eclipse.cdt.core.model.IEnumeration)
	 */
	public CModelChangedEvent addElement(IASTEnumerationSpecifier element, IEnumeration enumeration) {
		return buildEvent(EventType.ADD, element, enumeration);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier,
	 *      org.eclipse.cdt.core.model.IEnumeration)
	 */
	public CModelChangedEvent removeElement(IASTEnumerationSpecifier element, IEnumeration enumeration) {
		return buildEvent(EventType.REMOVE, element, enumeration);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTEnumerationSpecifier specifier,
			IEnumeration enumeration) {
		String enumName = ModelUtil.computeAnonymousTypeName(enumeration.getTranslationUnit().getPath()
				.removeFileExtension().lastSegment(), enumeration.getElementName(), specifier);
		if (eType == EventType.ADD) {
			return EnumerationAdded.builder().setEnumerators(specifier.getEnumerators())
					.currentName(enumName).translationUnit(enumeration.getTranslationUnit()).build();
		} else {
			return EnumerationRemoved.builder().setEnumerators(specifier.getEnumerators()).currentName(
					enumName).translationUnit(enumeration.getTranslationUnit()).build();
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier,
	 *      org.eclipse.cdt.core.model.IStructure)
	 */
	public CModelChangedEvent addElement(IASTCompositeTypeSpecifier specifier, IStructure structure) {
		return buildEvent(EventType.ADD, specifier, structure);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier,
	 *      org.eclipse.cdt.core.model.IStructure)
	 */
	public CModelChangedEvent removeElement(IASTCompositeTypeSpecifier specifier, IStructure structure) {
		return buildEvent(EventType.REMOVE, specifier, structure);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTCompositeTypeSpecifier specifier,
			IStructure structure) {
		boolean isUnion = false;

		try {
			isUnion = structure.isUnion();
		} catch (CModelException cme) {
			// TODO Use a more detailed message to locate the problem : Unable to find if XXX in XXX is a
			// UNION.
			Activator.log("Can't deteremine a UNION.", IStatus.WARNING, cme);
		}

		String typeName = ModelUtil.computeAnonymousTypeName(structure.getTranslationUnit().getPath()
				.removeFileExtension().lastSegment(), structure.getElementName(), specifier);
		if (!isUnion) {
			if (eType == EventType.ADD) {
				IASTDeclaration[] declarations = specifier.getDeclarations(false);
				return StructAdded.builder().setDeclarations(declarations).currentName(typeName)
						.translationUnit(structure.getTranslationUnit()).build();
			} else {
				return StructRemoved.builder().currentName(typeName).translationUnit(
						structure.getTranslationUnit()).build();
			}
		} else {
			IASTDeclaration[] declarations = specifier.getDeclarations(false);
			return UnionAdded.builder().setDeclarations(declarations).currentName(typeName).translationUnit(
					structure.getTranslationUnit()).build();
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier,
	 *      org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent addElement(IASTEnumerationSpecifier enumerationSpecifier, ITypeDef type) {
		return buildEvent(EventType.ADD, enumerationSpecifier, type);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier,
	 *      org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent removeElement(IASTEnumerationSpecifier enumerationSpecifier, ITypeDef type) {
		return buildEvent(EventType.REMOVE, enumerationSpecifier, type);
	}

	/**
	 * Handles <em>typedef enum X {} x;</em> pattern.
	 * 
	 * @param eType
	 *            The event type
	 * @param specifier
	 *            The AST type specifier
	 * @param type
	 *            The typedef
	 * @return the built event
	 */
	private CModelChangedEvent buildEvent(EventType eType, IASTEnumerationSpecifier specifier, ITypeDef type) {
		String redefinedEnumName = type.getTypeName().replaceFirst(BundleConstants.ENUM_REGEXP, "");
		if (eType == EventType.ADD) {
			return TypeDefEnumerationAdded.builder().setRedefinedEnumeration(redefinedEnumName).setSource(
					specifier).currentName(type.getElementName()).translationUnit(type.getTranslationUnit())
					.build();
		} else {
			return TypeDefEnumerationRemoved.builder().currentName(type.getElementName()).translationUnit(
					type.getTranslationUnit()).build();
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration,
	 *      org.eclipse.cdt.core.model.IVariableDeclaration)
	 */
	public CModelChangedEvent addElement(IASTSimpleDeclaration element, IVariableDeclaration variableDecl) {
		return buildEvent(EventType.ADD, element, variableDecl);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration,
	 *      org.eclipse.cdt.core.model.IVariableDeclaration)
	 */
	public CModelChangedEvent removeElement(IASTSimpleDeclaration element, IVariableDeclaration variableDecl) {
		return buildEvent(EventType.REMOVE, element, variableDecl);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTSimpleDeclaration element,
			IVariableDeclaration variableDecl) {
		String typeName = ASTUtil.computeType(element);
		if (typeName == null || typeName.length() == 0) {
			return null;
		}
		typeName = ModelUtil.computeAnonymousTypeName(variableDecl.getTranslationUnit().getPath()
				.removeFileExtension().lastSegment(), typeName, element.getDeclSpecifier());
		String varName = ASTUtil.computeName(element, variableDecl.getElementName());
		CModelChangedEvent event = null;

		try {
			if (eType == EventType.ADD) {
				event = VariableDeclarationAdded.builder().setConst(variableDecl.isConst()).setExtern(
						element.getRawSignature().contains("extern")).setVolatile(variableDecl.isVolatile())
						.setIsStatic(variableDecl.isStatic()).setRegister(
								element.getRawSignature().contains("register")).currentType(typeName)
						.currentName(varName).translationUnit(variableDecl.getTranslationUnit()).build();
			} else {
				event = VariableDeclarationRemoved.builder().currentType(typeName).currentName(
						variableDecl.getElementName()).translationUnit(variableDecl.getTranslationUnit())
						.build();
			}
		} catch (CModelException e) {
			Activator.log("Variable Addition Error : " + e.getMessage(), IStatus.ERROR);
		}
		return event;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration,
	 *      org.eclipse.cdt.core.model.IVariable)
	 */
	public CModelChangedEvent addElement(IASTSimpleDeclaration element, IVariable variable) {
		return buildEvent(EventType.ADD, element, variable);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration,
	 *      org.eclipse.cdt.core.model.IVariable)
	 */
	public CModelChangedEvent removeElement(IASTSimpleDeclaration element, IVariable variable) {
		return buildEvent(EventType.REMOVE, element, variable);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTSimpleDeclaration element, IVariable variable) {
		String initialisationExpression = null;
		if (element == null) {
			return null;
		}
		for (IASTDeclarator declarator : element.getDeclarators()) {
			if (variable.getElementName().equals(declarator.getName().toString())) {
				IASTInitializer initializer = declarator.getInitializer();
				if (declarator instanceof IASTArrayDeclarator && initializer instanceof IASTEqualsInitializer) {
					initialisationExpression = ((IASTEqualsInitializer)initializer).getInitializerClause()
							.getRawSignature();
					break;
				} else if (initializer != null
						&& initializer.getChildren()[0] instanceof IASTInitializerClause) {
					initialisationExpression = ((IASTInitializerClause)initializer.getChildren()[0])
							.getRawSignature();
					break;
				}
			}
		}

		String typeName = ASTUtil.computeType(element);
		typeName = ModelUtil.computeAnonymousTypeName(variable.getTranslationUnit().getPath()
				.removeFileExtension().lastSegment(), typeName, element.getDeclSpecifier());

		String varName = ASTUtil.computeName(element, variable.getElementName());
		CModelChangedEvent event = null;

		try {
			if (eType == EventType.ADD) {
				event = VariableDeclarationAdded.builder().setConst(variable.isConst()).setExtern(
						element.getRawSignature().contains("extern")).setVolatile(variable.isVolatile())
						.setIsStatic(variable.isStatic()).setRegister(
								element.getRawSignature().contains("register")).setInitializerExpression(
								initialisationExpression).currentType(typeName).currentName(varName)
						.translationUnit(variable.getTranslationUnit()).build();
			} else {
				event = VariableDeclarationRemoved.builder().currentType(typeName).currentName(
						variable.getElementName()).translationUnit(variable.getTranslationUnit()).build();
			}
		} catch (CModelException e) {
			Activator.log("Variable Addition Error : " + e.getMessage(), IStatus.ERROR);
		}
		return event;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration,
	 *      org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier, org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent addElement(IASTCompositeTypeSpecifier specifier, ITypeDef type) {
		return buildEvent(EventType.ADD, specifier, type);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier,
	 *      org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent removeElement(IASTCompositeTypeSpecifier specifier, ITypeDef type) {
		return buildEvent(EventType.REMOVE, specifier, type);
	}

	/**
	 * Handles <em>typedef struct X {} x;</em> pattern.
	 * 
	 * @param eType
	 *            The event type
	 * @param specifier
	 *            The AST type specifier
	 * @param type
	 *            The typedef
	 * @return the built event
	 */
	private CModelChangedEvent buildEvent(EventType eType, IASTCompositeTypeSpecifier specifier, ITypeDef type) {

		if (eType == EventType.ADD) {
			String redefinedStructName = type.getTypeName().replaceFirst(BundleConstants.STRUCT_REGEXP, "")
					.trim();
			return TypeDefStructAdded.builder().setRedefinedStruct(redefinedStructName).setSource(specifier)
					.currentName(type.getElementName()).translationUnit(type.getTranslationUnit()).build();
		} else {
			return TypeDefStructRemoved.builder().currentName(type.getElementName()).translationUnit(
					type.getTranslationUnit()).build();
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier,
	 *      org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent addElement(IASTElaboratedTypeSpecifier specifier, ITypeDef type) {
		return buildEvent(EventType.ADD, specifier, type);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier,
	 *      org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent removeElement(IASTElaboratedTypeSpecifier specifier, ITypeDef type) {
		return buildEvent(EventType.REMOVE, specifier, type);
	}

	/**
	 * Handles <em>typedef struct X x;</em> pattern.
	 * 
	 * @param eType
	 *            The event type
	 * @param specifier
	 *            The AST type specifier
	 * @param type
	 *            The typedef
	 * @return the built event
	 */
	private CModelChangedEvent buildEvent(EventType eType, IASTElaboratedTypeSpecifier specifier,
			ITypeDef type) {

		if (eType == EventType.ADD) {
			if (specifier.getKind() == IASTElaboratedTypeSpecifier.k_struct) {
				String redefinedStructName = type.getTypeName().replaceFirst(BundleConstants.STRUCT_REGEXP,
						"").trim();
				return TypeDefStructAdded.builder().setRedefinedStruct(redefinedStructName).setSource(
						specifier).currentName(type.getElementName()).translationUnit(
						type.getTranslationUnit()).build();
			} else {
				String redefinedEnumName = type.getTypeName().replaceFirst(BundleConstants.ENUM_REGEXP, "")
						.trim();
				return TypeDefEnumerationAdded.builder().setRedefinedEnumeration(redefinedEnumName)
						.setSource(specifier).currentName(type.getElementName()).translationUnit(
								type.getTranslationUnit()).build();
			}
		} else {
			if (specifier.getKind() == IASTElaboratedTypeSpecifier.k_struct) {
				return TypeDefStructRemoved.builder().currentName(type.getElementName()).translationUnit(
						type.getTranslationUnit()).build();
			} else {
				return TypeDefEnumerationRemoved.builder().currentName(type.getElementName())
						.translationUnit(type.getTranslationUnit()).build();
			}
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement,
	 *      org.eclipse.cdt.core.model.ITranslationUnit)
	 */
	public CModelChangedEvent addElement(IASTPreprocessorIncludeStatement include, ITranslationUnit tu) {
		return buildEvent(EventType.ADD, include, tu);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement,
	 *      org.eclipse.cdt.core.model.ITranslationUnit)
	 */
	public CModelChangedEvent removeElement(IASTPreprocessorIncludeStatement include, ITranslationUnit tu) {
		return buildEvent(EventType.REMOVE, include, tu);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTPreprocessorIncludeStatement include,
			ITranslationUnit tu) {
		boolean standard = include.getRawSignature().contains("<") && include.getRawSignature().contains("<");
		if (eType == EventType.ADD) {
			return IncludeAdded.builder().setStandard(standard).currentName(include.getName().toString())
					.translationUnit(tu).build();
		} else {
			return IncludeRemoved.builder().currentName(include.getName().toString()).translationUnit(tu)
					.build();
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator,
	 *      org.eclipse.cdt.core.model.IFunctionDeclaration)
	 */
	public CModelChangedEvent addElement(IASTFunctionDeclarator iFunctionDeclarator,
			IFunctionDeclaration originalFunction) {
		return buildEvent(EventType.ADD, iFunctionDeclarator, originalFunction);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator,
	 *      org.eclipse.cdt.core.model.IFunctionDeclaration)
	 */
	public CModelChangedEvent removeElement(IASTFunctionDeclarator iFunctionDeclarator,
			IFunctionDeclaration originalFunction) {
		return buildEvent(EventType.REMOVE, iFunctionDeclarator, originalFunction);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTFunctionDeclarator iFunctionDeclarator,
			IFunctionDeclaration originalFunction) {
		try {
			List<FunctionParameter> parameters = ASTUtil.collectParameterInformation(iFunctionDeclarator);
			if (eType == EventType.ADD) {
				return FunctionDeclarationAdded.builder().isStatic(originalFunction.isStatic())
						.setReturnType(originalFunction.getReturnType()).setParameters(parameters)
						.currentName(originalFunction.getElementName()).translationUnit(
								originalFunction.getTranslationUnit()).build();
			} else {
				return FunctionDeclarationRemoved.builder().isStatic(originalFunction.isStatic())
						.setParameters(parameters).setReturnType(originalFunction.getReturnType())
						.currentName(originalFunction.getElementName()).translationUnit(
								originalFunction.getTranslationUnit()).build();
			}
		} catch (CoreException ce) {
			return null;
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition,
	 *      org.eclipse.cdt.core.model.ITranslationUnit)
	 */
	public CModelChangedEvent addElement(IASTPreprocessorMacroDefinition astMacro, ITranslationUnit unit) {
		return buildEvent(EventType.ADD, astMacro, unit);
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition,
	 *      org.eclipse.cdt.core.model.ITranslationUnit)
	 */
	public CModelChangedEvent removeElement(IASTPreprocessorMacroDefinition astMacro, ITranslationUnit unit) {
		return buildEvent(EventType.REMOVE, astMacro, unit);
	}

	private CModelChangedEvent buildEvent(EventType eType, IASTPreprocessorMacroDefinition astMacro,
			ITranslationUnit unit) {
		String macroName = ASTUtil.computeName(astMacro);
		if (eType == EventType.ADD) {
			return MacroAdded.builder().setExpansion(astMacro.getExpansion()).currentName(macroName)
					.translationUnit(unit).build();
		} else {
			return MacroRemoved.builder().setExpansion(astMacro.getExpansion()).currentName(macroName)
					.translationUnit(unit).build();
		}
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTDeclarator,
	 *      org.eclipse.cdt.core.model.ITypeDef, int)
	 */
	public CModelChangedEvent addElement(IASTDeclarator iDeclarator, ITypeDef typeDef) {
		CModelChangedEvent event = null;
		if (iDeclarator instanceof IASTArrayDeclarator) {
			Collection<String> size = new ArrayList<String>();
			for (IASTArrayModifier arrayModifier : ((IASTArrayDeclarator)iDeclarator).getArrayModifiers()) {
				if (arrayModifier.getConstantExpression() != null) {
					size.add(arrayModifier.getConstantExpression().getRawSignature());
				}
			}
			event = TypeDefArrayAdded.builder().setDimensions(size).setRedefinedType(typeDef.getTypeName())
					.currentType(typeDef.getTypeName()).currentName(typeDef.getElementName())
					.translationUnit(typeDef.getTranslationUnit()).build();
		} else if (iDeclarator instanceof IASTFunctionDeclarator) {
			List<FunctionParameter> parameters = ASTUtil.collectParameterInformation(iDeclarator);

			// Remove the round brakets
			String returnType = typeDef.getTypeName().substring(0, typeDef.getTypeName().indexOf("("));
			event = TypeDefFunctionDeclarationAdded.builder().currentName(typeDef.getElementName())
					.setReturnType(returnType).setParameters(parameters).translationUnit(
							typeDef.getTranslationUnit()).build();
		} else if (iDeclarator instanceof IASTDeclarator) {
			event = TypeDefAdded.builder().setRedefinedType(typeDef.getTypeName()).currentType(
					typeDef.getTypeName()).currentName(typeDef.getElementName()).translationUnit(
					typeDef.getTranslationUnit()).build();
		}
		return event;
	}

	/**
	 * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTDeclarator,
	 *      org.eclipse.cdt.core.model.ITypeDef)
	 */
	public CModelChangedEvent removeElement(IASTDeclarator iDeclarator, ITypeDef typeDef) {
		CModelChangedEvent event = null;
		if (iDeclarator instanceof IASTArrayDeclarator) {
			Collection<String> dim = new ArrayList<String>();
			for (IASTArrayModifier arrayModifier : ((IASTArrayDeclarator)iDeclarator).getArrayModifiers()) {
				if (arrayModifier.getConstantExpression() != null) {
					dim.add(arrayModifier.getConstantExpression().getRawSignature());
				}
			}
			event = TypeDefArrayRemoved.builder().setDimensions(dim).setRedefinedType(typeDef.getTypeName())
					.currentName(typeDef.getElementName()).translationUnit(typeDef.getTranslationUnit())
					.build();
		} else if (iDeclarator instanceof IASTFunctionDeclarator) {
			List<FunctionParameter> parameters = ASTUtil.collectParameterInformation(iDeclarator);

			// Remove the round brakets
			String returnType = typeDef.getTypeName().substring(0, typeDef.getTypeName().indexOf("("));
			event = TypeDefFunctionDeclarationRemoved.builder().currentName(typeDef.getElementName())
					.setReturnType(returnType).setParameters(parameters).translationUnit(
							typeDef.getTranslationUnit()).build();
		} else if (iDeclarator instanceof IASTDeclarator) {
			event = TypeDefRemoved.builder().setRedefinedType(typeDef.getTypeName()).currentName(
					typeDef.getElementName()).translationUnit(typeDef.getTranslationUnit()).build();
		}
		return event;
	}
}
