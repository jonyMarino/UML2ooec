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
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;
import org.eclipse.umlgen.reverse.c.util.DiagramUtil;

/**
 * Event related to deletion of a type definition for a function declaration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class TypeDefFunctionDeclarationRemoved extends TypeDefFunctionDeclarationEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		Classifier localType = ModelUtil.findDataTypeInClassifier(matchingClassifier, getCurrentName());
		if (localType != null) {
			if (ModelUtil.isRemovable(localType)) {
				DiagramUtil.removeGraphicalRepresentation(localType, manager);
				localType.destroy();
			} else {
				ModelUtil.setVisibility(localType, getTranslationUnit(), EventType.REMOVE);
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<TypeDefFunctionDeclarationRemoved> builder() {
		return new Builder<TypeDefFunctionDeclarationRemoved>() {
			private TypeDefFunctionDeclarationRemoved event = new TypeDefFunctionDeclarationRemoved();

			/**
			 * @see org.eclipse.umlgen.reverse.c.TypeDefFunctionDeclarationEvent#getEvent()
			 */
			@Override
			protected TypeDefFunctionDeclarationRemoved getEvent() {
				return event;
			}
		};
	}

}
