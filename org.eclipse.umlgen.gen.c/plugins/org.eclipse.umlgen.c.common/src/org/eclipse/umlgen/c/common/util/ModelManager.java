/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.c.common.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.common.util.UML2Util.EObjectMatcher;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.umlgen.c.common.Activator;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;
import org.eclipse.umlgen.c.common.ui.PreferenceStoreManager;

/**
 * The model manager is in charge of loading/unloading synchronized models when it is necessary.
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 */
public class ModelManager {

    /** The eclipse project containing the model. */
    protected IProject project;

    /** The EMF resource for the model. */
    protected Resource modelResource;

    /** Flag to know if the model has been loaded from a modeler. */
    protected boolean loadedFromModeler;

    /** The model. */
    private EObject model;

    /** The UML Package containing the sources. */
    private Package srcPackage;

    /** The UML Package containing the types. */
    private Package typesPackage;

    /** The UML Package containing the libraries. */
    private Package libsPackage;

    /**
     * Constructor.
     *
     * @param rsc
     *            The resource from which the resource manager is invoked.
     */
    public ModelManager(IResource rsc) {
        // Initialize the preference store, get it and retrieve values
        project = rsc.getProject();

        IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
        if (synchronizer != null) {
            synchronizer.setDefaultValues(project);
        }

        initializeEditorPartListener();

        initializeModels();
    }

    /**
     * Disposes encapsuled items of the model manager.
     */
    public void dispose() {
        // save the models in their current states
        saveModels();

        // unload models
        unloadPreviousModels();

        project = null;

    }

    /**
     * Initialize potential editor part listeners.
     */
    protected void initializeEditorPartListener() {
    }

    /**
     * This loads the UML semantic model in EMF resources.
     */
    protected void initializeModels() {
        URI modelURI = getModelUri();
        ResourceSet rscSet = getResourceSet(modelURI);
        modelResource = rscSet.getResource(modelURI, true);
        model = modelResource.getContents().get(0);
    }

    /**
     * This retrieves the URI of the UML semantic model.
     *
     * @return The URI.
     */
    private URI getModelUri() {
        IPreferenceStore store = PreferenceStoreManager.getPreferenceStore(project);
        String modelPath = store.getString(BundleConstants.UML_MODEL_PATH);
        return URI.createURI(modelPath);
    }

    /**
     * Before loading other models into the resource srt, previous models must be unloaded properly.
     */
    protected void unloadPreviousModels() {
        srcPackage = null;
        typesPackage = null;
        libsPackage = null;
        model = null;

        if (!loadedFromModeler) {
            // resources are unloaded
            if (modelResource != null) {
                modelResource.unload();
                modelResource = null;
            }
        }
    }

    /**
     * Saves models loaded into the resource set (UML and UMLDI).
     */
    public void saveModels() {
        Map<String, String> options = new HashMap<String, String>();
        try {
            options.put(XMLResource.OPTION_ENCODING, project.getDefaultCharset(true));
            saveModels(options);
            // CHECKSTYLE:OFF
        } catch (Exception e) {
            // CHECKSTYLE:ON
            IStatus status = null;
            status = new Status(IStatus.ERROR, Activator.getBundleId(), IStatus.OK,
                    org.eclipse.umlgen.c.common.Messages.getModelManager() + e.getMessage(), e);
            Activator.log(status);
        }
    }

    /**
     * Saves models loaded into the resource set (UML and UMLDI).
     *
     * @param options
     *            The options to save models.
     * @throws CoreException
     *             e1
     * @throws IOException
     *             e2
     */
    protected void saveModels(Map<String, String> options) throws CoreException, IOException {
        if (modelResource != null) {
            modelResource.save(options);
        }
    }

    /**
     * Gets the <b>Model</b> model object.
     *
     * @return The Model element representing the root of the semantic model.
     */
    public Model getUMLModel() {
        return (Model)model;
    }

    /**
     * Gets the current {@link IProject} on which we are working.
     *
     * @return the Eclipse project
     */
    public IProject getProject() {
        return project;
    }

    /**
     * Gets the current C project.
     *
     * @return the C project
     */
    public ICProject getCProject() {
        return CoreModel.getDefault().getCModel().getCProject(getProject().getName());
    }

    /**
     * Gets the EMF resource of the model.
     *
     * @return the modelResource.
     */
    public Resource getModelResource() {
        return modelResource;
    }

    /**
     * Gets the resource set accordingly to the reference URI passed into parameter.
     *
     * @param modelURI
     *            The reference URI for which the resource set must be found.
     * @return the resource set on which the diagram model is already loaded, otherwise a new default resource
     *         set is instantiated.
     */
    protected ResourceSet getResourceSet(URI modelURI) {
        return new ResourceSetImpl();
    }

