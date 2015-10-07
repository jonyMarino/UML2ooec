/*******************************************************************************
 * Copyright (c) 2011, 2014, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephane Begaudeau (Obeo) - initial API and implementation
 *     Johan Hardy (Spacebel) - adapted for Embedded C generator
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.ui.launch;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.umlgen.gen.embedded.c.main.Uml2ec;
import org.eclipse.umlgen.gen.embedded.c.services.ConfigurationHolder;
import org.eclipse.umlgen.gen.embedded.c.ui.UML2ECUIActivator;
import org.eclipse.umlgen.gen.embedded.c.ui.common.ConfigurationServices;
import org.eclipse.umlgen.gen.embedded.c.utils.IUML2ECConstants;

/**
 * The UML to Embedded C launch configuration launcher.
 */
public class UML2ECLaunchDelegate implements ILaunchConfigurationDelegate {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
     *      java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
     */
    public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
            IProgressMonitor monitor) throws CoreException {
        String umlModelPath = "";
        Path outputFolderPath = null;
        try {
            umlModelPath = configuration.getAttribute(IUML2ECConstants.UML_MODEL_PATH, "");
            IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(configuration
                    .getAttribute(IUML2ECConstants.OUTPUT_FOLDER_PATH, "")));
            outputFolderPath = new Path(folder.getRawLocation().toString());
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }

        if (umlModelPath == null || umlModelPath.length() == 0) {
            return;
        }

        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(umlModelPath));
        IContainer container = ResourcesPlugin.getWorkspace().getRoot();
        if (file != null && container != null && file.isAccessible() && container.isAccessible()) {
            URI modelURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
            ConfigurationHolder configurationHolder = ConfigurationServices
                    .createConfigurationHolder(configuration);
            try {
                Uml2ec generator = new Uml2ec(modelURI, outputFolderPath.toFile(), new ArrayList<String>());
                generator.setConfigurationHolder(configurationHolder);
                generator.doGenerate(BasicMonitor.toMonitor(monitor));
                container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
            } catch (IOException e) {
                IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
                UML2ECUIActivator.getDefault().getLog().log(status);
            }
        }
    }
}
