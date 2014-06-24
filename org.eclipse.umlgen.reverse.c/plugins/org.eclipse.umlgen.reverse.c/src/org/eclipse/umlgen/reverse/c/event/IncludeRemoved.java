/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.core.runtime.Path;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to a deletion of an include declaration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class IncludeRemoved extends IncludeEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Classifier unitClass = ModelUtil.findClassifierInPackage(manager.getSourcePackage(), getUnitName());
		String usageName = new Path(getCurrentName()).removeFileExtension().toString();
		Dependency usage = unitClass.getClientDependency(usageName);
		if (usage != null) {
			if (ModelUtil.isRemovable(usage)) {
				IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
				if (synchronizer instanceof IDiagramSynchronizer) {
					((IDiagramSynchronizer)synchronizer).removeRepresentation(usage, manager);
				}
				usage.destroy();
			} else {
				// decrease the visibility
				ModelUtil.setVisibility(usage, getTranslationUnit(), EventType.REMOVE);
			}
			if (ModelUtil.isNotReferencedAnymore(usage.getSupplier(usage.getName()))) {
				usage.getSupplier(usage.getName()).destroy();
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<IncludeRemoved> builder() {
		return new Builder<IncludeRemoved>() {
			private IncludeRemoved event = new IncludeRemoved();

			/**
			 * @see org.eclipse.umlgen.reverse.c.IncludeBuilder#getEvent()
			 */
			@Override
			protected IncludeRemoved getEvent() {
				return event;
			}
		};
	}
}
