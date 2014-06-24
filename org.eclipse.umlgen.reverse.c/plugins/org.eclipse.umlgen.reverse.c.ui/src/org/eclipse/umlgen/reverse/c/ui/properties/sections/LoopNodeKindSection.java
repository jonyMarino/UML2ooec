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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section for {@link LoopNode} kind that can be a <i>For</i>, a <i>While</i> or a <i>Do while</i>.<br>
 * Changing one of these values impacts the structural feature 'isTestedFirst' of the {@link LoopNode}<br>
 * Creation : 20 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public class LoopNodeKindSection extends AbstractRadioButtonPropertySection {

	public static final String FOR = "For"; //$NON-NLS-1$

	public static final String WHILE = "While"; //$NON-NLS-1$

	public static final String DO_WHILE = "Do while"; //$NON-NLS-1$

	public static int UI_CHANGE_KIND = 12;

	/**
	 * This string stores the current value of the structure kind selected. Default value is DO_WHILE
	 */
	private String selectedKind = DO_WHILE;

	@Override
	protected void setInitialState() {
		Button toSelect = null;
		LoopNode loop = (LoopNode)getEObject();
		if (!loop.isTestedFirst()) {
			toSelect = getButton(DO_WHILE);
		} else {
			if (loop.getLoopVariables().isEmpty() && loop.getSetupParts().isEmpty()) {
				toSelect = getButton(WHILE);
			} else {
				toSelect = getButton(FOR);
			}
		}

		if (toSelect != null) {
			for (Button btn : getButtons()) {
				btn.setSelection(btn == toSelect);
			}

			// send the fake notification to update other related UI
			loop.eNotify(new ENotificationImpl((InternalEObject)loop, UI_CHANGE_KIND, null, selectedKind,
					toSelect.getText()));
			selectedKind = toSelect.getText();
		}

	}

	@Override
	public String[] getButtonLabels() {
		return new String[] {FOR, WHILE, DO_WHILE };
	}

	@Override
	protected EStructuralFeature getFeature() {
		return UMLPackage.eINSTANCE.getLoopNode_IsTestedFirst();
	}

	@Override
	protected String getLabelText() {
		return Messages.getString("LoopNode.Kind"); //$NON-NLS-1$
	}

	@Override
	protected SelectionListener createSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.widget instanceof Button) {
					Button btn = (Button)e.widget;
					LoopNode node = (LoopNode)getEObject();
					createCommand(node.isTestedFirst(), FOR.equals(btn.getText())
							|| WHILE.equals(btn.getText()));

					// send the fake notification to update other related UI
					node.eNotify(new ENotificationImpl((InternalEObject)node, UI_CHANGE_KIND, null,
							selectedKind, btn.getText()));

					selectedKind = btn.getText();
				}
			}
		};
	}

}
