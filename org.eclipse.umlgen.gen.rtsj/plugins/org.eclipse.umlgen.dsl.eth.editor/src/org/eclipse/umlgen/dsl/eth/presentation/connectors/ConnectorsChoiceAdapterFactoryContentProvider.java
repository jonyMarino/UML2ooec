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
package org.eclipse.umlgen.dsl.eth.presentation.connectors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Port;
import org.eclipse.umlgen.dsl.eth.presentation.util.Requestor;

/**
 * Specific content provider to display the choice of values amongst connectors.
 * 
 * @author cnotot
 * 
 */
public class ConnectorsChoiceAdapterFactoryContentProvider extends
		AdapterFactoryContentProvider {

	/** The selected values/connectors */
	private List<?> values;

	public ConnectorsChoiceAdapterFactoryContentProvider(
			AdapterFactory adapterFactory, List<?> values) {
		super(adapterFactory);
		this.values = values;
	}

	@Override
	public Object[] getElements(Object object) {
		List<EObject> result = new ArrayList<EObject>();
		Object[] objects = super.getElements(object);
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof Namespace
					&& ((Namespace) objects[i]).eContainer() instanceof Model
					&& getChildren(objects[i]).length > 0) {
				result.add((EObject) objects[i]);
			}
		}
		return result.toArray();
	}

	@Override
	public Object[] getChildren(Object object) {
		final List<EObject> result = new ArrayList<EObject>();
		if (isComponent(object)) {
			result.addAll(getOutgoingConnectors((org.eclipse.uml2.uml.Class) object));
		} else if (object instanceof Namespace) {
			final ItemProvider input = (ItemProvider) viewer.getInput();
			for (Object obj : input.getChildren()) {
				if (obj instanceof Namespace
						&& ((EObject) obj).eContainer() == object
						&& hasChildren(obj)) {
					result.add((EObject) obj);
				}
			}
		}

		return result.toArray();
	}

	/**
	 * From the given class/component, get the non selected outgoing connectors.
	 * 
	 * @param component
	 *            The given class
	 * @return The list of outgoing connectors.
	 */
	private List<Connector> getOutgoingConnectors(
			org.eclipse.uml2.uml.Class component) {
		final List<Connector> result = new ArrayList<Connector>();
		final Iterator<Port> ports = component.getOwnedPorts().iterator();
		while (ports.hasNext()) {
			final Port port = ports.next();
			if (port.getType() == null && port.getEnds().size() > 0) {
				for (Connector connector : Requestor.getConnectors(port)) {
					if (!values.contains(connector)) {
						result.add(connector);
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean hasChildren(Object object) {
		return getChildren(object).length > 0;
	}

	public boolean isComponent(Object obj) {
		return obj instanceof org.eclipse.uml2.uml.Class
				&& ((org.eclipse.uml2.uml.Class) obj).getOwnedPorts().size() > 0;
	}

}
