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

import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.umlgen.gen.embedded.c.ui.launch.tabs.UML2ECDocumentationLaunchConfigurationTab;
import org.eclipse.umlgen.gen.embedded.c.ui.launch.tabs.UML2ECGeneralLaunchConfigurationTab;

/**
 * The UML to Embedded C tab groups creator.
 */
public class UML2ECLaunchConfigurationTabGroup extends org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup {

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog,
     *      java.lang.String)
     */
    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        setTabs(new ILaunchConfigurationTab[] {new UML2ECGeneralLaunchConfigurationTab(),
                new UML2ECDocumentationLaunchConfigurationTab(), new CommonTab(), });
    }

}
