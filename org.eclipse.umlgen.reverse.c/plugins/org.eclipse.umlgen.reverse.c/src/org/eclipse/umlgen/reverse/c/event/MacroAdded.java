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

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to an addition of a macro.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class MacroAdded extends MacroEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges()
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		Type myType = ModelUtil.getType(manager, matchingClassifier, BundleConstants.MACRO_TYPE);
		Property attribute = matchingClassifier.getAttribute(getCurrentName(), myType);
		if (attribute == null) {
			if (matchingClassifier instanceof Class) {
				attribute = ((Class)matchingClassifier).createOwnedAttribute(getCurrentName(), myType);
			}
			if (matchingClassifier instanceof Interface) {
				attribute = ((Interface)matchingClassifier).createOwnedAttribute(getCurrentName(), myType);
			}
		}

		LiteralString literalString = (LiteralString)attribute.createDefaultValue(
				BundleConstants.READONLY_VALUE, null, UMLPackage.Literals.LITERAL_STRING);
		literalString.setValue(cleanInvalidXmlChars(getExpansion()));

		ModelUtil.setVisibility(attribute, getTranslationUnit(), EventType.ADD);
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<MacroAdded> builder() {
		return new Builder<MacroAdded>() {
			private MacroAdded event = new MacroAdded();

			/**
			 * @see org.eclipse.umlgen.reverse.c.MacroBuilder#getEvent()
			 */
			@Override
			protected MacroAdded getEvent() {
				return event;
			}
		};
	}
}
