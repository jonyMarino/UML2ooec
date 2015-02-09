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
package org.eclipse.umlgen.dsl.eth.provider.custom;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.umlgen.dsl.asl.provider.custom.CustomDecorationItemProvider;
import org.eclipse.umlgen.dsl.eth.provider.EthernetConfItemProvider;

/**
 * Specific item provider for the display of nodes related to ethernet configurations.
 *
 * @author cnotot
 */
public class CustomEthernetConfItemProvider extends EthernetConfItemProvider {

    /** Delegate to the generic decorations item provider. */
    private CustomDecorationItemProvider decorationDelegate;

    /**
     * Constructor.
     *
     * @param adapterFactory
     *            The adapter factory.
     */
    public CustomEthernetConfItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
        decorationDelegate = new CustomDecorationItemProvider(adapterFactory);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.dsl.eth.provider.EthernetConfItemProvider#getImage(java.lang.Object)
     */
    @Override
    public Object getImage(Object object) {
        return decorationDelegate.getImage(object, super.getImage(object));
    }

}
