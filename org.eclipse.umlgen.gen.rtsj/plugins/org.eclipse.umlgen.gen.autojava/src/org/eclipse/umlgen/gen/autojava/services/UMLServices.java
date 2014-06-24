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

public class UMLServices {

	/**
	 * Verify if an Element have a stereotype. Use keyword and profile to find
	 * stereotype. Multiple stereotype are allow.
	 * 
	 * @param elt
	 *            Element used.
	 * @param stereotype
	 *            Stereotype to search.
	 * @return true if found. false else.
	 * @throws ENodeCastException
	 */
	public boolean hasStereotype(Element elt, String stereotype) {
		Stereotype stereotypeFound = elt.getAppliedStereotype(stereotype);
		if (stereotypeFound == null) {
			// search with keywords
			if (elt.hasKeyword(stereotype))
				return true;

			return false;
		} else
			return true;
	}

	/**
	 * Get value of given stereotype property (same as Tagged Value in UML1.4)
	 * 
	 * @param element
	 * @param stereotypeName
	 *            stereotype name
	 * @param propertyName
	 *            property name
	 * @return value
	 */
	public String getStereotypeProperty(Element element, String stereotypeName,
			String propertyName) {
		Stereotype stereotype = element.getAppliedStereotype(stereotypeName);
		if (stereotype != null) {
			try {
				Object value = element.getValue(stereotype, propertyName);
				if (value instanceof String) {
					return (String) value;
				} else if (value instanceof Boolean) {
					return ((Boolean) value).toString();
				} else if (value instanceof Integer) {
					return ((Integer) value).toString();
				} else if (value instanceof EnumerationLiteral) {
					return ((EnumerationLiteral) value).getName();
				}
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * Remove the elements with the same name
	 * 
	 * @param list
	 * @return the list whithout elements with the same name
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
	 * Warning : The list of the properties has also the references of the
	 * metaclasses : the name of the property is base_"metaclass.name"
	 * name.startWith("base_") remove the metaclasses references
	 * 
	 * @param element
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
	 * 
	 * @param result
	 * @param el
	 * @return if the element el is already in the list. it is already if it has
	 *         the same name
	 */
	private boolean contain(List<NamedElement> result, NamedElement el) {
		for (NamedElement element : result) {
			if (el.getName().equals(element.getName())) {
				return true;
			}
		}
		return false;
	}

}
