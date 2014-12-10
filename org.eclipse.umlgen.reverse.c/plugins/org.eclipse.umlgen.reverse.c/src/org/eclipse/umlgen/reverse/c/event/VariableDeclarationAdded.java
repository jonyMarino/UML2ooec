/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Christophe LE CAMUS (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.event;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.util.AnnotationUtil;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to an add of a varaiable declaration.
 */
public class VariableDeclarationAdded extends AbstractVariableDeclarationEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        Type myType = ModelUtil.getType(manager, matchingClassifier, getCurrentTypeName());
        Property attribute = matchingClassifier.getAttribute(getCurrentName(), myType);
        if (attribute == null) {
            if (matchingClassifier instanceof Class) {
                attribute = ((Class)matchingClassifier).getOwnedAttribute(getCurrentName(), myType, false,
                        UMLPackage.Literals.PROPERTY, true);
            }
            if (matchingClassifier instanceof Interface) {
                attribute = ((Interface)matchingClassifier).getOwnedAttribute(getCurrentName(), myType,
                        false, UMLPackage.Literals.PROPERTY, true);
            }
        }

        String defaultValue = getInitializationExpression();
        if (defaultValue != null && defaultValue.length() > 0) {
            LiteralString literalString = (LiteralString)attribute.createDefaultValue(
                    BundleConstants.DEFAULT_VALUE, null, UMLPackage.Literals.LITERAL_STRING);
            literalString.setValue(defaultValue);
        }

        // set if the variable is Read Only
        attribute.setIsReadOnly(getIsConst());

        // set if the variable is Static
        attribute.setIsStatic(getIsStatic());

        // other characters that are specific to C language
        AnnotationUtil.setVolatileDetailEntry(attribute, getIsVolatile());
        AnnotationUtil.setRegisterDetailEntry(attribute, getIsRegister());
        if (!attribute.getVisibility().equals(VisibilityKind.PUBLIC_LITERAL)) {
            ModelUtil.setVisibility(attribute, getTranslationUnit(), EventType.ADD);
        }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<VariableDeclarationAdded> builder() {
        return new AbstractBuilder<VariableDeclarationAdded>() {
            private final VariableDeclarationAdded event = new VariableDeclarationAdded();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractVariableDeclarationEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected VariableDeclarationAdded getEvent() {
                return event;
            }
        };
    }
}
