/*******************************************************************************
 * Copyright (c) 2011, 2014, 2015 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Stephane Begaudeau (Obeo) - initial API and implementation
 *     Johan Hardy (Spacebel) - adapted for Embedded C generator
 *******************************************************************************/
package org.eclipse.umlgen.gen.embedded.c.ui.launch;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

/**
 * The source locator will plug the UML model lookup class.
 */
public class UML2ECPersistableSourceLocator extends AbstractSourceLookupDirector {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
     */
    public void initializeParticipants() {
        addParticipants(new ISourceLookupParticipant[] {new UML2ECSourceLookupParticipant() });
    }

}
