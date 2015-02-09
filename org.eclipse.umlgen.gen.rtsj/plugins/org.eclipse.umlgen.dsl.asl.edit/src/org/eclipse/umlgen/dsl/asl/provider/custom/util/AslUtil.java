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
package org.eclipse.umlgen.dsl.asl.provider.custom.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.umlgen.dsl.asl.AslPackage;
import org.eclipse.umlgen.dsl.asl.Decoration;

/** Utility class to manage broken links from decorators to the decorated model. */
public final class AslUtil {

    /** Default constructor. */
    private AslUtil() {
    }

    /**
     * This checks if the given decoration contains a broken link.
     *
     * @param decoration
     *            the decoration.
     * @return True if yes.
     */
    public static boolean containsBrokenLink(Decoration decoration) {
        final List<EObject> references = new ArrayList<EObject>();
        for (EReference eReference : decoration.eClass().getEAllReferences()) {
            if (!eReference.isContainment()
                    && !AslPackage.eINSTANCE.getNsURI()
                            .equals(eReference.getEType().getEPackage().getNsURI())) {
                if (eReference.isMany()) {
                    references.addAll((List<EObject>)decoration.eGet(eReference, false));
                } else {
                    references.add((EObject)decoration.eGet(eReference, false));
                }
            }
        }
        final Iterator<EObject> itReferences = references.iterator();
        while (itReferences.hasNext()) {
            final EObject elt = itReferences.next();
            if (isBrokenLink(elt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This checks if the reference to the given element is a broken link.
     *
     * @param elt
     *            the element.
     * @return True if yes.
     */
    public static boolean isBrokenLink(final EObject elt) {
        return ((InternalEObject)elt).eIsProxy()
                && ((InternalEObject)elt).eResolveProxy((InternalEObject)elt).equals(elt);
    }

    /**
     * This builds the broken decorator image from the current given image (icon).
     *
     * @param image
     *            The current icon.
     * @param locator
     *            The resource locator of the decorator image to add.
     * @return The resulting image.
     */
    public static ComposedImage getBrokenDecorator(Object image, ResourceLocator locator) {
        final List<Object> images = new ArrayList<Object>(2);
        images.add(image);
        images.add(locator.getImage("full/obj16/broken"));
        return new ComposedImage(images);
    }

}
