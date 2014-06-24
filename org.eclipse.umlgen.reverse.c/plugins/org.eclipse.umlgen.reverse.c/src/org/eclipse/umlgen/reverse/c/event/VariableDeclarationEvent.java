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
public abstract class VariableDeclarationEvent extends AbstractTypedEvent {
	private boolean isStatic;

	private boolean isExtern;

	private boolean isVolatile;

	private boolean isConst;

	private boolean isRegister;

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

	public abstract static class Builder<T extends VariableDeclarationEvent> extends AbstractTypedEvent.Builder<T> {

		public Builder<T> setIsStatic(boolean isStatic) {
			getEvent().setIsStatic(isStatic);
			return this;
		}

		public Builder<T> setConst(boolean isConst) {
			getEvent().setIsConst(isConst);
			return this;
		}

		public Builder<T> setExtern(boolean isExtern) {
			getEvent().setIsExtern(isExtern);
			return this;
		}

		public Builder<T> setVolatile(boolean isVolatile) {
			getEvent().setIsVolatile(isVolatile);
			return this;
		}

		public Builder<T> setRegister(boolean isRegister) {
			getEvent().setIsRegister(isRegister);
			return this;
		}

		public Builder<T> setInitializerExpression(String expression) {
			getEvent().setInitializationExpression(expression);
			return this;
		}
	}
}
