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

public abstract class FunctionDeclarationEvent extends AbstractNamedEvent {

	/** the return type of the function */
	private String returnType;

	/** the parameters list */
	private List<FunctionParameter> parameters;

	/** the static modifier keyword */
	private boolean isStatic;

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

	protected void setIsSatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public abstract static class Builder<T extends FunctionDeclarationEvent> extends AbstractNamedEvent.Builder<T> {
		public Builder<T> setReturnType(String returnType) {
			getEvent().setReturnType(returnType);
			return this;
		}

		public Builder<T> setParameters(List<FunctionParameter> parameters) {
			getEvent().setParameters(parameters);
			return this;
		}

		public Builder<T> isStatic(boolean isStatic) {
			getEvent().setIsSatic(isStatic);
			return this;
		}
	}

}
