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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {TestComplex.class, TestSimple.class, TestBreakInLoop.class, TestContinue.class,
        TestDoWhile.class, TestWhile.class, TestForAndIfNested.class, TestFor.class, TestIfElseIf.class,
        TestLabel.class, TestReturn.class, TestSpecialChars.class, TestSwitch.class, TestComments.class })
public class AllTests {
}
