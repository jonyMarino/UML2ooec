/*******************************************************************************
 * Copyright (c) 2010, 2015 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Mikael Barbero (Obeo) 	- evolutions
 *     Sebastien Gabel (CS-SI) - evolutions
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.ui.internal.handler;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.gen.c.files.Generate;

/**
 * This generates C code from elements of a UML model.
 */
public class GenerateCCodeFromUML extends AbstractHandler {

    /** A model manager. */
    private ModelManager manager;

    /**
     * {@inheritDoc}
     *
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        try {
            IStructuredSelection selection = (IStructuredSelection)HandlerUtil
                    .getCurrentSelectionChecked(event);

            EObject selectedObject = null;

            Object obj = selection.getFirstElement();

            if (obj instanceof IFile) {
                // URI uri = URI.createURI(((IFile)obj).getFullPath().toString());
                // ResourceSet rscSet = new ResourceSetImpl();
                // Resource resource = rscSet.getResource(uri, true);
                // if (resource.getContents().size() > 0) {
                // selectedObject = resource.getContents().get(0);
                // doGenerate(selectedObject);
                // }
                // return null;
                manager = new ModelManager((IFile)obj);
                Resource umlResource = manager.getModelResource();
                if (umlResource.getContents().size() > 0) {
                    doGenerate(umlResource.getContents().get(0));
                }
            } else if (obj instanceof EObject) {
                selectedObject = (EObject)obj;
                IResource model = ResourcesPlugin.getWorkspace().getRoot().findMember(
                        selectedObject.eResource().getURI().toPlatformString(true));
                manager = new ModelManager(model);

                EClass eClass = selectedObject.eClass();
                if (eClass == UMLPackage.Literals.ACTIVITY) {
                    caseActivity((Activity)selectedObject);
                } else if (eClass == UMLPackage.Literals.OPERATION) {
                    caseOperation((Operation)selectedObject);
                } else if (eClass == UMLPackage.Literals.CLASS) {
                    caseClass((Class)selectedObject);
                } else if (eClass == UMLPackage.Literals.INTERFACE) {
                    caseClass((Interface)selectedObject);
                } else if (eClass == UMLPackage.Literals.PACKAGE) {
                    casePackage((Package)selectedObject);
                } else if (eClass == UMLPackage.Literals.OPAQUE_BEHAVIOR) {
                    caseOpaqueBehavior((OpaqueBehavior)selectedObject);
                } else if (SynchronizersManager.getSynchronizer() instanceof IDiagramSynchronizer
                        && eClass == ((IDiagramSynchronizer)SynchronizersManager.getSynchronizer())
                        .getRepresentationKind()) {
                    caseDiagram(selectedObject);
                } else {
                    throw new ExecutionException("Bad object's class");
                }

            }
        } catch (ExecutionException e) {
            throw e;
        } finally {
            if (manager != null) {
                manager.dispose();
                manager = null;
            }
        }

        return null; // *MUST* be null (cf.
        // AbstractHandler.execute(ExecutionEvent))
    }

    /**
     * This generates from a given UML diagram.
     *
     * @param selectedObject
     *            The UML diagram.
     * @throws ExecutionException
     *             exception.
     */
    private void caseDiagram(EObject selectedObject) throws ExecutionException {
        doGenerate(manager.getSourcePackage());
    }

    /**
     * This generates from a given UML opaque behavior.
     *
     * @param selectedObject
     *            The UML opaque behavior.
     * @throws ExecutionException
     *             exception.
     */
    private void caseOpaqueBehavior(OpaqueBehavior selectedObject) throws ExecutionException {
        caseClass((Class)selectedObject.eContainer());
    }

    /**
     * This generates from a given UML package.
     *
     * @param selectedObject
     *            The UML package.
     * @throws ExecutionException
     *             exception.
     */
    private void casePackage(Package selectedObject) throws ExecutionException {
        doGenerate(selectedObject);
    }

    /**
     * This generates from the given model object.
     *
     * @param eObject
     *            The model object.
     * @throws ExecutionException
     *             exception.
     */
    private void doGenerate(EObject eObject) throws ExecutionException {
        try {
            Generate gen = new Generate(eObject, ResourcesPlugin.getWorkspace().getRoot().getLocation()
                    .toFile(), Collections.emptyList());
            gen.doGenerate(new BasicMonitor());
        } catch (IOException e) {
            throw new ExecutionException(e.getMessage(), e);
        }
        try {
            manager.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            // it's ok to silently give up this exception
        }
    }

    /**
     * This generates from a given UML class.
     *
     * @param selectedObject
     *            The UML class.
     * @throws ExecutionException
     *             exception.
     */
    private void caseClass(Class selectedObject) throws ExecutionException {
        doGenerate(selectedObject);
    }

    /**
     * This generates from a given UML interface.
     *
     * @param selectedObject
     *            The UML interface.
     * @throws ExecutionException
     *             exception.
     */
    private void caseClass(Interface selectedObject) throws ExecutionException {
        doGenerate(selectedObject);
    }

    /**
     * This generates from a given UML operation.
     *
     * @param selectedObject
     *            The UML operation.
     * @throws ExecutionException
     *             exception.
     */
    private void caseOperation(Operation selectedObject) throws ExecutionException {
        caseClass((Class)selectedObject.eContainer());
    }

    /**
     * This generates from a given UML activity.
     *
     * @param selectedObject
     *            The UML activity.
     * @throws ExecutionException
     *             exception.
     */
    private void caseActivity(Activity selectedObject) throws ExecutionException {
        caseClass((Class)selectedObject.eContainer().eContainer());
    }

}
