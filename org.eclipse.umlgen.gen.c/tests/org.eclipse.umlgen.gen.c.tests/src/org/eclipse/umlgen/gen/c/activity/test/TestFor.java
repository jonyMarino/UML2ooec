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
package org.eclipse.umlgen.gen.c.activity.test;

import org.eclipse.umlgen.gen.c.activity.test.util.AbstractTest;
import org.junit.Test;

public class TestFor extends AbstractTest {

    @Test
    public void testEmptyFor1() {
        testUmlActivityFile("forLoop/empty/emptyFor1.uml");
    }

    @Test
    public void testEmptyFor2() {
        testUmlActivityFile("forLoop/empty/emptyFor2.uml");
    }

    @Test
    public void testEmptyFor3() {
        testUmlActivityFile("forLoop/empty/emptyFor3.uml");
    }

    @Test
    public void testNestedFor1() {
        testUmlActivityFile("forLoop/nested/nestedFor1.uml");
    }

    @Test
    public void testNestedFor2() {
        testUmlActivityFile("forLoop/nested/nestedFor2.uml");
    }

    @Test
    public void testNestedFor3() {
        testUmlActivityFile("forLoop/nested/nestedFor3.uml");
    }

    @Test
    public void testNestedFor4() {
        testUmlActivityFile("forLoop/nested/nestedFor4.uml");
    }

    @Test
    public void testSimpleFor1() {
        testUmlActivityFile("forLoop/simple/simpleFor1.uml");
    }

    @Test
    public void testSimpleFor2() {
        testUmlActivityFile("forLoop/simple/simpleFor2.uml");
    }

}
