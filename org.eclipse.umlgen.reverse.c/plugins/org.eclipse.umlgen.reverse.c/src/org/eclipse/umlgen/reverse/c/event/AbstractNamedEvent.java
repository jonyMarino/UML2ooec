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
public abstract class AbstractNamedEvent extends CModelChangedEvent {

	private String previousName;

	private String currentName;

	public String getPreviousName() {
		return this.previousName;
	}

	public String getCurrentName() {
		return this.currentName;
	}

	protected void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	protected void setPreviousName(String previousName) {
		this.previousName = previousName;
	}

	public static abstract class Builder<T extends AbstractNamedEvent> extends CModelChangedEvent.Builder<T> {
		public Builder<T> currentName(String currentName) {
			getEvent().setCurrentName(currentName);
			return this;
		}

		public Builder<T> previousName(String previousName) {
			getEvent().setPreviousName(previousName);
			return this;
		}
	}
}
