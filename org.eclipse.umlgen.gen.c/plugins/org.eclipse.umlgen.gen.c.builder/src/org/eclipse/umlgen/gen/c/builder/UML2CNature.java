/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *      Sebastien GABEL (CS-SI) - Bug fix on deconfigure project
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.builder;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;

/**
 * Configures and deconfigures the synchronized C project in adding/removing the dedicated
 * <b>org.eclipse.umlgen.gen.c.builder</b> builder to the current project nature.
 *
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mikael BARBERO</a>
 */
@Deprecated
public class UML2CNature implements IProjectNature {

    /** The project for which nature are added/removed. */
    private IProject project;

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        ProjectUtil.addToBuildSpec(getProject(), UML2CBundleConstant.BUILDER_ID);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        ProjectUtil.removeFromBuildSpec(getProject(), UML2CBundleConstant.BUILDER_ID);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return project;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject value) {
        project = value;
    }

    /**
     * This checks the nature of the given project.
     *
     * @param cProject
     *            The project.
     * @return True if it is a UML2C project.
     */
    public static boolean isUML2CProject(ICProject cProject) {
        return isUML2CProject(cProject.getProject());
    }

    /**
     * This checks the nature of the given project.
     *
     * @param project
     *            The project.
     * @return True if it is a UML2C project.
     */
    public static boolean isUML2CProject(IProject project) {
        return ProjectUtil.hasNature(project, UML2CBundleConstant.NATURE_ID);
    }
}
