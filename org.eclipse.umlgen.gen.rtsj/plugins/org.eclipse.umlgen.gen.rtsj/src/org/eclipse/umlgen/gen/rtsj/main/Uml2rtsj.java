/*******************************************************************************
 * Copyright (c) 2008, 2015 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *     JF Rolland (ATOS) - implementation of rtsj specific generation
 *******************************************************************************/
package org.eclipse.umlgen.gen.rtsj.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.umlgen.gen.autojava.main.Uml2autojava;

/**
 * Entry point of the 'Uml2rtsj' generation module.
 *
 * @generated NOT
 */
public class Uml2rtsj extends Uml2autojava {
	/**
	 * The name of the module.
	 *
	 * @generated
	 */
	public static final String MODULE_FILE_NAME = "/org/eclipse/umlgen/gen/rtsj/main/uml2rtsj";

	/**
	 * The name of the templates that are to be generated.
	 *
	 * @generated
	 */
	public static final String[] TEMPLATE_NAMES = {"main" };

	/**
	 * The list of properties files from the launch parameters (Launch configuration).
	 *
	 * @generated
	 */
	private List<String> propertiesFiles = new ArrayList<String>();

	/**
	 * Allows the public constructor to be used. Note that a generator created this way cannot be used to
	 * launch generations before one of {@link #initialize(EObject, File, List)} or
	 * {@link #initialize(URI, File, List)} is called.
	 * <p>
	 * The main reason for this constructor is to allow clients of this generation to call it from another
	 * Java file, as it allows for the retrieval of {@link #getProperties()} and
	 * {@link #getGenerationListeners()}.
	 * </p>
	 *
	 * @generated
	 */
	public Uml2rtsj() {
		super();
	}

	/**
	 * This allows clients to instantiates a generator with all required information.
	 * 
	 * @param modelURI
	 *            URI where the model on which this generator will be used is located.
	 * @param targetFolder
	 *            This will be used as the output folder for this generation : it will be the base path
	 *            against which all file block URLs will be resolved.
	 * @param arguments
	 *            If the template which will be called requires more than one argument taken from the model,
	 *            pass them here.
	 * @throws IOException
	 *             This can be thrown in three scenarios : the module cannot be found, it cannot be loaded, or
	 *             the model cannot be loaded.
	 * @generated
	 */
	public Uml2rtsj(URI modelURI, File targetFolder, List<? extends Object> arguments) throws IOException {
		super(modelURI, targetFolder, arguments);
	}

	/**
	 * This allows clients to instantiates a generator with all required information.
	 * 
	 * @param model
	 *            We'll iterate over the content of this element to find Objects matching the first parameter
	 *            of the template we need to call.
	 * @param targetFolder
	 *            This will be used as the output folder for this generation : it will be the base path
	 *            against which all file block URLs will be resolved.
	 * @param arguments
	 *            If the template which will be called requires more than one argument taken from the model,
	 *            pass them here.
	 * @throws IOException
	 *             This can be thrown in two scenarios : the module cannot be found, or it cannot be loaded.
	 * @generated
	 */
	public Uml2rtsj(EObject model, File targetFolder, List<? extends Object> arguments) throws IOException {
		super(model, targetFolder, arguments);
	}

	public Uml2rtsj(URI modelURI, File targetFolder, List<? extends Object> arguments, String sDecorators)
					throws IOException {
		super(modelURI, targetFolder, arguments, sDecorators);
	}

	/**
	 * This can be used to launch the generation from a standalone application.
	 * 
	 * @param args
	 *            Arguments of the generation.
	 * @generated
	 */
	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				System.out.println("Arguments not valid : {model, folder}.");
			} else {
				URI modelURI = URI.createFileURI(args[0]);
				File folder = new File(args[1]);

				List<String> arguments = new ArrayList<String>();

				/*
				 * If you want to change the content of this method, do NOT forget to change the "@generated"
				 * tag in the Javadoc of this method to "@generated NOT". Without this new tag, any
				 * compilation of the Acceleo module with the main template that has caused the creation of
				 * this class will revert your modifications.
				 */

				/*
				 * Add in this list all the arguments used by the starting point of the generation If your
				 * main template is called on an element of your model and a String, you can add in
				 * "arguments" this "String" attribute.
				 */

				Uml2rtsj generator = new Uml2rtsj(modelURI, folder, arguments);

				/*
				 * Add the properties from the launch arguments. If you want to programmatically add new
				 * properties, add them in "propertiesFiles" You can add the absolute path of a properties
				 * files, or even a project relative path. If you want to add another "protocol" for your
				 * properties files, please override "getPropertiesLoaderService(AcceleoService)" in order to
				 * return a new property loader. The behavior of the properties loader service is explained in
				 * the Acceleo documentation (Help -> Help Contents).
				 */

				for (int i = 2; i < args.length; i++) {
					generator.addPropertiesFile(args[i]);
				}

				generator.doGenerate(new BasicMonitor());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launches the generation described by this instance.
	 * 
	 * @param monitor
	 *            This will be used to display progress information to the user.
	 * @throws IOException
	 *             This will be thrown if any of the output files cannot be saved to disk.
	 * @generated
	 */

	/**
	 * This will be called in order to find and load the module that will be launched through this launcher.
	 * We expect this name not to contain file extension, and the module to be located beside the launcher.
	 * 
	 * @return The name of the module that is to be launched.
	 * @generated
	 */
	@Override
	public String getModuleName() {
		return MODULE_FILE_NAME;
	}

	/**
	 * This will be used to get the list of templates that are to be launched by this launcher.
	 * 
	 * @return The list of templates to call on the module {@link #getModuleName()}.
	 * @generated
	 */
	@Override
	public String[] getTemplateNames() {
		return TEMPLATE_NAMES;
	}

	/**
	 * If the module(s) called by this launcher require properties files, return their qualified path from
	 * here.Take note that the first added properties files will take precedence over subsequent ones if they
	 * contain conflicting keys.
	 *
	 * @return The list of properties file we need to add to the generation context.
	 * @see java.util.ResourceBundle#getBundle(String)
	 * @generated NOT
	 */
	@Override
	public List<String> getProperties() {

		propertiesFiles.add("org/eclipse/umlgen/gen/rtsj/properties/default.properties");
		propertiesFiles.add("org/eclipse/umlgen/gen/rtsj/properties/types.properties");
		propertiesFiles.add("org/eclipse/umlgen/gen/rtsj/properties/primitiveTypes.properties");
		return propertiesFiles;
	}

}
