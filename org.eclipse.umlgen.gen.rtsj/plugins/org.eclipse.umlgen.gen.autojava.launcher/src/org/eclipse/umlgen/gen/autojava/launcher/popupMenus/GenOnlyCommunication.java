/*******************************************************************************
 * Copyright (c) 2008, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.gen.autojava.launcher.popupMenus;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.umlgen.gen.autojava.main.CommunicationOnly;
import org.eclipse.umlgen.gen.autojava.main.Uml2autojava;

/**
 * UML to Autojava components code generation.
 */
public class GenOnlyCommunication extends AbstractGenAutoJavaComponents {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.gen.autojava.launcher.popupMenus.AbstractGenAutoJavaComponents#getGenerator(org.eclipse.emf.common.util.URI,
     *      java.lang.String, java.io.File)
     */
    @Override
    protected Uml2autojava getGenerator(URI modelURI, String sDecorators, File target) throws IOException {
        return new CommunicationOnly(modelURI, target, Collections.emptyList(), sDecorators);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.umlgen.gen.autojava.launcher.popupMenus.AbstractGenAutoJavaComponents#getModuleQualifiedName()
     */
    @Override
    protected String getModuleQualifiedName() {
        return "org.eclipse.umlgen.gen.autojava.main.CommunicationOnly";
    }

}
