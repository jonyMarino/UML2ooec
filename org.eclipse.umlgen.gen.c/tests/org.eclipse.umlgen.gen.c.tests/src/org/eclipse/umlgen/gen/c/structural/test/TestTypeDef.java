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

public class TestTypeDef extends AbstractTest {

	@Test
	public void testAliasBuiltinTypeC() {
		testStructuralCFile("typedef/alias", "aliasBuiltin", "aliasBuiltinTypeC");
	}

	@Test
	public void testAliasBuiltinTypeH() {
		testStructuralHFile("typedef/alias", "aliasBuiltin", "aliasBuiltinTypeH");
	}

	@Test
	public void testArrayC() {
		testStructuralCFile("typedef/array", "array", "arrayC");
	}

	@Test
	public void testArrayH() {
		testStructuralHFile("typedef/array", "array", "arrayH");
	}

	@Test
	public void testEnumerationC() {
		testStructuralCFile("typedef/enumeration", "enum", "enumC");
	}

	@Test
	public void testEnumerationH() {
		testStructuralHFile("typedef/enumeration", "enum", "enumH");
	}

	@Test
	public void testNamedEnumerationC() {
		testStructuralCFile("typedef/named/enumeration", "namedEnum", "TypeDefEnumNamedC");
	}

	@Test
	public void testNamedEnumerationH() {
		testStructuralHFile("typedef/named/enumeration", "namedEnum", "TypeDefEnumNamedH");
	}

	@Test
	public void testOperationC() {
		testStructuralCFile("typedef/function", "operation", "TypeDefFunctionC");
	}

	@Test
	public void testOperationH() {
		testStructuralHFile("typedef/function", "operation", "TypeDefFunctionH");
	}

	@Test
	public void testNamedStructC() {
		testStructuralCFile("typedef/named/struct", "namedStruct", "TypeDefStructNamedC");
	}

	@Test
	public void testNamedStructH() {
		testStructuralHFile("typedef/named/struct", "namedStruct", "TypeDefStructNamedH");
	}

	@Test
	public void testPointerC() {
		testStructuralCFile("typedef/pointer", "pointer", "TypeDefPointerC");
	}

	@Test
	public void testPointerH() {
		testStructuralHFile("typedef/pointer", "pointer", "TypeDefPointerH");
	}

	@Test
	public void testStructC() {
		testStructuralCFile("typedef/struct", "struct", "TypeDefStructC");
	}

	@Test
	public void testStructH() {
		testStructuralHFile("typedef/struct", "struct", "TypeDefStructH");
	}

	@Test
	public void testSimpleC() {
		testStructuralCFile("typedef/multiple", "simple", "TypeDefMultipleSimpleC");
	}

	@Test
	public void testSimpleH() {
		testStructuralHFile("typedef/multiple", "simple", "TypeDefMultipleSimpleH");
	}

	@Test
	public void testMultipleArrayC() {
		testStructuralCFile("typedef/multiple", "array", "TypeDefMultipleArrayC");
	}

	@Test
	public void testMultipleArrayH() {
		testStructuralHFile("typedef/multiple", "array", "TypeDefMultipleArrayH");
	}

	@Test
	public void testMultiplePointerC() {
		testStructuralCFile("typedef/multiple", "pointer", "TypeDefMultiplePointerC");
	}

	@Test
	public void testMultiplePointerH() {
		testStructuralHFile("typedef/multiple", "pointer", "TypeDefMultiplePointerH");
	}
}
