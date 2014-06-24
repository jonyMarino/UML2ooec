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

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.umlgen.c.common.util.AnnotationUtil;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;

/**
 * Event related to an addition of an Ifndef declaration.
 *
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class IfndefAdded extends IfndefEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		// Check the Class is already created in the UML model and create it if
		// needed
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		AnnotationUtil.setIfndefConditionDetailEntry(matchingClassifier, getCondition());
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<IfndefAdded> builder() {
		return new Builder<IfndefAdded>() {
			private IfndefAdded event = new IfndefAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.IfndefBuilder#getEvent()
			 */
			@Override
			protected IfndefAdded getEvent() {
				return event;
			}
		};
	}

}
