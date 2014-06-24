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
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.AnnotationUtil;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

public class CUnitRemoved extends CUnitEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier matchingClassifier = ModelUtil.findMatchingClassifier(manager, getTranslationUnit(),
				getCurrentName().removeFileExtension().toString());
		if (matchingClassifier != null) {

			ModelUtil.setVisibility(matchingClassifier, getTranslationUnit(), EventType.REMOVE);
			AnnotationUtil.removeEAnnotations(matchingClassifier, getTranslationUnit());

			/*
			 * check if the class must be destroyed : if we delete only one file .c or .h it is not the case
			 */
			if (getTranslationUnit().isHeaderUnit()) {
				// delete all private model objects from this class
				ModelUtil.deleteAllVisibleObjects(matchingClassifier, VisibilityKind.PUBLIC_LITERAL, manager);
			} else if (getTranslationUnit().isSourceUnit()) {
				// delete all public model objects from this class
				ModelUtil
				.deleteAllVisibleObjects(matchingClassifier, VisibilityKind.PRIVATE_LITERAL, manager);
			}

			/* only if no details persists then we can delete the class */
			if (ModelUtil.isRemovable(matchingClassifier)) {
				IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
				if (synchronizer instanceof IDiagramSynchronizer) {
					((IDiagramSynchronizer)synchronizer).removeRepresentation(matchingClassifier, manager);
				}
				matchingClassifier.destroy();
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<CUnitRemoved> builder() {
		return new Builder<CUnitRemoved>() {
			private CUnitRemoved event = new CUnitRemoved();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.CUnitEvent.Builder#getEvent()
			 */
			@Override
			protected CUnitRemoved getEvent() {
				return event;
			}
		};
	}
}
