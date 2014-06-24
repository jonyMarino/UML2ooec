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
package org.eclipse.umlgen.dsl.eth.presentation.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Property;
import org.eclipse.umlgen.dsl.asl.Parameter;
import org.eclipse.umlgen.dsl.eth.Container;
import org.eclipse.umlgen.dsl.eth.EthernetConf;

/**
 * Specific content provider to display the choice of values amongst component
 * properties.
 * 
 * @author cnotot
 * 
 */
public class ComponentsChoiceAdapterFactoryContentProvider extends
		AdapterFactoryContentProvider {

	/** The selected values/properties */
	private List<?> values;

	/** The related container owning these properties */
	private Container container;

	/** The selected values/properties in the other containers */
	private List<Property> propertiesInOtherContainers;

	public ComponentsChoiceAdapterFactoryContentProvider(
			AdapterFactory adapterFactory, List<?> values, Object object) {
		super(adapterFactory);
		this.values = values;
		if (object instanceof Container) {
			container = (Container) object;
		}
		propertiesInOtherContainers = getpropertiesInOtherContainers();
	}

	public ComponentsChoiceAdapterFactoryContentProvider(
			AdapterFactory adapterFactory) {
		super(adapterFactory);
		this.values = Collections.EMPTY_LIST;
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
		final ItemProvider input = (ItemProvider) viewer.getInput();
		for (Object obj : input.getChildren()) {
			if (obj instanceof EObject
					&& ((EObject) obj).eContainer() == object
					&& (hasChildren(obj) || (isCandidate(obj) && hasToDisplayEmptyItems(obj)))) {
				result.add((EObject) obj);
			}
		}
		return result.toArray();
	}

	@Override
	public boolean hasChildren(Object object) {
		return getChildren(object).length > 0;
	}

	/**
	 * Check if the given object has to be shown in the viewer in relation to
	 * its selection.
	 * 
	 * @param obj
	 *            The given object
	 * @return True if it has to be shown (not present in values).False
	 *         otherwise.
	 */
	protected boolean hasToDisplayEmptyItems(Object obj) {
		return !values.contains(obj);
	}

	/**
	 * Check if the given object is a candidate to be shown in the viewer.
	 * 
	 * @param obj
	 *            The given object.
	 * @return true if it has to be shown.
	 */
	private boolean isCandidate(Object obj) {
		if (obj instanceof Property) {
			final Property aProperty = (Property) obj;
			if (container != null) {
				final EObject conf = container.eContainer();
				if (conf instanceof EthernetConf) {
					final Iterator<Connector> connectors = ((EthernetConf) conf)
							.getConnectors().iterator();
					while (connectors.hasNext()) {
						Connector connector = connectors.next();
						boolean check = false;
						for (ConnectorEnd connectorEnd : connector.getEnds()) {
							Property property = connectorEnd.getPartWithPort();
							if (property == aProperty) {
								if(!isInTheSameContainer(getOtherProperties(
										connector, connectorEnd))
										&& !isInOtherContainer((EthernetConf) conf,
										aProperty)) {
									check = true;
									continue;
								} else {
									return false;
								}
							}	
						}
						if (check) return true;
					}
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Get the other properties related to the given connector which are not the
	 * one of the specified connector end.
	 * 
	 * @param connector
	 *            The given connector.
	 * @param connectorEnd
	 *            The specified connector end.
	 * @return the list of found properties.
	 */
	private List<Property> getOtherProperties(Connector connector,
			ConnectorEnd connectorEnd) {
		final Property ref = connectorEnd.getPartWithPort();
		final List<Property> result = new ArrayList<Property>();
		for (ConnectorEnd currentConnectorEnd : connector.getEnds()) {
			if (currentConnectorEnd.getPartWithPort() != ref) {
				result.add(currentConnectorEnd.getPartWithPort());
			}
		}
		return result;
	}

	/**
	 * Check if one of the given properties at least already belongs to the
	 * selected values, in the container.
	 * 
	 * @param properties
	 * @return True if one of them has been selected in this container.
	 */
	private boolean isInTheSameContainer(List<Property> properties) {
		for (Property property : properties) {
			if (values.contains(property)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the given property has been selected in an other container.
	 * @param conf The ethernet configuration.
	 * @param property The given property.
	 * @return True if it has been selected in an other container.
	 */
	private boolean isInOtherContainer(EthernetConf conf, Property property) {
		return propertiesInOtherContainers.contains(property);
	}

	/**
	 * get the properties selected in the other containers.
	 * @return The list of thes properties.
	 */
	private List<Property> getpropertiesInOtherContainers() {
		final List<Property> existingProperties = new ArrayList<Property>();
		for (Parameter parameter : ((EthernetConf) container.eContainer())
				.getParameters()) {
			if (parameter instanceof Container && parameter != container) {
				existingProperties.addAll(((Container) parameter)
						.getComponents());
			}
		}
		return existingProperties;
	}

}
