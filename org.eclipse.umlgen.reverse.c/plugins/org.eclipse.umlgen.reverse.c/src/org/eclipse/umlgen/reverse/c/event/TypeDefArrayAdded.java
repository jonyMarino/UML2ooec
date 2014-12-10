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

import org.eclipse.uml2.uml.StringExpression;
import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Event related to an addition of an array.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class TypeDefArrayAdded extends AbstractTypeDefArrayEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractTypeDefEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        // call to super
        super.notifyChanges(manager);

        // handle dimension
        // CHECKSTYLE:OFF
        StringExpression expr = myTypeDef.getNameExpression() == null ? myTypeDef.createNameExpression(
                "dimension", null) : myTypeDef.getNameExpression();

                // Updates the value in all cases.
                StringBuilder sb = new StringBuilder();
                for (String dim : getDimensions()) {
                    sb = sb.append("[" + dim + "]");
                }
                expr.setSymbol(sb.toString());
        // CHECKSTYLE:ON
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<TypeDefArrayAdded> builder() {
        return new AbstractBuilder<TypeDefArrayAdded>() {
            private TypeDefArrayAdded event = new TypeDefArrayAdded();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractTypeDefArrayEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected TypeDefArrayAdded getEvent() {
                return event;
            }
        };
    }
}
