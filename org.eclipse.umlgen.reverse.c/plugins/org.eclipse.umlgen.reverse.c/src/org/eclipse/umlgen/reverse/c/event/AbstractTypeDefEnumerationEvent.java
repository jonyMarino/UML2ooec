/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     	Mikael BARBERO (Obeo) - initial API and implementation
 * 		Christophe LE CAMUS (CS-SI) - Major evolution
 * 		Sebastien GABEl (CS-SI) - Refactoring
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * Abstract representation of an event related to a type def enumeration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractTypeDefEnumerationEvent extends AbstractEnumerationEvent {

    /** The name of the redefined enumeration. */
    private String redefinedEnumerationName;

    /** The node source. */
    private IASTNode source;

    public String getRedefinedEnumerationName() {
        return redefinedEnumerationName;
    }

    protected void setRedefinedEnumerationName(String redefinedEnumerationName) {
        this.redefinedEnumerationName = redefinedEnumerationName;
    }

    public IASTNode getSource() {
        return source;
    }

    public void setSource(IASTNode source) {
        this.source = source;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractTypeDefEnumerationEvent> extends AbstractEnumerationEvent.AbstractBuilder<T> {

        /**
         * This sets the given redefined enumeration name to the event.
         *
         * @param name
         *            the name.
         * @return self.
         */
        public AbstractBuilder<T> setRedefinedEnumeration(String name) {
            getEvent().setRedefinedEnumerationName(name);
            return this;
        }

        /**
         * This sets the given source node to the event.
         *
         * @param source
         *            the source.
         * @return self.
         */
        public AbstractBuilder<T> setSource(IASTNode source) {
            getEvent().setSource(source);
            return this;
        }
    }
}
