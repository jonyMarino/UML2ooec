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

public class TestStruct extends AbstractTest {

    @Test
    public void testAnonymousC() {
        testStructuralCFile("struct", "anonymous", "StructanonymousC");
    }

    @Test
    public void testAnonymousH() {
        testStructuralHFile("struct", "anonymous", "StructanonymousH");
    }

    @Test
    public void testAnonymousWithVarDeclC() {
        testStructuralCFile("struct", "anonymousWithVarDecl", "StructanonymousWithVarDeclC");
    }

    @Test
    public void testAnonymousWithVarDeclH() {
        testStructuralHFile("struct", "anonymousWithVarDecl", "StructanonymousWithVarDeclH");
    }

    @Test
    public void testAnonymousWithVarDeclAndInitC() {
        testStructuralCFile("struct", "anonymousWithVarDeclAndInit", "StructanonymousWithVarDeclAndInitC");
    }

    @Test
    public void testAnonymousWithVarDeclAndInitH() {
        testStructuralHFile("struct", "anonymousWithVarDeclAndInit", "StructanonymousWithVarDeclAndInitH");
    }

    @Test
    public void testAnonymousWithArrayVarDeclC() {
        testStructuralCFile("struct", "anonymousWithArrayVarDecl", "StructanonymousWithArrayVarDeclC");
    }

    @Test
    public void testAnonymousWithArrayVarDeclH() {
        testStructuralHFile("struct", "anonymousWithArrayVarDecl", "StructanonymousWithArrayVarDeclH");
    }

    @Test
    public void testAnonymousWithArrayVarDeclAndInitC() {
        testStructuralCFile("struct", "anonymousWithArrayVarDeclAndInit",
                "StructanonymousWithArrayVarDeclAndInitC");
    }

    @Test
    public void testAnonymousWithArrayVarDeclAndInitH() {
        testStructuralHFile("struct", "anonymousWithArrayVarDeclAndInit",
                "StructanonymousWithArrayVarDeclAndInitH");
    }

    @Test
    public void testNamedC() {
        testStructuralCFile("struct", "named", "StructNamedC");
    }

    @Test
    public void testNamedH() {
        testStructuralHFile("struct", "named", "StructNamedH");
    }

    @Test
    public void testNamedWithVarDeclC() {
        testStructuralCFile("struct", "namedWithVarDecl", "StructnamedWithVarDeclC");
    }

    @Test
    public void testNamedWithVarDeclH() {
        testStructuralHFile("struct", "namedWithVarDecl", "StructnamedWithVarDeclH");
    }

    @Test
    public void testNamedWithVarDeclAndInitC() {
        testStructuralCFile("struct", "namedWithVarDeclAndInit", "StructnamedWithVarDeclAndInitC");
    }

    @Test
    public void testNamedWithVarDeclAndInitH() {
        testStructuralHFile("struct", "namedWithVarDeclAndInit", "StructnamedWithVarDeclAndInitH");
    }

    @Test
    public void testNamedWithArrayVarDeclC() {
        testStructuralCFile("struct", "namedWithArrayVarDecl", "StructnamedWithArrayVarDeclC");
    }

    @Test
    public void testNamedWithArrayVarDeclH() {
        testStructuralHFile("struct", "namedWithArrayVarDecl", "StructnamedWithArrayVarDeclH");
    }

    @Test
    public void testNamedWithArrayVarDeclAndInitC() {
        testStructuralCFile("struct", "namedWithArrayVarDeclAndInit", "StructnamedWithArrayVarDeclAndInitC");
    }

    @Test
    public void testNamedWithArrayVarDeclAndInitH() {
        testStructuralHFile("struct", "namedWithArrayVarDeclAndInit", "StructnamedWithArrayVarDeclAndInitH");
    }

    @Test
    public void testPointersMembersC() {
        testStructuralCFile("struct", "pointersMembers", "pointersMembersC");
    }

    @Test
    public void testPointersMembersH() {
        testStructuralHFile("struct", "pointersMembers", "pointersMembersH");
    }

    @Test
    public void testStructMembersC() {
        testStructuralCFile("struct", "structMembers", "structMembersC");
    }

    @Test
    public void testStructMembersH() {
        testStructuralHFile("struct", "structMembers", "structMembersH");
    }

    @Test
    public void testTypeDefMembersC() {
        testStructuralCFile("struct", "typeDefMembers", "typeDefMembersC");
    }

    @Test
    public void testTypeDefMembersH() {
        testStructuralHFile("struct", "typeDefMembers", "typeDefMembersH");
    }
}
