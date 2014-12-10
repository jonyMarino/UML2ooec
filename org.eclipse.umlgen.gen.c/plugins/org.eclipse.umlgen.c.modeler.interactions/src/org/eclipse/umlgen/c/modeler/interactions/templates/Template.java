/*******************************************************************************
 * Copyright (c) 2005 AIRBUS FRANCE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    David Sciamma (Anyware Technologies), Mathieu Garcia (Anyware Technologies),
 *    Jacques Lescot (Anyware Technologies) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.c.modeler.interactions.templates;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.umlgen.c.modeler.interactions.Activator;

/**
 * Template created from a file. <br>
 * creation : 6 sept. 2004
 */
public class Template {

    /** Buffer size. */
    private static final int BUFFER_SIZE = 1024;

    /** The eclipse file containing the model template. */
    private File source;

    /** The eclipse container where the result of the template will be generated. */
    private IContainer destination;

    /** A set of variables indexed by key. */
    private Map<String, String> variables;

    /** Errors in a string buffer. */
    private StringBuffer errors;

    /**
     * Constructor.
     *
     * @param src
     *            the template source file
     */
    public Template(File src) {
        source = src;
    }

    /**
     * Set the destination directory of the template.
     *
     * @param container
     *            the destination container
     */
    public void setDestination(IContainer container) {
        destination = container;
    }

    /**
     * Set the list of parameters and its values.
     *
     * @param variables
     *            the map of parameters (key="Parameter name String", value="Parameter value String")
     */
    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    /**
     * Set the value of a parameter.
     *
     * @param key
     *            the parameter name
     * @param value
     *            the parameter value
     */
    public void addVariable(String key, String value) {
        if (variables == null) {
            variables = new HashMap<String, String>();
        }

        variables.put(key, value);
    }

    /**
     * This checks if the generation is possible.
     * 
     * @return True if it is.
     */
    protected boolean checkTemplate() {
        boolean valid = true;
        errors = new StringBuffer();

        if (source == null || !source.exists()) {
            errors.append("The template source does not exist.\n");
            valid = false;
        }

        if (destination == null || !destination.exists()) {
            errors.append("The destination folder does not exist.\n");
        }

        return valid;
    }

    /**
     * This generates the files from the input eclipse file, to the given destination.
     * 
     * @param src
     *            the input eclipse file location.
     * @param dest
     *            the eclipse container destination location.
     * @param monitor
     *            A progress monitor.
     * @return The destination location.
     * @throws CoreException
     *             exception.
     */
    private IContainer generateFiles(File src, IContainer dest, IProgressMonitor monitor)
            throws CoreException {
        File[] members = src.listFiles();
        for (File member : members) {
            if (member.isDirectory()) {
                String folderName = getProcessedString(member.getName());
                IFolder dstContainer = dest.getFolder(new Path(folderName));

                if (!dstContainer.exists()) {
                    dstContainer.create(true, true, monitor);
                }
                generateFiles(member, dstContainer, monitor);
            } else {
                copyFile(member, dest, monitor);
            }
        }

        return dest;
    }

    /**
     * This copies the given eclipse file into the destination eclipse container.
     * 
     * @param file
     *            The input eclipse file.
     * @param dest
     *            The target eclipse container.
     * @param monitor
     *            A progress monitor.
     * @return The destination container.
     * @throws CoreException
     *             exception.
     */
    private IFile copyFile(File file, IContainer dest, IProgressMonitor monitor) throws CoreException {
        String targetFileName = getProcessedString(file.getName());

        monitor.subTask(targetFileName);
        IFile dstFile = dest.getFile(new Path(targetFileName));

        InputStream stream = null;
        try {
            stream = getProcessedStream(file, dest.getDefaultCharset());
            if (dstFile.exists()) {
                dstFile.setContents(stream, true, true, monitor);
            } else {
                dstFile.create(stream, true, monitor);
            }

        } catch (IOException ioe) {
            Activator.log(ioe);
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ioe2) {
                Activator.log(ioe2);
            }
        }

        return dstFile;
    }

    /**
     * This creates an input stream from the given eclipse file.
     * 
     * @param file
     *            The input file.
     * @param charset
     *            The charset to use.
     * @return The input stream.
     * @throws IOException
     *             exception.
     */
    private InputStream getProcessedStream(File file, String charset) throws IOException {
        FileInputStream stream = new FileInputStream(file);

        InputStreamReader reader = new InputStreamReader(stream);
        int bufsize = BUFFER_SIZE;
        char[] cbuffer = new char[bufsize];
        int read = 0;
        StringBuffer keyBuffer = new StringBuffer();
        StringBuffer outBuffer = new StringBuffer();

        boolean replacementMode = false;
        boolean escape = false;
        while (read != -1) {
            read = reader.read(cbuffer);
            for (int i = 0; i < read; i++) {
                char c = cbuffer[i];

                if (escape) {
                    if (c != '%') {
                        outBuffer.append('\\');
                    }

                    outBuffer.append(c);
                    escape = false;
                    continue;
                }

                if (c == '%') {
                    if (replacementMode) {
                        replacementMode = false;
                        String key = keyBuffer.toString();
                        String value = getVariable(key);
                        outBuffer.append(value);
                        keyBuffer.delete(0, keyBuffer.length());
                    } else {
                        replacementMode = true;
                    }
                } else {
                    if (c == '\\') {
                        escape = true;
                        continue;
                    }

                    if (replacementMode) {
                        keyBuffer.append(c);
                    } else {
                        outBuffer.append(c);
                    }
                }
            }
        }
        stream.close();
        return new ByteArrayInputStream(outBuffer.toString().getBytes(charset));
    }

    /**
     * Process the given String through the parameter map.
     *
     * @param src
     *            the source String
     * @return the processed string
     */
    public String getProcessedString(String src) {
        if (src.indexOf('%') == -1) {
            return src;
        }
        int loc = -1;
        StringBuffer buffer = new StringBuffer();
        boolean replacementMode = false;
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (c == '%') {
                if (replacementMode) {
                    String key = src.substring(loc, i);
                    String value = getVariable(key);
                    buffer.append(value);
                    replacementMode = false;
                } else {
                    replacementMode = true;
                    loc = i + 1;
                    continue;
                }
            } else if (!replacementMode) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * Get the variable from the given key.
     * 
     * @param key
     *            The key
     * @return The variable.
     */
    private String getVariable(String key) {
        if (variables != null && variables.get(key) != null) {
            return variables.get(key);
        }

        return key;
    }

    /**
     * Generates files as part of the template execution. The default implementation uses template location as
     * a root of the file templates. The files found in the location are processed in the following way: Files
     * and folders are copied directly into the target folder with the conditional generation and variable
     * replacement for files. Variable replacement also includes file names.
     *
     * @param monitor
     *            progress monitor to use to indicate generation progress
     * @return the processed resource
     * @throws CoreException
     *             exception.
     */
    public IResource generate(IProgressMonitor monitor) throws CoreException {
        monitor.setTaskName("Template Generation Process");
        IResource dest = null;

        if (source.isDirectory()) {
            dest = generateFiles(source, destination, monitor);
        } else {
            dest = copyFile(source, destination, monitor);
        }
        monitor.subTask(""); //$NON-NLS-1$
        monitor.worked(1);

        return dest;
    }
}
