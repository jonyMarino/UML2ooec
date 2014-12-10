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
 * Model synchronizer interface to interact with UML semantic models.
 */
public interface IModelSynchronizer {

    /**
     * This creates and returns the model manager to use from the semantic eclipse resource.
     *
     * @param model
     *            The semantic eclipse resource containing a UML model.
     * @return The model manager.
     */
    ModelManager createModelManager(IResource model);

    /**
     * This initializes a UML model in the given eclipse project and returns it as an eclipse file.
     *
     * @param project
     *            The eclipse project.
     * @return The eclipse file containing a UML model.
     * @throws CoreException
     *             exception.
     */
    IFile createModel(IProject project) throws CoreException;

    /**
     * This sets the default values related to the given eclipse project when the C2UML property page is
     * called (saved into the preference store i.e.).
     *
     * @param project
     *            The eclipse project.
     */
    void setDefaultValues(IProject project);

    /**
     * This sets the initial values related to the given eclipse project when the C nature is added to the
     * project (saved into the preference store i.e.).
     *
     * @param project
     *            The eclipse project.
     */
    void setInitialValues(IProject project);

}
