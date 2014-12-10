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
package org.eclipse.umlgen.gen.c.structural.test;

import org.eclipse.umlgen.gen.c.structural.test.util.AbstractTest;
import org.junit.Test;

public class TestIncludeGuard extends AbstractTest {

    @Test(expected = AssertionError.class)
    public void testIncludeGuard1() {
        testStructuralHFile("includeGuard", "aFile", "includeGuard1");
    }

    @Test(expected = AssertionError.class)
    public void testIncludeGuard2() {
        testStructuralHFile("includeGuard", "aFile", "includeGuard2");
    }

    @Test(expected = AssertionError.class)
    public void testIncludeGuard3() {
        testStructuralHFile("includeGuard", "aFile", "includeGuard3");
    }

    @Test
    public void testIncludeGuard4() {
        testStructuralHFile("includeGuard", "aFile", "includeGuard4");
    }

}
