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
import org.eclipse.umlgen.dsl.eth.Container;
import org.eclipse.umlgen.dsl.eth.provider.ContainerItemProvider;

/**
 * Specific item provider for the display of nodes related to containers.
 * @author cnotot
 * 
 */
public class CustomContainerItemProvider extends ContainerItemProvider {

	/** Delegate to the generic decorations item provider.*/
	private CustomDecorationItemProvider decorationDelegate;

	public CustomContainerItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		decorationDelegate = new CustomDecorationItemProvider(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		final String name = ((Container) object).getName();
		final String ip = ((Container) object).getIpAddress();
		int port = ((Container) object).getPortNumber();
		String result = name;
		if (ip != null && ip.length() > 0)
			result += (" " + ip);
		if (port != 0)
			result += (":" + port);

		return result == null || result.length() == 0 ? getString("_UI_Container_type")
				: getString("_UI_Container_type") + " " + result;
	}

	@Override
	public Object getImage(Object object) {
		return decorationDelegate.getImage(object, super.getImage(object));
	}

}
