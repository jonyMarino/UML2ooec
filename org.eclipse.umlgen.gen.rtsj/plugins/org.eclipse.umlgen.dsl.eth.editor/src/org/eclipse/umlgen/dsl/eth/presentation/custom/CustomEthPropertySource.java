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
 * @author cnotot
 *
 */
public class CustomEthPropertySource extends CustomAslPropertySource {

	public CustomEthPropertySource(Object object,
			IItemPropertySource itemPropertySource) {
		super(object, itemPropertySource);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected IPropertyDescriptor createPropertyDescriptor(
			IItemPropertyDescriptor itemPropertyDescriptor) {
		if (itemPropertyDescriptor.getFeature(null).equals(EthPackage.Literals.ETHERNET_CONF__CONNECTORS)){
			return new ConnectorsPropertyDescriptor(object, itemPropertyDescriptor);
		} else if (itemPropertyDescriptor.getFeature(null).equals(EthPackage.Literals.CONTAINER__COMPONENTS)) {
			return new ComponentsPropertyDescriptor(object, itemPropertyDescriptor);
		}
		return super.createPropertyDescriptor(itemPropertyDescriptor);
	}

}
