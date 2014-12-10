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
 * Event related to renaming of a definition.
 */
public class FunctionDefinitionRenamed extends AbstractFunctionDeclarationEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<FunctionDefinitionRenamed> builder() {
        return new AbstractBuilder<FunctionDefinitionRenamed>() {
            private FunctionDefinitionRenamed event = new FunctionDefinitionRenamed();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractFunctionDeclarationEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected FunctionDefinitionRenamed getEvent() {
                return event;
            }
        };
    }
}
