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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.dsl.asl.presentation.ValuesLabelProvider;
import org.eclipse.umlgen.dsl.eth.Container;
import org.eclipse.umlgen.dsl.eth.EthPackage;

/**
 * Specific descriptor to manage a specific selecting component properties dialog.
 *
 * @author cnotot
 */
public class ComponentsPropertyDescriptor extends PropertyDescriptor {

    /**
     * Constructor.
     *
     * @param object
     *            the related object.
     * @param itemPropertyDescriptor
     *            The item property descriptor.
     */
    public ComponentsPropertyDescriptor(Object object, IItemPropertyDescriptor itemPropertyDescriptor) {
        super(object, itemPropertyDescriptor);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ILabelProvider getLabelProvider() {
        return new ValuesLabelProvider((LabelProvider)super.getLabelProvider());
    }

    @Override
    public CellEditor createPropertyEditor(Composite composite) {
        if (!itemPropertyDescriptor.canSetProperty(object)) {
            return null;
        }

        CellEditor result = null;

        Object genericFeature = itemPropertyDescriptor.getFeature(object);
        final EStructuralFeature feature = (EStructuralFeature)genericFeature;
        final EClassifier eType = feature.getEType();

        if (feature.equals(EthPackage.Literals.CONTAINER__COMPONENTS)) {
            final Iterator<?> choiceOfValues = itemPropertyDescriptor.getChoiceOfValues(object).iterator();
            final Set<EObject> newChoiceOfValues = new HashSet<EObject>();

            while (choiceOfValues.hasNext()) {
                final Object obj = choiceOfValues.next();
                if (isCandidate((Property)obj)) {
                    newChoiceOfValues.add((Property)obj);
                    newChoiceOfValues.addAll(ancestorNamespaces((Property)obj));
                }
            }

            // CHECKSTYLE:OFF
            if (newChoiceOfValues != null) {
                if (itemPropertyDescriptor.isMany(object)) {
                    boolean valid = true;
                    for (Object choice : newChoiceOfValues) {
                        if (!eType.isInstance(choice)
                                && !UMLPackage.eINSTANCE.getNamespace().isInstance(choice)) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        final ILabelProvider editLabelProvider = getEditLabelProvider();
                        result = new ExtendedDialogCellEditor(composite, editLabelProvider) {
                            @Override
                            protected Object openDialogBox(Control cellEditorWindow) {
                                ComponentsEditorDialog dialog = new ComponentsEditorDialog(cellEditorWindow
                                        .getShell(), editLabelProvider, object, feature.getEType(),
                                        (List<?>)doGetValue(), getDisplayName(), new ArrayList<Object>(
                                                newChoiceOfValues), false, itemPropertyDescriptor
                                                .isSortChoices(object), feature.isUnique());
                                dialog.open();
                                return dialog.getResult();
                            }
                        };
                    }
                }
                if (result == null) {
                    result = new ExtendedComboBoxCellEditor(composite, new ArrayList<Object>(
                            newChoiceOfValues), getEditLabelProvider(), itemPropertyDescriptor
                            .isSortChoices(object));
                }
            }
            // CHECKSTYLE:ON
        } else {
            result = super.createPropertyEditor(composite);
        }

        return result;
    }

    /**
     * This checks if the given property is a good candidate to be displayed.
     *
     * @param aProperty
     *            The property.
     * @return true if yes.
     */
    public boolean isCandidate(Property aProperty) {
        if (this.object instanceof Container) {
            return aProperty.eContainer() instanceof org.eclipse.uml2.uml.Class
                    && ((Element)aProperty.eContainer()).getAppliedStereotype("RTSJ::Root") != null;

        }
        return false;
    }

    /**
     * Return the list of namespace ancestors from the given object.
     *
     * @param obj
     *            The object.
     * @return The namespace ancestors.
     */
    private List<Namespace> ancestorNamespaces(EObject obj) {
        List<Namespace> namespaces = new ArrayList<Namespace>();
        if (obj != null) {
            EObject container = obj.eContainer();
            if (container instanceof Namespace) {
                namespaces.add((Namespace)container);
                namespaces.addAll(ancestorNamespaces(container));
            }
        }
        return namespaces;
    }

}
