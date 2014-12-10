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

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;

/**
 * Abstract representation of an event related to a structure.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractStructEvent extends AbstractTypedEvent {

    /** The declarations. */
    private IASTDeclaration[] declarations;

    public IASTDeclaration[] getDeclarations() {
        return this.declarations;
    }

    protected void setDeclarations(IASTDeclaration[] declarations) {
        this.declarations = declarations;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractStructEvent> extends AbstractTypedEvent.AbstractBuilder<T> {

        /**
         * This sets the declarations to the event.
         *
         * @param declarations
         *            the declarations.
         * @return self.
         */
        public AbstractBuilder<T> setDeclarations(IASTDeclaration[] declarations) {
            getEvent().setDeclarations(declarations);
            return this;
        }
    }
}
