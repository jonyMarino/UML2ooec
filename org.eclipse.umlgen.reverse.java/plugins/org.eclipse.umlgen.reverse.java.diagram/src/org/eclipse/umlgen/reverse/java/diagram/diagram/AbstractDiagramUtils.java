/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Philippe Roland (Atos) - initialize API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.java.diagram.diagram;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.umlgen.reverse.java.logging.LogUtils;

/**
 * Abstract class for diagram creation utilities. You should create one such utility for each type of diagram
 * to be supported
 */
public abstract class AbstractDiagramUtils {

    /** Map of RelationShip. */
    protected Map<Diagram, Set<Relationship>> relationships;

    /**
     * Create diagrams.
     *
     * @param contents
     * @param resourceSet
     * @param openAutomatically
     * @return diagrams
     */
    public Set<Diagram> createDiagrams(final Package contents, final ModelSet resourceSet,
            final boolean openAutomatically) {
        init();
        final Set<Diagram> diagrams = new HashSet<Diagram>();
        Display.getDefault().asyncExec(new Runnable() {

            public void run() {
                diagrams.addAll(specificDiagramCreation(contents, resourceSet));
                // Making diagrams available and opening them is made after
                // creation, since we run a check to ensure empty ones
                // are not open. This is separate from the part where diagrams
                // are added to the resourceset, as the resource set
                // is needed for some parts of the initialization process
                try {
                    resourceSet.save(new NullProgressMonitor());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Diagram diagram : diagrams) {
                    addDiagram(diagram, openAutomatically);
                }
            }
        });
        return diagrams;
    }

    /**
     * Creates diagrams.
     *
     * @param contents
     * @param resourceSet
     * @return diagrams
     */
    protected abstract Set<Diagram> specificDiagramCreation(Package contents, ModelSet resourceSet);

    /**
     * Setup method, fired before each generation. Unless overridden, Resets the relationships field
     */
    protected void init() {
        relationships = new HashMap<Diagram, Set<Relationship>>();
    }

