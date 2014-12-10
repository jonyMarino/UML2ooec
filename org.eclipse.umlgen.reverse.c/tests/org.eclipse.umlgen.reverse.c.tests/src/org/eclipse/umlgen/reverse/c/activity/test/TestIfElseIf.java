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

public class TestIfElseIf extends AbstractTest {
    @Test
    public void ifElseIf1() {
        testCFile("ifElseIf/testIfElseIf1.c", false);
    }

    @Test
    public void ifElseIf2() {
        testCFile("ifElseIf/testIfElseIf2.c", false);
    }

    @Test
    public void ifElseIf3() {
        testCFile("ifElseIf/testIfElseIf3.c", false);
    }

    @Test
    public void ifElseIf4() {
        testCFile("ifElseIf/testIfElseIf4.c", false);
    }
}
