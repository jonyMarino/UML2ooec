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
package org.eclipse.umlgen.reverse.c.activity.util;

import java.util.Collection;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;

/** A UML activity sanitizer. */
public class UMLActivitySanitizer {

    /**
     * Sanitize the given activity.
     *
     * @param activity
     *            The activity.
     */
    public void sanitize(Activity activity) {
        mergeOpaqueActions(activity);
        setOpaqueActionsNames(activity);
    }

    /**
     * Set the name of the opaque actions of the given activity.
     *
     * @param activity
     *            The activity.
     */
    private void setOpaqueActionsNames(Activity activity) {
        TreeIterator<EObject> contentIterator = activity.eAllContents();
        while (contentIterator.hasNext()) {
            EObject content = contentIterator.next();
            if (content instanceof OpaqueAction) {
                setOpaqueActionName((OpaqueAction)content);
            }
        }
    }

    /**
     * Set the name of the given opaque action.
     *
     * @param opaqueAction
     *            The opaque action.
     */
    private void setOpaqueActionName(OpaqueAction opaqueAction) {
        if (opaqueAction.getName() == null) {
            String body = opaqueAction.getBodies().get(
                    opaqueAction.getLanguages().indexOf(BundleConstants.C_LANGUAGE)).trim();
            if (body.length() > 0) {
                int length = Math.min(BundleConstants.OPAQUE_ACTION_NAME_MAX_LENGTH, body.length());
                // CHECKSTYLE:OFF
                String name = body.substring(0, length)
                        + (body.length() > BundleConstants.OPAQUE_ACTION_NAME_MAX_LENGTH ? "..." : "");
                // CHECKSTYLE:ON
                opaqueAction.setName(name);
            }
        }
    }

    /**
     * Merge the opaque actions from the given activity.
     *
     * @param activity
     *            The activity.
     */
    private void mergeOpaqueActions(Activity activity) {
        ControlFlow cf = null;
        while ((cf = getNextControlFlowToMerge(activity)) != null) {
            // Merge du contenu
            OpaqueAction sourceOA = (OpaqueAction)cf.getSource();
            OpaqueAction targetOA = (OpaqueAction)cf.getTarget();

            String followingBody = targetOA.getBodies().get(0);
            if (followingBody != null && !"".equals(followingBody)) {
                String precedingBody = sourceOA.getBodies().get(0);
                StringBuilder sb = new StringBuilder(precedingBody);
                sb.append(BundleConstants.LINE_SEPARATOR).append(followingBody);
                sourceOA.getBodies().set(0, sb.toString());
            }

            // Merge des outgoings du target
            sourceOA.getOutgoings().addAll(targetOA.getOutgoings());
            targetOA.getOutgoings().clear();

            // Suppression
            targetOA.destroy();
            cf.destroy();
        }
    }

    /**
     * Get the next flow to merge from the given activity.
     *
     * @param activity
     *            The activity.
     * @return The flow.
     */
    private ControlFlow getNextControlFlowToMerge(Activity activity) {
        ControlFlow cf = null;
        TreeIterator<EObject> contentIterator = activity.eAllContents();
        while (contentIterator.hasNext()) {
            EObject content = contentIterator.next();

            if (content instanceof ControlFlow) {
                cf = (ControlFlow)content;
                // CHECKSTYLE:OFF
                if (cf.getGuard() instanceof LiteralBoolean && cf.getGuard().booleanValue() == true
                        && cf.getSource() instanceof OpaqueAction && cf.getTarget() instanceof OpaqueAction
                        && cf.getSource().getOutgoings().size() == 1
                        && cf.getTarget().getIncomings().size() == 1
                        && !isActionAttachedToClause(cf.getTarget(), activity)) {
                    // CHECKSTYLE:ON
                    return cf;
                }
                cf = null;
            }
        }
        return cf;
    }

    /**
     * Check if the given action is attached to a clause.
     *
     * @param action
     *            The action.
     * @param activity
     *            The related activity.
     * @return True if yes.
     */
    @SuppressWarnings("serial")
    private boolean isActionAttachedToClause(ActivityNode action, Activity activity) {
        Collection<EStructuralFeature.Setting> references = new EcoreUtil.UsageCrossReferencer(activity) {
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
        }.findUsage(action);
        return references.size() > 0;
    }
}
