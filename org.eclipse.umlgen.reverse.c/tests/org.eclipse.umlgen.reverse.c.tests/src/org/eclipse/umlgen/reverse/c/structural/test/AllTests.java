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
package org.eclipse.umlgen.reverse.c.structural.test;

import org.eclipse.umlgen.reverse.c.structural.test.addition.TestCUnit;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestComment;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestCommentInline;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestDefine;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestFunctions;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestFunctionsParameters;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestIncludeGuard;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestIncludes;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestOperation;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestSimpleEnumNamed;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestSimpleEnumUnNamed;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStorageClassExtern1to3;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStorageClassExtern4;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStorageClassExtern5to6AndRegister1;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStorageClassStatic1to3;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStorageClassStatic4to6;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStruct1;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestStruct2;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDef1;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDef2;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDefArrays;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDefEnum;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDefEnumNamed;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDefFunction;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDefStruct;
import org.eclipse.umlgen.reverse.c.structural.test.addition.TestTypeDefStructNamed;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {

TestFunctionsParameters.class, TestFunctions.class, TestStorageClassExtern4.class, TestComment.class,
		TestCommentInline.class, TestCUnit.class, TestDefine.class, TestIncludeGuard.class,
		TestIncludes.class, TestOperation.class, TestSimpleEnumNamed.class, TestSimpleEnumUnNamed.class,
		TestStorageClassExtern1to3.class, TestStorageClassExtern5to6AndRegister1.class,
		TestStorageClassStatic1to3.class, TestStorageClassStatic4to6.class, TestStruct1.class,
		TestStruct2.class, TestTypeDefArrays.class, TestTypeDefEnum.class, TestTypeDefEnumNamed.class,
		TestTypeDefFunction.class, TestTypeDefStruct.class, TestTypeDefStructNamed.class,
		org.eclipse.umlgen.reverse.c.structural.test.removal.TestOperation.class, TestTypeDef1.class,
		TestTypeDef2.class, })
public class AllTests {

}
