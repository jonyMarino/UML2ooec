/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.properties.sections;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;

/**
 * The section helps the user to create a consistent 'switch' structure. Creation 18 june 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class ClauseAssistantSection extends AbstractTabbedPropertySection {
	private ClauseAssistantComposite assistant;

	@Override
	protected void createWidgets(Composite composite) {
		assistant = new ClauseAssistantComposite(getWidgetFactory(), composite, SWT.NONE);
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		EditingDomain editDomain = (EditingDomain)getPart().getAdapter(EditingDomain.class);
		assistant.setEditingDomain(editDomain);
		assistant.setParent(getEObject());
	}

	@Override
	protected void setSectionData(Composite composite) {
		FormData data = new FormData(SWT.DEFAULT, SWT.DEFAULT);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		assistant.setLayoutData(data);
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	@Override
	protected EStructuralFeature getFeature() {
		return null;
	}

	@Override
	protected String getLabelText() {
		return null;
	}

}
