/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    JF Rolland (Atos) jean-francois.rolland@atos.net
 *    P Roland (Atos) philippe.roland@atos.net
 *    F Vivares (Atos) florence.vivares@atos.net
 *******************************************************************************/
package org.eclipse.umlgen.reverse.java.diagram.viewadapters;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * Wraps an IElementType to adapt it to the IAdaptable Eclipse platform API.
 */
public class EObjectAndElementTypeAdapter extends EObjectAdapter implements IElementTypeAwareAdapter {

    /**
     * The element type.
     */
    private final IElementType myElementType;

    /**
     * The visual ID.
     */
    private final int myVisualId;

    /**
     * Constructor.
     *
     * @param subject
     * @param elementType
     * @param visualId
     */
    public EObjectAndElementTypeAdapter(EObject subject, IElementType elementType, int visualId) {

        super(subject);
        myElementType = elementType;
        myVisualId = visualId;
    }

    @Override
    public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
        if (adapter.isInstance(myElementType)) {
            return myElementType;
        }
        return super.getAdapter(adapter);
    }

    public IElementType getElementType() {
        return myElementType;
    }

    public int getVisualId() {
        return myVisualId;
    }

    public String getSemanticHint() {
        return Integer.toString(myVisualId);
    }

    public int getVisualID() {
        return myVisualId;
    }
}
