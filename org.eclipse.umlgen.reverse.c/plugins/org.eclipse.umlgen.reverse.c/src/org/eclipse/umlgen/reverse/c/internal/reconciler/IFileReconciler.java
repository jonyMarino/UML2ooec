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
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.model.IEnumeration;
import org.eclipse.cdt.core.model.IFunction;
import org.eclipse.cdt.core.model.IFunctionDeclaration;
import org.eclipse.cdt.core.model.IStructure;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.ITypeDef;
import org.eclipse.cdt.core.model.IVariable;
import org.eclipse.cdt.core.model.IVariableDeclaration;
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;

public interface IFileReconciler {

	/**
	 * Handles removing of <b>variable declaration and definition</b> constructions.
	 */
	CModelChangedEvent addElement(IASTSimpleDeclaration element, IVariableDeclaration variableDecl);

	CModelChangedEvent addElement(IASTSimpleDeclaration element, IVariable variable);

	/**
	 * Handles adding of <b>enum</b> constructions. Note : no range needs to be provided !
	 */
	CModelChangedEvent addElement(IASTEnumerationSpecifier specifier, IEnumeration enumeration);

	/**
	 * Handles adding of <b>struct</b> constructions. Note : no range needs to be provided !
	 */
	CModelChangedEvent addElement(IASTCompositeTypeSpecifier specifier, IStructure structure);

	/**
	 * Handles adding of <b>typedef enum</b> constructions.
	 */
	CModelChangedEvent addElement(IASTEnumerationSpecifier enumerationSpecifier, ITypeDef type);

	/**
	 * Handles adding of <b>typedef struct</b> constructions.
	 */
	CModelChangedEvent addElement(IASTCompositeTypeSpecifier specifier, ITypeDef type);

	CModelChangedEvent addElement(IASTElaboratedTypeSpecifier specifier, ITypeDef typeDef);

	CModelChangedEvent addElement(IASTDeclarator iDeclarator, ITypeDef typeDef);

	/**
	 * Handles adding of <b>operation declaration and definition</b> constructions.
	 */
	CModelChangedEvent addElement(IASTFunctionDeclarator functionDecl, IFunctionDeclaration element);

	CModelChangedEvent addElement(IASTFunctionDefinition functionDef, IFunction element);

	/**
	 * Handles adding of <b>macro</b> statements.
	 */
	CModelChangedEvent addElement(IASTPreprocessorMacroDefinition astMacro, ITranslationUnit unit);

	/**
	 * Handles adding of <b>include</b> and <b>ifndef</b> statements.
	 */
	CModelChangedEvent addElement(IASTPreprocessorIncludeStatement include, ITranslationUnit unit);

	CModelChangedEvent addElement(IASTPreprocessorIfndefStatement statementAdded, ITranslationUnit unit);

	// **************//
	// REMOVE //
	// *************//

	/**
	 * Handles removing of <b>variable declaration and definition</b> constructions.
	 */
	CModelChangedEvent removeElement(IASTSimpleDeclaration element, IVariableDeclaration variableDecl);

	CModelChangedEvent removeElement(IASTSimpleDeclaration element, IVariable variable);

	/**
	 * Handles removing of <b>enum</b> constructionss.
	 */
	CModelChangedEvent removeElement(IASTEnumerationSpecifier element, IEnumeration enumeration);

	/**
	 * Handles removing of <b>struct</b> constructions.
	 */
	CModelChangedEvent removeElement(IASTCompositeTypeSpecifier specifier, IStructure structure);

	/**
	 * Handles removing of <b>typedef enum</b> constructions.
	 */
	CModelChangedEvent removeElement(IASTEnumerationSpecifier enumerationSpecifier, ITypeDef type);

	/**
	 * Handles removing of <b>typedef struct</b> constructions.
	 */
	CModelChangedEvent removeElement(IASTCompositeTypeSpecifier specifier, ITypeDef type);

	CModelChangedEvent removeElement(IASTElaboratedTypeSpecifier specifier, ITypeDef typeDef);

	CModelChangedEvent removeElement(IASTDeclarator iDeclarator, ITypeDef typeDef);

	/**
	 * Handles adding of <b>operation declaration and definition</b> constructions.
	 */
	CModelChangedEvent removeElement(IASTFunctionDeclarator functionDecl, IFunctionDeclaration element);

	CModelChangedEvent removeElement(IASTFunctionDefinition functionDef, IFunction element);

	/**
	 * Handles removing of <b>macro</b> statements.
	 */
	CModelChangedEvent removeElement(IASTPreprocessorMacroDefinition astMacro, ITranslationUnit unit);

	/**
	 * Handles removing of <b>include</b> and <b>ifndef</b> statement.
	 */
	CModelChangedEvent removeElement(IASTPreprocessorIncludeStatement include, ITranslationUnit unit);

	CModelChangedEvent removeElement(IASTPreprocessorIfndefStatement statementAdded, ITranslationUnit unit);

}
