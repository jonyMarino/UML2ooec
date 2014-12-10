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

public class TestEnum extends AbstractTest {

    @Test
    public void testAnonymousC() {
        testStructuralCFile("enums", "anonymous", "EnumanonymousC");
    }

    @Test
    public void testAnonymousH() {
        testStructuralHFile("enums", "anonymous", "EnumanonymousH");
    }

    @Test
    public void testAnonymousWithVarDeclC() {
        testStructuralCFile("enums", "anonymousWithVarDecl", "EnumanonymousWithVarDeclC");
    }

    @Test
    public void testAnonymousWithVarDeclH() {
        testStructuralHFile("enums", "anonymousWithVarDecl", "EnumanonymousWithVarDeclH");
    }

    @Test
    public void testAnonymousWithVarDeclAndInitC() {
        testStructuralCFile("enums", "anonymousWithVarDeclAndInit", "EnumanonymousWithVarDeclAndInitC");
    }

    @Test
    public void testAnonymousWithVarDeclAndInitH() {
        testStructuralHFile("enums", "anonymousWithVarDeclAndInit", "EnumanonymousWithVarDeclAndInitH");
    }

    @Test
    public void testAnonymousWithArrayVarDeclC() {
        testStructuralCFile("enums", "anonymousWithArrayVarDecl", "EnumanonymousWithArrayVarDeclC");
    }

    @Test
    public void testAnonymousWithArrayVarDeclH() {
        testStructuralHFile("enums", "anonymousWithArrayVarDecl", "EnumanonymousWithArrayVarDeclH");
    }

    @Test
    public void testAnonymousWithArrayVarDeclAndInitC() {
        testStructuralCFile("enums", "anonymousWithArrayVarDeclAndInit",
                "EnumanonymousWithArrayVarDeclAndInitC");
    }

    @Test
    public void testAnonymousWithArrayVarDeclAndInitH() {
        testStructuralHFile("enums", "anonymousWithArrayVarDeclAndInit",
                "EnumanonymousWithArrayVarDeclAndInitH");
    }

    @Test
    public void testNamedC() {
        testStructuralCFile("enums", "named", "namedC");
    }

    @Test
    public void testNamedH() {
        testStructuralHFile("enums", "named", "namedH");
    }

    @Test
    public void testNamedWithVarDeclC() {
        testStructuralCFile("enums", "namedWithVarDecl", "namedWithVarDeclC");
    }

    @Test
    public void testNamedWithVarDeclH() {
        testStructuralHFile("enums", "namedWithVarDecl", "namedWithVarDeclH");
    }

    @Test
    public void testNamedWithVarDeclAndInitC() {
        testStructuralCFile("enums", "namedWithVarDeclAndInit", "namedWithVarDeclAndInitC");
    }

    @Test
    public void testNamedWithVarDeclAndInitH() {
        testStructuralHFile("enums", "namedWithVarDeclAndInit", "namedWithVarDeclAndInitH");
    }

    @Test
    public void testNamedWithArrayVarDeclC() {
        testStructuralCFile("enums", "namedWithArrayVarDecl", "namedWithArrayVarDeclC");
    }

    @Test
    public void testNamedWithArrayVarDeclH() {
        testStructuralHFile("enums", "namedWithArrayVarDecl", "namedWithArrayVarDeclH");
    }

    @Test
    public void testNamedWithArrayVarDeclAndInitC() {
        testStructuralCFile("enums", "namedWithArrayVarDeclAndInit", "namedWithArrayVarDeclAndInitC");
    }

    @Test
    public void testNamedWithArrayVarDeclAndInitH() {
        testStructuralHFile("enums", "namedWithArrayVarDeclAndInit", "namedWithArrayVarDeclAndInitH");
    }

    @Test
    public void testWithAllValuedLiteralC() {
        testStructuralCFile("enums", "withAllValuedLiteral", "withAllValuedLiteralC");
    }

    @Test
    public void testWithAllValuedLiteralH() {
        testStructuralHFile("enums", "withAllValuedLiteral", "withAllValuedLiteralH");
    }

    @Test
    public void testWithSomeValuedLiteralC() {
        testStructuralCFile("enums", "withSomeValuedLiteral", "EnumwithSomeValuedLiteralC");
    }

    @Test
    public void testWithSomeValuedLiteralH() {
        testStructuralHFile("enums", "withSomeValuedLiteral", "EnumwithSomeValuedLiteralH");
    }
}
