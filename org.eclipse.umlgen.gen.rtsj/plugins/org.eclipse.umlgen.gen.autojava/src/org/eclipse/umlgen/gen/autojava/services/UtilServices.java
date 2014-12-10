/*******************************************************************************
 * Copyright (c) 2009, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nathalie Lepine (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.uml2.uml.BehavioralFeature;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Services providing diverse facilities.
 */
public class UtilServices {

    /** Key word for "else". */
    private static final String ELSE_CONDITION = "else";

    /**
     * This removes the objects which have the same name from the given list.<br>
     * Note that it is an other implementation of {@link UMLServices#minimizeByName(List)} except that it is
     * based on any string of the scanned objects (more generic behavior).<br>
     * So, the caller defines from which string feature he wishes to minimize.
     *
     * @param elts
     *            A receiver list of objects.
     * @param strings
     *            The list of the name of the objects contained in the receiver list.
     * @return The minimize list.
     */
    public List<NamedElement> minimizeByString(List<NamedElement> elts, List<String> strings) {
        List<String> distinct = new ArrayList<String>();
        List<NamedElement> result = new ArrayList<NamedElement>();
        if (elts.size() == strings.size()) {
            for (int i = 0; i < elts.size(); i++) {
                if (!distinct.contains(strings.get(i))) {
                    distinct.add(strings.get(i));
                    result.add(elts.get(i));
                }
            }
        } else {
            return elts;
        }
        return result;
    }

    /**
     * This returns the name of the behavioral features which owns the same name, from the given interface.
     *
     * @param myInterface
     *            The receiver interface.
     * @return The list of the duplicated names.
     */
    public Set<String> getSimilarBehaviorFeatures(Interface myInterface) {
        final List<String> distinctNames = new ArrayList<String>();
        final Set<String> similarNames = new HashSet<String>();

        final Iterator<Element> elements = myInterface.getOwnedElements().iterator();
        while (elements.hasNext()) {
            final Element elt = elements.next();
            if (elt instanceof BehavioralFeature) {
                BehavioralFeature feature = (BehavioralFeature)elt;
                String name = feature.getName();
                if (!distinctNames.contains(name)) {
                    distinctNames.add(name);
                } else {
                    similarNames.add(name);
                }
            }
        }
        return similarNames;
    }

