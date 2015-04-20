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
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ElementImportEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.RealizationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.UsageEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.umlgen.reverse.java.logging.LogUtils;

/**
 * Creates Class diagrams.
 */
public class ClassDiagramUtils extends AbstractDiagramUtils {

    /** Instance of ClassDiagramUtils. */
    public static ClassDiagramUtils INSTANCE = new ClassDiagramUtils();

    /**
     * Creates diagrams.
     *
     * @param contents
     * @param resourceSet
     * @return diagrams
     */
    @Override
    protected Set<Diagram> specificDiagramCreation(Package contents, ModelSet resourceSet) {
        Set<Diagram> diagrams = new HashSet<Diagram>();
        diagrams.add(populateOrCreatePackDiagram(contents, null, resourceSet));
        diagrams.addAll(createClassDiagrams(contents, null, resourceSet));
        return diagrams;
    }

    /**
     * Creates package diagram.
     *
     * @param model
     * @param diagram
     * @param resourceSet
     * @return the diagram
     */
    protected Diagram populateOrCreatePackDiagram(Package model, Diagram diagram, ModelSet resourceSet) {
        Map<EObject, View> viewMap = new HashMap<EObject, View>();
        if (diagram == null) {
            diagram = ViewService.createDiagram(model, ModelEditPart.MODEL_ID, new PreferencesHint(
                    UMLDiagramEditorPlugin.ID));
            LogUtils.logMessage("   class diagram for packageImports");
            diagram.setName(model.getName() + "_packageImports");
            diagram.setElement(model);
            addDiagramToResource(resourceSet, diagram);
        }
        if (diagram != null) {
            for (EObject obj : model.getPackagedElements()) {
                if (obj instanceof Package) {
                    List<Relationship> packageDependencies = new ArrayList<Relationship>();
                    for (EObject packChild : ((Package)obj).getPackagedElements()) {
                        if (packChild instanceof Usage) {
                            Usage usage = (Usage)packChild;
                            // if this is a single client-supplier Package<->Package relationship where the
                            // source is
                            // this package
                            if (usage.getClients() != null && usage.getClients().size() == 1
                                    && obj.equals(usage.getClients().get(0)) && usage.getSuppliers() != null
                                    && usage.getSuppliers().size() == 1
                                    && usage.getSuppliers().get(0) instanceof Package) {
                                packageDependencies.add(usage);
                                NamedElement client = usage.getClients().get(0);
                                NamedElement supplier = usage.getSuppliers().get(0);
                                // TODO if we ever need to crate Packages inside of Packages, the id of
                                // packaged
                                // packages is 3009
                                if (!viewMap.containsKey(client)) {
                                    View packageView = addNode(resourceSet, diagram, client, String
                                            .valueOf(PackageEditPart.VISUAL_ID), new PreferencesHint(
                                            UMLDiagramEditorPlugin.ID));
                                    viewMap.put(client, packageView);
                                }
                                if (!viewMap.containsKey(supplier)) {
                                    View packageView = addNode(resourceSet, diagram, supplier, String
                                            .valueOf(PackageEditPart.VISUAL_ID), new PreferencesHint(
                                            UMLDiagramEditorPlugin.ID));
                                    viewMap.put(supplier, packageView);
                                }
                            }
                        }
                    }
                    Set<Relationship> rel = relationships.get(diagram);
                    if (rel == null) {
                        rel = new HashSet<Relationship>();
                        relationships.put(diagram, rel);
                    }
                    relationships.get(diagram).addAll(packageDependencies);
                    populateOrCreatePackDiagram((Package)obj, diagram, resourceSet);
                }
            }
        }
        createRelationships(null, diagram, true, viewMap, resourceSet);
        return diagram;
    }

