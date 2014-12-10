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
package org.eclipse.umlgen.reverse.c.activity.test.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;

public abstract class AbstractTest {

    private final String UML_EXTENSION = "uml";

    private final String ACTIVITY_TEST_ROOT = "activity/";

    private ResourceSetImpl rs;

    static private List<String> testedFiles = new ArrayList<String>();

    public void testCFile(String cFilePath, boolean saveGeneratedModel) {

        String cFileFullPath = ACTIVITY_TEST_ROOT + cFilePath;
        if (testedFiles.contains(cFileFullPath)) {
            System.out.println("!!! File tested twice : " + cFileFullPath);
        } else {
            testedFiles.add(cFileFullPath);
        }

        int dotPos = cFileFullPath.lastIndexOf(".");
        String umlFilePath = cFileFullPath.substring(0, dotPos + 1) + UML_EXTENSION;

        IASTTranslationUnit unit = TestUtils.getTranslationUnit(cFileFullPath);
        Model expectedModel = TestUtils.getUMLModel(getResourceSet(), umlFilePath);
        Model actualModel = TestUtils.createOutputModel(getResourceSet(), expectedModel);

        // call here the creation of the activity
        IASTFunctionDefinition functionUnderTest = TestUtils.getFirstFunctionInUnit(unit);
        Activity activity = UMLActivityBuilder.build(functionUnderTest);
        actualModel.getPackagedElements().add(activity);

        if (saveGeneratedModel) {
            System.out.println("Saving generated model for C file : " + cFileFullPath);
            TestUtils.saveGeneratedModel(actualModel, rs, umlFilePath);
        }

        TestUtils.assertEquals(expectedModel, actualModel);
    }

    @BeforeClass
    public static void init() {
        @SuppressWarnings("unused")
        EPackage umlPackage = UMLPackage.eINSTANCE;
        // Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("orderedxmi",
        // new OrderedXMIResourceFactoryImpl());
    }

    @Before
    public void setUp() {
        rs = new ResourceSetImpl();
        rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(UML_EXTENSION,
                UMLResource.Factory.INSTANCE);
    }

    @After
    public void tearDown() {
        for (Iterator<Resource> it = rs.getResources().iterator(); it.hasNext();) {
            Resource r = it.next();
            r.unload();
            it.remove();
        }
        rs = null;
    }

    protected ResourceSet getResourceSet() {
        return rs;
    }
}
