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

public class TestSwitch extends AbstractTest {

    @Test
    public void testBigCaseClause() {
        testUmlActivityFile("switchConditional/bigCaseClause.uml");
    }

    @Test
    public void testCaseBreak() {
        testUmlActivityFile("switchConditional/caseBreak.uml");
    }

    @Test
    public void testFallthrough() {
        testUmlActivityFile("switchConditional/fallthrough.uml");
    }

    @Test
    public void testNoDefault() {
        testUmlActivityFile("switchConditional/noDefault.uml");
    }

    @Test
    public void testSpecial() {
        testUmlActivityFile("switchConditional/special.uml");
    }

    @Test
    public void testStandard() {
        testUmlActivityFile("switchConditional/standard.uml");
    }

    @Test
    public void testWithBreakAndContinue() {
        testUmlActivityFile("switchConditional/withBreakAndContinue.uml");
    }

    @Test
    public void testWithoutBreak() {
        testUmlActivityFile("switchConditional/withoutBreak.uml");
    }

}
