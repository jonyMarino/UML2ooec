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

import java.util.Collection;

/**
 * Abstract representation of an event related to an array.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractTypeDefArrayEvent extends AbstractTypeDefEvent {

    /** The definitions. */
    private Collection<String> dimensions;

    public Collection<String> getDimensions() {
        return dimensions;
    }

    protected void setDimensions(Collection<String> dim) {
        dimensions = dim;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractTypeDefArrayEvent> extends AbstractTypeDefEvent.AbstractBuilder<T> {

        /**
         * This sets the dimensions to the event.
         *
         * @param dim
         *            the dimensions.
         * @return self.
         */
        public AbstractBuilder<T> setDimensions(Collection<String> dim) {
            getEvent().setDimensions(dim);
            return this;
        }
    }
}
