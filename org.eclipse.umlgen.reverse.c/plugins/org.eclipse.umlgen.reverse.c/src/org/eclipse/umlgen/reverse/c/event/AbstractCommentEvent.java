/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS) - initial API and implementation
 *     Christophe Le Camus, Sebastien Gabel (CS) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.c.common.util.CommentFormatter;

/**
 * Abstract representation of an event related to a definition of a comment.
 */
public abstract class AbstractCommentEvent extends AbstractCModelChangedEvent {

    /** The body. */
    private String body;

    /** The parent node. */
    private IASTNode parent;

    /** The comment. */
    private IASTComment source;

    public String getBody() {
        return body;
    }

    protected void setBody(String body) {
        this.body = CommentFormatter.unwrap(body);
    }

    public IASTNode getParent() {
        return parent;
    }

    protected void setParent(IASTNode parent) {
        this.parent = parent;
    }

    public IASTComment getSource() {
        return source;
    }

    public void setSource(IASTComment source) {
        this.source = source;
    }

    /**
     * Get the annotation key name according to the location of the comment.
     *
     * @return The key name.
     */
    public String getKey() {
        String result = AnnotationConstants.DOCUMENTATION_KEY;
        if (!(parent instanceof IASTTranslationUnit)) {
            if (source.getFileLocation().getStartingLineNumber() == source.getFileLocation()
                    .getEndingLineNumber()) {
                if (source.getFileLocation().getStartingLineNumber() == parent.getFileLocation()
                        .getStartingLineNumber()) {
                    // CHECKSTYLE:OFF
                    if (source.getFileLocation().getNodeOffset() < parent.getFileLocation().getNodeOffset()) {
                        result = getTranslationUnit().isHeaderUnit() ? AnnotationConstants.H_INLINE_BEFORE
                                : AnnotationConstants.C_INLINE_BEFORE;
                    } else {
                        result = getTranslationUnit().isHeaderUnit() ? AnnotationConstants.H_INLINE_AFTER
                                : AnnotationConstants.C_INLINE_AFTER;
                    }
                    // CHECKSTYLE:ON
                }
            }
        }

        return result;
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractCommentEvent> extends AbstractCModelChangedEvent.AbstractBuilder<AbstractCommentEvent> {

        /**
         * This sets the given body to the event.
         *
         * @param body
         *            the body.
         * @return self.
         */
        public AbstractBuilder<T> setBody(String body) {
            getEvent().setBody(body);
            return this;
        }

        /**
         * This sets the given parent node to the event.
         *
         * @param parent
         *            The parent node.
         * @return self
         */
        public AbstractBuilder<T> setParent(IASTNode parent) {
            getEvent().setParent(parent);
            return this;
        }

        /**
         * This sets the given comment to the event.
         *
         * @param comment
         *            the comment
         * @return self
         */
        public AbstractBuilder<T> setSource(IASTComment comment) {
            getEvent().setSource(comment);
            return this;
        }
    }
}
