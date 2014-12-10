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

public class TestOperation extends AbstractTest {

    @Test
    public void testOperationC() {
        testStructuralCFile("operation", "operation", "operationC");
    }

    @Test
    public void testOperationH() {
        testStructuralHFile("operation", "operation", "operationH");
    }

    @Test
    public void testDefinitionBoth() {
        testStructuralFiles("operation", "functionDefinitionBOTH");
    }

    @Test
    public void testfunctionDefinitionC() {
        testStructuralCFile("operation", "functionDefinition", "functionDefinitionC");
    }

    @Test
    public void testfunctionDefinitionH() {
        testStructuralHFile("operation", "functionDefinition", "functionDefinitionH");
    }

    @Test
    public void testSimpleParameterC() {
        testStructuralCFile("operation", "simpleParameters", "simpleParametersC");
    }

    @Test
    public void testSimpleParameterH() {
        testStructuralHFile("operation", "simpleParameters", "simpleParametersH");
    }

    @Test
    public void testWithoutParameterC() {
        testStructuralCFile("operation", "withoutParameter", "withoutParameterC");
    }

    @Test
    public void testWithoutParameterH() {
        testStructuralHFile("operation", "withoutParameter", "withoutParameterH");
    }

    @Test
    public void testWithoutReturnTypeC() {
        testStructuralCFile("operation", "withoutReturnType", "withoutReturnTypeC");
    }

    @Test
    public void testWithoutReturnTypeH() {
        testStructuralHFile("operation", "withoutReturnType", "withoutReturnTypeH");
    }

    @Test
    public void testMultipleReferencesC() {
        testStructuralCFile("operation", "multipleReferences", "multipleReferencesC");
    }

    @Test
    public void testMultipleReferencesH() {
        testStructuralHFile("operation", "multipleReferences", "multipleReferencesH");
    }
}
