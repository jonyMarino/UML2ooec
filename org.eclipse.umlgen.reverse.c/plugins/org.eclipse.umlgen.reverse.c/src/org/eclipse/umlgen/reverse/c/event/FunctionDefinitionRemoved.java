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

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;

/**
 * Removes a {@link OpaqueBehavior} from a given {@link Class}.
 */
public class FunctionDefinitionRemoved extends FunctionDeclarationEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Class matchingClassifier = ModelUtil.findClassInPackage(manager.getSourcePackage(), getUnitName());
		assert matchingClassifier != null;

		OpaqueBehavior function = (OpaqueBehavior)matchingClassifier.getOwnedBehavior(getCurrentName(),
				false, UMLPackage.Literals.OPAQUE_BEHAVIOR, false);
		if (function != null) {
			if (ModelUtil.isRemovable(function)) {
				function.destroy();
				IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
				if (synchronizer instanceof IDiagramSynchronizer) {
					((IDiagramSynchronizer)synchronizer).removeRepresentation(function, manager);
				}

				// deduce if the parameter types can be deleted from the model
				for (FunctionParameter aParameter : getParameters()) {
					DataType parameterType = manager.getDataType(aParameter.getType());
					if (ModelUtil.isNotReferencedAnymore(parameterType)) {
						ModelUtil.destroy(parameterType);
					}
				}
				// deduce if the parameter types can be deleted from the model
				DataType returnType = manager.getDataType(getReturnType());
				if (ModelUtil.isNotReferencedAnymore(returnType)) {
					ModelUtil.destroy(returnType);
				}
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<FunctionDefinitionRemoved> builder() {
		return new Builder<FunctionDefinitionRemoved>() {
			private FunctionDefinitionRemoved event = new FunctionDefinitionRemoved();

			/**
			 * @see org.eclipse.umlgen.reverse.c.FunctionBuilder#getEvent()
			 */
			@Override
			protected FunctionDefinitionRemoved getEvent() {
				return event;
			}
		};
	}
}
