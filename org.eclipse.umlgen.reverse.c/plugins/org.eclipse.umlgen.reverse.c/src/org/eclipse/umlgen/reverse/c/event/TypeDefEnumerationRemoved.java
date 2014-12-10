/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Mikael BARBERO (Obeo) - initial API and implementation
 * 	   Christophe LE CAMUS (CS-SI) - Major evolution
 *     Sebastien Gabel (CS-SI) - Refactoring
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
 * Event related to deletion of a type definition of an enumeration.
 */
public class TypeDefEnumerationRemoved extends AbstractTypeDefEnumerationEvent {

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
    public static AbstractBuilder<TypeDefEnumerationRemoved> builder() {
        return new AbstractBuilder<TypeDefEnumerationRemoved>() {
            private TypeDefEnumerationRemoved event = new TypeDefEnumerationRemoved();

            @Override
            protected TypeDefEnumerationRemoved getEvent() {
                return event;
            }
        };
    }
}
