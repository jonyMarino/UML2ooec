/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.umlgen.c.common.util.ModelManager;

public class CUnitRenamed extends CUnitEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		// TODO : to be implemented
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<CUnitRenamed> builder() {
		return new Builder<CUnitRenamed>() {
			private CUnitRenamed event = new CUnitRenamed();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.CUnitEvent.Builder#getEvent()
			 */
			@Override
			protected CUnitRenamed getEvent() {
				return event;
			}
		};
	}
}
