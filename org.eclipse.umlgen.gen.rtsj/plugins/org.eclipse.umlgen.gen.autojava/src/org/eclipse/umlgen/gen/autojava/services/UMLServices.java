/*******************************************************************************
 * Copyright (c) 2009, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nathalie Lepine (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Services providing facilities about UML.
 */
public class UMLServices {

    /**
     * Verify if an Element have a stereotype. Use keyword and profile to find stereotype. Multiple stereotype
     * are allow.
     *
     * @param elt
     *            Element used.
     * @param stereotype
     *            Stereotype to search.
     * @return true if found. false else.
     * @throws ENodeCastException
     */
    public boolean hasStereotype(Element elt, String stereotype) {
        boolean result = false;
        Stereotype stereotypeFound = elt.getAppliedStereotype(stereotype);
        if (stereotypeFound == null) {
            // search with keywords
            if (elt.hasKeyword(stereotype)) {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Get value of given stereotype property (same as Tagged Value in UML1.4).
     *
     * @param element
     *            receiver
     * @param stereotypeName
     *            stereotype name
     * @param propertyName
     *            property name
     * @return value
     */
    public String getStereotypeProperty(Element element, String stereotypeName, String propertyName) {
        String result = null;
        Stereotype stereotype = element.getAppliedStereotype(stereotypeName);
        if (stereotype != null) {
            Object value = element.getValue(stereotype, propertyName);
            if (value instanceof String) {
                result = (String)value;
            } else if (value instanceof Boolean) {
                result = ((Boolean)value).toString();
            } else if (value instanceof Integer) {
                result = ((Integer)value).toString();
            } else if (value instanceof EnumerationLiteral) {
                result = ((EnumerationLiteral)value).getName();
            }
        }
        return result;
    }

    /**
     * This removes the objects which have the same name from the given list.<br>
     * The list is scanned and each new object which owns the same name of an already scanned object in the
     * list is ignored.
     *
     * @param list
     *            the list receiver.
     * @return the list without elements with the same name
     */
    public List<NamedElement> minimizeByName(List<NamedElement> list) {
        List<NamedElement> result = new ArrayList<NamedElement>();
        for (NamedElement el : list) {
            if (!contain(result, el)) {
                result.add(el);
            }
        }
        return result;
    }

    /**
     * Warning : The list of the properties has also the references of the metaclasses : the name of the
     * property is base_"metaclass.name" name.startWith("base_") remove the metaclasses references.
     *
     * @param element
     *            the receiver.
     * @return The list with the empty attributes
     */
    public List<Element> getEmptyStereotypeAttribute(Element element) {
        List<Element> result = new ArrayList<Element>();
        for (Stereotype stereotype : element.getAppliedStereotypes()) {
            for (Property property : stereotype.getAllAttributes()) {
                if (!property.getName().startsWith("base_")
                        && element.getValue(stereotype, property.getName()) == null) {
                    result.add(property);
                }
            }
        }
        return result;
    }

    /**
     * This returns true if the given list contain an object with the same name as the given one.
     * 
     * @param receiver
     *            The list to check.
     * @param el
     *            The reference object.
     * @return the result.
     */
    private boolean contain(List<NamedElement> receiver, NamedElement el) {
        for (NamedElement element : receiver) {
            if (el.getName().equals(element.getName())) {
                return true;
            }
        }
        return false;
    }

}
