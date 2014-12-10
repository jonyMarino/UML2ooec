/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to addition of an enumeration.
 */
public class EnumerationRemoved extends AbstractEnumerationEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        Enumeration myEnumeration = ModelUtil.findEnumerationInClassifier(matchingClassifier,
                getCurrentName());
        if (myEnumeration != null) {
            if (ModelUtil.isRemovable(myEnumeration)) {
                IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
                if (synchronizer instanceof IDiagramSynchronizer) {
                    ((IDiagramSynchronizer)synchronizer).removeRepresentation(myEnumeration, manager);
                }
                myEnumeration.destroy();
            } else {
                ModelUtil.setVisibility(myEnumeration, getTranslationUnit(), EventType.REMOVE);
            }
        }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<EnumerationRemoved> builder() {
        return new AbstractBuilder<EnumerationRemoved>() {
            private EnumerationRemoved event = new EnumerationRemoved();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractEnumerationEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected EnumerationRemoved getEvent() {
                return event;
            }
        };
    }
}
