/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity.test;

import org.eclipse.umlgen.reverse.c.activity.test.utils.AbstractTest;
import org.junit.Test;

public class TestForLoop extends AbstractTest {

    @Test
    public void emptyFor1() {
        testCFile("forLoop/empty/emptyFor1.c", false);
    }

    @Test
    public void emptyFor2() {
        testCFile("forLoop/empty/emptyFor2.c", false);
    }

    @Test
    public void emptyFor3() {
        testCFile("forLoop/empty/emptyFor3.c", false);
    }

    @Test
    public void simpleFor1() {
        testCFile("forLoop/simple/simpleFor1.c", false);
    }

    @Test
    public void simpleFor2() {
        testCFile("forLoop/simple/simpleFor2.c", false);
    }

    @Test
    public void nestedFor1() {
        testCFile("forLoop/nested/nestedFor1.c", false);
    }

    @Test
    public void nestedFor2() {
        testCFile("forLoop/nested/nestedFor2.c", false);
    }

    @Test
    public void nestedFor3() {
        testCFile("forLoop/nested/nestedFor3.c", false);
    }

    @Test
    public void nestedFor4() {
        testCFile("forLoop/nested/nestedFor4.c", false);
    }
}
