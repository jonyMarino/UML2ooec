/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

/**
 * Abstract representation of an event related to a definition of a simple of composite type.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public abstract class AbstractTypedEvent extends AbstractNamedEvent {

    /** The previous type name. */
    private String previousTypeName;

    /** The current type name. */
    private String currentTypeName;

    public String getCurrentTypeName() {
        return this.currentTypeName;
    }

    public String getPreviousTypeName() {
        return this.previousTypeName;
    }

    protected void setCurrentType(String pCurrentTypeName) {
        this.currentTypeName = pCurrentTypeName;
    }

    protected void setPreviousType(String setPreviousTypeName) {
        this.previousTypeName = setPreviousTypeName;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractTypedEvent> extends AbstractNamedEvent.AbstractBuilder<T> {

        /**
         * This sets the given type name as the current one, on the event.
         *
         * @param currentTypeName
         *            The current type name.
         * @return self
         */
        public AbstractBuilder<T> currentType(String currentTypeName) {
            getEvent().setCurrentType(currentTypeName);
            return this;
        }

        /**
         * This sets the given type name as the previous one, on the event.
         *
         * @param previousTypeName
         *            The previous type name.
         * @return self
         */
        public AbstractBuilder<T> previousType(String previousTypeName) {
            getEvent().setCurrentType(previousTypeName);
            return this;
        }
    }

}
