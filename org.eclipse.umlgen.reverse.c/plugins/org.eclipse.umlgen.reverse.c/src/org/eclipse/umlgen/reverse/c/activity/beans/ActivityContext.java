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
package org.eclipse.umlgen.reverse.c.activity.beans;

import java.util.List;

import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.StructuredActivityNode;

/**
 * An activity context.
 */
public class ActivityContext {

    /** The list of nodes. */
    private List<ActivityNode> nodes;

    /** The list of edges. */
    private List<ActivityEdge> edges;

    /**
     * Constructor.
     *
     * @param activity
     *            The activity.
     */
    public ActivityContext(Activity activity) {
        nodes = activity.getNodes();
        edges = activity.getEdges();
    }

    /**
     * Constructor.
     *
     * @param node
     *            The structured activity node.
     */
    public ActivityContext(StructuredActivityNode node) {
        nodes = node.getNodes();
        edges = node.getEdges();
    }

    public List<ActivityNode> getNodes() {
        return nodes;
    }

    /**
     * Add a node to the list of nodes.
     *
     * @param node
     *            The node to add.
     */
    public void addNode(ActivityNode node) {
        getNodes().add(node);
    }

    public List<ActivityEdge> getEdges() {
        return edges;
    }

    /**
     * Add an edge to the list of edges.
     *
     * @param edge
     *            The edge to add.
     */
    public void addEdge(ActivityEdge edge) {
        getEdges().add(edge);
    }
}
