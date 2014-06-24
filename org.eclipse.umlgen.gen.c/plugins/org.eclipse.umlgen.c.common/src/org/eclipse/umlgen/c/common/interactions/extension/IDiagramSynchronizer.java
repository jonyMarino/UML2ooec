/*******************************************************************************
 * Copyright (c) 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.common.interactions.extension;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Diagram Synchronizer interface to interact with graphical models
 */
public interface IDiagramSynchronizer extends IModelSynchronizer {

	void removeRepresentation(EObject object, ModelManager mngr);

	EClass getRepresentationKind();

	String getPresentation(Object object);

}
