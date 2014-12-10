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

import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;
import org.eclipse.umlgen.c.common.util.ModelUtil.EventType;

/**
 * Event related to addition of an enumeration.
 */
public class EnumerationAdded extends AbstractEnumerationEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        Enumeration myEnumeration = ModelUtil.findEnumerationInClassifier(matchingClassifier,
                getCurrentName());

        if (myEnumeration == null) {
            if (matchingClassifier instanceof Class) {
                myEnumeration = (Enumeration)((Class)matchingClassifier).getNestedClassifier(
                        getCurrentName(), false, UMLPackage.Literals.ENUMERATION, true);
            } else if (matchingClassifier instanceof Interface) {
                myEnumeration = (Enumeration)((Interface)matchingClassifier).getNestedClassifier(
                        getCurrentName(), false, UMLPackage.Literals.ENUMERATION, true);
            }
        } else {
            /* On supprime tous les attributs avant de les reconstruire */
            myEnumeration.getOwnedLiterals().clear();
        }

        createEnumerators(myEnumeration);

        ModelUtil.setVisibility(myEnumeration, getTranslationUnit(), EventType.ADD);
    }

    /**
     * Creates the enumerators (content of the enumeration).
     *
     * @param enumeration
     *            The enumeration
     */
    private void createEnumerators(Enumeration enumeration) {
        for (IASTEnumerator enumerator : getEnumerators()) {
            EnumerationLiteral literal = enumeration.createOwnedLiteral(enumerator.getName().toString());
            if (enumerator.getValue() != null) {
                LiteralString defaultExpression = (LiteralString)literal.createSpecification(
                        "initialisationValue", null, UMLPackage.Literals.LITERAL_STRING);
                defaultExpression.setValue(enumerator.getValue().getRawSignature());
            }
        }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<EnumerationAdded> builder() {
        return new AbstractBuilder<EnumerationAdded>() {
            private EnumerationAdded event = new EnumerationAdded();

            /**
             * @see org.eclipse.umlgen.reverse.c.event.AbstractEnumerationEvent.AbstractBuilder#getEvent()
             */
            @Override
            protected EnumerationAdded getEvent() {
                return event;
            }
        };
    }
}
