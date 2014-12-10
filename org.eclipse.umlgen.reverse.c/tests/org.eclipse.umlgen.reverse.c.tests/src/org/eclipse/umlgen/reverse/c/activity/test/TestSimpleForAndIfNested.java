/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity.test;

import org.eclipse.umlgen.reverse.c.activity.test.utils.AbstractTest;
import org.junit.Test;

public class TestSimpleForAndIfNested extends AbstractTest {

    @Test
    public void simpleForAndIfNested1() {
        testCFile("forAndIfNested/simpleForAndIfNested1.c", false);
    }

    @Test
    public void simpleForAndIfNested2() {
        testCFile("forAndIfNested/simpleForAndIfNested2.c", false);
    }
}
