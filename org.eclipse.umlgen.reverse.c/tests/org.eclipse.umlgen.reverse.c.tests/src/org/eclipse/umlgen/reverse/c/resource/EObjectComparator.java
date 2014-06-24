/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.resource;

import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class EObjectComparator implements Comparator<EObject> {

	public int compare(EObject o1, EObject o2) {
		return compareObjects(o1, o2);
	}

	private int compareObjects(EObject o1, EObject o2) {
		String targetString1 = extractComparisonString(o1);
		String targetString2 = extractComparisonString(o2);
		int result = targetString1.compareTo(targetString2);

		// Compare the contents of each object
		if (result == 0) {
			result = compareLists(o1.eContents(), o2.eContents());
		}
		// Compare the references of each object
		if (result == 0) {
			result = compareReferences(o1, o2);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private int compareReferences(EObject o1, EObject o2) {
		int result = 0;
		EList<EReference> refs1 = o1.eClass().getEReferences();
		EList<EReference> refs2 = o2.eClass().getEReferences();

		// the shorter list is considered as the first one
		result = new Integer(refs1.size()).compareTo(new Integer(refs2.size()));

		if (result == 0) { // Lists sizes are the same, let's compare elements
			// in lists
			int i = 0;
			int size = refs1.size();
			while (result == 0 && i < size) {
				Object ref1 = o1.eGet(refs1.get(i), false);
				Object ref2 = o2.eGet(refs2.get(i), false);
				if (ref1 instanceof EObject && ref2 instanceof EObject) {
					result = compareObjects((EObject)ref1, (EObject)ref2);
				} else if (ref1 instanceof List<?> && ref2 instanceof List<?>) {
					result = compareLists((List<EObject>)ref1, (List<EObject>)ref2);
				}
				i++;
			}
		}

		return result;
	}

	private int compareLists(List<EObject> list1, List<EObject> list2) {
		int result = 0;
		// the shorter list is considered as the first one
		result = new Integer(list1.size()).compareTo(new Integer(list2.size()));

		if (result == 0) { // Lists sizes are the same, let's compare elements
			// in lists
			// Sort the first list
			EList<EObject> l1 = new BasicEList<EObject>(list1);
			ECollections.sort(l1, this);
			// Sort the second list
			EList<EObject> l2 = new BasicEList<EObject>(list2);
			ECollections.sort(l2, this);

			int i = 0;
			int size = l1.size();
			while (result == 0 && i < size) {
				result = compareObjects(l1.get(i), l2.get(i));
				i++;
			}
		}

		return result;
	}

	private String extractComparisonString(EObject object) {
		return object.toString().replaceAll(object.getClass().getName(), "").replaceAll(
				Integer.toHexString(object.hashCode()), "");
	}
}
