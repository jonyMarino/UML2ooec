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
 * Abstract representation of an event related to a macro.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractMacroEvent extends AbstractTypedEvent {

    /** The expansion. */
    private String expansion;

    public String getExpansion() {
        return expansion;
    }

    protected void setExpansion(String value) {
        expansion = value;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractMacroEvent> extends AbstractTypedEvent.AbstractBuilder<T> {

        /**
         * This sets the expansion flag to the event.
         *
         * @param typeName
         *            the type name.
         * @return self.
         */
        public AbstractBuilder<T> setExpansion(String typeName) {
            getEvent().setExpansion(typeName);
            return this;
        }
    }

}
