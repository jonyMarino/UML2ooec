/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
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
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;
import org.eclipse.umlgen.reverse.c.internal.beans.FunctionParameter;

/**
 * Adds a {@link Operation} declaration to the model.
 */
public class FunctionDeclarationAdded extends AbstractFunctionDeclarationEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        // Get the operation by name if already existing otherwise create it
        Operation operation = matchingClassifier.getOperation(getCurrentName(), null, null);
        if (operation == null) {
            // opeartion is created without parameters, this will be done
            // later...
            if (matchingClassifier instanceof Class) {
                operation = ((Class)matchingClassifier).createOwnedOperation(getCurrentName(), null, null);
            } else if (matchingClassifier instanceof Interface) {
                operation = ((Interface)matchingClassifier)
                        .createOwnedOperation(getCurrentName(), null, null);
            }
        }

        // create parameters and set the right direction.
        handleParameters(manager, matchingClassifier, operation);

        // if the behavior is not already set for this operation...
        if (operation.getMethod(getCurrentName()) == null) {
            // if a behavior exists, we link the current operation to the
            // behavior found during the search
            OpaqueBehavior fctBehavior = ModelUtil.getReferredBehavior(matchingClassifier, getCurrentName(),
                    new BasicEList<Classifier>());
            if (fctBehavior != null) {
                // // At this stage, a private visibility means we have
                // certainly force the declaration creation in
                // // FunctionDefinitionAdded class. Actually, the real
                // declaration is this one we are processing.
                // if (fctBehavior.getVisibility() ==
                // VisibilityKind.PRIVATE_LITERAL)
                // {
                // // destroy the former uml:BehavioralFeature seen as an
                // internal function declaration. Cannot be null !
                // fctBehavior.getSpecification().destroy();
                // }
                // finally, do the relationship
                operation.getMethods().add(fctBehavior);
            }
        }

        // only set if the operation is Static
        operation.setIsStatic(getIsStatic());

        // set the visibility of the operation
        ModelUtil.setVisibility(operation, getTranslationUnit(), EventType.ADD);

        // set the visibility of the behavior associated with this operation
        ModelUtil.setVisibility(operation.getMethod(getCurrentName()), getTranslationUnit(), EventType.ADD);
    }

    /**
     * Creates the parameters for a given operation.
     *
     * @param manager
     *            The model manager
     * @param classifier
     *            The classifier on which the operation is added
     * @param operation
     *            The current operation TODO : see how to destroy properly type.
     */
    private void handleParameters(ModelManager manager, Classifier classifier, Operation operation) {
        // first, all existing parameters are destroyed and eventually unused
        // types.
        for (int i = operation.getOwnedParameters().size(); i > 0; i--) {
            Parameter oldParameter = operation.getOwnedParameters().get(i - 1);
            // Type oldType = oldParameter.getType();

            oldParameter.destroy();
            // if (ModelUtil.isNotReferencedAnymore(oldType))
            // {
            // ModelUtil.destroy((DataType) oldType);
            // }
        }

        // re-build the whole parameter list
        for (FunctionParameter aParam : getParameters()) {
            // find the UML type
            Type realType = manager.getDataType(aParam.getType());
            Parameter parameter = operation.createOwnedParameter(aParam.getName(), realType);

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
        operation.createReturnResult("", manager.getDataType(getReturnType()));
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<FunctionDeclarationAdded> builder() {
        return new AbstractBuilder<FunctionDeclarationAdded>() {
            private FunctionDeclarationAdded event = new FunctionDeclarationAdded();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractFunctionDeclarationEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected FunctionDeclarationAdded getEvent() {
                return event;
            }
        };
    }
}
