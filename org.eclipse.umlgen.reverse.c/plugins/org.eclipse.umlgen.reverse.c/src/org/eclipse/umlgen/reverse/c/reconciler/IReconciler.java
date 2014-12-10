/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Christophe Le Camus (CS) - initial API and implementation
 *      Mikael Barbero (Obeo) - evolutions
 *      Sebastien Gabel - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.reconciler;

import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.IWorkingCopy;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;
import org.eclipse.umlgen.reverse.c.listener.ICModelChangeListener;

/** A reconciler interface. */
public interface IReconciler {

    /**
     * Notify from a removed element.
     *
     * @param originalTranslationUnit
     *            The original translation unit.
     * @param newTranslationUnit
     *            The new translation unit.
     * @param workingcopy
     *            The working copy.
     * @param coreElement
     *            The core element
     * @throws CoreException
     *             exception
     */
    void removedElement(IASTTranslationUnit originalTranslationUnit, IASTTranslationUnit newTranslationUnit,
            ITranslationUnit workingcopy, ICElement coreElement) throws CoreException;

    /**
     * Notify from an added element.
     *
     * @param originalTranslationUnit
     *            The original translation unit.
     * @param newTranslationUnit
     *            The new translation unit.
     * @param workingcopy
     *            The working copy.
     * @param coreElement
     *            The core element
     * @throws CoreException
     *             exception
     */
    void addedElement(IASTTranslationUnit originalTranslationUnit, IASTTranslationUnit newTranslationUnit,
            ITranslationUnit workingcopy, ICElement coreElement) throws CoreException;

    /**
     * Notify from a removed element.
     *
     * @param originalUnit
     *            The original unit.
     * @param workingUnit
     *            The working unit.
     * @param element
     *            The core element.
     * @throws CoreException
     *             exception
     */
    void removedElement(ITranslationUnit originalUnit, IWorkingCopy workingUnit, ICElement element)
            throws CoreException;

    /**
     * Notify from an added element.
     *
     * @param originalUnit
     *            The original unit.
     * @param workingUnit
     *            The working unit.
     * @param element
     *            The core element.
     * @throws CoreException
     *             exception
     */
    void addedElement(ITranslationUnit originalUnit, IWorkingCopy workingUnit, ICElement element)
            throws CoreException;

    /**
     * Remove the model change listener.
     *
     * @param modelChangeListener
     *            the listener to remove.
     */
    void removeModelChangeListener(ICModelChangeListener modelChangeListener);

    /**
     * Add a model change listener.
     *
     * @param modelChangeListener
     *            The listener to add.
     */
    void addModelChangeListener(ICModelChangeListener modelChangeListener);

    /**
     * Return the model change listeners.
     *
     * @return The listeners
     */
    List<ICModelChangeListener> getModelChangeListeners();

    /**
     * Add the given model change listeners.
     *
     * @param listeners
     *            The listeners to add.
     */
    void addAllModelChangeListeners(List<ICModelChangeListener> listeners);

    /**
     * Notify listeners from the given event.
     *
     * @param event
     *            the event.
     * @param needSave
     *            This indicates if the model has to be saved.
     */
    void notifyListeners(AbstractCModelChangedEvent event, boolean needSave);

}
