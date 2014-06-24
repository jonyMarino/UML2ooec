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

import org.eclipse.core.runtime.Path;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.umlgen.c.common.util.AnnotationUtil;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to an addition of an include declaration.
 *
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class IncludeAdded extends IncludeEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		// Check the classifier is already created into the UML model and
		// creates it if needed
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		String includedLibraryName = new Path(getCurrentName()).removeFileExtension().toString();

		// test if a dependency already exists into the model before creating
		// it.
		Usage usage = ModelUtil.findUsage(matchingClassifier, includedLibraryName);
		if (usage == null) {
			// try to retrieve a classifier (means Class or Interface) into the
			// src package
			Classifier distantDependency = (Classifier)manager.getSourcePackage().getPackagedElement(
					includedLibraryName, false, UMLPackage.Literals.CLASSIFIER, false);

			// distantDependency not found means we need to create an interface
			// into Libs package
			// whereas standard libraries are considered to be included as
			// "< libraryName >" but is not already the
			// case.
			if (distantDependency == null || distantDependency instanceof Class
					&& VisibilityKind.PRIVATE_LITERAL.equals(distantDependency.getVisibility())) {
				distantDependency = (Interface)manager.getLibsPackage().getPackagedElement(
						includedLibraryName, false, UMLPackage.Literals.INTERFACE, true);
			}

			// the usage is created, the name is set with the name of the
			// current library name
			usage = matchingClassifier.createUsage(distantDependency);
			// by default, the name of the included library is given.
			usage.setName(includedLibraryName);

			ModelUtil.setVisibility(usage, getTranslationUnit(), EventType.ADD);
		} else if (usage.getVisibility() == VisibilityKind.PRIVATE_LITERAL) {
			// move this usage to the end of this reference list
			int newIndex = matchingClassifier.getClientDependencies().size() - 1;
			matchingClassifier.getClientDependencies().move(newIndex, usage);
			ModelUtil.setVisibility(usage, getTranslationUnit(), EventType.ADD);
		}

		AnnotationUtil.setLibraryDetailEntry(usage, getIsStandard());
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<IncludeAdded> builder() {
		return new Builder<IncludeAdded>() {
			private IncludeAdded event = new IncludeAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.IncludeBuilder#getEvent()
			 */
			@Override
			protected IncludeAdded getEvent() {
				return event;
			}
		};
	}

}
