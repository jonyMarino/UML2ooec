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
 * Event related to a function definition.
 */
public abstract class AbstractFunctionDefinitionEvent extends AbstractFunctionDeclarationEvent {

    /** The body of the function. */
    private String body;

    public String getBody() {
        return body;
    }

    protected void setBody(String impl) {
        body = impl;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractFunctionDefinitionEvent> extends AbstractFunctionDeclarationEvent.AbstractBuilder<T> {

        /**
         * This sets the body to the event.
         *
         * @param body
         *            the body.
         * @return self.
         */
        public AbstractBuilder<T> setBody(String body) {
            getEvent().setBody(body);
            return this;
        }
    }

}
