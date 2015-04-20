/*******************************************************************************
 * Copyright (c) 2015 Atos and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elorri Benoit (Atos) - initialize API and implementation
 *******************************************************************************/
package testStructural;

public class TestMethod {

    public void testMethod() {
        System.out.println("Test method without parameter");
    }

    public void testMethodWithParameters(int test) {
        System.out.println("Test method with parameter :" + test);
    }

    public int testMethodWithReturn() {
        int i = 0;
        return i;
    }

    public void testCallMethod() {
        testMethod();
    }
}
