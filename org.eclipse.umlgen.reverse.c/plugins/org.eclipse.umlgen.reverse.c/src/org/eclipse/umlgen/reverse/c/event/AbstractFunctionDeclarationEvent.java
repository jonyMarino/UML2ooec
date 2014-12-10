/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Christophe Le Camus (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import java.util.List;

import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;

/**
 * Event related to a change of function declaration.
 */
public abstract class AbstractFunctionDeclarationEvent extends AbstractNamedEvent {

    /** the return type of the function. */
    private String returnType;

    /** the parameters list. */
    private List<FunctionParameter> parameters;

    /** the static modifier keyword. */
    private boolean isStatic;

    /**
     * Get the return type.
     *
     * @return The return type.
     */
    public String getReturnType() {
        // special case : a not specified type is equivalent to primitive
        // integer type
        if ("".equals(returnType)) {
            return "int";
        }
        return returnType;
    }

    public List<FunctionParameter> getParameters() {
        return parameters;
    }

    public boolean getIsStatic() {
        return isStatic;
    }

    protected void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    protected void setParameters(List<FunctionParameter> parameters) {
        this.parameters = parameters;
    }

    protected void setIsSatic(boolean pIsStatic) {
        this.isStatic = pIsStatic;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractFunctionDeclarationEvent> extends AbstractNamedEvent.AbstractBuilder<T> {

        /**
         * This sets the return type to the event.
         *
         * @param returnType
         *            the return type.
         * @return self.
         */
        public AbstractBuilder<T> setReturnType(String returnType) {
            getEvent().setReturnType(returnType);
            return this;
        }

        /**
         * This sets the list of parameters to the event.
         *
         * @param parameters
         *            The parameters.
         * @return self
         */
        public AbstractBuilder<T> setParameters(List<FunctionParameter> parameters) {
            getEvent().setParameters(parameters);
            return this;
        }

        /**
         * This sets the static flag to the event.
         *
         * @param isStatic
         *            static flag
         * @return self
         */
        public AbstractBuilder<T> isStatic(boolean isStatic) {
            getEvent().setIsSatic(isStatic);
            return this;
        }
    }

}
