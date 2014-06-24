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

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.AnnotationUtil;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;

public class CUnitAdded extends CUnitEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		String name = getCurrentName().removeFileExtension().toString();
		Classifier matchingClassifier = ModelUtil.findMatchingClassifier(manager, getTranslationUnit(), name);

		// interface contained into Src Artefact that can be null
		Interface relatedInterface = findInterfaceAlreadyCreated(manager, name);

		// interface contained into Libs that can be null
		Interface extInterface = findExternalInterfaceAlreadyCreated(manager, name);

		if (extInterface != null) {
			// external interface exists !!!
			// need to update cross references + destroy the former element from
			// libs package
			ModelUtil.redefineType(extInterface, matchingClassifier);
			extInterface.destroy();
		} else if (relatedInterface != null && matchingClassifier instanceof Class) {
			// content of the interface must be copied into the matching
			// classifier
			Class theClass = (Class)matchingClassifier;

			theClass.getOwnedAttributes().addAll(relatedInterface.getAllAttributes());
			theClass.getOwnedOperations().addAll(relatedInterface.getAllOperations());
			theClass.getNestedClassifiers().addAll(relatedInterface.getNestedClassifiers());

			// finally, the former interface msut be removed from the model
			ModelUtil.redefineType(relatedInterface, theClass);
			relatedInterface.destroy();
		}

		// set the detail entry (H|C)_FILENAME containing the file location
		AnnotationUtil.setFileLocationDetailEntry(matchingClassifier, getTranslationUnit());

		// deduce the visibility to set considering that eventually the related
		// interface has been removed
		ModelUtil.setVisibility(matchingClassifier);

	}

	/**
	 * Looks for a given interface into the 'Libs' package.
	 *
	 * @param manager
	 *            The model manager
	 * @param name
	 *            The name of the classifier to look for
	 * @return the interface or null if not found.
	 */
	private Interface findExternalInterfaceAlreadyCreated(ModelManager manager, String name) {
		return (Interface)manager.getLibsPackage().getPackagedElement(name, false,
				UMLPackage.Literals.INTERFACE, false);
	}

	/**
	 * Looks for a given interface into the 'Libs' package.
	 *
	 * @param manager
	 *            The model manager
	 * @param name
	 *            The name of the classifier to look for
	 * @return the interface or null if not found.
	 */
	private Interface findInterfaceAlreadyCreated(ModelManager manager, String name) {
		return ModelUtil.findInterfaceFromPackage(manager.getSourcePackage(), name);
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<CUnitAdded> builder() {
		return new Builder<CUnitAdded>() {
			private CUnitAdded event = new CUnitAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.CUnitEvent.Builder#getEvent()
			 */
			@Override
			protected CUnitAdded getEvent() {
				return event;
			}
		};
	}
}