    /**
     * This returns true if the given object belongs to the receiver list.<br>
     * Note that this can be made by an Acceleo core service: includes().
     *
     * @param objects
     *            The receiver list.
     * @param object
     *            The given object.
     * @return The result.
     */
    public boolean contains(Set<Object> objects, Object object) {
        final Iterator<Object> objs = objects.iterator();
        while (objs.hasNext()) {
            Object obj = objs.next();
            if (obj.equals(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This enables to compute and return the result of the multiplication of a and b.
     *
     * @param a
     *            .
     * @param b
     *            .
     * @return The result.
     */
    public Integer multiply(Integer a, Integer b) {
        return new Integer(a.intValue() * b.intValue());
    }

    /**
     * This enables to call an EOperation from its name and the given receiver EObject.<br>
     * Note that it was required to call this kind of Java service in older versions of Acceleo (comes from
     * Acceleo 2).<br>
     * Now, you can call directly the EOperation of your EObject directly from the Acceleo templates, as core
     * service.
     *
     * @param receiver
     *            The receiver EObject.
     * @param opName
     *            The name of the EOperation to call.
     * @return The result of the EOperation as list.
     */
    public List<Object> call(EObject receiver, String opName) {
        List<Object> result = new ArrayList<Object>();
        if (receiver != null && opName != null) {
            if (receiver != null && opName != null) {
                EOperation myOp = null;
                Iterator<EOperation> operations = receiver.eClass().getEAllOperations().iterator();
                while (operations.hasNext()) {
                    EOperation op = operations.next();
                    if (op.getName() != null && op.getName().equals(opName)) {
                        myOp = op;
                        break;
                    }
                }
                if (myOp != null) {
                    Object obj = null;
                    try {
                        obj = receiver.eInvoke(myOp, ECollections.EMPTY_ELIST);
                    } catch (InvocationTargetException e) {
                        System.out.println("**DEBUG** " + receiver + " " + receiver.eClass() + " " + myOp);
                    }
                    // CHECKSTYLE:OFF
                    if (obj instanceof List) {
                        result.addAll((List)obj);
                    } else if (obj != null) {
                        result.add(obj);
                    }
                    // CHECKSTYLE:ON
                }
            }
        }
        return result;
    }

    /**
     * This enables to minimize the given list of objects.<br>
     * Note that the use of an ocl::Set (or OrderedSet) enables to avoid to have several same instances in a
     * collection (and so, to call this service).
     *
     * @param objs
     *            the list to minimize.
     * @return the minimized list.
     */
    public List<EObject> minimize(List<EObject> objs) {
        List<EObject> result = new ArrayList<EObject>();
        for (EObject obj : objs) {
            if (obj != null && !result.contains(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * This enables to retrieve the value of the feature from the given receiver object.<br>
     * This service is important to use with input UML models. Indeed, UML often stores values in a
     * multi-valued feature as a Set. Well, OCL applies a random operation on it to be sure that values are
     * not ordered... So, you can get results different from a generation to an other one.<br>
     * That's why this service calls the native Ecore reflexive methods to access to the features and enables
     * to keep the same order.
     *
     * @param receiver
     *            The receiver object.
     * @param feature
     *            The feature to get.
     * @return the value from this feature.
     */
    public List<Object> get(EObject receiver, String feature) {
        List<Object> result = new ArrayList<Object>();
        if (receiver != null && feature != null) {
            EStructuralFeature sFeature = receiver.eClass().getEStructuralFeature(feature);
            Object obj = receiver.eGet(sFeature);
            if (obj instanceof List) {
                result.addAll((List)obj);
            } else if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * Return the list of conditions sorted : the "else" condition is at the end.
     *
     * @param list
     *            The list of Transition.
     * @return the sorted list.
     */
    public List<Transition> sortByCondition(List<Transition> list) {
        List<Transition> result = new ArrayList<Transition>();
        List<Transition> elseCondition = new ArrayList<Transition>();
        for (Transition tr : list) {
            for (Constraint constraint : tr.getOwnedRules()) {
                ValueSpecification specification = constraint.getSpecification();
                if (specification instanceof OpaqueExpression
                        && !((OpaqueExpression)specification).getBodies().isEmpty()
                        && ((OpaqueExpression)specification).getBodies().get(0).equalsIgnoreCase(
                                ELSE_CONDITION)) {
                    elseCondition.add(tr);
                } else {
                    result.add(tr);
                }
            }
        }
        result.addAll(elseCondition);
        return result;
    }

    /**
     * This checks if the choice has different guards from the given transitions.
     *
     * @param el
     *            The receiver
     * @param transitions
     *            The transitions.
     * @return true if the choice has different guards.
     */
    public boolean exclusiveChoice(Element el, List<Transition> transitions) {
        List<String> bodies = new ArrayList<String>();
        for (Transition tr : transitions) {
            for (Constraint constraint : tr.getOwnedRules()) {
                ValueSpecification specification = constraint.getSpecification();
                if (specification instanceof OpaqueExpression
                        && !((OpaqueExpression)specification).getBodies().isEmpty()) {
                    bodies.add(((OpaqueExpression)specification).getBodies().get(0));
                }
            }
        }
        return exclusiveBody(bodies) && bodies.contains(ELSE_CONDITION);
    }

    /**
     * This checks if the list of string is exclusive.
     *
     * @param bodies
     *            The list of strings.
     * @return true if the list of string is exclusive.
     */
    private boolean exclusiveBody(List<String> bodies) {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                if (bodies.get(i).equalsIgnoreCase(bodies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
