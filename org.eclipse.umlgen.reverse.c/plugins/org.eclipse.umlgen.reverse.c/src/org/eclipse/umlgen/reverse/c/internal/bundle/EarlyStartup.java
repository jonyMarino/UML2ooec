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
package org.eclipse.umlgen.reverse.c.internal.bundle;

import org.eclipse.ui.IStartup;

/**
 * Fake class responsible to force plugin activation. Do nothing else !
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public final class EarlyStartup implements IStartup {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.ui.IStartup#earlyStartup()
     */
    public void earlyStartup() {
        // Do nothing but force plugin activation
    }

}
