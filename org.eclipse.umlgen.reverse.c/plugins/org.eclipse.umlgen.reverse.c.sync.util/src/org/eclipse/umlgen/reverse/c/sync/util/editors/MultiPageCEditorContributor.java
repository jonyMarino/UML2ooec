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
package org.eclipse.umlgen.reverse.c.sync.util.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors. Responsible for the
 * redirection of global actions to the active editor. Multi-page contributor replaces the contributors for
 * the individual editors in the multi-page editor.
 */
public class MultiPageCEditorContributor extends MultiPageEditorActionBarContributor {

    /** The active editor part. */
    private IEditorPart activeEditorPart;

    /** The sample action. */
    private Action sampleAction;

    /**
     * Creates a multi-page contributor.
     */
    public MultiPageCEditorContributor() {
        super();
        createActions();
    }

    /**
     * Returns the action registed with the given text editor.
     *
     * @param editor
     *            the editor.
     * @param actionID
     *            the action ID
     * @return IAction or null if editor is null.
     */
    protected IAction getAction(ITextEditor editor, String actionID) {
        // CHECKSTYLE:OFF
        return editor == null ? null : editor.getAction(actionID);
        // CHECKSTYLE:ON
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage(org.eclipse.ui.IEditorPart)
     */
    @Override
    public void setActivePage(IEditorPart part) {
        if (activeEditorPart == part) {
            return;
        }

        activeEditorPart = part;

        IActionBars actionBars = getActionBars();
        if (actionBars != null) {

            // CHECKSTYLE:OFF
            ITextEditor editor = part instanceof ITextEditor ? (ITextEditor)part : null;
            // CHECKSTYLE:ON

            actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), getAction(editor,
                    ITextEditorActionConstants.DELETE));
            actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), getAction(editor,
                    ITextEditorActionConstants.UNDO));
            actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), getAction(editor,
                    ITextEditorActionConstants.REDO));
            actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), getAction(editor,
                    ITextEditorActionConstants.CUT));
            actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), getAction(editor,
                    ITextEditorActionConstants.COPY));
            actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), getAction(editor,
                    ITextEditorActionConstants.PASTE));
            actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), getAction(editor,
                    ITextEditorActionConstants.SELECT_ALL));
            actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(), getAction(editor,
                    ITextEditorActionConstants.FIND));
            actionBars.setGlobalActionHandler(IDEActionFactory.BOOKMARK.getId(), getAction(editor,
                    IDEActionFactory.BOOKMARK.getId()));
            actionBars.updateActionBars();
        }
    }

    /**
     * Create actions.
     */
    private void createActions() {
        sampleAction = new Action() {
            @Override
            public void run() {
                MessageDialog.openInformation(null, "Util", "Sample Action Executed");
            }
        };
        sampleAction.setText("Sample Action");
        sampleAction.setToolTipText("Sample Action tool tip");
        sampleAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
                IDE.SharedImages.IMG_OBJS_TASK_TSK));
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void contributeToMenu(IMenuManager manager) {
        IMenuManager menu = new MenuManager("Editor &Menu");
        manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
        menu.add(sampleAction);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
     */
    @Override
    public void contributeToToolBar(IToolBarManager manager) {
        manager.add(new Separator());
        manager.add(sampleAction);
    }
}