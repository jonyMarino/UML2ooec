/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.dsl.asl.presentation.custom;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.umlgen.dsl.asl.AslPackage;
import org.eclipse.umlgen.dsl.asl.presentation.elements.ElementsPropertyDescriptor;

/**
 * Specific property source to rout to its own descriptor with a <code>ValuesLabelProvider</code>.
 *
 * @author cnotot
 */
public class CustomAslPropertySource extends PropertySource {

    /**
     * Constructor.
     * 
     * @param object
     *            The related object.
     * @param itemPropertySource
     *            the item property source.
     */
    public CustomAslPropertySource(Object object, IItemPropertySource itemPropertySource) {
        super(object, itemPropertySource);
        // TODO Auto-generated constructor stub
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.edit.ui.provider.PropertySource#createPropertyDescriptor(org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
     */
    @Override
    protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
        if (itemPropertyDescriptor.getFeature(null).equals(AslPackage.Literals.GENERIC_PARAM__REFERENCES)) {
            return new ElementsPropertyDescriptor(object, itemPropertyDescriptor);
        }
        return super.createPropertyDescriptor(itemPropertyDescriptor);
    }

}
