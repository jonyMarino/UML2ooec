/*******************************************************************************
 * Copyright (c) 2010, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *      Sebastien GABEL (CS) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.builder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.gen.c.builder.internal.UML2CBuilderBundle;
import org.eclipse.umlgen.gen.c.files.Generate;
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;
import org.eclipse.umlgen.reverse.c.resource.C2UMLSyncNature;

/**
 * Builder for the generation UML to C.
 */
@Deprecated
public class UML2CBuilder extends IncrementalProjectBuilder {

    /** The id of the builder. */
    public static final String BUILDER_ID = "org.eclipse.umlgen.gen.c.builder";

    /** The ticks number for the progress monitor. */
    private static final int TICKS = 50;

    /** A model manager. */
    private ModelManager manager;

    /** A resource visitor to generates again after a change. */
    private final class BuilderDeltaVisitor implements IResourceDeltaVisitor {

        /** A progress monitor. */
        private final IProgressMonitor monitor;

        /**
         * The constructor.
         *
         * @param monitor
         *            a progress monitor
         */
        private BuilderDeltaVisitor(IProgressMonitor monitor) {
            this.monitor = monitor;
        }

        /**
         * {@inheritDoc} <br>
         * TODO : temporary deactivate the element change listener.
         *
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         */
        public boolean visit(IResourceDelta delta) throws CoreException {
            if (delta.getKind() == IResourceDelta.CHANGED) {
                IResource resource = delta.getResource();
                IProject project = resource.getProject();
                if ("uml".equalsIgnoreCase(resource.getFileExtension()) && project != null
                        && C2UMLSyncNature.isC2UMLSynchProject(project)) {
                    // the change is in an uml file in a C2UML project
                    ModelManager modelManager = getModelManager(resource);
                    if (URI.createPlatformResourceURI(resource.getFullPath().toString(), true).equals(
                            modelManager.getModelResource().getURI())) {
                        // the uml file is the uml file we keep in synch
                        Resource umlResource = modelManager.getModelResource();
                        try {
                            // CoreModel.getDefault().removeElementChangedListener(Activator.getDefault().getElementChangedListener());
                            doBuild(resource, umlResource, monitor);
                        } catch (IOException e) {
                            UML2CBuilderBundle.log(e);
                        } finally {
                            // CoreModel.getDefault().addElementChangedListener(Activator.getDefault().getElementChangedListener());
                        }
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * This launches a generation from the given UML resource.
     *
     * @param resource
     *            An eclipse resource in the project containing the generated code.
     * @param umlResource
     *            The input UML resource.
     * @throws IOException
     *             exception.
     */
    private void doBuild(IResource resource, Resource umlResource) throws IOException {
        doBuild(resource, umlResource, new NullProgressMonitor());
    }

    /**
     * This launches a generation from the given UML resource.
     *
     * @param resource
     *            An eclipse resource in the project containing the generated code.
     * @param umlResource
     *            The input UML resource.
     * @param monitor
     *            A progress monitor.
     * @throws IOException
     *             exception.
     */
    private void doBuild(IResource resource, Resource umlResource, IProgressMonitor monitor)
            throws IOException {
        File rootFolder = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
        Generate gen = new Generate(umlResource.getContents().get(0), rootFolder, Collections.emptyList());
        gen.doGenerate(new BasicMonitor.EclipseSubProgress(monitor, TICKS));
        try {
            resource.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new BasicMonitor.EclipseSubProgress(monitor, TICKS));
        } catch (CoreException e) {
            // it's ok to silently give up this exception
        } finally {
            manager.dispose();
            manager = null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map,
     *      org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected IProject[] build(int kind, Map args, final IProgressMonitor monitor) throws CoreException {
        if (kind == INCREMENTAL_BUILD || kind == AUTO_BUILD) {
            IResourceDeltaVisitor visitor = new BuilderDeltaVisitor(monitor);
            IResourceDelta delta = getDelta(getProject());
            if (delta != null) {
                delta.accept(visitor);
            }
        }

        return null; // it's ok to return null
    }

    /**
     * This launches a generation from the given UML resource.
     *
     * @param modelFile
     *            The UML resource.
     */
    public void build(IResource modelFile) {
        ModelManager modelManager = getModelManager(modelFile);
        Resource umlResource = modelManager.getModelResource();
        try {
            doBuild(modelFile, umlResource);
        } catch (IOException e) {
            UML2CBuilderBundle.log(e);
        }
    }

    /**
     * This gets the model manager related to the project where the given change event has been thrown.
     *
     * @param event
     *            The change event.
     * @return The model manager.
     */
    protected ModelManager getModelManager(AbstractCModelChangedEvent event) {
        if (manager == null
                || !manager.getProject().equals(event.getTranslationUnit().getResource().getProject())) {
            manager = new ModelManager(event.getTranslationUnit().getResource());
        }
        return manager;
    }

    /**
     * This gets the model manager related to the project where the given resource has been modified.
     *
     * @param rsc
     *            The modified resource.
     * @return the model manager.
     */
    protected ModelManager getModelManager(IResource rsc) {
        if (manager == null || !manager.getProject().equals(rsc.getProject())) {
            manager = new ModelManager(rsc);
        }
        return manager;
    }

    /**
     * This gets the current model manager.
     *
     * @return The model manager.
     */
    protected ModelManager getModelManager() {
        return manager;
    }
}
