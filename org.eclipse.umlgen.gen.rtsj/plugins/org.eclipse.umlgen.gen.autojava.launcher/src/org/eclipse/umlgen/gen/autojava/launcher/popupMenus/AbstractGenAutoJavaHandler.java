/*******************************************************************************
 * Copyright (c) 2008, 2015 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.launcher.popupMenus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.umlgen.gen.autojava.launcher.Activator;
import org.eclipse.umlgen.gen.autojava.main.Uml2autojava;

/**
 * UML to Autojava code generation handler.
 */
public abstract class AbstractGenAutoJavaHandler extends AbstractHandler {

    /**
     * Selected model files.
     */
    protected List<IFile> files;

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection instanceof IStructuredSelection) {
            files = ((IStructuredSelection)selection).toList();
        }
        if (files != null) {
            IRunnableWithProgress operation = new GenRunner();
            try {
                PlatformUI.getWorkbench().getProgressService().run(true, true, operation);
            } catch (InvocationTargetException e) {
                IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
                Activator.getDefault().getLog().log(status);
            } catch (InterruptedException e) {
                IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
                Activator.getDefault().getLog().log(status);
            }
        }
        return null;
    }

    /**
     * Computes the arguments of the generator.
     *
     * @return the arguments
     * @generated
     */
    protected List<? extends Object> getArguments() {
        return new ArrayList<String>();
    }

    /**
     * This launches the given generator.
     *
     * @param monitor
     *            A progress monitor.
     * @param generator
     *            The generator to launch.
     */
    private void generate(IProgressMonitor monitor, AbstractAcceleoGenerator generator) {
        if (!generator.getTargetFolder().exists()) {
            generator.getTargetFolder().mkdirs();
        }
        monitor.subTask("Loading...");
        monitor.worked(1);
        String generationID = org.eclipse.acceleo.engine.utils.AcceleoLaunchingUtil.computeUIProjectID(
                "org.eclipse.umlgen.gen.autojava", getModuleQualifiedName(), generator.getModel().eResource()
                        .getURI().toString(), generator.getTargetFolder().getAbsolutePath(),
                new ArrayList<String>());
        generator.setGenerationID(generationID);
        try {
            generator.doGenerate(BasicMonitor.toMonitor(monitor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This returns an eclipse file addressed by an absolute path.
     *
     * @param model
     *            A model eclipse file.
     * @param workspaceRelativePath
     *            The path to convert to an absolute one.
     * @return The file in absolute.
     */
    private File getFileFromPath(IFile model, String workspaceRelativePath) {
        Path tempPath = new Path(workspaceRelativePath);
        String path;
        if (tempPath.isAbsolute()) {
            path = workspaceRelativePath;
        } else {
            path = model.getWorkspace().getRoot().getLocation().append(new Path(workspaceRelativePath))
                    .toString();
        }
        return new File(path);
    }

    /**
     * This creates and returns the generator to use.
     *
     * @param modelURI
     *            The input model URI.
     * @param sDecorators
     *            The potential list of model decorator paths (separated by ";").
     * @param target
     *            The target location of the generation.
     * @return The generator.
     * @throws IOException
     *             exception.
     */
    protected abstract Uml2autojava getGenerator(URI modelURI, String sDecorators, File target)
            throws IOException;

    /**
     * This returns the qualified name of the Java class corresponding to the generator to use.<br>
     * In the most cases, this will be the qualified name of the returned type of {@link
     * this#getGenerator(URI, String, File)}
     *
     * @return The qualified name of the generator to use.
     */
    protected abstract String getModuleQualifiedName();

    /**
     * The runner of the Autoava generation.
     */
    private class GenRunner implements IRunnableWithProgress {

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        public void run(IProgressMonitor monitor) {
            try {
                Iterator<IFile> filesIt = files.iterator();
                while (filesIt.hasNext()) {
                    IFile model = (IFile)filesIt.next();
                    URI modelURI = URI.createPlatformResourceURI(model.getFullPath().toString(), true);
                    try {

                        String sTarget = model.getPersistentProperty(new QualifiedName("",
                                "OUTPUT_PATH_AUTOJAVA_COMP"));
                        String sDecorators = model.getPersistentProperty(new QualifiedName("",
                                "COMMUNICATION_PARAMETERING"));

                        File target = getFileFromPath(model, sTarget);

                        Uml2autojava generator = getGenerator(modelURI, sDecorators, target);

                        generate(monitor, generator);

                    } catch (IOException e) {
                        IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
                        Activator.getDefault().getLog().log(status);
                    } finally {
                        model.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
                    }
                }
            } catch (CoreException e) {
                IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
                Activator.getDefault().getLog().log(status);
            }
        }
    }

}
