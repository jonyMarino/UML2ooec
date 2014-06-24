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
package org.eclipse.umlgen.c.common.interactions.extension;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Model synchronizer interface to interact with UML semantic models
 */
public interface IModelSynchronizer {

	ModelManager createModelManager(IResource model);

	IFile createModel(IProject project) throws CoreException;

	void setDefaultValues(IProject project);

	void setInitialValues(IProject project);

}
