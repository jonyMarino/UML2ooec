/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien GABEL (CS-SI) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.ui.internal.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModelUtil;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.umlgen.c.common.AnnotationConstants;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.util.ModelManager;
import org.eclipse.umlgen.reverse.c.activity.UMLActivityBuilder;
import org.eclipse.umlgen.reverse.c.resource.ProjectUtil;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Activator;
import org.eclipse.umlgen.reverse.c.ui.internal.bundle.Messages;

/**
 * Handler launching the C code reverse operation. Creation : 10 may 2010<br>
 *
 * @author <a href="mailto:sebastien.gabel@c-s.fr">Sebastien GABEL</a>
 * @author <a href="mailto:mikael.barbero@obeo.fr">Mikael Barbero</a>
 * @author <a href="mailto:christophe.le-camus@c-s.fr">Christophe LE CAMUS</a>
 */
public class GenerateActivityDiagrams extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the selection
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);

		if (selection != null && selection instanceof IStructuredSelection) {
			final Object obj = ((IStructuredSelection)selection).getFirstElement();
			IResource model = ResourcesPlugin.getWorkspace().getRoot().findMember(
					((EObject)obj).eResource().getURI().toPlatformString(true));
			int numberOfFunctions = 1;
			if (obj instanceof Class) {
				numberOfFunctions = ((Class)obj).getOwnedBehaviors().size();
			}
			if (obj instanceof Package) {
				numberOfFunctions = 0;
				for (NamedElement elt : ((Package)obj).getMembers()) {
					if (elt instanceof Class) {
						numberOfFunctions = numberOfFunctions + ((Class)elt).getOwnedBehaviors().size();
					}
				}
			}
			final int totalOfWork = numberOfFunctions;
			// create a ModelManager for these purposes
			final ModelManager mm = new ModelManager(model);
			try {
				ProjectUtil.removeFromBuildSpec(mm.getProject(), BundleConstants.UML2C_BUILDER_ID);
			} catch (CoreException e1) {
				Activator.log(e1);
			}
			if (obj instanceof EObject) {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent()
						.getActiveShell());
				try {
					dialog.run(true, true, new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) throws InvocationTargetException,
								InterruptedException {
							monitor.beginTask(
									Messages.getString("GenerateActivityDiagrams.JobTitle"), totalOfWork); //$NON-NLS-1$
							generate((EObject)obj, mm, monitor);
							monitor.done();
						}
					});
				} catch (InvocationTargetException ite) {
					Activator.log(ite);
				} catch (InterruptedException ie) {
					Activator.log(ie);
				} finally {
					// if we don't save project it will be null for
					// addToBuildSpec
					IProject project = mm.getProject();
					mm.dispose();
					try {
						ProjectUtil.addToBuildSpec(project, BundleConstants.UML2C_BUILDER_ID);
					} catch (CoreException ce) {
						Activator.log(ce);
					}
				}
			}
		}
		return null; // Reserved for future use, must be null.
	}

	private void generate(EObject obj, ModelManager mm, IProgressMonitor monitor) {
		Package srcArtefactPack = mm.getSourcePackage();
		try {
			if (EcoreUtil.isAncestor(srcArtefactPack, obj)) {
				if (obj instanceof Package) {
					generate((Package)obj, mm, monitor);
				} else if (obj instanceof OpaqueBehavior) {
					generate((OpaqueBehavior)obj, mm, monitor);
					monitor.worked(1);
				} else if (obj instanceof Activity) {
					generate((Activity)obj, mm, monitor);
					monitor.worked(1);
				} else if (obj instanceof Class) {
					generate((Class)obj, mm, monitor);
				} else if (obj instanceof Operation) {
					generate((Operation)obj, mm, monitor);
					monitor.worked(1);
				}
			}

			// FIXME MIGRATION reference to modeler
			// if (obj instanceof Diagram)
			// {
			// generate(mm, monitor);
			// }
		} catch (Exception e) {
			Activator.log(e);
		}
	}

	private void generate(Package pack, ModelManager mm, IProgressMonitor monitor) throws CoreException {
		// try to remove existing Markers from this model object
		// FIXME reference to facilities
		// EMFMarkerUtil.removeMarkerFor(pack);

		for (Iterator<EObject> it = pack.eAllContents(); it.hasNext();) {
			EObject current = it.next();
			if (current instanceof OpaqueBehavior) {
				generate((OpaqueBehavior)current, mm, monitor);
				monitor.worked(1);
			}
		}
	}

	private void generate(Class pack, ModelManager mm, IProgressMonitor monitor) throws CoreException {
		// try to remove existing Markers from this model object
		// FIXME reference to facilities
		// EMFMarkerUtil.removeMarkerFor(pack);

		for (Iterator<EObject> it = pack.eAllContents(); it.hasNext();) {
			EObject current = it.next();
			if (current instanceof OpaqueBehavior) {
				generate((OpaqueBehavior)current, mm, monitor);
				monitor.worked(1);
			}
		}
	}

	private void generate(final Operation operation, ModelManager mm, IProgressMonitor monitor)
			throws CoreException {
		// try to remove existing Markers from this model object
		// FIXME reference to facilities
		// EMFMarkerUtil.removeMarkerFor(operation);

		Behavior behavior = operation.getClass_().getOwnedBehavior(operation.getName());
		if (behavior instanceof OpaqueBehavior && behavior.getSpecification() == operation) {
			generate((OpaqueBehavior)behavior, mm, monitor);
		} else {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					String title = String.format(Messages
							.getString("GenerateActivityDiagrams.OperationTitle"), operation.getName()); //$NON-NLS-1$
					String msg = String.format(
							Messages.getString("GenerateActivityDiagrams.OperationMsg"), operation.getName()); //$NON-NLS-1$
					MessageDialog.openWarning(Display.getDefault().getActiveShell(), title, msg);
					Activator.log(msg, IStatus.WARNING);
				}
			});
		}
	}

	private void generate(final Activity activity, ModelManager mm, IProgressMonitor monitor)
			throws CoreException {
		// try to remove existing Markers from this model object
		// FIXME reference to facilities
		// EMFMarkerUtil.removeMarkerFor(activity);

		BehavioredClassifier behavioredClassifier = activity.getContext();

		String activityName = activity.getName() != null ? activity.getName() : ""; //$NON-NLS-1$
		String behavioredClassifierName = behavioredClassifier.getName() != null ? behavioredClassifier
				.getName() : ""; //$NON-NLS-1$

				if (behavioredClassifier instanceof OpaqueBehavior && behavioredClassifierName.equals(activityName)) {
					generate((OpaqueBehavior)behavioredClassifier, mm, monitor);
				} else {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							String title = String.format(
									Messages.getString("GenerateActivityDiagrams.ActivityTitle"), activity.getName()); //$NON-NLS-1$
							String msg = String.format(
									Messages.getString("GenerateActivityDiagrams.ActivityMsg"), activity.getName()); //$NON-NLS-1$
							MessageDialog.openWarning(Display.getDefault().getActiveShell(), title, msg);
							Activator.log(msg, IStatus.WARNING);
						}
					});
				}
	}

	private void generate(OpaqueBehavior behavior, ModelManager mm, IProgressMonitor monitor)
			throws CoreException {
		// try to remove existing Markers from this model object
		// FIXME reference to facilities
		// EMFMarkerUtil.removeMarkerFor(behavior);

		try {
			EAnnotation eAnnotation = behavior.getContext().getEAnnotation(
					AnnotationConstants.REVERSE_PROCESS);
			if (eAnnotation != null) {
				IPath relativePath = new Path(mm.getProject().getLocation().toString()).append(eAnnotation
						.getDetails().get(AnnotationConstants.C_FILENAME));
				ITranslationUnit tu = CoreModelUtil.findTranslationUnitForLocation(relativePath, mm
						.getCProject());
				IASTTranslationUnit ast = tu.getAST();
				IASTDeclaration[] declarations = ast.getDeclarations();
				for (IASTDeclaration declaration : declarations) {
					if (declaration instanceof IASTFunctionDefinition) {
						IASTFunctionDefinition functionDefinition = (IASTFunctionDefinition)declaration;
						if (functionDefinition.getDeclarator().getName().toString()
								.equals(behavior.getName())) {
							Activity activity = UMLActivityBuilder.build(functionDefinition);
							Behavior previousActivity = behavior.getOwnedBehavior(activity.getName(), false,
									UMLPackage.Literals.ACTIVITY, false);
							if (previousActivity != null) {
								behavior.getOwnedBehaviors().remove(previousActivity);
							}
							behavior.getOwnedBehaviors().add(activity);

							// FIXME MIGRATION reference to modeler
							// Diagram diagram = createDiagram(activity,
							// previousActivity, mm.getDiagramsModel(),
							// UML_ACTIVITYDIAGRAM_ID, activity.getName());
							// importObjects(diagram, monitor);
							//
							// // apply custom styles specific to this module
							// customizeDiagram(diagram);
							//
							// // create subdiagram of type
							// StructuredActivityNode
							// createStructuredActivityNodeDiagram(diagram,
							// monitor);
						}
					}
				}
			}
		} catch (CModelException e) {
			Activator.log(e);
		} catch (CoreException e) {
			Activator.log(e);
		}
		// FIXME MIGRATION reference to modeler
		// catch (IOException e)
		// {
		// Activator.log(e);
		// }

	}

	private void generate(ModelManager manager, IProgressMonitor monitor) throws CoreException {
		for (Iterator<EObject> it = manager.getSourcePackage().eAllContents(); it.hasNext();) {
			EObject current = it.next();
			if (current instanceof OpaqueBehavior) {
				generate((OpaqueBehavior)current, manager, monitor);
			}
		}
	}

	// FIXME MIGRATION reference to modeler
	// private void importObjects(final Diagram diagram, final IProgressMonitor
	// monitor)
	// {
	// Display.getDefault().syncExec(new Runnable()
	// {
	// /**
	// * @see java.lang.Runnable#run()
	// */
	// public void run()
	// {
	// syncImportObjects(diagram, monitor);
	// }
	// });
	// }
	//
	// private void syncImportObjects(final Diagram diagram, final
	// IProgressMonitor monitor)
	// {
	// try
	// {
	// Modeler modeler = Utils.getCurrentModeler();
	// modeler.setActiveDiagram(diagram);
	//
	// final Importer importer = new Importer(modeler,
	// Utils.getElement(diagram).eContents());
	// importer.setCtrlKeyDown(true);
	// GraphicalViewer viewer = (GraphicalViewer)
	// modeler.getAdapter(GraphicalViewer.class);
	// GraphicalEditPart target = (GraphicalEditPart)
	// viewer.getEditPartRegistry().get(diagram);
	// importer.setTargetEditPart(target);
	// Dimension insets = new Dimension(10, 10);
	// target.getContentPane().translateToAbsolute(insets);
	// importer.setLocation(target.getContentPane().getBounds().getTopLeft().translate(insets));
	// importer.setCtrlKeyDown(true);
	// importer.run(monitor);
	// }
	// catch (BoundsFormatException bfe)
	// {
	// Activator.log("BoundsFormatException in diagram : " + diagram.getName(),
	// IStatus.WARNING, bfe);
	// }
	// catch (InterruptedException ie)
	// {
	// Activator.log("InterruptedException in diagram : " + diagram.getName(),
	// IStatus.WARNING, ie);
	// }
	// }
	//
	// private Diagram createDiagram(EObject root, EObject previousRoot,
	// Diagrams parentDiagrams, String diagramId, String name) throws
	// IOException
	// {
	// // retrieve the Diagrams and the DiagramInterchange factory singletons
	// DiagramsFactory factory = DiagramsFactory.eINSTANCE;
	// DiagramInterchangeFactory diFactory =
	// DiagramInterchangeFactory.eINSTANCE;
	//
	// // create the EObject of the diagram model
	// Diagram newDiag = diFactory.createDiagram();
	// Diagrams diagrams = factory.createDiagrams();
	// EMFSemanticModelBridge emfSemanticModelBridge =
	// diFactory.createEMFSemanticModelBridge();
	//
	// // set the properties of the diagrams model
	// diagrams.setModel(root);
	// diagrams.getDiagrams().add(newDiag);
	//
	// // remove previous diagrams if any
	// EList<Diagrams> toRemove = new BasicEList<Diagrams>();
	// for (Iterator<Diagrams> it = parentDiagrams.getSubdiagrams().iterator();
	// it.hasNext();)
	// {
	// Diagrams diagram = it.next();
	// final EObject nextModel = diagram.getModel();
	// if (previousRoot == nextModel)
	// {
	// toRemove.add(diagram);
	// }
	// }
	// parentDiagrams.getSubdiagrams().removeAll(toRemove);
	//
	// // add the new one and set the active diagram.
	// parentDiagrams.getSubdiagrams().add(diagrams);
	// parentDiagrams.setActiveDiagram(newDiag);
	//
	// // set the properties of the Diagram
	// newDiag.setSize(new Dimension(100, 100));
	// newDiag.setViewport(new Point(0, 0));
	// newDiag.setPosition(new Point(0, 0));
	// newDiag.setName(name);
	// newDiag.setSemanticModel(emfSemanticModelBridge);
	//
	// // set the properties of the SemanticModelBridge
	// emfSemanticModelBridge.setElement(root);
	// emfSemanticModelBridge.setPresentation(diagramId);
	//
	// return newDiag;
	// }
	//
	// /**
	// * Creates sub-diagrams of type <i>Structured Activity Node</i> when a
	// {@link StructuredActivityNode} model object
	// * is found as child of the current diagram.
	// *
	// * @param diagram The upper diagram
	// * @throws IOException
	// */
	// private void createStructuredActivityNodeDiagram(Diagram diagram,
	// IProgressMonitor monitor) throws IOException
	// {
	// for (GraphNode node : EcoreUtil.<GraphNode>
	// getObjectsByType(diagram.getContained(),
	// DiagramInterchangePackage.eINSTANCE.getGraphNode()))
	// {
	// EObject semanticElt = Utils.getElement(node);
	// if (semanticElt instanceof StructuredActivityNode)
	// {
	// StructuredActivityNode structuredActivity = (StructuredActivityNode)
	// semanticElt;
	// Diagrams diagrams = (Diagrams) diagram.eContainer();
	// Diagram newDiagram = createDiagram(structuredActivity,
	// structuredActivity.getOwner(), diagrams, UML_STRUCTUREDACTIVITYNODE_ID,
	// structuredActivity.getName());
	// importObjects(newDiagram, monitor);
	// customizeDiagram(newDiagram);
	// createStructuredActivityNodeDiagram(newDiagram, monitor);
	// }
	// }
	// }
	//
	// private void customizeDiagram(Diagram diagram)
	// {
	// for (GraphEdge edge : EcoreUtil.<GraphEdge>
	// getObjectsByType(diagram.getContained(),
	// DiagramInterchangePackage.eINSTANCE.getGraphEdge()))
	// {
	// EObject semanticElt = Utils.getElement(edge);
	// if (semanticElt instanceof ControlFlow)
	// {
	// ControlFlow flow = (ControlFlow) semanticElt;
	// if (flow.getGuard() instanceof OpaqueExpression)
	// {
	// OpaqueExpression condition = ((OpaqueExpression) flow.getGuard());
	// if (condition.getLanguages().contains(BundleConstants.C_LANGUAGE))
	// {
	// int rang = condition.getLanguages().indexOf(BundleConstants.C_LANGUAGE);
	// if (rang > -1)
	// {
	// /* else clause on transition */
	//                            if (condition.getBodies().get(rang).equals("[else]")) //$NON-NLS-1$
	// {
	// /* else option is set to light grey */
	// Property property = DiagramInterchangeFactory.eINSTANCE.createProperty();
	//                                property.setKey("foregroundColor"); //$NON-NLS-1$
	//                                property.setValue("192,191,193"); //$NON-NLS-1$
	// edge.getProperty().add(property);
	//
	// /* then we graphically remove the else clause */
	// hideGuardClause(edge);
	// }
	// }
	//
	// }
	// }
	// if (flow.getGuard() instanceof LiteralBoolean)
	// {
	// LiteralBoolean condition = ((LiteralBoolean) flow.getGuard());
	// if (condition.isValue())
	// {
	// hideGuardClause(edge);
	// }
	// }
	// }
	// }
	// }
	//
	// private void hideGuardClause(GraphEdge edge)
	// {
	// for (EdgeObjectUV aProperty : EcoreUtil.<EdgeObjectUV>
	// getObjectsByType(edge.getContained(),
	// DiagramInterchangePackage.eINSTANCE.getEdgeObjectUV()))
	// {
	//            if (aProperty.getId().equals("guardEdgeObject")) //$NON-NLS-1$
	// {
	// aProperty.setVisible(false);
	// }
	// }
	// }

}
