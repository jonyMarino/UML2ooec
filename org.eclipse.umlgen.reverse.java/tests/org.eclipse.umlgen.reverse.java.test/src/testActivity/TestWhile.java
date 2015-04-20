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
package testActivity;

public class TestWhile {

    public boolean condition;

    public int n;

    public void testWhile() {
        while (condition) {
            condition = false;
        }
        condition = true;
    }

    public void testWhileBreak() {
        while (condition) {
            condition = false;
            if (n == 0) {
                break;
            }
        }
        condition = true;
    }
}
