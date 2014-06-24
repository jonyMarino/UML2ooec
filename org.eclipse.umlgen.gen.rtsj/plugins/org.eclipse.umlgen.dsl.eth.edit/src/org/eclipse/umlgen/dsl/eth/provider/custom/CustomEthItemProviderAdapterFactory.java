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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.umlgen.dsl.eth.provider.EthItemProviderAdapterFactory;

/**
 * Specific factory to specialize item providers of containers and ethernet configurations.
 * @author cnotot
 *
 */
public class CustomEthItemProviderAdapterFactory extends EthItemProviderAdapterFactory {
	
	@Override
	public Adapter createContainerAdapter() {
		if (containerItemProvider == null) {
			containerItemProvider = new CustomContainerItemProvider(this);
		}
		return containerItemProvider;
	}
	
	@Override
	public Adapter createEthernetConfAdapter() {
		if (ethernetConfItemProvider == null) {
			ethernetConfItemProvider = new CustomEthernetConfItemProvider(this);
		}
		return ethernetConfItemProvider;
	}
}