    /**
     * Creates class diagrams.
     *
     * @param contents
     * @param parentName
     * @param resourceSet
     * @return diagrams
     */
    protected Set<Diagram> createClassDiagrams(Package contents, String parentName, ModelSet resourceSet) {
        Map<EObject, View> viewMap = new HashMap<EObject, View>();
        Set<Diagram> diagrams = new HashSet<Diagram>();
        String name = contents.getName();
        Diagram diagram = ViewService.createDiagram(contents, ModelEditPart.MODEL_ID, new PreferencesHint(
                UMLDiagramEditorPlugin.ID));
        LogUtils.logMessage("   class diagram for " + contents.getName());
        diagram.setName(name);
        diagram.setElement(contents);
        diagrams.add(diagram);
        addDiagramToResource(resourceSet, diagram);
        for (EObject child : contents.getPackagedElements()) {
            if (child instanceof Package) {
                diagrams.addAll(createClassDiagrams((Package)child, name, resourceSet));
            } else {
                addElement(diagram, diagram, child, viewMap, resourceSet);
            }
        }
        createRelationships(contents, diagram, false, viewMap, resourceSet);
        return diagrams;
    }

    /**
     * Create the diagram's relationships by fetching from the relationships map Relationships coming from
     * elements from outside the package are ignored.
     *
     * @param pack
     *            : the package
     * @param diagram
     *            : the diagram
     * @param isPackageDiagram
     *            : if this is a package diagram, set to true. it will allow package-to-package relationships
     *            to be created
     * @param viewMap
     * @param modelSet
     */
    protected void createRelationships(Package pack, Diagram diagram, boolean isPackageDiagram,
            Map<EObject, View> viewMap, ModelSet modelSet) {
        Set<Relationship> rels = relationships.get(diagram);
        if (rels == null || rels.isEmpty()) {
            return;
        }
        for (Relationship r : rels) {
            Edge edge = null;
            View source = null;
            View target = null;
            if (r instanceof DirectedRelationship) {
                Object sourceObj = ((DirectedRelationship)r).getSources().get(0);
                Object targetObj = ((DirectedRelationship)r).getTargets().get(0);
                // The condition that the source element be contained directly within pack does not aply to
                // package
                // diagrams
                if (sourceObj != null && targetObj != null
                        && (isPackageDiagram || pack.getPackagedElements().contains(sourceObj))) {
                    source = viewMap.get(sourceObj);
                    target = viewMap.get(targetObj);
                    if (r instanceof Generalization) {
                        edge = addEdge(modelSet, UMLElementTypes.Generalization_4002, diagram, String
                                .valueOf(GeneralizationEditPart.VISUAL_ID), ViewUtil.APPEND, true,
                                new PreferencesHint(UMLDiagramEditorPlugin.ID));

                    } else if (r instanceof Realization) {
                        edge = addEdge(modelSet, UMLElementTypes.Realization_4005, diagram, String
                                .valueOf(RealizationEditPart.VISUAL_ID), ViewUtil.APPEND, true,
                                new PreferencesHint(UMLDiagramEditorPlugin.ID));
                    } else if (r instanceof Usage) {
                        if (sourceObj instanceof Package && targetObj instanceof Package && isPackageDiagram) {
                            edge = addEdge(modelSet, UMLElementTypes.Usage_4007, diagram, String
                                    .valueOf(UsageEditPart.VISUAL_ID), ViewUtil.APPEND, true,
                                    new PreferencesHint(UMLDiagramEditorPlugin.ID));
                        }
                    } else if (r instanceof ElementImport) {
                        // FIXME : mm sol que pour usage
                        edge = (Edge)addEdge(modelSet, UMLElementTypes.ElementImport_4009, diagram, String
                                .valueOf(ElementImportEditPart.VISUAL_ID), ViewUtil.APPEND, true,
                                new PreferencesHint(UMLDiagramEditorPlugin.ID));
                    }
                }
            } else if (r instanceof Association) {
                Association association = (Association)r;
                // Take the list of member ends, remove the target end to find the source
                ArrayList<Property> memberEndsCopy = new ArrayList<Property>(association.getMemberEnds());
                memberEndsCopy.removeAll(association.getOwnedEnds());
                // Associations should not be created in a package diagram, but we'll keep this to prevent
                // NPEs
                if (memberEndsCopy.size() == 1
                        && (isPackageDiagram || pack.getPackagedElements().contains(
                                memberEndsCopy.get(0).getType()))) {
                    edge = addEdge(modelSet, UMLElementTypes.Association_4001, diagram, String
                            .valueOf(AssociationEditPart.VISUAL_ID), ViewUtil.APPEND, true,
                            new PreferencesHint(UMLDiagramEditorPlugin.ID));

                    EList<Property> memberEnds = ((Association)r).getMemberEnds();
                    if (memberEnds.size() == 2) {
                        source = viewMap.get(memberEnds.get(0).getType());
                        target = viewMap.get(memberEnds.get(1).getType());
                    }
                }

            }
            if (edge != null && source != null && target != null) {
                setEdgeInfo(modelSet, edge, source, target, r);
            }
        }

    }