    /**
     * Finds a package into the current loaded UML model. Inspects the preference store and browse the loaded
     * UML model.
     *
     * @param pkgConstante
     *            The name of the package to locate
     * @return The package found or null if not found
     */
    private Package findPackage(String pkgConstante) {
        IPreferenceStore store = PreferenceStoreManager.getPreferenceStore(project);
        String packToLocate = store.getString(pkgConstante);
        Collection<Package> packages = UMLUtil.<Package> findNamedElements(modelResource, packToLocate,
                false, UMLPackage.Literals.PACKAGE);
        // CHECKSTYLE:OFF
        return packages.isEmpty() ? null : packages.toArray(new Package[0])[0];
        // CHECKSTYLE:ON
    }

    /**
     * Finds the package into the current UML model where <b>created classes and interfaces (reversed
     * code)</b> will be stored.
     *
     * @return The identified package
     */
    public Package getSourcePackage() {
        if (srcPackage == null) {
            srcPackage = findPackage(BundleConstants.SRC_PCK_NAME);
        }
        return srcPackage;
    }

    /**
     * Finds the package into the current UML model where <b>created types</b> will be stored.
     *
     * @return The identified package
     */
    public Package getTypePackage() {
        if (typesPackage == null) {
            typesPackage = findPackage(BundleConstants.TYPE_PCK_NAME);
        }
        return typesPackage;
    }

    /**
     * Finds the package into the current UML model where <b>external interfaces</b> will be stored.
     *
     * @return The identified package
     */
    public Package getLibsPackage() {
        if (libsPackage == null) {
            libsPackage = findPackage(BundleConstants.EXT_PCK_NAME);
        }
        return libsPackage;
    }

    /**
     * Finds a type in the current loaded UML model provided by the model manager. This search is not limited
     * to the type package, so it means that the type can be found into any classifier of the model. However,
     * the visibility of the returned element should be public.
     *
     * @param typeName
     *            The type name to find
     * @return The type or null if not found
     */
    public DataType findDataType(final String typeName) {
        TreeIterator<DataType> allDataTypes = UML2Util.<DataType> getAllContents(model, false, false);
        return (DataType)UML2Util.findEObject(allDataTypes, new EObjectMatcher() {

            public boolean matches(EObject eObject) {
                if (eObject instanceof DataType) {
                    DataType type = (DataType)eObject;
                    String name = type.getName();
                    VisibilityKind visibility = type.getVisibility();
                    return typeName.equals(name) && visibility == VisibilityKind.PUBLIC_LITERAL;
                }
                return false;
            }
        });
    }

    /**
     * Finds a given type into a given classifier.
     *
     * @param dataTypeName
     *            : the name of the DataType we want to find
     * @return the UML DataType Class found
     */
    public DataType findDataTypeInTypesPck(String dataTypeName) {
        String qualifier = getTypePackage().getQualifiedName().concat("::" + dataTypeName); //$NON-NLS-1$
        Collection<DataType> dataTypes = UMLUtil.<DataType> findNamedElements(modelResource, qualifier,
                false, UMLPackage.Literals.DATA_TYPE);
        // CHECKSTYLE:OFF
        return dataTypes.isEmpty() ? null : dataTypes.toArray(new DataType[0])[0];
        // CHECKSTYLE:ON
    }

    /**
     * Try to locate a given type. If the type is not found, it is created into the defined 'Types' packages.
     *
     * @param typeName
     *            The type name to find or create
     * @return a type corresponding to the search.
     */
    public DataType getDataType(String typeName) {
        DataType myType = findDataType(typeName);
        if (myType == null) {
            myType = createNewDataType(typeName);
        }
        return myType;
    }

    /**
     * Create a new type into the Type package of the model.
     *
     * @param typeName
     *            The type name
     * @return The newly type
     */
    private DataType createNewDataType(String typeName) {
        DataType newType = null;
        if (ModelUtil.getPrimitiveTypes().contains(typeName)) {
            newType = getTypePackage().createOwnedPrimitiveType(typeName);
        } else {
            newType = (DataType)getTypePackage().createOwnedType(typeName, UMLPackage.Literals.DATA_TYPE);
            setSuperDataType(newType);
        }
        newType.setVisibility(VisibilityKind.PUBLIC_LITERAL);
        return newType;
    }

    /**
     * Compute the dependency hierarchy between types. Interemediate types that are not necessarly used are
     * also created if needed.
     *
     * @param pointerType
     *            A pointer type containing at least one star character.
     */
    private void setSuperDataType(DataType pointerType) {
        if (pointerType.getName().contains("*")) { //$NON-NLS-1$
            while (pointerType.getRedefinedClassifiers().isEmpty()) {
                String superType = pointerType.getName();
                superType = superType.substring(0, superType.lastIndexOf("*")); //$NON-NLS-1$
                DataType candidateType = findDataType(superType);

                if (candidateType == null) {
                    candidateType = createNewDataType(superType);
                }
                pointerType.getRedefinedClassifiers().add(candidateType);
            }
        }
    }

}
