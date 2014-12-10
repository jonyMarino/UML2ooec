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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.c.common.util.ModelUtil;

/**
 * Event related to a deletion of an Ifndef declaration.
 *
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class IfndefRemoved extends AbstractIfndefEvent {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent#notifyChanges(org.eclipse.umlgen.c.common.util.ModelManager)
     */
    @Override
    public void notifyChanges(ModelManager manager) {
        Classifier matchingClassifier = ModelUtil.findClassifierInPackage(manager.getSourcePackage(),
                getUnitName());
        EAnnotation annot = matchingClassifier.getEAnnotation(AnnotationConstants.REVERSE_PROCESS);
        if (annot != null) {
            annot.getDetails().removeKey(AnnotationConstants.IFNDEF_CONDITION);
            if (annot.getDetails().isEmpty()) {
                matchingClassifier.getEAnnotations().remove(annot);
            }
        }
    }

    /**
     * Gets the right builder.
     *
     * @return the builder for this event
     */
    public static AbstractBuilder<IfndefRemoved> builder() {
        return new AbstractBuilder<IfndefRemoved>() {
            private IfndefRemoved event = new IfndefRemoved();

            /**
             * @see org.eclipse.umlgen.reverse.c.IfndefBuilder#getEvent()
             */
            @Override
            protected IfndefRemoved getEvent() {
                return event;
            }
        };
    }
}
