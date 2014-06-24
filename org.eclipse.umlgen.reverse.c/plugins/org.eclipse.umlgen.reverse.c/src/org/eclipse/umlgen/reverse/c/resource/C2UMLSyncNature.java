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
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.resource;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.internal.bundle.Messages;

/**
 * Configures and deconfigures the synchronized C project in adding/removing the dedicated
 * <b>org.eclipse.umlgen.reverse.c.builder</b> builder to the current project nature.
 *
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mikael BARBERO</a>
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class C2UMLSyncNature implements IProjectNature {
	/** Represents the name template variable */
	private static final String NAME = "name"; //$NON-NLS-1$

	/** Represents the escaped name template variable (aka the encoded name) */
	private static final String ESCAPED_NAME = "escapedName"; //$NON-NLS-1$

	/** Represents the charset template variable */
	private static final String CHARSET = "charset"; //$NON-NLS-1$

	/** The project for which nature are added/removed */
	private IProject project;

	/**
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	public void configure() throws CoreException {
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
	}

	/***
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	public void setProject(IProject value) {
		project = value;
	}

	// FIXME MIGRATION reference to modeler
	// /**
	// * Updates the template
	// *
	// * @param template the template to update
	// * @return true if the template was successfully created
	// */
	// private static boolean updateTemplate(Template template, IFolder project,
	// String name)
	// {
	// try
	// {
	// template.setDestination((IContainer) project);
	// template.addVariable(NAME, name);
	// template.addVariable(ESCAPED_NAME, URI.encodeFragment(name, false));
	// template.generate(new NullProgressMonitor());
	// }
	// catch (CoreException ce)
	// {
	// Activator.log(ce);
	// return false;
	// }
	// return true;
	// }

	/**
	 * Opens a selection dialog to choose an existing UML model to reverse. Before returning the resource, the
	 * selection is checked. If the selected resource is not an UML model then null is returned.
	 *
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
				if (firstObj instanceof IFile) {
					IFile selectedFile = (IFile)firstObj;
					if (BundleConstants.UML_EXTENSION.equals(selectedFile.getFileExtension())) {
						return selectedFile;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Creates a couple of UML/UMLDI models to start reversing the source code.
	 *
	 * @return The UML model as reference
	 * @throws CoreException
	 *             If something fails during the creation operation.
	 */
	public static IFile createUMLanUMLDIFromTemplates(IProject project) throws CoreException {
		IFolder modelFolder = project.getFolder(BundleConstants.MODELS_FOLDER);
		if (!modelFolder.isAccessible()) {
			modelFolder.create(false, true, null);
		}

		if (ProjectUtil.hasNature(project, BundleConstants.NATURE_ID)) {
			ICProjectDescription description = CoreModel.getDefault().createProjectDescription(project, true);
			ICConfigurationDescription defaultConfiguration = description.getActiveConfiguration();

			try {
				ICSourceEntry[] newEntries = CDataUtil.setExcluded(modelFolder.getFullPath(),
						modelFolder instanceof IFolder, true, defaultConfiguration.getSourceEntries());
				defaultConfiguration.setSourceEntries(newEntries);
				description.setActiveConfiguration(defaultConfiguration);
				CoreModel.getDefault().setProjectDescription(project, description);
			} catch (CoreException e) {
				Activator.log(e);
			}
		}

		String modelFileName = project.getFullPath().addFileExtension(BundleConstants.UML_EXTENSION)
				.lastSegment();
		IFile modelFile = modelFolder.getFile(modelFileName);
		if (modelFile.exists()) {
			boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), Messages
					.getString("C2UMLSyncNature.dialog.title"), //$NON-NLS-1$
					Messages.getString("C2UMLSyncNature.dialog.question")); //$NON-NLS-1$
			if (!result) {
				return null;
			}
		} else {
			// FIXME MIGRATION reference to modeler
			// Template template =
			// TemplatesManager.getInstance().find(BundleConstants.TEMPLATE_ID).getTemplateModel();
			// template.setDestination((IContainer) modelFolder);
			// template.addVariable(NAME, project.getName());
			// template.addVariable(ESCAPED_NAME,
			// URI.encodeFragment(project.getName(), false));
			// template.addVariable(CHARSET, project.getDefaultCharset(true));
			// template.generate(new NullProgressMonitor());
			// Template templateDi =
			// TemplatesManager.getInstance().find(BundleConstants.TEMPLATE_ID).getTemplateDI();
			// updateTemplate(templateDi, modelFolder, project.getName());
		}
		String diagramFileName = project.getFullPath().addFileExtension(BundleConstants.UML_EXTENSION)
				.lastSegment();
		IFile diagramFile = modelFolder.getFile(diagramFileName);
		if (!diagramFile.exists()) {
			// FIXME MIGRATION reference to modeler
			// Template templateDi =
			// TemplatesManager.getInstance().find(BundleConstants.TEMPLATE_ID).getTemplateDI();
			// updateTemplate(templateDi, modelFolder, project.getName());
		}
		return modelFile;
	}

	public static boolean isC2UMLSynchProject(ICProject cProject) {
		return isC2UMLSynchProject(cProject.getProject());
	}

	public static boolean isC2UMLSynchProject(IProject project) {
		return ProjectUtil.hasNature(project, BundleConstants.NATURE_ID);
	}
}
