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
 * Abstract representation of an event related to a variable declaration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractVariableDeclarationEvent extends AbstractTypedEvent {

    /** Flag static. */
    private boolean isStatic;

    /** Flag extern. */
    private boolean isExtern;

    /** Flag volatile. */
    private boolean isVolatile;

    /** Flag constant. */
    private boolean isConst;

    /** Flag register. */
    private boolean isRegister;

    /** The initialization expression. */
    private String initializationExpression;

    public boolean getIsStatic() {
        return this.isStatic;
    }

    public boolean getIsExtern() {
        return this.isExtern;
    }

    public boolean getIsVolatile() {
        return this.isVolatile;
    }

    public boolean getIsConst() {
        return this.isConst;
    }

    public boolean getIsRegister() {
        return this.isRegister;
    }

    public String getInitializationExpression() {
        return this.initializationExpression;
    }

    protected void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    protected void setIsExtern(boolean isExtern) {
        this.isExtern = isExtern;
    }

    protected void setIsVolatile(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }

    protected void setIsConst(boolean isConst) {
        this.isConst = isConst;
    }

    protected void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    protected void setInitializationExpression(String expression) {
        this.initializationExpression = expression;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractVariableDeclarationEvent> extends AbstractTypedEvent.AbstractBuilder<T> {

        /**
         * This sets the flag static to the event.
         *
         * @param isStatic
         *            the flag static.
         * @return self.
         */
        public AbstractBuilder<T> setIsStatic(boolean isStatic) {
            getEvent().setIsStatic(isStatic);
            return this;
        }

        /**
         * This sets the flag const to the event.
         *
         * @param isConst
         *            The flag const.
         * @return self.
         */
        public AbstractBuilder<T> setConst(boolean isConst) {
            getEvent().setIsConst(isConst);
            return this;
        }

        /**
         * This sets the flag extern to the event.
         *
         * @param isExtern
         *            The flag extern.
         * @return self.
         */
        public AbstractBuilder<T> setExtern(boolean isExtern) {
            getEvent().setIsExtern(isExtern);
            return this;
        }

        /**
         * This sets the flag volatile to the event.
         *
         * @param isVolatile
         *            The flag volatile.
         * @return self.
         */
        public AbstractBuilder<T> setVolatile(boolean isVolatile) {
            getEvent().setIsVolatile(isVolatile);
            return this;
        }

        /**
         * This sets the flag register to the event.
         *
         * @param isRegister
         *            The flag register.
         * @return self.
         */
        public AbstractBuilder<T> setRegister(boolean isRegister) {
            getEvent().setIsRegister(isRegister);
            return this;
        }

        /**
         * This sets the initializer expression to the event.
         * 
         * @param expression
         *            The initializer expression.
         * @return self.
         */
        public AbstractBuilder<T> setInitializerExpression(String expression) {
            getEvent().setInitializationExpression(expression);
            return this;
        }
    }
}
