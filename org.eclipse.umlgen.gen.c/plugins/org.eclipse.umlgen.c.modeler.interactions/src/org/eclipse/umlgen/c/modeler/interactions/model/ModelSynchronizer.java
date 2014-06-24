/*******************************************************************************
 * Copyright (c) 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.modeler.interactions.model;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.umlgen.c.common.Activator;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.ui.PreferenceStoreManager;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.modeler.interactions.Messages;
import org.eclipse.umlgen.c.modeler.interactions.templates.Template;
import org.eclipse.umlgen.c.modeler.interactions.templates.TemplatesManager;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;

public class ModelSynchronizer implements IModelSynchronizer {

	/** Represents the name template variable */
	protected static final String NAME = "name"; //$NON-NLS-1$

	/** Represents the escaped name template variable (aka the encoded name) */
	protected static final String ESCAPED_NAME = "escapedName"; //$NON-NLS-1$

	/** Represents the charset template variable */
	protected static final String CHARSET = "charset"; //$NON-NLS-1$

	private static final String TEMPLATE_ID = "org.eclipse.umlgen.c.modeler.interactions.template";

	public ModelManager createModelManager(IResource model) {
		return new ModelManager(model);
	}

	public IFile createModel(IProject project) throws CoreException {
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
			boolean result = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
					getString("C2UMLSyncNature.dialog.title"), //$NON-NLS-1$
					getString("C2UMLSyncNature.dialog.question")); //$NON-NLS-1$
			if (!result) {
				return null;
			}
		} else {
			createModel(project, modelFolder);
		}
		return modelFile;
	}

	protected void createModel(IProject project, IFolder modelFolder) throws CoreException {
		Template template = TemplatesManager.getInstance().find(TEMPLATE_ID).getTemplateModel();
		template.setDestination(modelFolder);
		template.addVariable(NAME, project.getName());
		template.addVariable(ESCAPED_NAME, URI.encodeFragment(project.getName(), false));
		template.addVariable(CHARSET, project.getDefaultCharset(true));
		template.generate(new NullProgressMonitor());
	}

	protected String getString(String key) {
		return Messages.getString(key);
	}

	public void setDefaultValues(IProject project) {
		PreferenceStoreManager.setDefaultValues(project);
	}

	public void setInitialValues(IProject project) {
		PreferenceStoreManager.setInitialValues(project);
	}

}
