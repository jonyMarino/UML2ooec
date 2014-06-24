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

public abstract class FunctionBodyEvent extends CModelChangedEvent {

	private String currentName;

	private String body;

	private String oldBody;

	/**
	 * @param currentName
	 *            the currentName to set
	 */
	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	/**
	 * @return the currentName
	 */
	public String getCurrentName() {
		return currentName;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param oldBody
	 *            the oldBody to set
	 */
	public void setOldBody(String oldBody) {
		this.oldBody = oldBody;
	}

	/**
	 * @return the oldBody
	 */
	public String getOldBody() {
		return oldBody;
	}

	public static abstract class Builder<T extends FunctionBodyEvent> extends CModelChangedEvent.Builder<T> {

		public Builder<T> currentName(String currentName) {
			getEvent().setCurrentName(currentName);
			return this;
		}

		public Builder<T> setBody(String body) {
			getEvent().setBody(body);
			return this;
		}

		public Builder<T> setOldBody(String body) {
			getEvent().setOldBody(body);
			return this;
		}

		@Override
		protected abstract T getEvent();
	}

}
