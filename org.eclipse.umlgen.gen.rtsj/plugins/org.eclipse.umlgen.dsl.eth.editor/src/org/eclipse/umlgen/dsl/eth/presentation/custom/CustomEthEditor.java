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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.umlgen.dsl.eth.Container;
import org.eclipse.umlgen.dsl.eth.EthPackage;
import org.eclipse.umlgen.dsl.eth.EthernetConf;
import org.eclipse.umlgen.dsl.eth.presentation.EthEditor;
import org.eclipse.umlgen.dsl.eth.presentation.util.Requestor;

/**
 * Editor which manages the consistency between the Ethernet connectors and the related components during deleting of connectors.
 * @author cnotot
 *
 */
public class CustomEthEditor extends EthEditor {
	
	protected EContentAdapter consistencyAdapter = 
			new EContentAdapter() {
		
				private PropertyToContainerReferencer mapPropertyToContainer;
				
				@Override
				public void notifyChanged(Notification notification) {
					if (notification.getFeature() == EthPackage.Literals.ETHERNET_CONF__CONNECTORS && notification.getEventType() == Notification.REMOVE) {
						final Connector connector = (Connector) notification.getOldValue();
						if (mapPropertyToContainer == null) {
							mapPropertyToContainer = new PropertyToContainerReferencer(connector.eResource().getResourceSet());
							mapPropertyToContainer.computeCrossReferences();
						}
						for (ConnectorEnd connectorEnd : connector.getEnds()) {
							final Property property = connectorEnd.getPartWithPort();
							final Type component = property.getType();
							if (component instanceof org.eclipse.uml2.uml.Class) {
								if (hasOtherEthernetConnector((EthernetConf) notification.getNotifier(), connector, (org.eclipse.uml2.uml.Class) component)) {
									continue;
								}
								final Collection<Setting> settings = mapPropertyToContainer.get(property);
								for (Setting setting : settings) {
									if (setting.getEStructuralFeature() == EthPackage.Literals.CONTAINER__COMPONENTS) {
										final Container container = (Container)setting.getEObject();									
										container.getComponents().remove(property);
									}
								}
							}
						}
					} else {
						super.notifyChanged(notification);
					}
				}
	};
	
	@Override
	public void createModel() {
		super.createModel();
		editingDomain.getResourceSet().eAdapters().add(consistencyAdapter);
	}
	
	private static boolean hasOtherEthernetConnector(EthernetConf ethernetConf, final Connector currentConnector, final org.eclipse.uml2.uml.Class component) {
		final Set<Connector> connectors = getAllConnectors(component);
		connectors.remove(currentConnector);
		for (Connector connect : ethernetConf.getConnectors()) {
			if (connectors.contains(connect)) {
				return true;
			}
		}
		return false;
	}

	private static Set<Connector> getAllConnectors(org.eclipse.uml2.uml.Class clazz) {
		final Set<Connector> connectors = new HashSet<Connector>();
		for (Iterator<Port> iterator = clazz.getOwnedPorts().iterator(); iterator.hasNext();) {
			final Port port = iterator.next();
			connectors.addAll(Requestor.getConnectors(port));
		}
		return connectors;
	}
	
	private static class PropertyToContainerReferencer extends CrossReferencer {
		
		protected PropertyToContainerReferencer(ResourceSet arg0) {
			super(arg0);
		}

		private static final long serialVersionUID = 1L;
		
		@Override
		protected boolean crossReference(EObject eObject, EReference eReference, EObject crossReferencedEObject) {
			return eReference == EthPackage.Literals.CONTAINER__COMPONENTS;
		}
		
		public void computeCrossReferences(){
			crossReference();
		    done();
		}
	}
	
}