    /**
     * Adds diagrams to the Resource.
     *
     * @param modelSet
     * @param diagram
     */
    protected void addDiagramToResource(final ModelSet modelSet, final Diagram diagram) {
        ICommand addDiagramCommand = new AbstractTransactionalCommand(modelSet
                .getTransactionalEditingDomain(), "Add diagram", null) {
            @Override
            protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                NotationUtils.getNotationModel(modelSet).getResource().getContents().add(diagram);
                return CommandResult.newOKCommandResult();
            }
        };
        modelSet.getTransactionalEditingDomain().getCommandStack().execute(
                new GMFtoEMFCommandWrapper(addDiagramCommand));
    }

    /**
     * Adds node.
     *
     * @param modelSet
     * @param diagram
     * @param client
     * @param semanticHint
     * @param preferencesHint
     * @return node
     */
    protected Node addNode(final ModelSet modelSet, final View diagram, final EObject client,
            final String semanticHint, final PreferencesHint preferencesHint) {

        AddNodeCommand addNodeCommand = new AddNodeCommand(modelSet, diagram, client, semanticHint,
                preferencesHint);
        modelSet.getTransactionalEditingDomain().getCommandStack().execute(
                new GMFtoEMFCommandWrapper(addNodeCommand));
        return addNodeCommand.getNode();

    }

    /**
     * Adds Edge.
     *
     * @param modelSet
     * @param semanticAdapter
     * @param diagram
     * @param semanticHint
     * @param index
     * @param persisted
     * @param preferencesHint
     * @return edge
     */
    protected Edge addEdge(final ModelSet modelSet, final IAdaptable semanticAdapter, final Diagram diagram,
            final String semanticHint, final int index, final boolean persisted,
            final PreferencesHint preferencesHint) {

        AddEdgeCommand addEdgeActivityCommand = new AddEdgeCommand(modelSet, semanticAdapter, diagram,
                semanticHint, index, persisted, preferencesHint);
        modelSet.getTransactionalEditingDomain().getCommandStack().execute(
                new GMFtoEMFCommandWrapper(addEdgeActivityCommand));
        return addEdgeActivityCommand.getEdge();
    }

    /**
     * Adds diagram.
     *
     * @param diagram
     * @param openAutomatically
     */
    private void addDiagram(Diagram diagram, boolean openAutomatically) {
        try {
            IPageManager pageManager = ServiceUtilsForEObject.getInstance().getIPageManager(diagram);
            // add the diagram to the list of pages so it is available for
            // opening, but do not open
            // it unless it has children
            if (openAutomatically && diagram.getPersistedChildren() != null
                    && !diagram.getPersistedChildren().isEmpty()) {
                pageManager.openPage(diagram);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            LogUtils.logThrowable(e);

        }
    }

    /**
     * Creates an edge with a given type and connects it between the given . source and target
     *
     * @author ebenoit
     */
    protected class AddEdgeCommand extends AbstractTransactionalCommand {

        /** The edge. */
        private Edge edge;

        /** an adapter for the model object. */
        private IAdaptable adapter;

        /** The diagram. */
        private Diagram diagram;

        /** Visual ID. */
        private String semanticHint;

        /** position in the container view's list of children views. */
        private int index;

        /** indicates if the created edge will be persisted or not. */
        private boolean persisted;

        /**
         * The preference hint that is to be used to find the appropriate preference store from which to
         * retrieve diagram preference values.
         */
        private PreferencesHint preferencesHint;

        /**
         * Constructor.
         *
         * @param modelSet
         * @param adapter
         * @param diagram
         * @param semanticHint
         * @param index
         * @param persisted
         * @param preferencesHint
         */
        public AddEdgeCommand(final ModelSet modelSet, final IAdaptable adapter, final Diagram diagram,
                final String semanticHint, final int index, final boolean persisted,
                final PreferencesHint preferencesHint) {

            super(modelSet.getTransactionalEditingDomain(), "Add Edge", null);
            this.adapter = adapter;
            this.diagram = diagram;
            this.semanticHint = semanticHint;
            this.index = index;
            this.persisted = persisted;
            this.preferencesHint = preferencesHint;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
                throws ExecutionException {

            Edge edgeView = ViewService.getInstance().createEdge(adapter, diagram, semanticHint, index,
                    persisted, preferencesHint);

            if (edgeView instanceof Edge) {
                edge = (Edge)edgeView;
                return CommandResult.newOKCommandResult();
            } else {
                return CommandResult.newErrorCommandResult("Creation of edge " + semanticHint
                        + " returned non-Edge object");
            }
        }

        public Edge getEdge() {
            return edge;
        }
    }

    /**
     * Creates a node for a given eObject and with a given type and inserts it into a given container.
     */
    protected class AddNodeCommand extends AbstractTransactionalCommand {

        /** The node. */
        private Node node;

        /** The node view diagram. */
        private View diagram;

        /** The node view type, check for predefined values. */
        private String semanticHint;

        /** The node view object context. */
        private EObject client;

        /**
         * The preference hint that is to be used to find the appropriate preference store from which to
         * retrieve diagram preference value.
         */
        private PreferencesHint preferencesHint;

        /**
         * Constructor.
         *
         * @param modelSet
         * @param diagram
         * @param client
         * @param semanticHint
         * @param preferencesHint
         */
        public AddNodeCommand(final ModelSet modelSet, final View diagram, final EObject client,
                final String semanticHint, final PreferencesHint preferencesHint) {

            super(modelSet.getTransactionalEditingDomain(), "Add Node", null);
            this.diagram = diagram;
            this.client = client;
            this.semanticHint = semanticHint;
            this.preferencesHint = preferencesHint;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
                throws ExecutionException {
            node = ViewService.createNode(diagram, client, semanticHint, preferencesHint);
            if (node instanceof Node) {
                // node = ((Node) view);
                return CommandResult.newOKCommandResult();
            } else {
                return CommandResult.newErrorCommandResult("Creation of edge " + semanticHint
                        + " returned non-Edge object");
            }
        }

        public Node getNode() {
            return node;
        }
    }

    /**
     * Set informations of the edge.
     *
     * @param modelSet
     * @param edge
     * @param source
     * @param target
     * @param element
     */
    protected void setEdgeInfo(final ModelSet modelSet, final Edge edge, final View source,
            final View target, final EObject element) {
        ICommand setResourceCommand = new AbstractTransactionalCommand(modelSet
                .getTransactionalEditingDomain(), "Set Edge Info", null) {
            @Override
            protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                edge.setSource(source);
                edge.setTarget(target);
                edge.setElement(element);
                return CommandResult.newOKCommandResult();
            }
        };
        modelSet.getTransactionalEditingDomain().getCommandStack().execute(
                new GMFtoEMFCommandWrapper(setResourceCommand));
    }
}
