/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe Le Camus (CS-SI) - initial API and implementation
 *     Mikael Barbero (Obeo) 	- evolutions
 *     Sebastien Gabel (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.gen.c.ui.internal.handler;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.gen.c.files.Generate;

public class GenerateCCodeFromUML extends AbstractHandler {

	private ModelManager manager;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IStructuredSelection selection = (IStructuredSelection)HandlerUtil
					.getCurrentSelectionChecked(event);
			EObject selectedObject = (EObject)selection.getFirstElement();

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
			}
			// FIXME MIGRATION reference to modeler
			// else if (eClass == DiagramInterchangePackage.Literals.DIAGRAM)
			// {
			// caseDiagram((Diagram) selectedObject);
			// }
			else {
				throw new ExecutionException("Bad object's class");
			}
		} catch (ExecutionException e) {
			throw e;
		} finally {
			manager.dispose();
			manager = null;
		}

		return null; // *MUST* be null (cf.
		// AbstractHandler.execute(ExecutionEvent))
	}

	// FIXME MIGRATION reference to modeler
	// private void caseDiagram(Diagram selectedObject) throws
	// ExecutionException
	// {
	// doGenerate(manager.getSourcePackage());
	// }

	private void caseOpaqueBehavior(OpaqueBehavior selectedObject) throws ExecutionException {
		caseClass((Class)selectedObject.eContainer());
	}

	private void casePackage(Package selectedObject) throws ExecutionException {
		doGenerate(selectedObject);
	}

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

	private void caseClass(Class selectedObject) throws ExecutionException {
		doGenerate(selectedObject);
	}

	private void caseClass(Interface selectedObject) throws ExecutionException {
		doGenerate(selectedObject);
	}

	private void caseOperation(Operation selectedObject) throws ExecutionException {
		caseClass((Class)selectedObject.eContainer());
	}

	private void caseActivity(Activity selectedObject) throws ExecutionException {
		caseClass((Class)selectedObject.eContainer().eContainer());
	}

}
