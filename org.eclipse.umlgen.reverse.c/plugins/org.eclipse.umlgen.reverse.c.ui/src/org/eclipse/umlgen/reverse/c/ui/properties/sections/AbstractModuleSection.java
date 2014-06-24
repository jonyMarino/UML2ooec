/*******************************************************************************
 * Copyright (c) 2011, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.properties.sections;

import java.util.Map.Entry;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Section allowing to specify information related to a C module (module or header).<br>
 * This information will be used by the Acceleo generator.<br>
 * This section is available from a {@link org.eclipse.uml2.uml.Class} or an
 * {@link org.eclipse.uml2.uml.Interface}.<br>
 * Creation : 04 february 2011<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
// FIXME MIGRATION reference to tabbedproperties
public abstract class AbstractModuleSection extends AbstractStringPropertySection {
	/**
	 * @return A constant representing the key of the details entry
	 */
	protected abstract String getKeyDetailsEntry();

	/**
	 * Gets the eAnnotation attached to the model element.
	 *
	 * @return The annotation or null if no annotation is attached
	 */
	protected EAnnotation getEAnnotation() {
		EObject eObj = getEObject();
		if (eObj instanceof EModelElement) {
			return UML2Util.getEAnnotation((EModelElement)eObj, AnnotationConstants.REVERSE_PROCESS, false);
		}
		return null;
	}

	@Override
	protected void createCommand(Object oldValue, Object newValue) {
		boolean equals = oldValue == null ? false : oldValue.equals(newValue);
		if (!equals) {
			AbstractCommand cmd = null;
			EditingDomain editingDomain = getEditingDomain();
			EAnnotation eAnnotation = getEAnnotation();

			// 1 - This is a new entry that needs to be created first.
			if ("".equals(oldValue) && !"".equals(newValue)) //$NON-NLS-1$ //$NON-NLS-2$
			{
				// test if the eAnnotation already exists
				if (eAnnotation == null) {
					// create the annotation with the entry
					eAnnotation = UML2Util.createEAnnotation(null, AnnotationConstants.REVERSE_PROCESS);
					eAnnotation.getDetails().put(getKeyDetailsEntry(), newValue.toString());
					cmd = (AbstractCommand)AddCommand.create(editingDomain, getEObject(),
							EcorePackage.eINSTANCE.getEModelElement_EAnnotations(), eAnnotation);
				} else {
					// create only the entry within the current eAnnotation
					EStringToStringMapEntryImpl newEntry = (EStringToStringMapEntryImpl)EcoreFactory.eINSTANCE
							.create(EcorePackage.eINSTANCE.getEStringToStringMapEntry());
					newEntry.setTypedKey(getKeyDetailsEntry());
					newEntry.setTypedValue(newValue.toString());
					cmd = (AbstractCommand)AddCommand.create(editingDomain, eAnnotation,
							EcorePackage.eINSTANCE.getEAnnotation_Details(), newEntry);
				}
				cmd.setLabel(Messages.getString("Metadata.cmd.creation")); //$NON-NLS-1$

			}
			// 2 - this is an update of the entry
			else if (!"".equals(oldValue) && !"".equals(newValue)) //$NON-NLS-1$ //$NON-NLS-2$
			{
				Entry<String, String> entry = eAnnotation.getDetails().get(
						eAnnotation.getDetails().indexOfKey(getKeyDetailsEntry()));
				cmd = (AbstractCommand)SetCommand.create(editingDomain, entry, EcorePackage.eINSTANCE
						.getEStringToStringMapEntry_Value(), newValue);
				cmd.setLabel(Messages.getString("Metadata.cmd.modification")); //$NON-NLS-1$
			}
			// 3 - The eAnnotation and all its attached information need to be
			// removed from the model.
			else if (!"".equals(oldValue) && "".equals(newValue)) //$NON-NLS-1$ //$NON-NLS-2$
			{
				if (eAnnotation.getDetails().size() == 1) {
					// the entry we want to remove is the last one, so the
					// entire eAnnotation is removed
					cmd = (AbstractCommand)RemoveCommand.create(editingDomain, getEAnnotation());
				} else {
					// here, only the entry is removed from the eAnnotation
					Entry<String, String> entry = eAnnotation.getDetails().get(
							eAnnotation.getDetails().indexOfKey(getKeyDetailsEntry()));
					cmd = (AbstractCommand)RemoveCommand.create(editingDomain, entry);
				}
				cmd.setLabel(Messages.getString("Metadata.cmd.deletion")); //$NON-NLS-1$
			}
			// execute the command if it is possible
			if (cmd != null && cmd.canExecute()) {
				editingDomain.getCommandStack().execute(cmd);
			}
		}
	}

	@Override
	protected EStructuralFeature getFeature() {
		return EcorePackage.eINSTANCE.getEAnnotation_Details();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected String getFeatureAsString() {
		EMap<String, String> map = getEAnnotation() == null ? null : (EMap<String, String>)getEAnnotation()
				.eGet(getFeature());
		if (map == null || map.isEmpty() || !map.containsKey(getKeyDetailsEntry())) {
			return ""; //$NON-NLS-1$
		}
		return map.get(getKeyDetailsEntry());
	}
}
