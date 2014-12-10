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

import org.eclipse.umlgen.c.common.util.ModelManager;

/**
 * Event related to change of a parameter.
 */
public class FunctionParameterChanged extends AbstractFunctionParameterEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        // TODO : to be implemented

        // Class myClass =
        // ModelUtil.findClassInPackage(manager.getSourcePackage(),
        // getUnitName());
        //
        // Behavior functionBehavior = null;
        // if (getSource() instanceof IASTFunctionDefinition)
        // {
        // functionBehavior = ModelUtil.getReferredBehavior(myClass,
        // getFunctionName(), new BasicEList<Element>());
        // functionBehavior.getOwnedParameters().clear();
        // }
        // Operation operation = null;
        // if (getSource() instanceof IASTFunctionDeclarator)
        // {
        // operation = ModelUtil.findOperationInClassByName(myClass,
        // getFunctionName(), new BasicEList<Element>());
        // operation.getOwnedParameters().clear();
        // }
        //
        // // Traitement des types des param�tres : les retrouver ou les
        // cr�er puis les stocker dans une liste
        // Type myType = null;
        // List<Type> typesTypeList = new BasicEList<Type>();
        // List<String> typesList = getParameterTypes();
        // for (String aTypeName : typesList)
        // {
        // if (aTypeName.lastIndexOf("*") >= 0)
        // {
        // aTypeName = aTypeName.substring(0, aTypeName.indexOf("*"));
        // }
        // myType = manager.findType(aTypeName);
        // if (myType == null)
        // {
        // if (ModelUtil.primitiveTypesSet().contains(aTypeName))
        // {
        // myType =
        // manager.getTypesPackage().createOwnedPrimitiveType(aTypeName);
        // }
        // else
        // {
        // myType = manager.getTypesPackage().createOwnedType(aTypeName,
        // UMLPackage.Literals.DATA_TYPE);
        // }
        // }
        // typesTypeList.add(myType);
        // }
        //
        // List<String> namesList = new BasicEList<String>();
        // List<ParameterDirectionKind> parameterDirections = new
        // BasicEList<ParameterDirectionKind>();
        // for (String parameterName : getParameterNames())
        // {
        // if (parameterName.indexOf("*") >= 0)
        // {
        // namesList.add(parameterName.substring(parameterName.indexOf("*") + 1,
        // parameterName.length()));
        // parameterDirections.add(ParameterDirectionKind.INOUT_LITERAL);
        // }
        // else
        // {
        // namesList.add(parameterName);
        // parameterDirections.add(ParameterDirectionKind.IN_LITERAL);
        // }
        // }
        //
        // // Creation des valeurs par d�faut des param�tres
        // int j = 0;
        // Type parameterType = null;
        // for (String name : namesList)
        // {
        // parameterType = manager.findType(typesList.get(j));
        // Parameter parameter = null;
        // if (operation != null)
        // {
        // parameter = operation.createOwnedParameter(name, parameterType);
        // }
        // else
        // {
        // if (functionBehavior != null)
        // {
        // parameter = functionBehavior.createOwnedParameter(name,
        // parameterType);
        // }
        // }
        // parameter.setDirection(parameterDirections.get(j));
        // if (getParameterInitializers().get(j) != null)
        // {
        // OpaqueExpression defaultExpression = (OpaqueExpression)
        // parameter.createDefaultValue("default", null,
        // UMLPackage.Literals.OPAQUE_EXPRESSION);
        // defaultExpression.getLanguages().add(BundleConstants.C_LANGUAGE);
        // defaultExpression.getBodies().add(getParameterInitializers().get(j));
        // }
        // j++;
        // }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<FunctionParameterChanged> builder() {
        return new AbstractBuilder<FunctionParameterChanged>() {
            private FunctionParameterChanged event = new FunctionParameterChanged();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected FunctionParameterChanged getEvent() {
                return event;
            }
        };
    }

}
