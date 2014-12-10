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
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.dsl.eth.EthPackage;
import org.eclipse.umlgen.dsl.eth.presentation.util.Requestor;

/**
 * Specific descriptor to manage a specific selecting connectors dialog.
 *
 * @author cnotot
 */
public class ConnectorsPropertyDescriptor extends PropertyDescriptor {

    /**
     * Constructor.
     *
     * @param object
     *            The related object.
     * @param itemPropertyDescriptor
     *            The item property descriptor.
     */
    public ConnectorsPropertyDescriptor(Object object, IItemPropertyDescriptor itemPropertyDescriptor) {
        super(object, itemPropertyDescriptor);
    }

    @Override
    public ILabelProvider getLabelProvider() {
        return new ConnectorsFeatureLabelProvider((LabelProvider)super.getLabelProvider());
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

        if (feature.equals(EthPackage.Literals.ETHERNET_CONF__CONNECTORS)) {
            final Iterator<?> choiceOfValues = itemPropertyDescriptor.getChoiceOfValues(object).iterator();
            final Set<EObject> newChoiceOfValues = new HashSet<EObject>();

            while (choiceOfValues.hasNext()) {
                Object obj = choiceOfValues.next();
                if (isCandidate((Connector)obj)) {
                    newChoiceOfValues.add((EObject)obj);
                    newChoiceOfValues.add(getOwningComponent((Connector)obj));
                    newChoiceOfValues.addAll(ancestorNamespaces(getOwningComponent((Connector)obj)));
                }
            }

            if (newChoiceOfValues != null) {
                if (itemPropertyDescriptor.isMany(object)) {
                    // CHECKSTYLE:OFF
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
                                ConnectorsEditorDialog dialog = new ConnectorsEditorDialog(cellEditorWindow
                                        .getShell(), editLabelProvider, object, feature.getEType(),
                                        (List<?>)doGetValue(), getDisplayName(), new ArrayList<Object>(
                                                newChoiceOfValues), false, itemPropertyDescriptor
                                                .isSortChoices(object), feature.isUnique(),
                                                itemPropertyDescriptor);
                                dialog.open();
                                return dialog.getResult();
                            }
                        };
                    }
                    // CHECKSTYLE:ON
                }
                if (result == null) {
                    result = new ExtendedComboBoxCellEditor(composite, new ArrayList<Object>(
                            newChoiceOfValues), getEditLabelProvider(), itemPropertyDescriptor
                            .isSortChoices(object));
                }
            }
        } else {
            result = super.createPropertyEditor(composite);
        }

        return result;
    }

    /**
     * This checks if the given connector is a good candidate to be displayed.
     *
     * @param connector
     *            The connector.
     * @return True if yes.
     */
    private boolean isCandidate(Connector connector) {
        return connector.getOwner() != null
                && connector.getOwner().eClass().equals(UMLPackage.eINSTANCE.getClass_())
                && (isEventData(connector) || isAsynchronous(connector));
    }

    /**
     * This checks if the given connector is event data.
     *
     * @param connector
     *            The connector.
     * @return True if yes.
     */
    private boolean isEventData(Connector connector) {
        return Requestor.getEnd(connector) != null
                && Requestor.getEnd(connector).getRole().getType().getAppliedStereotype("RTSJ::EventData") != null;
    }

    /**
     * This checks if the given connector is asynchronous.
     *
     * @param connector
     *            The connector.
     * @return True if yes.
     */
    private boolean isAsynchronous(Connector connector) {
        return Requestor.getEnd(connector) != null
                && Requestor.getEnd(connector).getRole().getType().getAppliedStereotype("RTSJ::Asynchronous") != null;
    }

    /**
     * This returns the owning component of the given connector.
     *
     * @param connector
     *            The connector.
     * @return the related component.
     */
    private org.eclipse.uml2.uml.Class getOwningComponent(Connector connector) {
        ConnectorEnd start = Requestor.getStart(connector);
        if (start == null && connector.getEnds().size() > 1) {
            start = connector.getEnds().get(0);
        }
        if (start.getRole().eContainer() instanceof org.eclipse.uml2.uml.Class) {
            return (org.eclipse.uml2.uml.Class)start.getRole().eContainer();
        }
        return null;
    }

    /**
     * This returns the namespace ancestors from the given object.
     * 
     * @param obj
     *            The object.
     * @return The ancestors
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
