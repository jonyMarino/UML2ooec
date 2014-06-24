/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.resource;

import java.util.Map;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;

public class OrderedXMISaveImpl extends XMISaveImpl {
	public OrderedXMISaveImpl(XMLHelper helper) {
		super(helper);
	}

	public OrderedXMISaveImpl(Map<?, ?> options, XMLHelper helper, String encoding) {
		super(options, helper, encoding, "1.0");
	}

	public OrderedXMISaveImpl(Map<?, ?> options, XMLHelper helper, String encoding, String xmlVersion) {
		super(options, helper, encoding, xmlVersion);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void saveContainedMany(EObject o, EStructuralFeature f) {

		EList<EObject> valeurs = (EList<EObject>)helper.getValue(o, f);
		ECollections.sort(valeurs, new EObjectComparator());
		for (EObject valeur : valeurs) {
			if (valeur != null) {
				saveElement(valeur, f);
			}
		}
	}
}
