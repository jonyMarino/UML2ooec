/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.widgets;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Dialog asking to user to specify the starting point of the C to UML synchronisation. Creation : 23 june
 * 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
@Deprecated
public class QuestionDialog extends MessageDialog {

    /** The preference store. */
    private IPreferenceStore store;

    /**
     * Constructor.
     *
     * @param parentShell
     *            The parent shell
     * @param dialogTitle
     *            The dialog title
     * @param message
     *            The body of this dialog
     * @param pref
     *            The preference store on which values will be stored
     */
    public QuestionDialog(Shell parentShell, String dialogTitle, String message, IPreferenceStore pref) {
        super(parentShell, dialogTitle, null, message, MessageDialog.QUESTION, new String[] {
                Messages.getString("QuestionDialog.source"), Messages.getString("QuestionDialog.model"), }, 0); //$NON-NLS-1$ //$NON-NLS-2$
        store = pref;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.jface.dialogs.MessageDialog#buttonPressed(int)
     */
    @Override
    protected void buttonPressed(int buttonId) {
        if (store != null) {
            // set synchronization mode default value
            store.setDefault(BundleConstants.SYNC_AT_STARTING, BundleConstants.SYNC_SOURCE_VALUE);
            // O = from sources ; 1 = from UML model
            // set the value chosen by the user
            // CHECKSTYLE:OFF
            store.setValue(BundleConstants.SYNC_AT_STARTING,
                    buttonId == Window.OK ? BundleConstants.SYNC_SOURCE_VALUE
                            : BundleConstants.SYNC_MODEL_VALUE);
            // CHECKSTYLE:ON
        }

        super.buttonPressed(buttonId);
    }

}
