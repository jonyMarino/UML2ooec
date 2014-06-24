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
	private File _source;

	private IContainer _destination;

	private Map<String, String> _variables;

	private StringBuffer _errors;

	/**
	 * Constructor
	 *
	 * @param src
	 *            the template source file
	 */
	public Template(File src) {
		_source = src;
	}

	/**
	 * Set the destination directory of the template
	 *
	 * @param container
	 *            the destination container
	 */
	public void setDestination(IContainer container) {
		_destination = container;
	}

	/**
	 * Set the list of parameters and its values
	 *
	 * @param variables
	 *            the map of parameters (key="Parameter name String", value="Parameter value String")
	 */
	public void setVariables(Map<String, String> variables) {
		_variables = variables;
	}

	/**
	 * Set the value of a parameter
	 *
	 * @param key
	 *            the parameter name
	 * @param value
	 *            the parameter value
	 */
	public void addVariable(String key, String value) {
		if (_variables == null) {
			_variables = new HashMap<String, String>();
		}

		_variables.put(key, value);
	}

	protected boolean _checkTemplate() {
		boolean valid = true;
		_errors = new StringBuffer();

		if (_source == null || !_source.exists()) {
			_errors.append("The template source does not exist.\n");
			valid = false;
		}

		if (_destination == null || !_destination.exists()) {
			_errors.append("The destination folder does not exist.\n");
		}

		return valid;
	}

	private IContainer _generateFiles(File src, IContainer dest, IProgressMonitor monitor)
			throws CoreException {
		File[] members = src.listFiles();
		for (File member : members) {
			if (member.isDirectory()) {
				String folderName = getProcessedString(member.getName());
				IFolder dstContainer = dest.getFolder(new Path(folderName));

				if (!dstContainer.exists()) {
					dstContainer.create(true, true, monitor);
				}
				_generateFiles(member, dstContainer, monitor);
			} else {
				_copyFile(member, dest, monitor);
			}
		}

		return dest;
	}

	private IFile _copyFile(File file, IContainer dest, IProgressMonitor monitor) throws CoreException {
		String targetFileName = getProcessedString(file.getName());

		monitor.subTask(targetFileName);
		IFile dstFile = dest.getFile(new Path(targetFileName));

		InputStream stream = null;
		try {
			stream = _getProcessedStream(file, dest.getDefaultCharset());
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

	private InputStream _getProcessedStream(File file, String charset) throws IOException {
		FileInputStream stream = new FileInputStream(file);

		InputStreamReader reader = new InputStreamReader(stream);
		int bufsize = 1024;
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
						String value = _getVariable(key);
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
	 * Process the given String through the parameter map
	 *
	 * @param source
	 *            the source String
	 * @return the processed string
	 */
	public String getProcessedString(String source) {
		if (source.indexOf('%') == -1) {
			return source;
		}
		int loc = -1;
		StringBuffer buffer = new StringBuffer();
		boolean replacementMode = false;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (c == '%') {
				if (replacementMode) {
					String key = source.substring(loc, i);
					String value = _getVariable(key);
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

	private String _getVariable(String key) {
		if (_variables != null && _variables.get(key) != null) {
			return _variables.get(key);
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
	 */
	public IResource generate(IProgressMonitor monitor) throws CoreException {
		monitor.setTaskName("Template Generation Process");
		IResource dest = null;

		if (_source.isDirectory()) {
			dest = _generateFiles(_source, _destination, monitor);
		} else {
			dest = _copyFile(_source, _destination, monitor);
		}
		monitor.subTask(""); //$NON-NLS-1$
		monitor.worked(1);

		return dest;
	}
}
