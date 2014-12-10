/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to deletion of a type definition.
 */
public class TypeDefRemoved extends AbstractTypeDefEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractTypeDefEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        Classifier localType = ModelUtil.findDataTypeInClassifier(matchingClassifier, getCurrentName());
        if (localType != null) {
            if (ModelUtil.isRemovable(localType)) {
                IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
                if (synchronizer instanceof IDiagramSynchronizer) {
                    ((IDiagramSynchronizer)synchronizer).removeRepresentation(localType, manager);
                }
                localType.destroy();
            } else {
                ModelUtil.setVisibility(localType, getTranslationUnit(), EventType.REMOVE);
            }
        }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<TypeDefRemoved> builder() {
        return new AbstractBuilder<TypeDefRemoved>() {
            private TypeDefRemoved event = new TypeDefRemoved();

            /**
             * @see org.eclipse.umlgen.reverse.c.TypeDefBuilder#getEvent()
             */
            @Override
            protected TypeDefRemoved getEvent() {
                return event;
            }
        };
    }
}
