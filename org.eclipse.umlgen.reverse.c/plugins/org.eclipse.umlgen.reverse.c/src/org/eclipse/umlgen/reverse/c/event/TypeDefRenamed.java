/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Event related to renaming of a type definition.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class TypeDefRenamed extends AbstractTypeDefEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractTypeDefEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        // TODO : to be implemented
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<TypeDefRenamed> builder() {
        return new AbstractBuilder<TypeDefRenamed>() {
            private TypeDefRenamed event = new TypeDefRenamed();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractTypeDefEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected TypeDefRenamed getEvent() {
                return event;
            }
        };
    }
}
