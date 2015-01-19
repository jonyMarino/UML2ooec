/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Mikael Barbero (Obeo) 	- evolutions
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.reverse.c.StructuralBuilder;

/**
 * This generates C code from elements of a UML model.
 */
public class ReverseCCodeToUML extends AbstractHandler {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        StructuralBuilder sb = null;

        try {
            IStructuredSelection selection = (IStructuredSelection)HandlerUtil
                    .getCurrentSelectionChecked(event);
            IProject project = (IProject)selection.getFirstElement();

            IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
            if (synchronizer != null) {
                synchronizer.setInitialValues(project);
            }
            IFile modelFile = null;

            if (synchronizer != null) {
                modelFile = synchronizer.createModel(project);
            }

            if (modelFile != null && modelFile.exists()) {
                sb = new StructuralBuilder(modelFile);
                sb.build();
                sb.dispose();
            }

        } catch (ExecutionException e) {
            throw e;
        } catch (CoreException e2) {
            throw new ExecutionException(e2.getMessage(), e2);
        } finally {
            if (sb != null) {
                sb.dispose();
            }
        }
        return null;
    }

}
