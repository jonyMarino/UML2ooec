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

import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * Abstract representation of an event related to a structure.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractTypeDefStructEvent extends AbstractStructEvent {

    /** The redefined structure name. */
    private String redefinedStructName;

    /** The source node. */
    private IASTNode source;

    public String getRedefinedStructName() {
        return this.redefinedStructName;
    }

    protected void setRedefinedStructName(String redefinedStructName) {
        this.redefinedStructName = redefinedStructName;
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
    public abstract static class AbstractBuilder<T extends AbstractTypeDefStructEvent> extends AbstractStructEvent.AbstractBuilder<T> {

        /**
         * This sets the given redefined structure name to the event.
         *
         * @param name
         *            the redefined structure name.
         * @return self.
         */
        public AbstractBuilder<T> setRedefinedStruct(String name) {
            getEvent().setRedefinedStructName(name);
            return this;
        }

        /**
         * This sets the given source node to the event.
         *
         * @param source
         *            The source node.
         * @return self.
         */
        public AbstractBuilder<T> setSource(IASTNode source) {
            getEvent().setSource(source);
            return this;
        }
    }

}
