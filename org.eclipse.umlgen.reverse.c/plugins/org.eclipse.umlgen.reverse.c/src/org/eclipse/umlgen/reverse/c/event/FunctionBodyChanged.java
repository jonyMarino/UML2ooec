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

import org.eclipse.core.resources.IMarker;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.reverse.c.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.util.EMFMarkerUtil;

/**
 * Event related to changes performed on a function body.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class FunctionBodyChanged extends FunctionBodyEvent {
	/**
	 * @see org.eclipse.umlgen.reverse.c.CModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
	 */
	@Override
	public void notifyChanges(ModelManager manager) {
		Class myClass = ModelUtil.findClassInPackage(manager.getSourcePackage(), getUnitName());
		if (myClass != null) {
			// if a behavior already exists we link the operation to the
			// behavior
			OpaqueBehavior function = (OpaqueBehavior)myClass.getOwnedBehavior(getCurrentName(), false,
					UMLPackage.Literals.OPAQUE_BEHAVIOR, false);
			if (function != null) {
				try {
					String oldBody = getOldBody();
					String newBoby = cleanInvalidXmlChars(getBody());
					if (!oldBody.equals(newBoby)) {
						EMFMarkerUtil.removeMarkerFor(function);

						// removes the former function body
						function.getBodies().remove(oldBody);
						// sets the new function body
						function.getBodies().add(newBoby);

						EMFMarkerUtil
								.addMarkerFor(
										function,
										"Function behavior body has changed. Existing Activity diagrams need to be reversed.",
										IMarker.SEVERITY_WARNING);
					}
				} catch (Exception e) {
					Activator.log(e);
				}
			}
		}
	}

	/**
	 * Gets the right builder
	 *
	 * @return the builder for this event
	 */
	public static Builder<FunctionBodyChanged> builder() {
		return new Builder<FunctionBodyChanged>() {
			private FunctionBodyChanged event = new FunctionBodyChanged();

			/**
			 * @see org.eclipse.umlgen.reverse.c.FunctionBodyBuilder#getEvent()
			 */
			@Override
			protected FunctionBodyChanged getEvent() {
				return event;
			}
		};
	}
}
