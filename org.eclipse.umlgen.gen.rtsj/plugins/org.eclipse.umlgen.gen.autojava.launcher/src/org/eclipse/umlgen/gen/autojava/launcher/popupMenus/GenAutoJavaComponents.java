/*******************************************************************************
 * Copyright (c) 2008, 2014 CNES and others.
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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.umlgen.gen.autojava.launcher.Activator;
import org.eclipse.umlgen.gen.autojava.main.Uml2autojava;

/**
 * UML to Autojava components code generation.
 */
public abstract class GenAutoJavaComponents extends ActionDelegate implements IActionDelegate {

	/**
	 * Selected model files.
	 */
	protected List<IFile> files;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			files = ((IStructuredSelection)selection).toList();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.actions.ActionDelegate#run(org.eclipse.jface.action.IAction)
	 * @generated
	 */
	@Override
	public void run(IAction action) {
		if (files != null) {
			IRunnableWithProgress operation = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					try {
						Iterator<IFile> filesIt = files.iterator();
						while (filesIt.hasNext()) {
							IFile model = (IFile)filesIt.next();
							URI modelURI = URI
									.createPlatformResourceURI(model.getFullPath().toString(), true);
							try {

								String sTarget = model.getPersistentProperty(new QualifiedName("",
										"OUTPUT_PATH_AUTOJAVA_COMP"));
								String sDecorators = model.getPersistentProperty(new QualifiedName("",
										"COMMUNICATION_PARAMETERING"));

								File target = getFileFromPath(model, sTarget);

								Uml2autojava generator = getGenerator(modelURI, sDecorators, target);

								generate(monitor, generator);

							} catch (IOException e) {
								IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
										.getMessage(), e);
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
			};
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

	abstract protected Uml2autojava getGenerator(URI modelURI, String sDecorators, File target)
			throws IOException;

	abstract protected String getModuleQualifiedName();

}