    /**
     * Adds element.
     *
     * @param diagram
     * @param container
     * @param obj
     * @param viewMap
     * @param modelSet
     */
    protected void addElement(Diagram diagram, View container, EObject obj, Map<EObject, View> viewMap,
            ModelSet modelSet) {
        boolean isInPackage = !(container instanceof Diagram);
        if (obj instanceof Activity) {
            // TODO
            // do nothing
        } else if (obj instanceof Class) {
            createClass(diagram, container, obj, viewMap, true, modelSet);
        } else if (obj instanceof Interface) {
            Node interfaceNode = null;
            if (isInPackage) {
                // TODO
            } else {
                interfaceNode = addNode(modelSet, container, obj,
                        String.valueOf(InterfaceEditPart.VISUAL_ID), new PreferencesHint(
                                UMLDiagramEditorPlugin.ID));
            }
            viewMap.put(obj, interfaceNode);
            addInterfaceChildren(diagram, interfaceNode, (Interface)obj, viewMap, modelSet);
        } else if (obj instanceof Enumeration) {
            Node enumNode = addNode(modelSet, container, obj, String.valueOf(EnumerationEditPart.VISUAL_ID),
                    new PreferencesHint(UMLDiagramEditorPlugin.ID));
            if (enumNode != null) {
                viewMap.put(obj, enumNode);
                addEnumChildren(enumNode, (Enumeration)obj, modelSet);
            }
        } else if (obj instanceof DirectedRelationship) {
            Set<Relationship> rel = relationships.get(diagram);
            if (rel == null) {
                rel = new HashSet<Relationship>();
                relationships.put(diagram, rel);
            }
            relationships.get(diagram).add((Relationship)obj);
        }
    }

    /**
     * Creates class.
     *
     * @param diagram
     *            : the diagram
     * @param container
     * @param obj
     * @param viewMap
     * @param createChildren
     * @param modelSet
     */
    protected void createClass(Diagram diagram, View container, EObject obj, Map<EObject, View> viewMap,
            boolean createChildren, ModelSet modelSet) {
        Node classNode = null;
        boolean isInPackage = !(container instanceof Diagram);
        if (!isInPackage) {
            classNode = addNode(modelSet, container, obj, String.valueOf(ClassEditPart.VISUAL_ID),
                    new PreferencesHint(UMLDiagramEditorPlugin.ID));
        }
        viewMap.put(obj, classNode);
        if (createChildren) {
            addClassChildren(diagram, classNode, (Class)obj, viewMap, modelSet);
        }
    }

    /**
     * Adds enumeration children.
     *
     * @param enumNomde
     * @param obj
     * @param modelSet
     */
    protected void addEnumChildren(Node enumNomde, Enumeration obj, ModelSet modelSet) {
        View litContainer = null;
        for (Object childNode : enumNomde.getPersistedChildren()) {
            if (childNode instanceof View) {
                if ("7015".equals(((View)childNode).getType())) {
                    litContainer = (View)childNode;
                }
            }
        }
        for (EObject literal : obj.getOwnedLiterals()) {
            addNode(modelSet, litContainer, literal, "3017", new PreferencesHint(UMLDiagramEditorPlugin.ID));
        }
    }

