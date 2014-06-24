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
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;
import org.eclipse.umlgen.reverse.c.listener.ICModelChangeListener;

public interface IReconciler {

	void removedElement(IASTTranslationUnit originalTranslationUnit, IASTTranslationUnit newTranslationUnit,
			ITranslationUnit workingcopy, ICElement coreElement) throws CoreException;

	void addedElement(IASTTranslationUnit originalTranslationUnit, IASTTranslationUnit newTranslationUnit,
			ITranslationUnit workingcopy, ICElement coreElement) throws CoreException;

	void removedElement(ITranslationUnit originalUnit, IWorkingCopy workingUnit, ICElement element)
			throws CoreException;

	void addedElement(ITranslationUnit originalUnit, IWorkingCopy workingUnit, ICElement element)
			throws CoreException;

	void removeModelChangeListener(ICModelChangeListener modelChangeListener);

	void addModelChangeListener(ICModelChangeListener modelChangeListener);

	List<ICModelChangeListener> getModelChangeListeners();

	void addAllModelChangeListeners(List<ICModelChangeListener> listeners);

	void notifyListeners(CModelChangedEvent event, boolean needSave);

}
