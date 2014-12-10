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

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
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
 * Event related to addition of a type definition for a function declaration.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class TypeDefFunctionDeclarationAdded extends AbstractTypeDefFunctionDeclarationEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        DataType existingType = manager.findDataType(getCurrentName());
        DataType myfonctionType = ModelUtil.findDataTypeRedefinitionInClassifier(matchingClassifier,
                getCurrentName());
        if (myfonctionType == null) {
            if (matchingClassifier instanceof Class) {
                myfonctionType = (DataType)((Class)matchingClassifier).createNestedClassifier(
                        getCurrentName(), UMLPackage.Literals.DATA_TYPE);
            } else if (matchingClassifier instanceof Interface) {
                myfonctionType = (DataType)((Interface)matchingClassifier).createNestedClassifier(
                        getCurrentName(), UMLPackage.Literals.DATA_TYPE);
            }
        }

        if (existingType != null && existingType != myfonctionType) {
            ModelUtil.redefineType(existingType, myfonctionType);
            existingType.destroy();
        }

        EList<Type> typesList = new BasicEList<Type>();
        EList<String> namesList = new BasicEList<String>();
        initializeParameters(manager, matchingClassifier, namesList, typesList);

        if (myfonctionType.getOperation(getCurrentName(), null, null) == null) {
            Operation operation = myfonctionType.createOwnedOperation(getCurrentName(), namesList, typesList);
            ModelUtil.setVisibility(operation, getTranslationUnit(), EventType.ADD);

            // Modify parameters
            modifyParameters(operation, getParameters());

            // Process the return type
            Type myType = manager.getDataType(getReturnType());
            operation.createReturnResult(null, myType);
        }

        ModelUtil.setVisibility(myfonctionType, getTranslationUnit(), EventType.ADD);
    }

    /**
     * Initializes the list or parameters.
     *
     * @param manager
     *            The current model manager
     * @param matchingClassifier
     *            The classifier in which the function is declarated
     * @param names
     *            The ordered list of parameter names
     * @param types
     *            The ordered list of UML parameter types
     */
    private void initializeParameters(ModelManager manager, Classifier matchingClassifier,
            EList<String> names, EList<Type> types) {
        for (FunctionParameter aParameter : getParameters()) {
            names.add(aParameter.getName());
            Type realType = manager.getDataType(aParameter.getType());
            aParameter.setUMLType(realType);
            types.add(realType);
        }
    }

    /**
     * Modifies the list of parameters for a given operation.
     *
     * @param operation
     *            The operation.
     * @param parameters
     *            The list of paramters to modify.
     */
    private void modifyParameters(Operation operation, List<FunctionParameter> parameters) {
        for (FunctionParameter aParam : parameters) {
            Parameter parameter = operation.getOwnedParameter(aParam.getName(), aParam.getUMLType());

            if (!aParam.isConst() && aParam.isPointer()) {
                parameter.setDirection(ParameterDirectionKind.INOUT_LITERAL);
            } else {
                parameter.setDirection(ParameterDirectionKind.IN_LITERAL);
            }
            if (aParam.getInitializer() != null) {
                OpaqueExpression defaultExpression = (OpaqueExpression)parameter.createDefaultValue(
                        "default", null, UMLPackage.Literals.OPAQUE_EXPRESSION);
                defaultExpression.getLanguages().add(BundleConstants.C_LANGUAGE);
                defaultExpression.getBodies().add(aParam.getInitializer());
            }
        }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<TypeDefFunctionDeclarationAdded> builder() {
        return new AbstractBuilder<TypeDefFunctionDeclarationAdded>() {
            private TypeDefFunctionDeclarationAdded event = new TypeDefFunctionDeclarationAdded();

            /**
             * @see org.eclipse.umlgen.reverse.c.AbstractTypeDefFunctionDeclarationEvent#getEvent()
             */
            @Override
            protected TypeDefFunctionDeclarationAdded getEvent() {
                return event;
            }
        };
    }
}