    /**
     * Adds Interfaces children.
     *
     * @param diagram
     * @param interfaceNode
     * @param interf
     * @param viewMap
     * @param modelSet
     */
    protected void addInterfaceChildren(Diagram diagram, Node interfaceNode, Interface interf,
            Map<EObject, View> viewMap, ModelSet modelSet) {
        View propContainer = null;
        View opContainer = null;
        for (Object childNode : interfaceNode.getPersistedChildren()) {
            if (childNode instanceof View) {
                if ("7006".equals(((View)childNode).getType()) || "7006".equals(((View)childNode).getType())) // Type
                {
                    propContainer = (View)childNode;
                }
                if ("7007".equals(((View)childNode).getType()) || "7007".equals(((View)childNode).getType())) // Type
                {
                    opContainer = (View)childNode;
                }
            }
        }

        for (EObject prop : interf.getOwnedAttributes()) {
            Type type = ((Property)prop).getType();
            if (type instanceof DataType && !(type instanceof PrimitiveType) && !viewMap.containsKey(type)) {
                Node dataTypeNode = addNode(modelSet, diagram, type, "2010", new PreferencesHint(
                        UMLDiagramEditorPlugin.ID));
                viewMap.put(type, dataTypeNode);
            } else if (type instanceof Class && !viewMap.containsKey(type)) {
                createClass(diagram, diagram, type, viewMap, false, modelSet);
            }
            addNode(modelSet, propContainer, prop, "3006", new PreferencesHint(UMLDiagramEditorPlugin.ID));
        }
        for (EObject op : interf.getOperations()) {
            addNode(modelSet, opContainer, op, "3007", new PreferencesHint(UMLDiagramEditorPlugin.ID));
        }
        for (Relationship r : interf.getRelationships()) {
            Set<Relationship> rel = relationships.get(diagram);
            if (rel == null) {
                rel = new HashSet<Relationship>();
                relationships.put(diagram, rel);
            }
            relationships.get(diagram).add((Relationship)r);
        }
    };

    /**
     * Adds class children.
     *
     * @param diagram
     * @param classNode
     * @param clazz
     * @param viewMap
     * @param modelSet
     */
    protected void addClassChildren(Diagram diagram, Node classNode, Class clazz, Map<EObject, View> viewMap,
            ModelSet modelSet) {
        View propContainer = null;
        View opContainer = null;
        View behContainer = null;
        for (Object childNode : classNode.getPersistedChildren()) {
            if (childNode instanceof View) {
                if ("7017".equals(((View)childNode).getType()) || "7011".equals(((View)childNode).getType())) {
                    propContainer = (View)childNode;
                }
                if ("7018".equals(((View)childNode).getType()) || "7012".equals(((View)childNode).getType())) {
                    opContainer = (View)childNode;
                }
                if ("7019".equals(((View)childNode).getType()) || "7013".equals(((View)childNode).getType())) {
                    behContainer = (View)childNode;
                }
            }
        }

        for (EObject prop : clazz.getOwnedAttributes()) {
            Type type = ((Property)prop).getType();
            if (type instanceof DataType && !(type instanceof PrimitiveType) && !viewMap.containsKey(type)) {
                Node dataTypeNode = addNode(modelSet, diagram, type, "2010", new PreferencesHint(
                        UMLDiagramEditorPlugin.ID));
                viewMap.put(type, dataTypeNode);
            } else if (type instanceof Class && !viewMap.containsKey(type)) {
                createClass(diagram, diagram, type, viewMap, false, modelSet);
            }
            addNode(modelSet, propContainer, prop, "3012", new PreferencesHint(UMLDiagramEditorPlugin.ID));
        }
        for (EObject op : clazz.getOperations()) {
            addNode(modelSet, opContainer, op, "3013", new PreferencesHint(UMLDiagramEditorPlugin.ID));
        }
        for (EObject behavior : clazz.getOwnedBehaviors()) {
            if (behavior instanceof OpaqueBehavior) {
                addNode(modelSet, behContainer, behavior, "3014", new PreferencesHint(
                        UMLDiagramEditorPlugin.ID));
            }
        }
        for (EObject nestedClassifier : clazz.getNestedClassifiers()) {
            if (nestedClassifier instanceof Enumeration) {
                Node enumNomde = addNode(modelSet, diagram, nestedClassifier, "2006", new PreferencesHint(
                        UMLDiagramEditorPlugin.ID));

                if (enumNomde != null) {
                    viewMap.put(nestedClassifier, enumNomde);
                    addEnumChildren(enumNomde, (Enumeration)nestedClassifier, modelSet);
                }
            }
        }
        for (Relationship r : clazz.getRelationships()) {
            Set<Relationship> rel = relationships.get(diagram);
            if (rel == null) {
                rel = new HashSet<Relationship>();
                relationships.put(diagram, rel);
            }
            relationships.get(diagram).add((Relationship)r);
        }
    }

}
