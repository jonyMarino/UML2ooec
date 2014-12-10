/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.internal.reconciler;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.model.IFunction;
import org.eclipse.cdt.core.model.IStructure;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;
import org.eclipse.umlgen.reverse.c.event.IfndefAdded;
import org.eclipse.umlgen.reverse.c.event.IfndefRemoved;
import org.eclipse.umlgen.reverse.c.event.StructAdded;

/**
 * A H file reconciler.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class HFileReconciler extends AbstractFileReconciler {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement,
     *      org.eclipse.cdt.core.model.ITranslationUnit)
     */
    public AbstractCModelChangedEvent addElement(IASTPreprocessorIfndefStatement statementAdded,
            ITranslationUnit translationUnit) {
        return IfndefAdded.builder().setCondition(statementAdded.getCondition()).translationUnit(
                translationUnit).build();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#addElement(org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition,
     *      org.eclipse.cdt.core.model.IFunction)
     */
    public AbstractCModelChangedEvent addElement(IASTFunctionDefinition functionDef, IFunction element) {
        // .h files never contain for us a body of a function
        return null;
    }

    /**
     * Handles adding of <b>simple declaration</b> constructions.
     *
     * @param element
     *            The simple declaration.
     * @param structure
     *            The structure.
     * @return The event.
     */
    public AbstractCModelChangedEvent addElement(IASTSimpleDeclaration element, IStructure structure) {
        // following test to avoid combination notification if multiple declaration copied/pasted.
        if (((IASTCompositeTypeSpecifier)element.getDeclSpecifier()).getName().toString().equals(
                structure.getElementName())) {
            String structName = ModelUtil.computeAnonymousTypeName(structure.getTranslationUnit().getPath()
                    .removeFileExtension().lastSegment(), structure.getElementName(), element
                    .getDeclSpecifier());
            IASTDeclaration[] activeDeclarations = ((IASTCompositeTypeSpecifier)element.getDeclSpecifier())
                    .getDeclarations(false);
            return StructAdded.builder().setDeclarations(activeDeclarations).currentName(structName)
                    .translationUnit(structure.getTranslationUnit()).build();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition,
     *      org.eclipse.cdt.core.model.IFunction)
     */
    public AbstractCModelChangedEvent removeElement(IASTFunctionDefinition element, IFunction originalFunction) {
        // .h file never contain body of a function
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.internal.reconciler.IFileReconciler#removeElement(org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement,
     *      org.eclipse.cdt.core.model.ITranslationUnit)
     */
    public AbstractCModelChangedEvent removeElement(IASTPreprocessorIfndefStatement statementAdded,
            ITranslationUnit translationUnit) {
        return IfndefRemoved.builder().setCondition(statementAdded.getCondition()).translationUnit(
                translationUnit).build();
    }

}
