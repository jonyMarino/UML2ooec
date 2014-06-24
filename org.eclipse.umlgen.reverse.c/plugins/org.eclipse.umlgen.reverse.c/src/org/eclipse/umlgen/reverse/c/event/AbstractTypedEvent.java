/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

/**
 * Abstract representation of an event related to a definition of a simple of composite type.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public abstract class AbstractTypedEvent extends AbstractNamedEvent {

	private String previousTypeName;

	private String currentTypeName;

	public String getCurrentTypeName() {
		return this.currentTypeName;
	}

	public String getPreviousTypeName() {
		return this.previousTypeName;
	}

	protected void setCurrentType(String currentTypeName) {
		this.currentTypeName = currentTypeName;
	}

	protected void setPreviousType(String setPreviousTypeName) {
		this.previousTypeName = setPreviousTypeName;
	}

	public static abstract class Builder<T extends AbstractTypedEvent> extends AbstractNamedEvent.Builder<T> {

		public Builder<T> currentType(String currentTypeName) {
			getEvent().setCurrentType(currentTypeName);
			return this;
		}

		public Builder<T> previousType(String previousTypeName) {
			getEvent().setCurrentType(previousTypeName);
			return this;
		}
	}

}
