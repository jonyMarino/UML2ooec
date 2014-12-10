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
 * Event related to a change of function body.
 */
public abstract class AbstractFunctionBodyEvent extends AbstractCModelChangedEvent {

    /** The current name. */
    private String currentName;

    /** The body. */
    private String body;

    /** The old body. */
    private String oldBody;

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setOldBody(String oldBody) {
        this.oldBody = oldBody;
    }

    public String getOldBody() {
        return oldBody;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractFunctionBodyEvent> extends AbstractCModelChangedEvent.AbstractBuilder<T> {

        /**
         * This sets the given current name to the event.
         *
         * @param currentName
         *            the current name.
         * @return self.
         */
        public AbstractBuilder<T> currentName(String currentName) {
            getEvent().setCurrentName(currentName);
            return this;
        }

        /**
         * This sets the given body to the event.
         *
         * @param body
         *            The body
         * @return self.
         */
        public AbstractBuilder<T> setBody(String body) {
            getEvent().setBody(body);
            return this;
        }

        /**
         * This sets the given old body to the event.
         *
         * @param body
         *            the old body.
         * @return self.
         */
        public AbstractBuilder<T> setOldBody(String body) {
            getEvent().setOldBody(body);
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent.AbstractBuilder#getEvent()
         */
        @Override
        protected abstract T getEvent();
    }

}
