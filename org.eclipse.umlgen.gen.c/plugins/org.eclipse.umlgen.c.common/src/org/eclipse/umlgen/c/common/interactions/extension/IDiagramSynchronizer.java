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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Diagram Synchronizer interface to interact with graphical models.
 */
public interface IDiagramSynchronizer extends IModelSynchronizer {

    /**
     * This enables to remove the representations related to the given semantic object.
     *
     * @param object
     *            The semantic object.
     * @param mngr
     *            A model manager.
     */
    void removeRepresentation(EObject object, ModelManager mngr);

    /**
     * This returns the EClass defining a concept of representation (diagram i.e.).
     *
     * @return The EClass which represents a kind of representation.
     */
    EClass getRepresentationKind();

    /**
     * This returns the label of the semantic object.<br>
     * It is used by a property tester to activate a popup menu on a Diagram.
     *
     * @param object
     *            The target diagram object.
     * @return The label of the semantic object.
     */
    String getPresentation(Object object);

}
