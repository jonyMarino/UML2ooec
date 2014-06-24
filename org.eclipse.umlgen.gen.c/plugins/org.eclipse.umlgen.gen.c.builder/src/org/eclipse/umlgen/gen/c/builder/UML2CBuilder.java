/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
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
import org.eclipse.umlgen.reverse.c.event.CModelChangedEvent;
import org.eclipse.umlgen.reverse.c.resource.C2UMLSyncNature;

public class UML2CBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.eclipse.umlgen.gen.c.builder";

	private ModelManager manager;

	private class BuilderDeltaVisitor implements IResourceDeltaVisitor {
		private final IProgressMonitor monitor;

		private BuilderDeltaVisitor(IProgressMonitor monitor) {
			this.monitor = monitor;
		}

		/**
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 *      TODO : temporary deactivate the element change listener
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

	private void doBuild(IResource resource, Resource umlResource) throws IOException {
		doBuild(resource, umlResource, new NullProgressMonitor());
	}

	private void doBuild(IResource resource, Resource umlResource, IProgressMonitor monitor)
			throws IOException {
		File rootFolder = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
		Generate gen = new Generate(umlResource.getContents().get(0), rootFolder, Collections.emptyList());
		gen.doGenerate(new BasicMonitor.EclipseSubProgress(monitor, 50));
		try {
			resource.getProject().refreshLocal(IResource.DEPTH_INFINITE,
					new BasicMonitor.EclipseSubProgress(monitor, 50));
		} catch (CoreException e) {
			// it's ok to silently give up this exception
		} finally {
			manager.dispose();
			manager = null;
		}
	}

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

	public void build(IResource modelFile) {
		ModelManager modelManager = getModelManager(modelFile);
		Resource umlResource = modelManager.getModelResource();
		try {
			doBuild(modelFile, umlResource);
		} catch (IOException e) {
			UML2CBuilderBundle.log(e);
		}
	}

	protected ModelManager getModelManager(CModelChangedEvent event) {
		if (manager == null
				|| !manager.getProject().equals(event.getTranslationUnit().getResource().getProject())) {
			manager = new ModelManager(event.getTranslationUnit().getResource());
		}
		return manager;
	}

	protected ModelManager getModelManager(IResource rsc) {
		if (manager == null || !manager.getProject().equals(rsc.getProject())) {
			manager = new ModelManager(rsc);
		}
		return manager;
	}

	protected ModelManager getModelManager() {
		return manager;
	}
}
