/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mikael Barbero (Obeo) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - Bug fix on deconfigure project
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.resource;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.internal.bundle.Messages;

/**
 * Configures and deconfigures the synchronized C project in adding/removing the dedicated
 * <b>org.eclipse.umlgen.reverse.c.builder</b> builder to the current project nature.
 *
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mikael BARBERO</a>
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class C2UMLSyncNature implements IProjectNature {

    /** The project for which nature are added/removed. */
    private IProject project;

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return project;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject value) {
        project = value;
    }

    /**
     * Opens a selection dialog to choose an existing UML model to reverse. Before returning the resource, the
     * selection is checked. If the selected resource is not an UML model then null is returned.
     *
     * @param project
     *            The eclipse project.
     * @return The selected file chosen by the user.
     */
    public static IFile selectExistingUMLModel(IProject project) {
        ResourceSelectionDialog dialog = new ResourceSelectionDialog(Display.getCurrent().getActiveShell(),
                project, Messages.getString("C2UMLSyncNature.select.resource")) //$NON-NLS-1$
        {
            @Override
            public void checkStateChanged(CheckStateChangedEvent event) {
                event.getElement();
                super.checkStateChanged(event);
            }
        };
        int result = dialog.open();
        if (result == ResourceSelectionDialog.OK) {
            Object[] results = dialog.getResult();
            if (results.length > 0) {
                // take only the first value
                Object firstObj = results[0];
                if (firstObj instanceof IFile
                        && BundleConstants.UML_EXTENSION.equals(((IFile)firstObj).getFileExtension())) {
                    return (IFile)firstObj;
                }
            }
        }
        return null;
    }

    /**
     * This checks if the given CDT project is a "C2UML synchronized" project.
     *
     * @param cProject
     *            The CDT project.
     * @return True if yes.
     */
    public static boolean isC2UMLSynchProject(ICProject cProject) {
        return isC2UMLSynchProject(cProject.getProject());
    }

    /**
     * This checks if the given eclipse project is a "C2UML synchronized" project.
     *
     * @param project
     *            The eclipse project.
     * @return True if yes.
     */
    public static boolean isC2UMLSynchProject(IProject project) {
        return ProjectUtil.hasNature(project, BundleConstants.NATURE_ID);
    }
}
