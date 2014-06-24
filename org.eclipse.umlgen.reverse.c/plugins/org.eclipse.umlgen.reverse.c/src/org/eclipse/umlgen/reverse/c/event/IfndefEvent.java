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
 * Abstract representation of an event related to a Ifndef.
 *
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class IfndefEvent extends CModelChangedEvent {

	private String condition;

	public String getCondition() {
		return condition;
	}

	protected void setCondition(char[] condition) {
		this.condition = new String(condition);
	}

	public static abstract class Builder<T extends IfndefEvent> extends CModelChangedEvent.Builder<T> {
		public Builder<T> setCondition(char[] condition) {
			getEvent().setCondition(condition);
			return this;
		}

		@Override
		protected abstract T getEvent();
	}

}
