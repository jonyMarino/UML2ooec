/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.sync.util.views;

import org.eclipse.cdt.core.model.CoreModelUtil;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.umlgen.reverse.c.sync.util.providers.CASTContentProvider;
import org.eclipse.umlgen.reverse.c.sync.util.providers.CASTLabelProvider;

public class CASTView extends ViewPart implements IPartListener {

	private TreeViewer treeViewer;
	private IEditorPart currentEditorPart = null;

	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.NONE);
		treeViewer.setContentProvider(new CASTContentProvider());
		treeViewer.setLabelProvider(new CASTLabelProvider());
		setTreeViewerInput();

		getSite().getPage().addPartListener(this);
	}

	public void dispose() {
		super.dispose();
		getSite().getPage().removePartListener(this);
	}

	private void setTreeViewerInput() {
		IEditorPart activeEditor = currentEditorPart = PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (activeEditor != null) {
			currentEditorPart = activeEditor;
			Object input = getInput(currentEditorPart);
			if (input == null) {
				treeViewer.setInput("Pas d'AST disponible");
			} else {
				currentEditorPart = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				treeViewer.setInput(input);
			}
		} else {
			treeViewer.setInput(null);
		}
	}

	private Object getInput(IEditorPart editor) {
		Object input = null;
		ITranslationUnit tu = getTranslationUnit(editor);

		if (tu != null) {
			try {
				input = tu.getAST();
			} catch (CoreException e) {
				ErrorDialog.openError(getSite().getShell(),
						"Error creating C AST View", null, e.getStatus());
			}
		}
		return input;
	}

	private ITranslationUnit getTranslationUnit(IEditorPart editor) {
		IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
		return CoreModelUtil.findTranslationUnit(file);
	}

	public void setFocus() {
		treeViewer.getControl().setFocus();
	}

	public void partActivated(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			if (currentEditorPart == null || part != currentEditorPart) {
				setTreeViewerInput();
			}
		}
	}

	public void partBroughtToTop(IWorkbenchPart part) {
	}

	public void partClosed(IWorkbenchPart part) {
		if (currentEditorPart != null && part == currentEditorPart) {
			treeViewer.setInput(null);
		}
	}

	public void partDeactivated(IWorkbenchPart part) {
	}

	public void partOpened(IWorkbenchPart part) {
	}

}
