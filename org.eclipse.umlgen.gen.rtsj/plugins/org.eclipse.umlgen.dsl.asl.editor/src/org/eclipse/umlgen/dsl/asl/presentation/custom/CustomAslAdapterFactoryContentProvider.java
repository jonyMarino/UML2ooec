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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Specific factory to rout to its own descriptor with a <code>ValuesLabelProvider</code>.
 * @author cnotot
 *
 */
public class CustomAslAdapterFactoryContentProvider extends AdapterFactoryContentProvider {

	public CustomAslAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected IPropertySource createPropertySource(Object object,
			IItemPropertySource itemPropertySource) {
		// TODO Auto-generated method stub
		return new CustomAslPropertySource(object, itemPropertySource);
	}

}
