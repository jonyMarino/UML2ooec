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
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Removes a variable declaration<br>
 */
public class VariableDeclarationRemoved extends VariableDeclarationEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
				getUnitName());
		DataType myType = manager.findDataType(getCurrentTypeName());
		Property attribute = matchingClassifier.getAttribute(getCurrentName(), myType);
		if (attribute != null) {
			if (ModelUtil.isRemovable(attribute)) {
				// the attribute is first desctroyed
				attribute.destroy();

				// diagrams are updated consequently
				IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
				if (synchronizer instanceof IDiagramSynchronizer) {
					((IDiagramSynchronizer)synchronizer).removeRepresentation(attribute, manager);
				}

				// type is deleted if not more used elsewhere
				if (ModelUtil.isNotReferencedAnymore(myType)) {
					ModelUtil.destroy(myType);
				}
			} else {
				ModelUtil.setVisibility(attribute, getTranslationUnit(), EventType.REMOVE);
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<VariableDeclarationRemoved> builder() {
		return new Builder<VariableDeclarationRemoved>() {
			private VariableDeclarationRemoved event = new VariableDeclarationRemoved();

			/**
			 * @see org.eclipse.umlgen.reverse.c.event.VariableDeclarationEvent.Builder#getEvent()
			 */
			@Override
			protected VariableDeclarationRemoved getEvent() {
				return event;
			}
		};
	}
}
