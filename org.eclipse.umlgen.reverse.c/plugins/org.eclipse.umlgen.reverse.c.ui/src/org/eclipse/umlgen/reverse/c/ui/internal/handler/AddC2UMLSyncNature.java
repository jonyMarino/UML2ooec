/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Mikael BARBERO (Obeo) - initial API and implementation
 *     Christophe LE CAMUS (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - initialize default values to preference store value.
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.ui.PreferenceStoreManager;
import org.eclipse.umlgen.gen.c.builder.UML2CBuilder;
import org.eclipse.umlgen.gen.c.builder.UML2CBundleConstant;
import org.eclipse.umlgen.reverse.c.StructuralBuilder;
import org.eclipse.umlgen.reverse.c.resource.C2UMLSyncNature;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;
import org.eclipse.umlgen.reverse.c.ui.internal.widgets.QuestionDialog;

/**
 * Handler adding the <b>org.eclipse.umlgen.reverse.c.syncNature</b> to the current C project. During this
 * operation a default couple of UML/UMLDI files are created using the <b>Neptune for Synchronized models</b>.
 * Default configuration of the project is also done; C2UML property page is set with default values.
 *
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mikael BARBERO</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
@Deprecated
public class AddC2UMLSyncNature extends AbstractHandler {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
            IStructuredSelection selection = (IStructuredSelection)HandlerUtil
                    .getCurrentSelectionChecked(event);
            IProject project = (IProject)selection.getFirstElement();

            IPreferenceStore store = PreferenceStoreManager.getPreferenceStore(project);
            QuestionDialog dialog = new QuestionDialog(
                    Display.getCurrent().getActiveShell(),
                    Messages.getString("AddC2UMLSyncNature.dialogTitle"), Messages.getString("AddC2UMLSyncNature.dialogBody"), //$NON-NLS-1$ //$NON-NLS-2$
                    store);
            int result = dialog.open();
            if (result > -1) {
                // set initial values into the preference store related to the
                // project.
                IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
                if (synchronizer != null) {
                    synchronizer.setInitialValues(project);
                }

                if (result == 0) {
                    // a synchronization from C sources has been asked (default
                    // behavior)
                    syncFromSources(project);
                } else if (result == 1) {
                    // a synchronization from an existing UML model has been
                    // asked
                    syncFromModel(project);
                }
            }
        } catch (CoreException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        return null; // *MUST* be null (cf.
        // AbstractHandler.execute(ExecutionEvent))
    }

    /**
     * Start a synchronization from an existing UML model.
     *
     * @param project
     *            The project in which sources must be located, analyzed and reversed.
     * @throws CoreException
     *             If something failed during this operation.
     */
    private void syncFromModel(IProject project) throws CoreException {
        IFile modelFile = C2UMLSyncNature.selectExistingUMLModel(project);
        if (modelFile != null) {
            // add the nature to the .project file of the current project
            ProjectUtil.addNature(project, BundleConstants.NATURE_ID);
            ProjectUtil.addNature(project, UML2CBundleConstant.NATURE_ID);

            UML2CBuilder builder = new UML2CBuilder();
            builder.build(modelFile);
        }
    }

    /**
     * Start a synchronization from a source folder containing a set of C and H files.
     *
     * @param project
     *            The project in which sources must be located, analyzed and reversed.
     * @throws CoreException
     *             If something failed during this operation.
     */
    private void syncFromSources(IProject project) throws CoreException {
        // add the nature to the .project file of the current project
        ProjectUtil.addNature(project, BundleConstants.NATURE_ID);
        ProjectUtil.addNature(project, UML2CBundleConstant.NATURE_ID);

        IFile modelFile = null;

        // create the required UML from template
        IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
        if (synchronizer != null) {
            modelFile = synchronizer.createModel(project);
        }

        if (modelFile != null && modelFile.exists()) {
            // Temporally removing UML2C builder to avoid workspace building
            // after each reverse during the structural
            // build
            ProjectUtil.removeFromBuildSpec(project, BundleConstants.UML2C_BUILDER_ID);
            // Instantiate the builder
            StructuralBuilder sb = new StructuralBuilder(modelFile);
            sb.build();
            sb.dispose();
            ProjectUtil.addToBuildSpec(project, BundleConstants.UML2C_BUILDER_ID);
        }

    }
}
