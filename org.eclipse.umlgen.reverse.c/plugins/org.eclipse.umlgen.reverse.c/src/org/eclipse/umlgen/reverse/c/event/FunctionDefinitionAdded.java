/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;

/**
 * Event related to a function definition add.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class FunctionDefinitionAdded extends AbstractFunctionDefinitionEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Class myClass = ModelUtil.findClassInPackage(manager.getSourcePackage(), getUnitName());

        OpaqueBehavior function = (OpaqueBehavior)myClass.getOwnedBehavior(getCurrentName(), false,
                UMLPackage.Literals.OPAQUE_BEHAVIOR, false);
        // only if the function does not exist yet.
        if (function == null) {
            function = (OpaqueBehavior)myClass.getOwnedBehavior(getCurrentName(), false,
                    UMLPackage.Literals.OPAQUE_BEHAVIOR, true);
        }

        // update/create the parameters (since Bug correction #2523)
        handleParameters(manager, myClass, function);

        // update/create the body content
        if (function.getLanguages().contains(BundleConstants.C_LANGUAGE)) {
            // update
            int index = function.getLanguages().indexOf(BundleConstants.C_LANGUAGE);
            function.getBodies().set(index, cleanInvalidXmlChars(getBody()));
        } else {
            // create
            function.getBodies().add(cleanInvalidXmlChars(getBody()));
            function.getLanguages().add(BundleConstants.C_LANGUAGE);
        }

        // handle operation referencement if it's not already done.
        if (function.getSpecification() == null) {
            // fill this information to look for operation (the return type is
            // included into lists)
            EList<Type> typesList = computeTypes(manager, myClass);

            // try to find first the operation into the current classifier,
            // otherwise explore the dependency graph.
            Operation operation = ModelUtil.getReferredOperation(myClass, getCurrentName(), typesList,
                    new BasicEList<Element>());
            if (operation == null) {
                // create the operation with the private status (seen as an
                // internal function declaration)
                FunctionDeclarationAdded event = FunctionDeclarationAdded.builder().isStatic(getIsStatic())
                        .setParameters(getParameters()).setReturnType(getReturnType()).currentName(
                                getCurrentName()).translationUnit(getTranslationUnit()).build();
                event.notifyChanges(manager);
            } else {
                function.setSpecification(operation);
            }
        }

        // set the same visibility than the operation (behavioral feature)
        if (function.getSpecification() != null) {
            function.setVisibility(function.getSpecification().getVisibility());
        }
    }

    /**
     * Initializes a list or 'types' defining the global function signature.
     *
     * @param manager
     *            The current model manager
     * @param matchingClassifier
     *            The classifier in which the function is declarated
     * @return The ordered list of UML parameter types
     */
    private EList<Type> computeTypes(ModelManager manager, Classifier matchingClassifier) {
        EList<Type> types = new BasicEList<Type>();
        for (FunctionParameter aParameter : getParameters()) {
            types.add(manager.getDataType(aParameter.getType()));
        }

        // add the return type
        types.add(manager.getDataType(getReturnType()));
        return types;
    }

    /**
     * Creates the parameters for a given operation.
     *
     * @param manager
     *            The model manager
     * @param classifier
     *            The classifier on which the operation is added
     * @param behavior
     *            The current operation TODO : see how to destroy properly type.
     */
    private void handleParameters(ModelManager manager, Classifier classifier, OpaqueBehavior behavior) {
        // first, all existing parameters are destroyed and eventually unused
        // types.
        for (int i = behavior.getOwnedParameters().size(); i > 0; i--) {
            Parameter oldParameter = behavior.getOwnedParameters().get(i - 1);
            oldParameter.destroy();
        }

        // re-build the whole parameter list
        for (FunctionParameter aParam : getParameters()) {
            // find the UML type
            Type realType = manager.getDataType(aParam.getType());
            Parameter parameter = behavior.createOwnedParameter(aParam.getName(), realType);

            // adjust the parameter direction
            if (!aParam.isConst() && aParam.isPointer()) {
                parameter.setDirection(ParameterDirectionKind.INOUT_LITERAL);
            } else {
                parameter.setDirection(ParameterDirectionKind.IN_LITERAL);
            }
            // handle initialization if defined
            if (aParam.getInitializer() != null) {
                OpaqueExpression defaultExpression = (OpaqueExpression)parameter.createDefaultValue(
                        "default", null, UMLPackage.Literals.OPAQUE_EXPRESSION);
                defaultExpression.getLanguages().add(BundleConstants.C_LANGUAGE);
                defaultExpression.getBodies().add(aParam.getInitializer());
            }
        }

        // create the return type
        Parameter returnParam = behavior.createOwnedParameter("", manager.getDataType(getReturnType()));
        returnParam.setDirection(ParameterDirectionKind.RETURN_LITERAL);
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<FunctionDefinitionAdded> builder() {
        return new AbstractBuilder<FunctionDefinitionAdded>() {
            private FunctionDefinitionAdded event = new FunctionDefinitionAdded();

            /**
             * @see org.eclipse.umlgen.reverse.c.FunctionBuilder#getEvent()
             */
            @Override
            protected FunctionDefinitionAdded getEvent() {
                return event;
            }
        };
    }
}
