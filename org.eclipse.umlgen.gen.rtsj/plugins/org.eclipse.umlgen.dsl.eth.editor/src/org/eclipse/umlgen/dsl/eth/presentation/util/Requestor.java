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
package org.eclipse.umlgen.dsl.eth.presentation.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;

/**
 * A Class to call some requests on a UML model.
 */
public final class Requestor {

    /** Default constructor. */
    private Requestor() {
    }

    /**
     * Return the connector end considered as the start of the given connector.
     *
     * @param connector
     *            The connector.
     * @return The connector end.
     */
    public static ConnectorEnd getStart(Connector connector) {
        for (ConnectorEnd end : connector.getEnds()) {
            EObject obj = end.getRole();
            if (obj instanceof Port) {
                Port port = (Port)obj;
                if (port.getType() == null) {
                    return end;
                }
            }
        }
        return null;
    }

    /**
     * Return the connector end considered as the end of the given connector.
     *
     * @param connector
     *            The connector.
     * @return The connector end.
     */
    public static ConnectorEnd getEnd(Connector connector) {
        for (ConnectorEnd end : connector.getEnds()) {
            EObject obj = end.getRole();
            if (obj instanceof Port) {
                Port port = (Port)obj;
                if (port.getType() != null) {
                    return end;
                }
            }
        }
        return null;
    }

    /**
     * Get the connectors related to the given port.
     *
     * @param port
     *            The port.
     * @return The connectors.
     */
    public static Set<Connector> getConnectors(final Port port) {
        final Set<Connector> connectors = new HashSet<Connector>();
        for (ConnectorEnd connectorEnd : port.getEnds()) {
            connectors.add((Connector)connectorEnd.eContainer());
        }
        return connectors;
    }
}
