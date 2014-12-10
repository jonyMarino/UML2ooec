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
package org.eclipse.umlgen.dsl.eth.presentation.custom;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.umlgen.dsl.asl.presentation.custom.CustomAslPropertySource;
import org.eclipse.umlgen.dsl.eth.EthPackage;
import org.eclipse.umlgen.dsl.eth.presentation.components.ComponentsPropertyDescriptor;
import org.eclipse.umlgen.dsl.eth.presentation.connectors.ConnectorsPropertyDescriptor;

/**
 * Specific property source to rout to its own descriptor with a specific editing dialog.
 *
 * @author cnotot
 */
public class CustomEthPropertySource extends CustomAslPropertySource {

    /**
     * Constructor.
     *
     * @param object
     *            The related object.
     * @param itemPropertySource
     *            The item property source.
     */
    public CustomEthPropertySource(Object object, IItemPropertySource itemPropertySource) {
        super(object, itemPropertySource);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.dsl.asl.presentation.custom.CustomAslPropertySource#createPropertyDescriptor(org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
     */
    @Override
    protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
        IPropertyDescriptor result = super.createPropertyDescriptor(itemPropertyDescriptor);
        if (itemPropertyDescriptor.getFeature(null).equals(EthPackage.Literals.ETHERNET_CONF__CONNECTORS)) {
            result = new ConnectorsPropertyDescriptor(object, itemPropertyDescriptor);
        } else if (itemPropertyDescriptor.getFeature(null).equals(EthPackage.Literals.CONTAINER__COMPONENTS)) {
            result = new ComponentsPropertyDescriptor(object, itemPropertyDescriptor);
        }
        return result;
    }

}
