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

/**
 * Abstract representation of an event related to an inclusion.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractIncludeEvent extends AbstractNamedEvent {

    /** standard flag. */
    private boolean isStandard;

    public boolean getIsStandard() {
        return isStandard;
    }

    protected void setStandard(boolean pIsStandard) {
        this.isStandard = pIsStandard;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractIncludeEvent> extends AbstractNamedEvent.AbstractBuilder<T> {

        /**
         * This sets the standard flag to the event.
         *
         * @param standard
         *            the standard flag.
         * @return self.
         */
        public AbstractBuilder<T> setStandard(boolean standard) {
            getEvent().setStandard(standard);
            return this;
        }
    }
}
