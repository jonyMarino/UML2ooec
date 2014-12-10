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
 * Abstract representation of an event related to a Ifndef.
 *
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractIfndefEvent extends AbstractCModelChangedEvent {

    /** The condition. */
    private String condition;

    public String getCondition() {
        return condition;
    }

    protected void setCondition(char[] condition) {
        this.condition = new String(condition);
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractIfndefEvent> extends AbstractCModelChangedEvent.AbstractBuilder<T> {

        /**
         * This sets the condition to the event.
         *
         * @param condition
         *            the condition.
         * @return self.
         */
        public AbstractBuilder<T> setCondition(char[] condition) {
            getEvent().setCondition(condition);
            return this;
        }

        @Override
        protected abstract T getEvent();
    }

}
