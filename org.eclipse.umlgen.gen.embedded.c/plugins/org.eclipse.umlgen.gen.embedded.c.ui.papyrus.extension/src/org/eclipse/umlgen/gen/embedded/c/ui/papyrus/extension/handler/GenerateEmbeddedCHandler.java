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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

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
                EClass eClass = ((EObjectTreeElementImpl)selectedObj).getEObject().eClass();
                if (eClass == UMLPackage.Literals.CLASS) {
                    caseClass((Class)((EObjectTreeElementImpl)selectedObj).getEObject());
                } else if (eClass == UMLPackage.Literals.PACKAGE) {
                    casePackage((Package)((EObjectTreeElementImpl)selectedObj).getEObject());
                } else if (eClass == UMLPackage.Literals.MODEL) {
                    caseModel((Model)((EObjectTreeElementImpl)selectedObj).getEObject());
                } else {
                    throw new ExecutionException("Cannot start generation from " + eClass.toString());
                }
            } else {
                throw new ExecutionException("Cannot start generation from "
                        + ((EObjectTreeElementImpl)selectedObj).getEObject().toString());
            }
        } catch (ExecutionException e) {
            throw e;
        }
        return null;

    }

    /**
     * This generates from a given UML class.
     *
     * @param selectedObject
     *            The UML class.
     * @throws ExecutionException
     *             exception.
     */
    private void caseClass(Class selectedObject) throws ExecutionException {
        org.eclipse.umlgen.gen.embedded.c.ui.handler.GenerateEmbeddedCHandler.doGenerate(selectedObject,
                selectedObject.getModel());
    }

    /**
     * This generates from a given UML package.
     *
     * @param selectedObject
     *            The UML package.
     * @throws ExecutionException
     *             exception.
     */
    private void casePackage(Package selectedObject) throws ExecutionException {
        org.eclipse.umlgen.gen.embedded.c.ui.handler.GenerateEmbeddedCHandler.doGenerate(selectedObject,
                selectedObject.getModel());
    }

    /**
     * This generates from a given UML model.
     *
     * @param selectedObject
     *            The UML package.
     * @throws ExecutionException
     *             exception.
     */
    private void caseModel(Model selectedObject) throws ExecutionException {
        org.eclipse.umlgen.gen.embedded.c.ui.handler.GenerateEmbeddedCHandler.doGenerate(selectedObject,
                selectedObject.getModel());
    }

}
