/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Christophe Le Camus (CS) - initial API and implementation
 *      Mikael Barbero (Obeo) - evolutions
 *      Sebastien Gabel - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.reconciler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.IWorkingCopy;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.listener.ICModelChangeListener;

/**
 * Defines an abstract reconcilier for interpreting changes that can be done.
 *
 * @see {@link CASTReconciler}
 * @see {@link ASTCommentReconciler}
 */
public abstract class AbstractReconciler implements IReconciler {

    /** List of model change listeners. */
    private List<ICModelChangeListener> modelChangeListeners;

    /** Constructor. */
    public AbstractReconciler() {
        modelChangeListeners = new ArrayList<ICModelChangeListener>();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#removeModelChangeListener(org.eclipse.umlgen.reverse.c.listener.ICModelChangeListener)
     */
    public synchronized void removeModelChangeListener(ICModelChangeListener listener) {
        modelChangeListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#addModelChangeListener(org.eclipse.umlgen.reverse.c.listener.ICModelChangeListener)
     */
    public synchronized void addModelChangeListener(ICModelChangeListener listener) {
        modelChangeListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#notifyListeners(org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent,
     *      boolean)
     */
    public void notifyListeners(AbstractCModelChangedEvent event, boolean needSave) {
        if (event != null) {
            for (ICModelChangeListener listener : modelChangeListeners) {
                listener.notifyChanges(event, needSave);
            }
        } else {
            Activator.log(new RuntimeException("Null event received"));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#getModelChangeListeners()
     */
    public List<ICModelChangeListener> getModelChangeListeners() {
        return modelChangeListeners;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#addAllModelChangeListeners(java.util.List)
     */
    public synchronized void addAllModelChangeListeners(List<ICModelChangeListener> listeners) {
        modelChangeListeners.addAll(listeners);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#removedElement(org.eclipse.cdt.core.dom.ast.IASTTranslationUnit,
     *      org.eclipse.cdt.core.dom.ast.IASTTranslationUnit, org.eclipse.cdt.core.model.ITranslationUnit,
     *      org.eclipse.cdt.core.model.ICElement)
     */
    public void removedElement(IASTTranslationUnit originalTranslationUnit,
            IASTTranslationUnit newTranslationUnit, ITranslationUnit workingcopy, ICElement coreElement)
                    throws CoreException {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#addedElement(org.eclipse.cdt.core.dom.ast.IASTTranslationUnit,
     *      org.eclipse.cdt.core.dom.ast.IASTTranslationUnit, org.eclipse.cdt.core.model.ITranslationUnit,
     *      org.eclipse.cdt.core.model.ICElement)
     */
    public void addedElement(IASTTranslationUnit originalTranslationUnit,
            IASTTranslationUnit newTranslationUnit, ITranslationUnit workingcopy, ICElement coreElement)
                    throws CoreException {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#removedElement(org.eclipse.cdt.core.model.ITranslationUnit,
     *      org.eclipse.cdt.core.model.IWorkingCopy, org.eclipse.cdt.core.model.ICElement)
     */
    public void removedElement(ITranslationUnit originalUnit, IWorkingCopy workingUnit, ICElement element)
            throws CoreException {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.reconciler.IReconciler#addedElement(org.eclipse.cdt.core.model.ITranslationUnit,
     *      org.eclipse.cdt.core.model.IWorkingCopy, org.eclipse.cdt.core.model.ICElement)
     */
    public void addedElement(ITranslationUnit originalUnit, IWorkingCopy workingUnit, ICElement element)
            throws CoreException {
    }

}
