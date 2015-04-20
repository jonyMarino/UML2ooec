/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Philippe Roland (Atos) - initialize API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.java.diagram.viewadapters;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * Interface for IElementType Adapter.
 */
public interface IElementTypeAwareAdapter extends IAdaptable {

    /**
     * Returns the element type.
     *
     * @return the element type
     */
    IElementType getElementType();

    /**
     * Returns the visual ID.
     *
     * @return the visual ID
     */
    int getVisualID();

    /**
     * Returns the string of visual ID.
     *
     * @return the string
     */
    String getSemanticHint();
}
