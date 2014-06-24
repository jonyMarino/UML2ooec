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

public class TestDefine extends AbstractTest {

	@Test
	public void cEmptyDefine() {
		testStructuralCFile("define", "aFile", "defineemptyC");
	}

	@Test
	public void hEmptyDefine() {
		testStructuralHFile("define", "aFile", "defineemptyH");
	}

	@Test
	public void cIntDefine() {
		testStructuralCFile("define", "aFile", "intDefineC");
	}

	@Test
	public void hIntDefine() {
		testStructuralHFile("define", "aFile", "intDefineH");
	}

	@Test
	public void cStringDefine() {
		testStructuralCFile("define", "aFile", "stringDefineC");
	}

	@Test
	public void hStringDefine() {
		testStructuralHFile("define", "aFile", "stringDefineH");
	}
}
