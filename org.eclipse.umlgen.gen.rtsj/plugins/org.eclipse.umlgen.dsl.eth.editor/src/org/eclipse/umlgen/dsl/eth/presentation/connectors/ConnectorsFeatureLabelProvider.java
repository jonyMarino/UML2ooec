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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.umlgen.dsl.asl.presentation.ValuesLabelProvider;
import org.eclipse.umlgen.dsl.eth.presentation.util.Requestor;

/**
 * Specific label provider to display the selected connectors.
 *
 * @author cnotot
 */
public class ConnectorsFeatureLabelProvider extends ValuesLabelProvider {

    /**
     * Constructor.
     *
     * @param labelProvider
     *            The label provider.
     */
    public ConnectorsFeatureLabelProvider(LabelProvider labelProvider) {
        super(labelProvider);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.dsl.asl.presentation.ValuesLabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object object) {
        String result = super.getText(object);
        if (object instanceof Connector && ((Connector)object).eResource() != null) {
            Connector connector = (Connector)object;
            ConnectorEnd start = Requestor.getStart(connector);
            ConnectorEnd end = Requestor.getEnd(connector);
            // CHECKSTYLE:OFF
            if ((start == null || end == null) && connector.getEnds().size() > 1) {
                start = connector.getEnds().get(0);
                end = connector.getEnds().get(0);
                result = start.getRole().getName() + " - "
                        + ((NamedElement)end.getRole().eContainer()).getName() + " ("
                        + end.getRole().getName() + ")";
            } else {
                result = ((NamedElement)start.getRole().eContainer()).getName() + " ("
                        + start.getRole().getName() + ")" + " -> "
                        + ((NamedElement)end.getRole().eContainer()).getName() + " ("
                        + end.getRole().getName() + ")";
            }
            // CHECKSTYLE:ON
        }
        return result;
    }

}
