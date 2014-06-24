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

import java.util.Collections;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section with common behaviors and implementations of main features expected for sections related to
 * {@link LoopNode}. Creation : 22 june 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public abstract class AbstractLoopNodeSection extends AbstractStringWithButtonSection {

	@Override
	protected String getFeatureAsString() {
		OpaqueAction opaqueAction = getOpaqueAction();
		if (opaqueAction != null) {
			int index = opaqueAction.getLanguages().indexOf(BundleConstants.C_LANGUAGE);
			if (index > -1) {
				return opaqueAction.getBodies().get(index);
			}
		}
		return null;
	}

	@Override
	protected EStructuralFeature getFeature() {
		return UMLPackage.eINSTANCE.getOpaqueAction_Body();
	}

	@Override
	protected void createCommand(Object oldValue, Object newValue) {
		boolean equals = oldValue == null ? false : oldValue.equals(newValue);
		if (!equals) {
			EditingDomain editingDomain = getEditingDomain();
			CompoundCommand cc = new CompoundCommand(Messages
					.getString("AbstractLoopNodeSection.PropertyValueChange")); //$NON-NLS-1$
			cc.appendIfCanExecute(SetCommand.create(editingDomain, getOpaqueAction(), getFeature(),
					Collections.singletonList(newValue)));
			cc.appendIfCanExecute(SetCommand.create(editingDomain, getOpaqueAction(), UMLPackage.eINSTANCE
					.getNamedElement_Name(), newValue));
			editingDomain.getCommandStack().execute(cc);
		}
	}

	@Override
	protected void createModelObject() {
		CompoundCommand cc = new CompoundCommand(Messages.getString("AbstractLoopNodeSection.CmdTitle")); //$NON-NLS-1$
		addOpaqueAction(createNewOpaqueAction(), cc);
		getEditingDomain().getCommandStack().execute(cc);
	}

	/**
	 * Adds an {@link OpaqueAction} into the current {@link LoopNode}.
	 *
	 * @param newObj
	 *            The new object to add to the model
	 * @param cc
	 *            The compound command in which all the add/set commands must be stored.
	 */
	protected void addOpaqueAction(OpaqueAction newObj, CompoundCommand cc) {
		cc.appendIfCanExecute(AddCommand.create(getEditingDomain(), getEObject(), UMLPackage.eINSTANCE
				.getStructuredActivityNode_Node(), newObj));
	}

	/**
	 * Defines the opaque action access policy depending of the context. Clients must implement this method.
	 *
	 * @return The opaque action found or null if it doesn't exist.
	 */
	protected abstract OpaqueAction getOpaqueAction();

	/**
	 * Creates the new opaque action when necessary. Clients must implement this method.
	 *
	 * @return The newly {@link OpaqueAction}
	 */
	protected abstract OpaqueAction createNewOpaqueAction();

}
