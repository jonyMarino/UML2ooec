/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Philippe Roland (Atos) - initialize API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.java.diagram;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageManager;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.wizards.category.NewPapyrusModelCommand;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.umlgen.reverse.java.AbstractJava2UMLConverter;
import org.eclipse.umlgen.reverse.java.AbstractJava2UMLConverter.ActivityGeneration;
import org.eclipse.umlgen.reverse.java.diagram.diagram.ActivityDiagramUtils;
import org.eclipse.umlgen.reverse.java.diagram.diagram.ClassDiagramUtils;
import org.eclipse.umlgen.reverse.java.internal.ReversePlugin;
import org.eclipse.umlgen.reverse.java.internal.wizards.Java2UMLWizard;
import org.eclipse.umlgen.reverse.java.logging.LogUtils;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the provided container. If the
 * container resource (a folder or a project) is selected in the workspace when the wizard is opened, it will
 * accept it as the target container.
 */
public class PrototypeJava2UMLWizard extends Java2UMLWizard {

    /** ModelSet. */
    private ModelSet resourceSet;

    /**
     * The worker method. It will find the container, create the file if missing or just replace its contents,
     * and open the editor on the newly created file.
     *
     * @param containerName
     * @param fileName
     * @param importList
     * @param monitor
     * @param modelName
     * @param visibility
     *            : The max visibiltiy to import.
     * @param activityGen
     * @throws CoreException
     */
    @Override
    protected void doFinish(String containerName, String fileName, String[] importList,
            org.eclipse.core.runtime.IProgressMonitor monitor, String modelName, VisibilityKind visibility,
            ActivityGeneration activityGen) throws org.eclipse.core.runtime.CoreException {
        // create a sample file
        monitor.beginTask("Creating " + fileName, 2);
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IResource resource = root.findMember(new Path(containerName));
        if (!resource.exists() || !(resource instanceof IContainer)) {
            throwCoreException("Container \"" + containerName + "\" does not exist.");
        }
        IContainer container = (IContainer)resource;
        final IFile file = container.getFile(new Path(fileName));

        if (javaElement == null) {
            throwCoreException("No java element selected.");
        }
        resourceSet = new ModelSet();
        // Get the URI of the model file.
        URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), false);
        // Create a resource for this file.
        Resource emfResource = resourceSet.createResource(fileURI);
        // Get the URI of the temporary model file.
        URI tmpFileURI = URI.createPlatformResourceURI(file.getFullPath().toString() + "_mergetemp_", false);
        // Create a resource for this file.
        Resource tmpEmfResource = resourceSet.createResource(tmpFileURI);
        // Load the actual content
        if (file.exists()) {
            try {
                emfResource.load(new HashMap<Object, Object>());
            } catch (IOException e) {
                IStatus status = new Status(IStatus.ERROR, ReversePlugin.getId(), IStatus.OK,
                        "An error occured during loading resource", e);
                throw new CoreException(status);
            }
        }
        Model oldModel = null;
        EList<EObject> contentsList = emfResource.getContents();
        for (final EObject content : contentsList) {
            if (content instanceof Model) {
                oldModel = (Model)content;
            }
        }
        if (oldModel != null) {
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    ErrorDialog errorDialog = new ErrorDialog(getShell(), "Warning", "Warning message",
                            new Status(IStatus.ERROR, ReversePlugin.getId(),
                                    "A model already exists, this one will be deleted"), IStatus.ERROR);
                    errorDialog.open();
                }
            });
            emfResource.getContents().clear();
        }
        AbstractJava2UMLConverter converter = new PrototypeJava2UMLConverter();
        converter.setImportList(importList);
        converter.setModelName(modelName);
        // TSL: limit the visibility
        converter.setVisibility(visibility);
        Package model = converter.convert(javaElement, emfResource, activityGen);

        // Save the contents of the resource to the file system
        Map<?, ?> options = new HashMap<Object, Object>();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        try {
            LogUtils.logMessage("Saving .uml resource: "
                    + dateFormat.format(Calendar.getInstance().getTime()));
            emfResource.save(options);
            LogUtils.logMessage("Finished saving .uml resource: "
                    + dateFormat.format(Calendar.getInstance().getTime()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            IStatus status = new Status(IStatus.ERROR, ReversePlugin.getId(), IStatus.OK,
                    "An error occured while saving resource", ioe);
            throw new CoreException(status);
        }
        // Arrange all model elements
        try {
            // Initialize registry and modelset
            final ServicesRegistry registry = createServicesRegistry();
            if (registry == null) {
                return;
            }
            // have to create the model set and populate it with the DI model
            // before initializing other services that actually need the DI
            // model, such as the SashModel Manager service
            final ModelSet modelSet = registry.getService(ModelSet.class);

            for (final EObject contents : emfResource.getContents()) {
                if (contents instanceof Model) {
                    RecordingCommand command = new NewPapyrusModelCommand(modelSet, fileURI);
                    modelSet.getTransactionalEditingDomain().getCommandStack().execute(command);

                    ICommand papModelsCommand = new AbstractTransactionalCommand(modelSet
                            .getTransactionalEditingDomain(), "Create Diagram and save resources", null) {
                        @Override
                        protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {
                            NotationUtils.getNotationModel(modelSet).getResource();
                            SashModelUtils.getSashModel(modelSet).getResource();
                            try {
                                registry.startRegistry();
                                registry.getService(IPageManager.class);
                            } catch (ServiceException e) {
                                e.printStackTrace();
                                LogUtils.logThrowable(e);
                            }
                            LogUtils.logMessage("Creating class diagrams");
                            ClassDiagramUtils.INSTANCE.createDiagrams((Package)contents, modelSet, true);
                            LogUtils.logMessage("Creating activity diagrams");
                            ActivityDiagramUtils.INSTANCE.createDiagrams((Package)contents, modelSet, false);
                            try {
                                LogUtils.logMessage("Saving .notation resource");
                                NotationUtils.getNotationModel(modelSet).getResource().save(null);
                                LogUtils.logMessage("Finished saving .notation resource");
                                String uri = NotationUtils.getNotationModel(modelSet).getResource().getURI()
                                        .toPlatformString(false).replace(".notation", ".di");
                                Resource res = SashModelUtils.getSashModel(modelSet).getResource();
                                res.setURI(URI.createPlatformResourceURI(uri, false));
                                LogUtils.logMessage("Saving .di resource");
                                res.save(null);
                                LogUtils.logMessage("Finished saving .di resource");
                            } catch (IOException e) {
                                e.printStackTrace();
                                LogUtils.logThrowable(e);
                            }
                            return CommandResult.newOKCommandResult();
                        }
                    };
                    modelSet.getTransactionalEditingDomain().getCommandStack().execute(
                            new GMFtoEMFCommandWrapper(papModelsCommand));
                }
            }
        } catch (ServiceException e1) {
            e1.printStackTrace();
            LogUtils.logThrowable(e1);
        }
        // Refreshing java element to ensure log file is present and up to date
        javaElement.getResource().refreshLocal(IResource.DEPTH_ONE, null);
    }

    /**
     * Code taken from Papyrus CreateModelWizard.
     *
     * @return result
     */
    protected ServicesRegistry createServicesRegistry() {
        ServicesRegistry result = null;

        try {
            result = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
        } catch (ServiceException e) {
            // couldn't create the registry? Fatal problem
        }

        try {
            // have to create the model set and populate it with the DI model
            // before initializing other services that actually need the DI
            // model, such as the SashModel Manager service
            result.startServicesByClassKeys(ModelSet.class);
        } catch (ServiceException ex) {
            // Ignore this exception: some services may not have been loaded,
            // which is probably normal at this point
        }
        return result;
    }
}
