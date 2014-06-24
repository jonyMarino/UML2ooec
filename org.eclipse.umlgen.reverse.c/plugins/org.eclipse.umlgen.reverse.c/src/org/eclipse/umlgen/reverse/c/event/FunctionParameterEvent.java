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

public abstract class FunctionParameterEvent extends CModelChangedEvent {

	private String functionName;

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

	public static abstract class Builder<T extends FunctionParameterEvent> extends CModelChangedEvent.Builder<T> {
		public Builder<T> setParameters(List<FunctionParameter> parameters) {
			getEvent().setParameters(parameters);
			return this;
		}

		public Builder<T> functionName(String functionName) {
			getEvent().setFunctionName(functionName);
			return this;
		}

		public Builder<T> parameterIndex(int index) {
			return this;
		}

		@Override
		protected abstract T getEvent();
	}
}
