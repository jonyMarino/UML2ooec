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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.umlgen.reverse.java.logging.LogUtils;

/**
 * Creates Activity diagrams.
 */
public class ActivityDiagramUtils extends AbstractDiagramUtils {

    /** Instance of ActivityDiagramUtils. */
    public static ActivityDiagramUtils INSTANCE = new ActivityDiagramUtils();

    @Override
    protected Set<Diagram> specificDiagramCreation(Package contents, ModelSet resourceSet) {
        Set<Diagram> diagrams = new HashSet<Diagram>();

        List<Activity> activities = new ArrayList<Activity>();
        findActivities(contents, activities);
        for (Activity activity : activities) {
            Diagram diagram = ViewService.createDiagram(contents, ActivityDiagramEditPart.MODEL_ID,
                    new PreferencesHint(UMLDiagramEditorPlugin.ID));
            LogUtils.logMessage("   activity diagram for " + contents.getName());
            Element owner = activity.getOwner();
            String ownerString = owner instanceof NamedElement ? "." + ((NamedElement)owner).getName() : "";
            String ownerPackageName = owner.getNearestPackage().getQualifiedName().replaceAll("::", ".");
            diagram.setName(ownerPackageName + ownerString + "." + activity.getName());
            diagram.setElement(activity);
            addDiagramToResource(resourceSet, diagram);
            initDiagram(diagram, activity, resourceSet);
            diagrams.add(diagram);
        }

        return diagrams;
    }

    /**
     * Initializes diagram.
     *
     * @param diagram
     * @param activity
     * @param modelSet
     */
    protected void initDiagram(Diagram diagram, Activity activity, ModelSet modelSet) {
        Map<EObject, View> viewMap = new HashMap<EObject, View>();
        // Main activity node
        Node mainNode = addNode(modelSet, diagram, activity, "2001", new PreferencesHint(
                UMLDiagramEditorPlugin.ID));
        for (Object childObj : mainNode.getChildren()) {
            View childView = (View)childObj;
            if ("7004".equals(childView.getType())) {
                for (ActivityNode node : activity.getNodes()) {
                    Node createdNode = addNode(modelSet, childView, node, String.valueOf(UMLVisualIDRegistry
                            .getNodeVisualID(childView, node)),
                            new PreferencesHint(UMLDiagramEditorPlugin.ID));
                    viewMap.put(node, createdNode);
                }
                for (ActivityEdge edge : activity.getEdges()) {
                    View node1 = viewMap.get(edge.getSource());
                    View node2 = viewMap.get(edge.getTarget());

                    int typeID = UMLVisualIDRegistry.getLinkWithClassVisualID(edge);
                    // FIXME
                    IElementType elementType = UMLElementTypes.getElementType(typeID);
                    CreateElementRequestAdapter adapter = new CreateElementRequestAdapter(
                            new CreateRelationshipRequest(elementType));
                    String type = String.valueOf(typeID);
                    PreferencesHint preferencesHint = new PreferencesHint(UMLDiagramEditorPlugin.ID);
                    Edge edgeView = addEdge(modelSet, adapter, diagram, type, -1, true, preferencesHint);

                    setEdgeInfo(modelSet, edgeView, node1, node2, edge);
                }
                break;
            }
        }
    }

    /**
     * Finds Activities in a Package.
     *
     * @param contents
     * @param activities
     */
    protected void findActivities(Package contents, List<Activity> activities) {
        for (EObject obj : contents.getPackagedElements()) {
            if (obj instanceof Package) {
                findActivities((Package)obj, activities);
            } else if (obj instanceof Class) {
                findActivities((Class)obj, activities);
            }
        }
    }

    /**
     * Find Activities in a class.
     *
     * @param clazz
     * @param activities
     */
    protected void findActivities(Class clazz, List<Activity> activities) {
        EList<Behavior> ownedBehaviors = clazz.getOwnedBehaviors();
        for (Behavior behavior : ownedBehaviors) {
            if (behavior instanceof Activity) {
                activities.add((Activity)behavior);
            }
        }
    }

}
