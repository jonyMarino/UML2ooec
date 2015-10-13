/*******************************************************************************
 * Copyright (c) 2015 Spacebel SA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Johan Hardy (Spacebel) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.ui.papyrus.extension.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This generates Embedded C code from elements of a UML model.
 */
public class GenerateEmbeddedCHandler extends AbstractHandler {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
            IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelectionChecked(
                    event);
            Object selectedObj = selection.getFirstElement();

            /* Check whether selection is an EObjectTreeElementImpl */
            if (selectedObj instanceof EObjectTreeElementImpl) {
                org.eclipse.umlgen.gen.embedded.c.ui.handler.GenerateEmbeddedCHandler.doGenerate(
                        ((EObjectTreeElementImpl)selectedObj).getEObject());
            } else {
                throw new ExecutionException("Cannot start generation from "
                        + ((EObjectTreeElementImpl)selectedObj).getEObject().toString());
            }
        } catch (ExecutionException e) {
            throw e;
        }
        return null;
    }

}
