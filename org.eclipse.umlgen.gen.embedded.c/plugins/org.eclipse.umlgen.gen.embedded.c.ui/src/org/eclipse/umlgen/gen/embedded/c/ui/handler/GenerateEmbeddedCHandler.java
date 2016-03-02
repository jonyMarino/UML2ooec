/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Mikael Barbero (Obeo) - evolutions
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *     Johan Hardy (Spacebel) - adapted for Embedded C generator
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.ui.handler;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.gen.embedded.c.main.Uml2ec;
import org.eclipse.umlgen.gen.embedded.c.services.ConfigurationHolder;
import org.eclipse.umlgen.gen.embedded.c.ui.UML2ECUIActivator;
import org.eclipse.umlgen.gen.embedded.c.ui.common.ConfigurationServices;
import org.eclipse.umlgen.gen.embedded.c.utils.IUML2ECConstants;

/**
 * This generates Embedded C code from elements of a UML model.
 */
public class GenerateEmbeddedCHandler extends AbstractHandler {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
            IStructuredSelection selection = (IStructuredSelection)HandlerUtil
                    .getCurrentSelectionChecked(event);
            IResource model = null;
            Object selectedObj = selection.getFirstElement();

            /* Check whether selection is a Resource or an EObject */
            if (selectedObj instanceof IResource) {
                model = (IResource)selectedObj;
            } else if (selectedObj instanceof EObject) {
                EClass eClass = ((EObject)selectedObj).eClass();
                if (eClass == UMLPackage.Literals.CLASS) {
                    caseClass((Class)selectedObj);
                } else if (eClass == UMLPackage.Literals.PACKAGE) {
                    casePackage((Package)selectedObj);
                } else if (eClass == UMLPackage.Literals.MODEL) {
                    caseModel((Model)selectedObj);
                } else {
                    throw new ExecutionException("Cannot start generation from " + eClass.toString());
                }
            }

            if (model != null) {
                String configName = ConfigurationServices.getConfigurationProperty(model);
                if (configName != null) {
                    ILaunchConfiguration config = ConfigurationServices.getStoredLaunchConfiguration(
                            configName);

                    if (config != null) {
                        String computedModelPath = model.getFullPath().toString();

                        String modelPath = config.getAttribute(IUML2ECConstants.UML_MODEL_PATH, "");

                        if (modelPath != null && modelPath.equals(computedModelPath)) {
                            ILaunchGroup group = ConfigurationServices.getLaunchGroup();
                            if (group != null) {
                                DebugUITools.launch(config, group.getMode());
                            }
                        } else {
                            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID,
                                    "No configuration matches with this model.");
                            UML2ECUIActivator.getDefault().getLog().log(status);
                        }
                    } else {
                        IStatus status = new Status(IStatus.INFO, UML2ECUIActivator.PLUGIN_ID,
                                "The launch configuration \"" + configName
                                        + "\" does not exist. Maybe it has been removed. You may define this in the properties of the model: \""
                                        + model.getFullPath().toString() + "\"");
                        UML2ECUIActivator.getDefault().getLog().log(status);
                    }
                } else {
                    IStatus status = new Status(IStatus.INFO, UML2ECUIActivator.PLUGIN_ID,
                            "No Java generation launch configuration has been chosen for the model: \""
                                    + model.getFullPath().toString() + "\"");
                    UML2ECUIActivator.getDefault().getLog().log(status);
                }
            }
        } catch (ExecutionException e) {
            throw e;
        } catch (CoreException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /**
     * This generates from the given model object.
     *
     * @param eObject
     *            The model object.
     * @param root
     *            The model root object.
     * @throws ExecutionException
     *             exception.
     */
    static public void doGenerate(EObject eObject, EObject root) throws ExecutionException {
        Path outputFolderPath = null;
        final IContainer container = ResourcesPlugin.getWorkspace().getRoot();

        /* Find out the model resource */
        IResource model = ResourcesPlugin.getWorkspace().getRoot().findMember(root.eResource().getURI()
                .toPlatformString(true));

        /* Retrieve corresponding launch configuration and create a configuration holder */
        String configNameToSelect = ConfigurationServices.getConfigurationProperty((IResource)model);
        ILaunchConfiguration configuration = ConfigurationServices
                .getStoredLaunchConfiguration(configNameToSelect);
        ConfigurationHolder configurationHolder = ConfigurationServices
                .createConfigurationHolder(configuration);

        try {
            /* Build the output path */
            IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(configuration
                    .getAttribute(IUML2ECConstants.OUTPUT_FOLDER_PATH, "")));
            outputFolderPath = new Path(folder.getRawLocation().toString());
        } catch (CoreException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }

        try {
            /* Perform the generation */
            final Uml2ec generator = new Uml2ec(eObject, outputFolderPath.toFile(), Collections.emptyList());
            generator.setConfigurationHolder(configurationHolder);
            Job generationJob = new Job("Generation in progress...") {
                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    try {
                        generator.doGenerate(BasicMonitor.toMonitor(monitor));
                    } catch (IOException e) {
                        IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e
                                .getMessage(), e);
                        UML2ECUIActivator.getDefault().getLog().log(status);
                    }
                    try {
                        container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                    } catch (CoreException e) {
                        /* Just silent the current exception */
                    }
                    return Status.OK_STATUS;
                }

            };
            generationJob.setPriority(Job.LONG);
            generationJob.schedule(); // start as soon as possible
        } catch (IOException e) {
            IStatus status = new Status(IStatus.ERROR, UML2ECUIActivator.PLUGIN_ID, e.getMessage(), e);
            UML2ECUIActivator.getDefault().getLog().log(status);
        }
    }

    /**
     * This generates from a given UML class.
     *
     * @param selectedObject
     *            The UML class.
     * @throws ExecutionException
     *             exception.
     */
    private void caseClass(Class selectedObject) throws ExecutionException {
        doGenerate(selectedObject, selectedObject.getModel());
    }

    /**
     * This generates from a given UML package.
     *
     * @param selectedObject
     *            The UML package.
     * @throws ExecutionException
     *             exception.
     */
    private void casePackage(Package selectedObject) throws ExecutionException {
        doGenerate(selectedObject, selectedObject.getModel());
    }

    /**
     * This generates from a given UML model.
     *
     * @param selectedObject
     *            The UML package.
     * @throws ExecutionException
     *             exception.
     */
    private void caseModel(Model selectedObject) throws ExecutionException {
        doGenerate(selectedObject, selectedObject.getModel());
    }

}
