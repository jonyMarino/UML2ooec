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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {TestSimple.class, TestIfElseIf.class, TestForLoop.class, TestDoWhileLoop.class,
        TestWhileLoop.class, TestSwitchConditional.class, TestBreakInLoop.class,
        TestSimpleForAndIfNested.class, TestReturn.class, TestContinue.class, TestSpecialChars.class,
        TestLabel.class, TestComments.class })
public class AllTests {
}
