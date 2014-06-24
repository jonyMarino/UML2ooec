/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.common;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Manager to set default and current values into the corresponding preference
 * store.<br>
 * 
 * Creation : 10 may 2010<br>
 * 
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public final class PreferenceStoreManager {

	private static final String LOGICAL_VIEW = "Logical View"; //$NON-NLS-1$

	/**
	 * Constructor
	 */
	private PreferenceStoreManager() {
		// avoid to be instantiated.
	}

	/**
	 * Sets the default values into the preference store when the C2UML property
	 * page is called.
	 * 
	 * @param project
	 *            The selected IProject corresponding to the C Project
	 */
	public static void setDefaultValues(IProject project) {
		IPreferenceStore store = getPreferenceStore(project);
		IFolder modelFolder = project.getFolder(BundleConstants.MODELS_FOLDER);
		IFile modelFile = modelFolder.getFile(project.getFullPath()
				.addFileExtension(BundleConstants.UML_EXTENSION));
		IFile diagramFile = modelFolder.getFile(project.getFullPath()
				.addFileExtension(BundleConstants.UMLDI_EXTENSION));

		// handle default synchronized model paths
		URI uri = URI.createPlatformResourceURI(modelFile.getFullPath()
				.toString(), true);
		store.setDefault(BundleConstants.UML_MODEL_PATH, uri.toString());

		uri = URI.createPlatformResourceURI(diagramFile.getFullPath()
				.toString(), true);
		store.setDefault(BundleConstants.UMLDI_MODEL_PATH, uri.toString());

		// handle default configuration settings
		String srcQualifiedName = project.getName()
				+ "::" + LOGICAL_VIEW + "::" + BundleConstants.SOURCE_PACKAGE_NAME; //$NON-NLS-1$ //$NON-NLS-2$
		store.setDefault(BundleConstants.SRC_PCK_NAME, srcQualifiedName);

		String typeQualifiedName = project.getName()
				+ "::" + LOGICAL_VIEW + "::" + BundleConstants.TYPE_PACKAGE_NAME; //$NON-NLS-1$ //$NON-NLS-2$
		store.setDefault(BundleConstants.TYPE_PCK_NAME, typeQualifiedName);

		String extQualifiedName = project.getName()
				+ "::" + LOGICAL_VIEW + "::" + BundleConstants.LIB_PACKAGE_NAME; //$NON-NLS-1$ //$NON-NLS-2$
		store.setDefault(BundleConstants.EXT_PCK_NAME, extQualifiedName);
	}

	/**
	 * Sets the initial values when the C nature is added to the project.
	 * 
	 * @param project
	 *            The selected IProject corresponding to the C Project
	 */
	public static void setInitialValues(IProject project) {
		IPreferenceStore store = getPreferenceStore(project);
		IFolder modelFolder = project.getFolder(BundleConstants.MODELS_FOLDER);
		IFile modelFile = modelFolder.getFile(project.getFullPath()
				.addFileExtension(BundleConstants.UML_EXTENSION));
		IFile diagramFile = modelFolder.getFile(project.getFullPath()
				.addFileExtension(BundleConstants.UMLDI_EXTENSION));

		// handle default synchronized model paths
		URI uri = URI.createPlatformResourceURI(modelFile.getFullPath()
				.toString(), true);
		store.setValue(BundleConstants.UML_MODEL_PATH, uri.toString());

		uri = URI.createPlatformResourceURI(diagramFile.getFullPath()
				.toString(), true);
		store.setValue(BundleConstants.UMLDI_MODEL_PATH, uri.toString());

		// handle default configuration settings
		String srcQualifiedName = project.getName()
				+ "::" + LOGICAL_VIEW + "::" + BundleConstants.SOURCE_PACKAGE_NAME; //$NON-NLS-1$ //$NON-NLS-2$
		store.setValue(BundleConstants.SRC_PCK_NAME, srcQualifiedName);

		String typeQualifiedName = project.getName()
				+ "::" + LOGICAL_VIEW + "::" + BundleConstants.TYPE_PACKAGE_NAME; //$NON-NLS-1$ //$NON-NLS-2$
		store.setValue(BundleConstants.TYPE_PCK_NAME, typeQualifiedName);

		String extQualifiedName = project.getName()
				+ "::" + LOGICAL_VIEW + "::" + BundleConstants.LIB_PACKAGE_NAME; //$NON-NLS-1$ //$NON-NLS-2$
		store.setValue(BundleConstants.EXT_PCK_NAME, extQualifiedName);
	}

	/**
	 * Gets the project or instance preference store.
	 * 
	 * @param project
	 *            the IProject in which UML and UMLDI template files are
	 *            created.
	 * @return the project scope preference store or the instance scope
	 */
	public static IPreferenceStore getPreferenceStore(IProject project) {
		IPersistentPreferenceStore store;
		if (project != null) {
			store = new ScopedPreferenceStore(new ProjectScope(project),
					Activator.getBundleId());
		} else {
			store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
					Activator.getBundleId());
		}
		return store;
	}

}
