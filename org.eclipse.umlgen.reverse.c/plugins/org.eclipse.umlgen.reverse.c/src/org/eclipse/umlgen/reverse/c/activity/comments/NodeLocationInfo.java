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
package org.eclipse.umlgen.reverse.c.activity.comments;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

/** The node location information. */
public class NodeLocationInfo {

    /** The starting offset number. */
    private int startingOffset;

    /** The ending offset number. */
    private int endingOffset;

    /** The starting line number. */
    private int startingLine;

    /** The ending line number. */
    private int endingLine;

    /** The starting offset number for inline. */
    private int startingOffsetForInline;

    /** The ending offset number for inline. */
    private int endingOffsetForInline;

    /**
     * Constructor.
     *
     * @param node
     *            the node.
     */
    public NodeLocationInfo(IASTNode node) {
        startingOffset = node.getFileLocation().getNodeOffset();
        endingOffset = startingOffset + node.getFileLocation().getNodeLength() - 1;
        startingLine = node.getFileLocation().getStartingLineNumber();
        endingLine = node.getFileLocation().getEndingLineNumber();
        startingOffsetForInline = startingOffset;
        if (node instanceof IASTSwitchStatement) {
            IASTNode nextEnclosedNode = ((IASTSwitchStatement)node).getBody();
            endingOffsetForInline = nextEnclosedNode.getFileLocation().getNodeOffset() - 1;
        } else if (node instanceof IASTDoStatement) {
            IASTNode nextEnclosedNode = ((IASTDoStatement)node).getBody();
            endingOffsetForInline = nextEnclosedNode.getFileLocation().getNodeOffset() - 1;
        } else if (node instanceof IASTWhileStatement) {
            IASTNode nextEnclosedNode = ((IASTWhileStatement)node).getBody();
            endingOffsetForInline = nextEnclosedNode.getFileLocation().getNodeOffset() - 1;
        } else if (node instanceof IASTForStatement) {
            IASTNode nextEnclosedNode = ((IASTForStatement)node).getBody();
            endingOffsetForInline = nextEnclosedNode.getFileLocation().getNodeOffset() - 1;
        } else if (node instanceof IASTIfStatement) {
            IASTNode nextEnclosedNode = ((IASTIfStatement)node).getThenClause();
            endingOffsetForInline = nextEnclosedNode.getFileLocation().getNodeOffset() - 1;
        } else if (node instanceof IASTCompoundStatement) {
            endingOffsetForInline = startingOffsetForInline;
        } else {
            endingOffsetForInline = endingOffset;
        }
    }

    public int getStartingOffset() {
        return startingOffset;
    }

    public void setStartingOffset(int startingOffset) {
        this.startingOffset = startingOffset;
    }

    public int getEndingOffset() {
        return endingOffset;
    }

    public void setEndingOffset(int endingOffset) {
        this.endingOffset = endingOffset;
    }

    public int getStartingLine() {
        return startingLine;
    }

    public void setStartingLine(int startingLine) {
        this.startingLine = startingLine;
    }

    public int getEndingLine() {
        return endingLine;
    }

    public void setEndingLine(int endingLine) {
        this.endingLine = endingLine;
    }

    public int getStartingOffsetForInline() {
        return startingOffsetForInline;
    }

    public void setStartingOffsetForInline(int startingOffsetForInline) {
        this.startingOffsetForInline = startingOffsetForInline;
    }

    public int getEndingOffsetForInline() {
        return endingOffsetForInline;
    }

    public void setEndingOffsetForInline(int endingOffsetForInline) {
        this.endingOffsetForInline = endingOffsetForInline;
    }
}
