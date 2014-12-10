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
 * Event related to a function parameter.
 */
public abstract class AbstractFunctionParameterEvent extends AbstractCModelChangedEvent {

    /** The function name. */
    private String functionName;

    /** The parameters. */
    private List<FunctionParameter> parameters;

    public String getFunctionName() {
        return functionName;
    }

    public List<FunctionParameter> getParameters() {
        return this.parameters;
    }

    protected void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    protected void setParameters(List<FunctionParameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractFunctionParameterEvent> extends AbstractCModelChangedEvent.AbstractBuilder<T> {
        /**
         * This sets the parameters to the event.
         *
         * @param parameters
         *            the parameters.
         * @return self.
         */
        public AbstractBuilder<T> setParameters(List<FunctionParameter> parameters) {
            getEvent().setParameters(parameters);
            return this;
        }

        /**
         * This sets the function name to the event.
         *
         * @param functionName
         *            the function name.
         * @return self.
         */
        public AbstractBuilder<T> functionName(String functionName) {
            getEvent().setFunctionName(functionName);
            return this;
        }

        /**
         * This sets the index to the event.
         *
         * @param index
         *            the index.
         * @return self.
         */
        public AbstractBuilder<T> parameterIndex(int index) {
            return this;
        }

        @Override
        protected abstract T getEvent();
    }
}
