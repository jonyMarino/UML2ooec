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

public class TestSimple extends AbstractTest {

    @Test
    public void testEmptyActivity() {
        testUmlActivityFile("simple/testEmptyActivity.uml");
    }

    @Test
    public void testIfElse() {
        testUmlActivityFile("simple/testIfElse.uml");
    }

    @Test
    public void testSingleIf() {
        testUmlActivityFile("simple/testSingleIf.uml");
    }

}
