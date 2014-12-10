/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *      Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity.comments;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.activity.beans.CommentInfo;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

/** The comment builder. */
public class CommentBuilder {

    /** The annotation key to store comment before. */
    private static final String KEY_BEFORE = "COMMENT_BEFORE";

    /** The annotation key to store comment inline. */
    private static final String KEY_INLINE = "COMMENT_INLINE";

    /** The annotation key to store comment last line. */
    private static final String KEY_LAST_LINE = "COMMENT_LAST_LINE";

    /** The annotation key to store comment same line. */
    private static final String KEY_SAME_LINE = "COMMENT_SAME_LINE";

    /** The activity factory. */
    private UMLActivityFactory factory;

    /**
     * Constructor.
     *
     * @param factory
     *            The activity factory.
     */
    public CommentBuilder(UMLActivityFactory factory) {
        this.factory = factory;
    }

    /**
     * This builds comments.
     *
     * @param action
     *            The action to comment.
     * @param commentInfo
     *            The comment information.
     */
    public void buildComment(OpaqueAction action, CommentInfo commentInfo) {
        if (commentInfo == null) {
            return;
        }

        if (commentInfo.hasBefore()) {
            action.getBodies().set(0,
                    commentInfo.getBefore() + BundleConstants.LINE_SEPARATOR + action.getBodies().get(0));
        }
        if (commentInfo.hasSameLine()) {
            action.getBodies().set(0, action.getBodies().get(0) + " " + commentInfo.getSameLine());
        }
        if (commentInfo.hasLastLine()) {
            action.getBodies().set(0,
                    action.getBodies().get(0) + BundleConstants.LINE_SEPARATOR + commentInfo.getLastLine());
        }
    }

    /**
     * This builds comments.
     *
     * @param umlElement
     *            The element to comment.
     * @param commentInfo
     *            The comment information.
     */
    public void buildComment(Element umlElement, CommentInfo commentInfo) {
        if (commentInfo == null) {
            return;
        }
        EAnnotation ann = createCommentAnnotation(commentInfo);
        umlElement.getEAnnotations().add(ann);
    }

    /**
     * This creates the annotation to store comments.
     *
     * @param commentInfo
     *            The comment information.
     * @return The annotation.
     */
    private EAnnotation createCommentAnnotation(CommentInfo commentInfo) {
        EAnnotation ann = factory.createEAnnotation(AnnotationConstants.DOCUMENTATION_SOURCE);
        if (commentInfo.hasBefore()) {
            ann.getDetails().put(KEY_BEFORE, commentInfo.getBefore());
        }
        if (commentInfo.hasSameLine()) {
            ann.getDetails().put(KEY_SAME_LINE, commentInfo.getSameLine());
        }
        if (commentInfo.hasInline()) {
            ann.getDetails().put(KEY_INLINE, commentInfo.getInline());
        }
        if (commentInfo.hasLastLine()) {
            ann.getDetails().put(KEY_LAST_LINE, commentInfo.getLastLine());
        }
        return ann;
    }
}
