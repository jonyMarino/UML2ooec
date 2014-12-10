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

/** A view. */
public class CASTView extends ViewPart implements IPartListener {

    /** The viewer. */
    private TreeViewer treeViewer;

    /** The current edit part. */
    private IEditorPart currentEditorPart;

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(Composite parent) {
        treeViewer = new TreeViewer(parent, SWT.NONE);
        treeViewer.setContentProvider(new CASTContentProvider());
        treeViewer.setLabelProvider(new CASTLabelProvider());
        setTreeViewerInput();

        getSite().getPage().addPartListener(this);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        getSite().getPage().removePartListener(this);
    }

    /**
     * Set the tree viewer input.
     */
    private void setTreeViewerInput() {
        IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .getActiveEditor();
        if (activeEditor != null) {
            currentEditorPart = activeEditor;
            Object input = getInput(currentEditorPart);
            if (input == null) {
                treeViewer.setInput("Pas d'AST disponible");
            } else {
                currentEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                        .getActiveEditor();
                treeViewer.setInput(input);
            }
        } else {
            treeViewer.setInput(null);
        }
    }

    /**
     * Get the input from the given editor.
     *
     * @param editor
     *            The editor.
     * @return The input.
     */
    private Object getInput(IEditorPart editor) {
        Object input = null;
        ITranslationUnit tu = getTranslationUnit(editor);

        if (tu != null) {
            try {
                input = tu.getAST();
            } catch (CoreException e) {
                ErrorDialog.openError(getSite().getShell(), "Error creating C AST View", null, e.getStatus());
            }
        }
        return input;
    }

    /**
     * Get the translation unit.
     * 
     * @param editor
     *            The editor.
     * @return the unit.
     */
    private ITranslationUnit getTranslationUnit(IEditorPart editor) {
        IFile file = (IFile)editor.getEditorInput().getAdapter(IFile.class);
        return CoreModelUtil.findTranslationUnit(file);
    }

    @Override
    public void setFocus() {
        treeViewer.getControl().setFocus();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
     */
    public void partActivated(IWorkbenchPart part) {
        if (part instanceof EditorPart) {
            if (currentEditorPart == null || part != currentEditorPart) {
                setTreeViewerInput();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
     */
    public void partBroughtToTop(IWorkbenchPart part) {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
     */
    public void partClosed(IWorkbenchPart part) {
        if (currentEditorPart != null && part == currentEditorPart) {
            treeViewer.setInput(null);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
     */
    public void partDeactivated(IWorkbenchPart part) {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
     */
    public void partOpened(IWorkbenchPart part) {
    }

}
