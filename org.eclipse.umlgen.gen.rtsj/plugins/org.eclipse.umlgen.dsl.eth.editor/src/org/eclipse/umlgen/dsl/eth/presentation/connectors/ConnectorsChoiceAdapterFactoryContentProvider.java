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
 */
public class ConnectorsChoiceAdapterFactoryContentProvider extends AdapterFactoryContentProvider {

    /** The selected values/connectors. */
    private List<?> values;

    /**
     * Constructor.
     *
     * @param adapterFactory
     *            The adapter factory.
     * @param values
     *            The values.
     */
    public ConnectorsChoiceAdapterFactoryContentProvider(AdapterFactory adapterFactory, List<?> values) {
        super(adapterFactory);
        this.values = values;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object object) {
        List<EObject> result = new ArrayList<EObject>();
        Object[] objects = super.getElements(object);
        for (Object object2 : objects) {
            if (object2 instanceof Namespace && ((Namespace)object2).eContainer() instanceof Model
                    && getChildren(object2).length > 0) {
                result.add((EObject)object2);
            }
        }
        return result.toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object object) {
        final List<EObject> result = new ArrayList<EObject>();
        if (isComponent(object)) {
            result.addAll(getOutgoingConnectors((org.eclipse.uml2.uml.Class)object));
        } else if (object instanceof Namespace) {
            final ItemProvider input = (ItemProvider)viewer.getInput();
            for (Object obj : input.getChildren()) {
                if (obj instanceof Namespace && ((EObject)obj).eContainer() == object && hasChildren(obj)) {
                    result.add((EObject)obj);
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
    private List<Connector> getOutgoingConnectors(org.eclipse.uml2.uml.Class component) {
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

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
     */
    @Override
    public boolean hasChildren(Object object) {
        return getChildren(object).length > 0;
    }

    /**
     * This checks if the given object is a component.
     *
     * @param obj
     *            the object to check.
     * @return True if yes.
     */
    public boolean isComponent(Object obj) {
        return obj instanceof org.eclipse.uml2.uml.Class
                && ((org.eclipse.uml2.uml.Class)obj).getOwnedPorts().size() > 0;
    }

}
