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
package org.eclipse.umlgen.gen.c.structural.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.umlgen.gen.c.files.Generate;

public class AbstractTest {
    private static final String C_EXTENSION = "c";

    private static final String DOT_C_EXTENSION = "." + C_EXTENSION;

    private static final String H_EXTENSION = "h";

    private static final String DOT_H_EXTENSION = "." + H_EXTENSION;

    private static final String UML_EXTENSION = "uml";

    private static final String DOT_UML_EXTENSION = "." + UML_EXTENSION;

    private static final String EXPECTED_TEST_ROOT = "../org.eclipse.umlgen.reverse.c.tests/resource/structural/addition";

    private static final String TEST_ROOT = "resource/structural/";

    public void testStructuralFiles(String path, String cFilename, String hFilename, String umlFileName) {

        String generatedCFilePath = null, expectedCFilePath = null, generatedHFilePath = null, expectedHFilePath = null;

        String outputDir = TEST_ROOT + "/" + path + "/"
                + umlFileName.substring(0, umlFileName.lastIndexOf("."));
        String expectedDir = TEST_ROOT + "/" + path + "/"
                + umlFileName.substring(0, umlFileName.lastIndexOf(".")) + "/expected";

        if (cFilename != null) {
            generatedCFilePath = outputDir + "/" + cFilename;
            expectedCFilePath = expectedDir + "/" + cFilename;
            deleteGeneratedFile(generatedCFilePath);
        }

        if (hFilename != null) {
            generatedHFilePath = outputDir + "/" + hFilename;
            expectedHFilePath = expectedDir + "/" + hFilename;
            deleteGeneratedFile(generatedHFilePath);
        }

        String umlFilePath = EXPECTED_TEST_ROOT + "/" + path + "/" + umlFileName;
        try {
            URI modelURI = URI.createFileURI(umlFilePath);
            File folder = new File(outputDir);
            List<String> arguments = new ArrayList<String>();
            Generate generator = new Generate(modelURI, folder, arguments);
            generator.addProperty("UNITTEST", "true");
            generator.doGenerate(new BasicMonitor());

            Resource eResource = generator.getModel().eResource();
            ResourceSet resourceSet = eResource.getResourceSet();

            eResource.unload();
            resourceSet.getResources().remove(eResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (cFilename != null) {
            assertEquals(getFileContents(expectedCFilePath).replaceAll("\r\n", "\n"), getFileContents(
                    generatedCFilePath).replaceAll("\r\n", "\n"));
        }

        if (hFilename != null) {
            assertEquals(getFileContents(expectedHFilePath).replaceAll("\r\n", "\n"), getFileContents(
                    generatedHFilePath).replaceAll("\r\n", "\n"));
        }
    }

    private void deleteGeneratedFile(String generatedCFilePath) {
        File generatedCFile = new File(generatedCFilePath);
        if (generatedCFile.exists()) {
            generatedCFile.delete();
            assertFalse("File " + generatedCFile.getPath() + " can not be removed before generation.",
                    generatedCFile.exists());
        }
    }

    public void testStructuralFiles(String path, String cCommonName, String umlFileName) {
        this.testStructuralFiles(path, cCommonName + DOT_C_EXTENSION, cCommonName + DOT_H_EXTENSION,
                umlFileName + DOT_UML_EXTENSION);
    }

    public void testStructuralCFile(String path, String cFilename, String umlFileName) {
        this.testStructuralFiles(path, cFilename + DOT_C_EXTENSION, null, umlFileName + DOT_UML_EXTENSION);
    }

    public void testStructuralCFile(String path, String commonName) {
        this.testStructuralFiles(path, commonName + DOT_C_EXTENSION, null, commonName + DOT_UML_EXTENSION);
    }

    public void testStructuralHFile(String path, String hFilename, String umlFileName) {
        this.testStructuralFiles(path, null, hFilename + DOT_H_EXTENSION, umlFileName + DOT_UML_EXTENSION);
    }

    public void testStructuralHFile(String path, String commonName) {
        this.testStructuralFiles(path, null, commonName + DOT_H_EXTENSION, commonName + DOT_UML_EXTENSION);
    }

    public void testStructuralFiles(String path, String commonName) {
        this.testStructuralFiles(path, commonName, commonName);
    }

    private String getFileContents(String filename) {
        String contents = null;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename)).useDelimiter("\\Z");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found " + e.getMessage(), e);
        }
        if (scanner != null) {
            try {
                contents = scanner.next();
            } catch (NoSuchElementException e) {
                contents = "";
            }
            scanner.close();
        }
        return contents;
    }
}
