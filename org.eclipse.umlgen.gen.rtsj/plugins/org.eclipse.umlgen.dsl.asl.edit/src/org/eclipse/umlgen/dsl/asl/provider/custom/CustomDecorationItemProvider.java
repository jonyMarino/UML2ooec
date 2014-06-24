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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.umlgen.dsl.asl.Decoration;
import org.eclipse.umlgen.dsl.asl.provider.AslEditPlugin;
import org.eclipse.umlgen.dsl.asl.provider.custom.util.AslUtil;

/**
 * Specific item provider to manage unresolved proxies decoration on decoration
 * objects.
 * 
 * @author cnotot
 * 
 */
public class CustomDecorationItemProvider extends ItemProviderAdapter {

	public CustomDecorationItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * It returns the given <code>image</code> decorated with a decorator (red
	 * cross) if the given <code>object</code> references an unresolved proxy.
	 * 
	 * @param object the current object.
	 * @param image The image related to the given object.
	 * @return The new decorated image.
	 */
	public Object getImage(Object object, Object image) {
		if (AslUtil.containsBrokenLink((Decoration) object)) {
			return AslUtil.getBrokenDecorator(object, image,
					AslEditPlugin.INSTANCE.getPluginResourceLocator());
		} else {
			return image;
		}
	}

}
