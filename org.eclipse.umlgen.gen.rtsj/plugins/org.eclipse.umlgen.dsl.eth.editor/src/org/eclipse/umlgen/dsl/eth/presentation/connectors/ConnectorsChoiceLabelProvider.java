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

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.umlgen.dsl.eth.presentation.util.Requestor;

/**
 * Specific label provider to display the choice of the connectors.
 * @author cnotot
 *
 */
public class ConnectorsChoiceLabelProvider extends LabelProvider {
	
	private IItemLabelProvider itemLabelProvider;
	
	public ConnectorsChoiceLabelProvider(IItemLabelProvider itemLabelProvider) {
		super();
		this.itemLabelProvider = itemLabelProvider;
	}
	
	@Override
	public String getText(Object object) {
		if (object instanceof Connector) {
			Connector connector = (Connector) object;
			ConnectorEnd start = Requestor.getStart(connector);
			ConnectorEnd end = Requestor.getEnd(connector);
			if ((start == null || end == null)
					&& connector.getEnds().size() > 1) {
				start = connector.getEnds().get(0);
				end = connector.getEnds().get(0);
				return start.getRole().getName() + " - "
						+ ((NamedElement) end.getRole().eContainer()).getName()
						+ " (" + end.getRole().getName() + ")";
			}

			return start.getRole().getName() + " -> "
					+ ((NamedElement) end.getRole().eContainer()).getName()
					+ " (" + end.getRole().getName() + ")";
		} else if (object instanceof Namespace) {
			if (((Namespace) object).getName() != null) {
				return ((Namespace) object).getName();
			} else {
				return "";
			}
		}

		return itemLabelProvider.getText(object);
	}

	@Override
	public Image getImage(Object object) {
		return ExtendedImageRegistry.getInstance().getImage(
				itemLabelProvider.getImage(object));
	}
	
}
