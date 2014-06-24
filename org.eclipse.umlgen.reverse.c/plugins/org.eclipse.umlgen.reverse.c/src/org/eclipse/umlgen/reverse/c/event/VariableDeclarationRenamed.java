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

import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Event related to renaming of a variable declaration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class VariableDeclarationRenamed extends VariableDeclarationEvent {
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
	public static Builder<VariableDeclarationRenamed> builder() {
		return new Builder<VariableDeclarationRenamed>() {
			private VariableDeclarationRenamed event = new VariableDeclarationRenamed();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.VariableDeclarationEvent.Builder#getEvent()
			 */
			@Override
			protected VariableDeclarationRenamed getEvent() {
				return event;
			}
		};
	}
}
