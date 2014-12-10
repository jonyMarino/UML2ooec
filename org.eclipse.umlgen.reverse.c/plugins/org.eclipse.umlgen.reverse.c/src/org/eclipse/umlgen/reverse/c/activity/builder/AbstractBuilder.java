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
package org.eclipse.umlgen.reverse.c.activity.builder;

import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.activity.beans.CommentInfo;
import org.eclipse.umlgen.reverse.c.activity.comments.CommentBuilder;
import org.eclipse.umlgen.reverse.c.activity.util.UMLActivityFactory;

/** Abstract builder. */
public abstract class AbstractBuilder {

    /** Activity builder. */
    protected UMLActivityBuilder activityBuilder;

    /** Activity factory. */
    protected UMLActivityFactory factory;

    /** Comment builder. */
    protected CommentBuilder commentBuilder;

    /** Map comment info to nodes. */
    protected Map<IASTNode, CommentInfo> nodesAndComments;

    /**
     * Constructor.
     *
     * @param activityBuilder
     *            the activity builder.
     * @param factory
     *            The factory.
     * @param commentBuilder
     *            The comment builder.
     */
    public AbstractBuilder(UMLActivityBuilder activityBuilder, UMLActivityFactory factory,
            CommentBuilder commentBuilder) {
        this.activityBuilder = activityBuilder;
        this.factory = factory;
        this.commentBuilder = commentBuilder;
    }

    public void setStatementsAndComments(Map<IASTNode, CommentInfo> pNodesAndComments) {
        this.nodesAndComments = pNodesAndComments;
    }

    /**
     * Get the comment info from the given node.
     *
     * @param node
     *            The node.
     * @return The comment info.
     */
    public CommentInfo getCommentInfo(IASTNode node) {
        return nodesAndComments.remove(node);
    }
}
