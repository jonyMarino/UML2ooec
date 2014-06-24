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

import java.util.List;

import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;

/**
 * Abstract representation of an event related to a type definition for a function declaration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class TypeDefFunctionDeclarationEvent extends CModelChangedEvent {

	private String previousName;

	private String currentName;

	private String returnType;

	private List<FunctionParameter> parameters;

	private String visibility;

	public String getPreviousName() {
		return this.previousName;
	}

	public String getCurrentName() {
		return this.currentName;
	}

	public String getReturnType() {
		return this.returnType;
	}

	public List<FunctionParameter> getParameters() {
		return this.parameters;
	}

	public String getVisibility() {
		return this.visibility;
	}

	protected void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	protected void setPreviousName(String previousName) {
		this.previousName = previousName;
	}

	protected void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	protected void setParameters(List<FunctionParameter> parameters) {
		this.parameters = parameters;
	}

	protected void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public static abstract class Builder<T extends TypeDefFunctionDeclarationEvent> extends CModelChangedEvent.Builder<T> {

		public Builder<T> variableName(String functionName) {
			getEvent().setCurrentName(functionName);
			return this;
		}

		public Builder<T> currentName(String currentName) {
			getEvent().setCurrentName(currentName);
			return this;
		}

		public Builder<T> previousName(String previousName) {
			getEvent().setPreviousName(previousName);
			return this;
		}

		public Builder<T> setVisibility(String visibility) {
			getEvent().setVisibility(visibility);
			return this;
		}

		public Builder<T> setReturnType(String returnType) {
			getEvent().setReturnType(returnType);
			return this;
		}

		public Builder<T> setParameters(List<FunctionParameter> parameters) {
			getEvent().setParameters(parameters);
			return this;
		}
	}
}
