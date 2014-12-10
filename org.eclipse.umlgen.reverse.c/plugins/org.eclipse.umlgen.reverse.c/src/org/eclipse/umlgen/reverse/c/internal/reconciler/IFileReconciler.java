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
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;

/** File reconciler. */
public interface IFileReconciler {

    /**
     * Handles adding of <b>variable declaration</b> constructions.
     *
     * @param element
     *            The AST declaration
     * @param variableDecl
     *            The variable declaration
     * @return The event
     */
    AbstractCModelChangedEvent addElement(IASTSimpleDeclaration element, IVariableDeclaration variableDecl);

    /**
     * Handles adding of <b>variable</b> constructions.
     *
     * @param element
     *            The AST declaration
     * @param variable
     *            The variable declaration
     * @return The event
     */
    AbstractCModelChangedEvent addElement(IASTSimpleDeclaration element, IVariable variable);

    /**
     * Handles adding of <b>enum</b> constructions. Note : no range needs to be provided !
     *
     * @param specifier
     *            The AST specifier
     * @param enumeration
     *            The enumeration
     * @return The event
     */
    AbstractCModelChangedEvent addElement(IASTEnumerationSpecifier specifier, IEnumeration enumeration);

    /**
     * Handles adding of <b>struct</b> constructions. Note : no range needs to be provided !
     *
     * @param specifier
     *            The AST specifier
     * @param structure
     *            The structure
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTCompositeTypeSpecifier specifier, IStructure structure);

    /**
     * Handles adding of <b>typedef enum</b> constructions.
     *
     * @param enumerationSpecifier
     *            The AST specifier.
     * @param type
     *            The type definition.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTEnumerationSpecifier enumerationSpecifier, ITypeDef type);

    /**
     * Handles adding of <b>typedef struct</b> constructions.
     *
     * @param specifier
     *            The AST specifier
     * @param type
     *            The type definition.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTCompositeTypeSpecifier specifier, ITypeDef type);

    /**
     * Handles adding of <b>typedef struct</b> constructions.
     *
     * @param specifier
     *            The AST specifier
     * @param typeDef
     *            The type definition.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTElaboratedTypeSpecifier specifier, ITypeDef typeDef);

    /**
     * Handles adding of <b>typedef struct</b> constructions.
     *
     * @param iDeclarator
     *            The AST declarator.
     * @param typeDef
     *            The type definition.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTDeclarator iDeclarator, ITypeDef typeDef);

    /**
     * Handles adding of <b>operation declaration</b> constructions.
     *
     * @param functionDecl
     *            The AST function declarator.
     * @param element
     *            The operation declaration.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTFunctionDeclarator functionDecl, IFunctionDeclaration element);

    /**
     * Handles adding of <b>operation definition</b> constructions.
     *
     * @param functionDef
     *            The AST function declarator.
     * @param element
     *            The operation definition.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTFunctionDefinition functionDef, IFunction element);

    /**
     * Handles adding of <b>macro</b> statements.
     *
     * @param astMacro
     *            The AST macro definition.
     * @param unit
     *            The translation unit.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTPreprocessorMacroDefinition astMacro, ITranslationUnit unit);

    /**
     * Handles adding of <b>include</b> statements.
     *
     * @param include
     *            The AST include statement.
     * @param unit
     *            The include statement.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTPreprocessorIncludeStatement include, ITranslationUnit unit);

    /**
     * Handles adding of <b>ifndef</b> statements.
     *
     * @param statementAdded
     *            The AST ifndef statement
     * @param unit
     *            the ifndef statement.
     * @return The event.
     */
    AbstractCModelChangedEvent addElement(IASTPreprocessorIfndefStatement statementAdded,
            ITranslationUnit unit);

    // **************//
    // REMOVE //
    // *************//

    /**
     * Handles removing of <b>variable declaration</b> constructions.
     *
     * @param element
     *            The AST declaration
     * @param variableDecl
     *            the variable declaration
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTSimpleDeclaration element, IVariableDeclaration variableDecl);

    /**
     * Handles removing of <b>variable definition</b> constructions.
     *
     * @param element
     *            The AST declaration
     * @param variable
     *            The variable declaration
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTSimpleDeclaration element, IVariable variable);

    /**
     * Handles removing of <b>enum</b> constructions.
     *
     * @param element
     *            The AST specifier.
     * @param enumeration
     *            The enumeration
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTEnumerationSpecifier element, IEnumeration enumeration);

    /**
     * Handles removing of <b>structure</b> constructions.
     *
     * @param specifier
     *            The AST specifier
     * @param structure
     *            the structure
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTCompositeTypeSpecifier specifier, IStructure structure);

    /**
     * Handles removing of <b>typedef enum</b> constructions.
     *
     * @param enumerationSpecifier
     *            The AST specifier
     * @param type
     *            the typdef enum
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTEnumerationSpecifier enumerationSpecifier, ITypeDef type);

    /**
     * Handles removing of <b>typedef struct</b> constructions.
     *
     * @param specifier
     *            The AST specifier
     * @param type
     *            The typdedef struct
     * @return the event
     */
    AbstractCModelChangedEvent removeElement(IASTCompositeTypeSpecifier specifier, ITypeDef type);

    /**
     * Handles removing of <b>typedef struct</b> constructions.
     *
     * @param specifier
     *            The AST specifier
     * @param typeDef
     *            the typedef struct
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTElaboratedTypeSpecifier specifier, ITypeDef typeDef);

    /**
     * Handles removing of <b>typedef struct</b> constructions.
     *
     * @param iDeclarator
     *            The AST declarator.
     * @param typeDef
     *            The type def struct.
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTDeclarator iDeclarator, ITypeDef typeDef);

    /**
     * Handles adding of <b>operation declaration</b> constructions.
     *
     * @param functionDecl
     *            The AST function declaration
     * @param element
     *            The operation declaration.
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTFunctionDeclarator functionDecl, IFunctionDeclaration element);

    /**
     * Handles adding of <b>operation definition</b> constructions.
     *
     * @param functionDef
     *            The AST function definition.
     * @param element
     *            The operation definition.
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTFunctionDefinition functionDef, IFunction element);

    /**
     * Handles removing of <b>macro</b> statements.
     *
     * @param astMacro
     *            The AST macro definition
     * @param unit
     *            The macro statement
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTPreprocessorMacroDefinition astMacro, ITranslationUnit unit);

    /**
     * Handles removing of <b>include</b> statement.
     *
     * @param include
     *            The AST include statement
     * @param unit
     *            The include statement
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTPreprocessorIncludeStatement include, ITranslationUnit unit);

    /**
     * Handles removing of <b>ifndef</b> statement.
     *
     * @param statementAdded
     *            The AST ifndef statement
     * @param unit
     *            The idndef statement
     * @return The event
     */
    AbstractCModelChangedEvent removeElement(IASTPreprocessorIfndefStatement statementAdded,
            ITranslationUnit unit);

}
