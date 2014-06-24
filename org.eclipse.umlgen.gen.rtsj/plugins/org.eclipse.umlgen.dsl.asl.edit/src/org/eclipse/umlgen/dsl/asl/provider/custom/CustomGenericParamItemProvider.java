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
import org.eclipse.umlgen.dsl.asl.provider.GenericParamItemProvider;

/**
 * Specific item provider for generic parameters in order to benefit from
 * unresolved proxies decoration.
 * 
 * @author cnotot
 * 
 */
public class CustomGenericParamItemProvider extends GenericParamItemProvider {

	private CustomDecorationItemProvider decorationDelegate;

	public CustomGenericParamItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		decorationDelegate = new CustomDecorationItemProvider(adapterFactory);
	}

	@Override
	public Object getImage(Object object) {
		return decorationDelegate.getImage(object, super.getImage(object));
	}

}
