/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     	Mikael BARBERO (Obeo) - initial API and implementation
 * 		Christophe LE CAMUS (CS-SI) - Major evolution
 * 		Sebastien GABEl (CS-SI) - Refactoring
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to addition of a type definition of an enumeration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class TypeDefEnumerationAdded extends TypeDefEnumerationEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		// Retrieves the created data type or create it if not existing
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		Enumeration myTypeDef = ModelUtil.findEnumerationRedefinitionInClassifier(matchingClassifier,
				getCurrentName());
		if (myTypeDef == null) {
			if (matchingClassifier instanceof Class) {
				myTypeDef = (Enumeration)((Class)matchingClassifier).createNestedClassifier(getCurrentName(),
						UMLPackage.Literals.ENUMERATION);
			} else if (matchingClassifier instanceof Interface) {
				myTypeDef = (Enumeration)((Interface)matchingClassifier).createNestedClassifier(
						getCurrentName(), UMLPackage.Literals.ENUMERATION);
			}
		}

		// Set the right visibility
		ModelUtil.setVisibility(myTypeDef, getTranslationUnit(), EventType.ADD);

		// Destroy previous existing type in type pck
		DataType existingType = manager.findDataTypeInTypesPck(getCurrentName());
		if (existingType != null && myTypeDef != null) {
			ModelUtil.redefineType(existingType, myTypeDef);
			existingType.destroy();
		}

		String redefinedEnumerationName = ModelUtil.computeAnonymousTypeName(getUnitName(),
				getRedefinedEnumerationName(), getSource());
		Enumeration redefinedEnumeration = ModelUtil.findEnumerationInClassifier(matchingClassifier,
				redefinedEnumerationName);
		myTypeDef.getRedefinedClassifiers().add(redefinedEnumeration);

		// re-order the elements => redefined type is placed before the defined
		// type
		if (matchingClassifier instanceof Class) {
			Class theClass = (Class)matchingClassifier;
			int redefinedIndex = theClass.getNestedClassifiers().indexOf(myTypeDef);
			int previousIndex = redefinedIndex - 1;
			Classifier previousClassifier = theClass.getNestedClassifiers().get(previousIndex);
			if (!previousClassifier.getRedefinedClassifiers().contains(redefinedEnumeration)) {
				theClass.getNestedClassifiers().move(previousIndex, redefinedEnumeration);
			}
		} else if (matchingClassifier instanceof Interface) {
			Interface theInterface = (Interface)matchingClassifier;
			int redefinedIndex = theInterface.getNestedClassifiers().indexOf(myTypeDef);
			int previousIndex = redefinedIndex - 1;
			Classifier previousClassifier = theInterface.getNestedClassifiers().get(previousIndex);
			if (!previousClassifier.getRedefinedClassifiers().contains(redefinedEnumeration)) {
				theInterface.getNestedClassifiers().move(previousIndex, redefinedEnumeration);
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<TypeDefEnumerationAdded> builder() {
		return new Builder<TypeDefEnumerationAdded>() {
			private TypeDefEnumerationAdded event = new TypeDefEnumerationAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.CModelChangedEvent.Builder#getEvent()
			 */
			@Override
			protected TypeDefEnumerationAdded getEvent() {
				return event;
			}
		};
	}
}
