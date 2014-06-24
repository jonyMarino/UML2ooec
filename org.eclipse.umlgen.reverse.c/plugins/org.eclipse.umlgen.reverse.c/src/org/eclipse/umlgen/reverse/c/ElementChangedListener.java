/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Christophe Le Camus (CS) - initial API and implementation
 *      Sebastien GABEL (CS) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c;

import org.eclipse.cdt.core.model.ElementChangedEvent;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementDelta;
import org.eclipse.cdt.core.model.ICModel;
import org.eclipse.cdt.core.model.IElementChangedListener;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.IWorkingCopy;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;
import org.eclipse.umlgen.reverse.c.event.CUnitRemoved;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.reconciler.CASTReconciler;
import org.eclipse.umlgen.reverse.c.resource.C2UMLSyncNature;

public class ElementChangedListener implements IElementChangedListener {

	private CASTReconciler cASTReconciler;

	public synchronized void setASTReconciler(CASTReconciler reconciler) {
		cASTReconciler = reconciler;
	}

	/**
	 * @see org.eclipse.cdt.core.model.IElementChangedListener#elementChanged(org.eclipse.cdt.core.model.ElementChangedEvent)
	 */
	public void elementChanged(final ElementChangedEvent event) {
		final ICElementDelta delta = event.getDelta();
		final ICElement element = delta.getElement();
		IWorkspaceRoot wsRoot = element.getResource().getWorkspace().getRoot();
		final int elementType = element.getElementType();

		// element received must be instance of ITranslationUnit and must belong
		// to the workspace.
		if ((element instanceof ITranslationUnit || element instanceof ICModel)
				&& wsRoot.findMember(element.getPath()) != null) {
			Job job = new WorkspaceJob("Synchronizing C code and UML Model from : ".concat(element
					.getElementName())) {

				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					switch (elementType) {
						case ICElement.C_UNIT:
							if (C2UMLSyncNature.isC2UMLSynchProject(element.getCProject())) {
								if (element instanceof IWorkingCopy
										&& ((IWorkingCopy)element).hasUnsavedChanges()) {
									workingCopyChanged(delta);
								}
							}
							break;
						case ICElement.C_MODEL:
							cModelChanged(delta);
							break;
					}
					return Status.OK_STATUS;
				}
			};
			job.schedule();
		}
	}

	private void cModelChanged(ICElementDelta delta) {
		ICElementDelta[] children = delta.getAffectedChildren();
		ICElement element = delta.getElement();
		int elementType = element.getElementType();

		if (elementType == ICElement.C_UNIT && C2UMLSyncNature.isC2UMLSynchProject(element.getCProject())) {
			if (!(element instanceof IWorkingCopy)) {
				switch (delta.getKind()) {
					case ICElementDelta.ADDED:
						cUnitAdded(delta, element);
						break;
					case ICElementDelta.REMOVED:
						cUnitRemoved(element);
						break;
					case ICElementDelta.CHANGED:
						cUnitChanged(delta, element);
						break;
				}
			}
		} else {
			for (ICElementDelta element2 : children) {
				cModelChanged(element2);
			}
		}
	}

	private void cUnitChanged(ICElementDelta delta, ICElement element) {
		int changeFlags = delta.getFlags();
		if ((changeFlags & ICElementDelta.F_CONTENT) != 0) {
		}
	}

	private void cUnitRemoved(ICElement element) {
		CModelChangedEvent event;
		event = CUnitRemoved.builder().translationUnit((ITranslationUnit)element).currentName(
				((ITranslationUnit)element).getElementName()).build();
		cASTReconciler.notifyListeners(event, true);
	}

	private void cUnitAdded(ICElementDelta delta, ICElement element) {

		StructuralBuilder builder = new StructuralBuilder(((ITranslationUnit)element).getResource());
		try {
			builder.build((ITranslationUnit)element);
		} catch (Exception e) {
			Activator.log(e);
		} finally {
			builder.dispose();
		}

		final ICElementDelta[] affectedChildren = delta.getAffectedChildren();
		for (ICElementDelta element2 : affectedChildren) {
			cModelChanged(element2);
		}
	}

	private void workingCopyChanged(ICElementDelta delta) throws CoreException {
		IWorkingCopy workingUnit = (IWorkingCopy)delta.getElement();
		ITranslationUnit originalUnit = workingUnit.getOriginalElement();
		boolean reconcile = false;

		ICElementDelta[] children = delta.getAffectedChildren();
		ICElementDelta[] addedChildren = delta.getAddedChildren();
		ICElementDelta[] removedChildrenInVrac = delta.getRemovedChildren();
		ICElementDelta[] removedChildren = new ICElementDelta[removedChildrenInVrac.length];

		int j = 0;
		// insert the Includes in first position so client and supplier are not
		// removed first
		for (ICElementDelta element : removedChildrenInVrac) {
			if (element.getElement().getElementType() != ICElement.C_ENUMERATION
					&& element.getElement().getElementType() != ICElement.C_STRUCT
					&& element.getElement().getElementType() != ICElement.C_TYPEDEF
					&& element.getElement().getElementType() != ICElement.C_INCLUDE) {
				removedChildren[j] = element;
				j = j + 1;
			}
		}
		// continue to fill the removedChildren from the j position
		for (ICElementDelta element : removedChildrenInVrac) {
			if (element.getElement().getElementType() == ICElement.C_ENUMERATION
					|| element.getElement().getElementType() == ICElement.C_STRUCT
					|| element.getElement().getElementType() == ICElement.C_TYPEDEF
					|| element.getElement().getElementType() == ICElement.C_INCLUDE) {
				removedChildren[j] = element;
				j = j + 1;
			}
		}

		if (children.length > 0 && children.length == 2 * addedChildren.length
				&& addedChildren.length == removedChildren.length) {
			cASTReconciler.reconcile(originalUnit, workingUnit);
			reconcile = true;
		} else if (children.length == 0) { // WORKING_UNIT [?]: {FINE GRAINED}
			if (workingUnit.getBuffer().hasUnsavedChanges() || originalUnit.getBuffer().hasUnsavedChanges()) {
				cASTReconciler.reconcile(originalUnit, workingUnit);
				reconcile = true;
			} else {
				Activator
						.log("ElementChangedListener.workingCopyChanged() - neither the IWorkingUnit nor the ITranslationUnit has unsaved changes",
								IStatus.INFO);
			}
		} else {
			int removedOrder = 0;
			for (ICElementDelta child : children) {
				ICElement childElement = child.getElement();

				Activator.log("Traitrement de : ".concat(childElement.getElementName()), IStatus.INFO);
				if (child.getKind() == ICElementDelta.ADDED) {
					elementAdded(workingUnit, originalUnit, childElement);
				} else if (child.getKind() == ICElementDelta.REMOVED) {
					// we know that all the removed children will be treated
					elementRemoved(workingUnit, originalUnit, removedChildren[removedOrder].getElement());
					removedOrder = removedOrder + 1;
				}
			}

			if (!reconcile) {
				cASTReconciler.reconcile(originalUnit, workingUnit);
			}
		}
		workingUnit.getBuffer().save(new NullProgressMonitor(), true);
	}

	private void elementRemoved(IWorkingCopy workingUnit, ITranslationUnit originalUnit, ICElement element)
			throws CoreException {
		if (element.getElementType() == ICElement.C_INCLUDE || element.getElementType() == ICElement.C_MACRO) {
			cASTReconciler.removedElement(originalUnit, workingUnit, element);
		} else {
			cASTReconciler.removedElement(originalUnit.getAST(), workingUnit.getAST(), workingUnit
					.getOriginalElement(), element);
		}
	}

	private void elementAdded(IWorkingCopy workingUnit, ITranslationUnit originalUnit, ICElement element)
			throws CoreException {
		if (element.getElementType() == ICElement.C_INCLUDE || element.getElementType() == ICElement.C_MACRO) {
			cASTReconciler.addedElement(originalUnit, workingUnit, element);
		} else {
			cASTReconciler.addedElement(originalUnit.getAST(), workingUnit.getAST(), workingUnit
					.getOriginalElement(), element);
		}
	}

}
