/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to deletion of a structure.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class TypeDefStructRemoved extends TypeDefStructEvent {

	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		DataType localType = ModelUtil.findDataTypeInClassifier(matchingClassifier, getCurrentName());

		if (localType != null) {
			if (ModelUtil.isRemovable(localType)) {
				IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
				if (synchronizer instanceof IDiagramSynchronizer) {
					((IDiagramSynchronizer)synchronizer).removeRepresentation(localType, manager);
				}
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
	public static Builder<TypeDefStructRemoved> builder() {
		return new Builder<TypeDefStructRemoved>() {
			private TypeDefStructRemoved event = new TypeDefStructRemoved();

			/**
			 * @see org.eclipse.umlgen.reverse.c.TypeDefStructEvent#getEvent()
			 */
			@Override
			protected TypeDefStructRemoved getEvent() {
				return event;
			}
		};
	}
}
