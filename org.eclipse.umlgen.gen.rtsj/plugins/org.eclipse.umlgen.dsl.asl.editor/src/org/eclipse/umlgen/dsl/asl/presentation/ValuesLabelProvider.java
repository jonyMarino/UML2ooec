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
package org.eclipse.umlgen.dsl.asl.presentation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.umlgen.dsl.asl.provider.AslEditPlugin;
import org.eclipse.umlgen.dsl.asl.provider.custom.util.AslUtil;

/**
 * Label provider to display a specific text when the related object is an unresolved proxy.
 * @author cnotot
 *
 */
public class ValuesLabelProvider extends LabelProvider {
	
	private LabelProvider itemLabelProvider;
	
	public ValuesLabelProvider(LabelProvider itemLabelProvider) {
		super();
		this.itemLabelProvider = itemLabelProvider;
	}
	
	@Override
	public String getText(Object object) {
		if (object instanceof EObject && AslUtil.isBrokenLink((EObject)object)){
			return "<deleted>";
		}
		return itemLabelProvider.getText(object);
	}

	@Override
	public Image getImage(Object object) {
		Object result = itemLabelProvider.getImage(object);
		if (object instanceof EObject && AslUtil.isBrokenLink((EObject)object)){
			result = AslUtil.getBrokenDecorator(object, result, AslEditPlugin.INSTANCE.getPluginResourceLocator());
		}
		
		return ExtendedImageRegistry.getInstance().getImage(result);	
	}
	
}
