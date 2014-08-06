/*******************************************************************************
 * Copyright (c) 2009, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.resource;

import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Set of utility methods to handles {@link Resource resource} and {@link ResourceSet resource set}
 *
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mika�l barbero</a>
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class EResources {

	private static final String FILE_ENCODING = "file.encoding"; //$NON-NLS-1$

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private EResources() {
		// prevent instantiation
	}

	/**
	 * Attaches the given {@link EObject} to a new resource created in a new {@link ResourceSet} with the
	 * given URI.
	 *
	 * @param resourceURI
	 *            URI of the new resource to create.
	 * @param root
	 *            EObject to attach to a new resource.
	 * @return The resource <tt>root</tt> has been attached to.
	 */
	public static Resource attachResource(URI resourceURI, EObject root) {
		final Resource newResource = EResources.createResource(resourceURI);
		newResource.getContents().add(root);
		return newResource;
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for.
	 *
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @return The {@link Resource} given the model extension it is intended for.
	 */
	public static Resource createResource(URI modelURI) {
		// return new OrderedXMIResourceImpl(modelURI);
		return EResources.createResource(modelURI, new ResourceSetImpl());
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 *
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 */
	public static Resource createResource(URI modelURI, ResourceSet resourceSet) {
		String fileExtension = modelURI.fileExtension();
		if (fileExtension == null || fileExtension.length() == 0) {
			fileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;
		}

		final Resource.Factory.Registry registry = resourceSet.getResourceFactoryRegistry();
		Object resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);
		if (resourceFactory == null) {
			resourceFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
					.get(fileExtension);
			if (resourceFactory != null) {
				registry.getExtensionToFactoryMap().put(fileExtension, resourceFactory);
			} else {
				registry.getExtensionToFactoryMap().put(fileExtension, new XMIResourceFactoryImpl());
			}
		}

		return resourceSet.createResource(modelURI);
	}

	/**
	 * Serializes the given EObjet as a String.
	 *
	 * @param root
	 *            Root of the objects to be serialized.
	 * @return The given EObjet serialized as a String.
	 * @throws IOException
	 *             Thrown if an I/O operation has failed or been interrupted during the saving process.
	 */
	public static String serialize(EObject root) throws IOException {
		// Copies the root to avoid modifying it
		final EObject copyRoot = EcoreUtil.copy(root);
		// Should not throw ClassCast since uri calls for an xml resource
		final XMLResource resource = (XMLResource)EResources.attachResource(URI
				.createFileURI("resource.orderedxmi"), copyRoot); //$NON-NLS-1$
		resource.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING,
				System.getProperty(EResources.FILE_ENCODING));
		// resource.getDefaultSaveOptions().put(XMLResource.OPTION_SKIP_ESCAPE, Boolean.TRUE);

		final StringWriter writer = new StringWriter();
		resource.save(writer, null);
		final String result = writer.toString();
		writer.flush();
		return result;
	}
}
