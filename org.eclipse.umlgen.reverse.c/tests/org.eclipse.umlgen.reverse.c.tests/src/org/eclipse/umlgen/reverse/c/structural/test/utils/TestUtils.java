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
package org.eclipse.umlgen.reverse.c.structural.test.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionManager;
import org.eclipse.cdt.managedbuilder.core.IBuilder;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IProjectType;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.internal.core.Configuration;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedProject;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;

public class TestUtils {

	static IWorkspace workspace = ResourcesPlugin.getWorkspace();

	static IWorkspaceRoot root = null;

	static {
		root = workspace.getRoot();
	}

	@SuppressWarnings("restriction")
	static void addBuilders(IProject iProject) throws CoreException {
		ICProjectDescriptionManager mgr = CoreModel.getDefault().getProjectDescriptionManager();
		ICProjectDescription des = mgr.getProjectDescription(iProject, true);

		if (des != null) {
			return; // C project description already exists
		}

		des = mgr.createProjectDescription(iProject, true);

		ManagedBuildInfo info = ManagedBuildManager.createBuildInfo(iProject);

		final String osName = System.getProperty("os.name").toLowerCase();

		IProjectType projType = null;
		if (osName.indexOf("win") > 0) {
			projType = ManagedBuildManager.getExtensionProjectType("cdt.managedbuild.target.gnu.cygwin.exe");
		} else if (osName.indexOf("mac") > 0) {
			projType = ManagedBuildManager.getExtensionProjectType("cdt.managedbuild.target.macosx.exe");
		} else {
			projType = ManagedBuildManager.getExtensionProjectType("cdt.managedbuild.target.gnu.exe");
		}

		IToolChain toolChain = null;
		if (osName.indexOf("win") > 0) {
			toolChain = ManagedBuildManager
					.getExtensionToolChain("cdt.managedbuild.toolchain.gnu.cygwin.exe.release");
		} else if (osName.indexOf("mac") > 0) {
			toolChain = ManagedBuildManager
					.getExtensionToolChain("cdt.managedbuild.toolchain.gnu.macosx.exe.release");
		} else {
			toolChain = ManagedBuildManager
					.getExtensionToolChain("cdt.managedbuild.toolchain.gnu.exe.release");
		}

		ManagedProject mProj = new ManagedProject(iProject, projType);
		info.setManagedProject(mProj);

		IConfiguration[] configs = ManagedBuildManager.getExtensionConfigurations(toolChain, projType);

		for (IConfiguration icf : configs) {
			if (!(icf instanceof Configuration)) {
				continue;
			}
			Configuration cf = (Configuration)icf;

			String id = ManagedBuildManager.calculateChildId(cf.getId(), null);
			Configuration config = new Configuration(mProj, cf, id, false, true);

			ICConfigurationDescription cfgDes = des.createConfiguration(
					ManagedBuildManager.CFG_DATA_PROVIDER_ID, config.getConfigurationData());
			config.setConfigurationDescription(cfgDes);
			config.exportArtifactInfo();

			IBuilder bld = config.getEditableBuilder();
			if (bld != null) {
				bld.setManagedBuildOn(true);
			}

			config.setName(toolChain.getName());
			config.setArtifactName(iProject.getName());
		}

		mgr.setProjectDescription(iProject, des);
	}

	public static Model getActual(ResourceSet rs, IProject project) {
		IFile umlFile = project.getFolder("Model").getFile(project.getName() + ".uml");
		URI umlResourceURI = URI.createPlatformResourceURI(umlFile.getFullPath().toString(), true);
		Resource umlResource = rs.getResource(umlResourceURI, true);
		Model umlModel = (Model)umlResource.getContents().get(0);
		return umlModel;
	}

	private static Package findFirstPackageWithName(Model umlModel, String packageName) {
		TreeIterator<EObject> tit = umlModel.eAllContents();
		while (tit.hasNext()) {
			EObject current = tit.next();
			if (current instanceof Package && packageName.equals(((Package)current).getName())) {
				return (Package)current;
			} else if (current instanceof Classifier) {
				tit.prune();
			}
		}
		return null;
	}

	private static Model getTemplate(ResourceSet rs) {
		ImmutableMap<String, String> variables = ImmutableMap.<String, String> builder().put("name",
				"expected").build();

		final URI platformPluginURI = URI
				.createPlatformPluginURI(getProcessedString(
						"/org.eclipse.umlgen.reverse.c.ui/templates/SynchronizedNeptune/%name%.uml",
						variables), true);
		Resource modelResource = rs.createResource(platformPluginURI);
		try {
			InputStream resourceInputStream = getResourceInputStream("resource/structural/%name%.uml");
			resourceInputStream = getProcessedStream(resourceInputStream, "UTF-8", variables);
			modelResource.load(resourceInputStream, Collections.emptyMap());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return (Model)modelResource.getContents().get(0);
	}

	private static Model mergeTemplateWithSourcePackage(Model template, Package pack) {
		Package templatePackage = findFirstPackageWithName(template, pack.getName());
		Package superPackage = (Package)templatePackage.eContainer();
		final EList<PackageableElement> packagedElements = superPackage.getPackagedElements();
		int indexOfPack = packagedElements.indexOf(templatePackage);
		packagedElements.remove(indexOfPack);
		packagedElements.add(indexOfPack, pack);
		return template;
	}

	public static Model getExpected(ResourceSet rs, String umlFilePath) {
		final URI platformPluginURI = URI.createPlatformPluginURI("/org.eclipse.umlgen.reverse.c.tests"
				+ umlFilePath, true);

		Resource modelResource = rs.getResource(platformPluginURI, true);
		Model ret = null;
		List<EObject> contents = modelResource.getContents();

		if (contents.size() > 0) {
			EObject content = contents.get(0);
			if (content instanceof Package) {
				ret = mergeTemplateWithSourcePackage(getTemplate(rs), (Package)content);
			} else {
				throw new IllegalArgumentException("first element of the resource is not a UML Package");
			}
		} else {
			throw new IllegalArgumentException("no content in the given resource");
		}

		return ret;
	}

	public static URL getResource(String resourceName) {
		URL url = TestUtils.class.getClassLoader().getResource(resourceName);
		Preconditions.checkArgument(url != null, "resource %s not found.", resourceName);
		return url;
	}

	public static InputStream getResourceInputStream(String resourceName) {
		try {
			return getResource(resourceName).openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getProcessedString(String source, Map<String, String> variables) {
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
					String value = variables.get(key);
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

	private static InputStream getProcessedStream(InputStream stream, String charset,
			Map<String, String> variables) throws IOException {
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
						String value = variables.get(key);
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
}
