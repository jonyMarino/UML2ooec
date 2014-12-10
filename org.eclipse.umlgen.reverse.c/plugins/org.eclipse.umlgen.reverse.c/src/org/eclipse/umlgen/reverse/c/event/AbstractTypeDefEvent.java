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

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Abstract representation of an event related to a type definition.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public abstract class AbstractTypeDefEvent extends AbstractTypedEvent {

    /** The type definition. */
    protected DataType myTypeDef;

    /** The redefined type. */
    private String redefinedType;

    public String getRedefinedType() {
        return this.redefinedType;
    }

    public void setRedefinedType(String type) {
        redefinedType = type;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        DataType existingType = manager.findDataTypeInTypesPck(getCurrentName());
        myTypeDef = ModelUtil.findDataTypeInClassifier(matchingClassifier, getCurrentName());

        if (myTypeDef == null) {
            if (matchingClassifier instanceof Class) {
                myTypeDef = (DataType)((Class)matchingClassifier).getNestedClassifier(getCurrentName(),
                        false, UMLPackage.Literals.DATA_TYPE, true);
            } else if (matchingClassifier instanceof Interface) {
                myTypeDef = (DataType)((Interface)matchingClassifier).getNestedClassifier(getCurrentName(),
                        false, UMLPackage.Literals.DATA_TYPE, true);
            }
        }

        if (existingType != null && existingType != myTypeDef) {
            ModelUtil.redefineType(existingType, myTypeDef);
            existingType.destroy();
        }

        String redefType = getRedefinedType().replaceAll(BundleConstants.SQUARE_BRAKETS_REGEXP, "");
        DataType myRedefinedType = manager.getDataType(redefType);
        myTypeDef.getRedefinedClassifiers().add((Classifier)myRedefinedType);

        ModelUtil.setVisibility(myTypeDef, getTranslationUnit(), EventType.ADD);
    }

    /**
     * Generic behavior for builders from events.
     */
    public abstract static class AbstractBuilder<T extends AbstractTypeDefEvent> extends AbstractTypedEvent.AbstractBuilder<T> {

        /**
         * This sets the redefined type to the event.
         *
         * @param type
         *            the type.
         * @return self.
         */
        public AbstractBuilder<T> setRedefinedType(String type) {
            getEvent().setRedefinedType(type);
            return this;
        }
    }

}
