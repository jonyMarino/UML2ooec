/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mikael BARBERO (Obeo) - initial API and implementation
 *     Sebastien GABEL (CS-SI) - evolution
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.services;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Clause;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.PreferenceStoreManager;

public class UML2Services {

	/**
	 * Gets an <b>ordered collection</b> of dependencies.
	 *
	 * @param elt
	 *            A named element representing the starting point.
	 * @return A collection of dependencies (mainly uml:Usage)
	 */
	public static Collection<Dependency> getClientDependencies(NamedElement elt) {
		return elt.getClientDependencies();
	}

	/**
	 * Tries to return the qualified name of the sources package.
	 *
	 * @param elt
	 *            A named element representing the starting point.
	 * @return The qualified package name
	 */
	public static String getSourcePackageName(NamedElement elt) {
		URI uri = EcoreUtil.getURI(elt);
		String relativePath = uri.toPlatformString(true);
		IResource rsc = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(relativePath));
		if (rsc != null) {
			IPreferenceStore store = PreferenceStoreManager.getPreferenceStore(rsc.getProject());
			return store.getString(BundleConstants.SRC_PCK_NAME);
		}
		return "";
	}

	public static String eResourceName(EObject eObject) {
		return eObject.eResource().getURI().trimFileExtension().lastSegment();
	}

	public static MergeNode getCorrespondingMergeNode(DecisionNode decisionNode) {
		return getNextMergeNode(decisionNode);
	}

	private static MergeNode getNextMergeNode(ActivityNode node) {
		if (node.getOutgoings().size() > 0) {
			for (ActivityEdge flow : node.getOutgoings()) {
				if (flow.getTarget() instanceof MergeNode) {
					return (MergeNode)flow.getTarget();
				}
			}
			for (ActivityEdge flow : node.getOutgoings()) {
				MergeNode mergeNode = getNextMergeNode(flow.getTarget());
				if (mergeNode != null) {
					return mergeNode;
				}
			}
		}
		return null;
	}

	public static boolean usesFallthrough(Clause clause) {
		ExecutableNode body = clause.getBodies().get(0);
		for (Clause succClause : clause.getSuccessorClauses()) {
			if (succClause.getBodies().get(0).equals(body)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isReferencedByClause(ActivityNode node) {
		Collection<EStructuralFeature.Setting> references = new EcoreUtil.UsageCrossReferencer(node
				.eResource()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean crossReference(EObject eObject, EReference eReference, EObject referencedObj) {
				return super.crossReference(eObject, eReference, referencedObj)
						&& eReference == UMLPackage.Literals.CLAUSE__BODY;
			}

			@Override
			protected boolean containment(EObject eObject) {
				return !(eObject instanceof OpaqueAction);
			}

			@Override
			public Collection<EStructuralFeature.Setting> findUsage(EObject eObject) {
				return super.findUsage(eObject);
			}
		}.findUsage(node);
		return references.size() > 0;
	}
}
