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
package org.eclipse.umlgen.dsl.asl.provider.custom;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.umlgen.dsl.asl.provider.AslItemProviderAdapterFactory;

/**
 * Specific item provider adapter factory to manage decorators in case of unresolved proxies.
 *
 * @author cnotot
 */
public class CustomAslItemProviderAdapterFactory extends AslItemProviderAdapterFactory {

    @Override
    public Adapter createGenericParamAdapter() {
        if (genericParamItemProvider == null) {
            genericParamItemProvider = new CustomGenericParamItemProvider(this);
        }
        return genericParamItemProvider;
    }

}
