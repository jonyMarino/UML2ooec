/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.common.util;

import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.umlgen.c.common.AnnotationConstants;

/**
 * Utility class helping to work with annotations and detail entries.<br>
 * Creation : 18 november 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public final class AnnotationUtil {

	/**
	 * Gets the reverse annotation or creates a new one if necessary.
	 *
	 * @param element
	 *            An UML element to annotate
	 * @return The new reverse annotation
	 */
	private static EAnnotation getReverseAnnotation(Element element) {
		EAnnotation annot = UML2Util.getEAnnotation(element, AnnotationConstants.REVERSE_PROCESS, true);

		return annot;
	}

	private static void removeDetailEntry(EAnnotation annotation, String entryKey) {
		annotation.getDetails().removeKey(entryKey);
	}

	static boolean isSameTypeBetweenCandHUnits(Element element) {
		EAnnotation reverseAnnotation = element.getEAnnotation(AnnotationConstants.REVERSE_PROCESS);
		if (reverseAnnotation != null) {
			return reverseAnnotation.getDetails().containsKey(AnnotationConstants.C_FILENAME)
					&& !reverseAnnotation.getDetails().containsKey(AnnotationConstants.H_FILENAME);
		}
		return false;
	}

	/**
	 * Gets the documentation annotation or creates a new one if necessary.
	 *
	 * @param element
	 *            An UML element to annotate
	 * @return The new documentation annotation
	 */
	public static EAnnotation getDocumentationAnnotation(Element element) {
		// FIXME MIGRATION reference to modeler
		// return UML2Util.getEAnnotation(element,
		// IAnnotationConstants.DOCUMENTATION_SOURCE, true);
		return null;
	}

	public static void removeEAnnotations(Element element, ITranslationUnit tu) {
		EAnnotation reverse = getReverseAnnotation(element);
		if (tu.isHeaderUnit()) {
			AnnotationUtil.removeDetailEntry(reverse, AnnotationConstants.H_FILENAME);
		} else {
			AnnotationUtil.removeDetailEntry(reverse, AnnotationConstants.C_FILENAME);
		}
	}

	/**
	 * Sets the origin file in the reverse annotation. Create the reverse annotation if does not exist.
	 *
	 * @param classifier
	 *            A classifier of our model.
	 * @param tu
	 *            The translation unit
	 * @return The reverse annotation containing one or several details entry whose the file name.
	 */
	public static EAnnotation setFileLocationDetailEntry(Classifier classifier, ITranslationUnit tu) {
		EAnnotation annotation = getReverseAnnotation(classifier);
		String value = tu.getPath().removeFirstSegments(1).toString();
		annotation.getDetails().put(
				tu.isHeaderUnit() ? AnnotationConstants.H_FILENAME : AnnotationConstants.C_FILENAME, value);
		return annotation;
	}

	/**
	 * Sets the <b>'IFNDEF'</b> detail entry to the reverse annotation of a {@link Classifier}.
	 *
	 * @param classifier
	 *            A classifier to tag
	 * @param condition
	 *            The ifndef condition.
	 */
	public static void setIfndefConditionDetailEntry(Classifier classifier, String condition) {
		EAnnotation reverseAnnotation = getReverseAnnotation(classifier);
		reverseAnnotation.getDetails().put(AnnotationConstants.IFNDEF_CONDITION, condition);
	}

	/**
	 * Sets the <b>'REGISTER'</b> detail entry to the reverse annotation of a {@link Property}.
	 *
	 * @param property
	 *            A property to tag
	 * @param isRegister
	 *            a boolean indicating if the object is register or not
	 */
	public static void setRegisterDetailEntry(Property property, boolean isRegister) {
		EAnnotation reverseAnnotation = getReverseAnnotation(property);
		if (isRegister) {
			// this annotation is only added if the extern is set to true
			reverseAnnotation.getDetails().put(AnnotationConstants.REGISTER, "true"); //$NON-NLS-1$
		} else {
			reverseAnnotation.getDetails().removeKey(AnnotationConstants.REGISTER);
		}
		if (reverseAnnotation.getDetails().isEmpty()) {
			property.getEAnnotations().remove(reverseAnnotation);
		}
	}

	/**
	 * Sets the <b>'VOLATILE'</b> detail entry to the reverse annotation of a {@link Property}.
	 *
	 * @param property
	 *            A property to tag
	 * @param isVolatile
	 *            a boolean indicating if the object is volatile or not
	 */
	public static void setVolatileDetailEntry(Property property, boolean isVolatile) {
		EAnnotation reverseAnnotation = getReverseAnnotation(property);
		if (isVolatile) {
			// this annotation is only added if the extern is set to true
			reverseAnnotation.getDetails().put(AnnotationConstants.VOLATILE, "true"); //$NON-NLS-1$
		} else {
			reverseAnnotation.getDetails().removeKey(AnnotationConstants.VOLATILE);
		}
		if (reverseAnnotation.getDetails().isEmpty()) {
			property.getEAnnotations().remove(reverseAnnotation);
		}
	}

	/**
	 * Sets the <b>'STD_LIBRARY'</b> detail entry into the reverse annotation meaning that the usage is local
	 * or external.
	 *
	 * @param usage
	 *            The usage to tag
	 * @param status
	 *            The status indicating if the usage is local or external.
	 */
	public static void setLibraryDetailEntry(Usage usage, boolean status) {
		EAnnotation reverseAnnotation = getReverseAnnotation(usage);
		if (status) {
			reverseAnnotation.getDetails().put(AnnotationConstants.STD_LIBRARY, "true"); //$NON-NLS-1$
		} else {
			reverseAnnotation.getDetails().removeKey(AnnotationConstants.STD_LIBRARY);
		}
		if (reverseAnnotation.getDetails().isEmpty()) {
			usage.getEAnnotations().remove(reverseAnnotation);
		}
	}

	/**
	 * Called by the generator to re-build the entire relative path.
	 *
	 * @param elt
	 *            A named element
	 * @param key
	 *            The key of the element to retrieve to the map
	 * @return the relative path
	 */
	public static String getRelativePath(NamedElement elt, String key) {
		EAnnotation reverseAnnotation = getReverseAnnotation(elt);
		String value = reverseAnnotation.getDetails().get(key);
		if (value != null && !"".equals(value)) //$NON-NLS-1$
		{
			URI uri = EcoreUtil.getURI(elt);
			String relativePath = uri.toPlatformString(true);
			IResource rsc = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(relativePath));
			IProject project = rsc.getProject();
			return project.getFullPath().append(value).toString();
		}
		return ""; //$NON-NLS-1$
	}
}
