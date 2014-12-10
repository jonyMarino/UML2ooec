/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Mikael Barbero (Obeo) - evolutions
 *     Christophe Le Camus (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.listener;

import org.eclipse.core.resources.IResource;
import org.eclipse.umlgen.c.common.util.ModelManager;
// CHECKSTYLE:OFF
import org.eclipse.umlgen.reverse.c.StructuralBuilder;
//CHECKSTYLE:ON
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;

/** A UML model change listener. */
public class UMLModelChangeListener implements ICModelChangeListener {

    /** The associated model manager. **/
    private ModelManager manager;

    /**
     * Constructor.
     */
    public UMLModelChangeListener() {
        // do nothing
    }

    /**
     * Constructor invoked since {@link StructuralBuilder} class.
     *
     * @param rsc
     *            A workspace resource
     */
    public UMLModelChangeListener(IResource rsc) {
        manager = getModelManager(rsc);
    }

    /**
     * Disposes the model manager.
     */
    public void dispose() {
        if (manager != null) {
            manager.dispose();
            manager = null;
        }
    }

    /**
     * Gets the model manager according to the resource sent in parameter.
     *
     * @param rsc
     *            The eclipse resource.
     * @return The model manager.
     */
    private ModelManager getModelManager(IResource rsc) {
        if (manager == null) {
            manager = createModelManager(rsc);
        } else if (!manager.getProject().equals(rsc.getProject())) {
            manager.dispose();
            manager = createModelManager(rsc);
        }
        return manager;
    }

    /**
     * Creates a new model manager based on a resource.
     *
     * @param rsc
     *            The workspace resource
     * @return The newly resource manager
     */
    private ModelManager createModelManager(IResource rsc) {
        return new ModelManager(rsc);
    }

    /**
     * Gets the model manager.
     *
     * @return the instantiated model manager
     */
    public ModelManager getModelManager() {
        return manager;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.listener.ICModelChangeListener#notifyChanges(org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent,
     *      boolean)
     */
    public void notifyChanges(final AbstractCModelChangedEvent event, boolean needSave) {
        if (event != null) {
            ModelManager mgr = getModelManager(event.getTranslationUnit().getResource());
            event.notifyChanges(mgr);
            if (needSave) {
                mgr.saveModels();
            }
        }
    }

}
