/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.gen.c.builder.UML2CBundleConstant;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;

/**
 * Handler removing the <b>org.eclipse.umlgen.reverse.c.syncNature</b> to the current C project.<br>
 * Creation : 11 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
@Deprecated
public class RemoveC2UMLSyncNature extends AbstractHandler {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelectionChecked(event);
        IProject project = (IProject)selection.getFirstElement();

        try {
            ProjectUtil.removeNature(project, BundleConstants.NATURE_ID);
            ProjectUtil.removeNature(project, UML2CBundleConstant.NATURE_ID);
        } catch (CoreException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        return null;
    }
}
