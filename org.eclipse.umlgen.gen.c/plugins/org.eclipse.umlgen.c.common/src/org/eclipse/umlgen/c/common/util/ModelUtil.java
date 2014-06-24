/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastien Gabel (CS-SI) - initial API and implementation
 *     Cedric Notot (Obeo) - evolutions to cut off from diagram part
 *******************************************************************************/
package org.eclipse.umlgen.c.common.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.common.util.UML2Util.EObjectMatcher;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.c.common.interactions.SynchronizersManager;
import org.eclipse.umlgen.c.common.interactions.extension.IDiagramSynchronizer;
import org.eclipse.umlgen.c.common.interactions.extension.IModelSynchronizer;

public final class ModelUtil {
	public static Set<String> primitiveTypes = new HashSet<String>(
			Arrays.asList(new String[] {
					"void", "char", "unsigned char", "signed char", "short", "signed short", "unsigned short", "int", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
					"unsigned", "unsigned int", "long", "unsigned long", "signed long", "long long", "unsigned long long", "signed long long", "float", "double", "long double", BundleConstants.MACRO_TYPE })); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$

	public enum EventType {
		ADD, REMOVE, MODIFY
	};

	private ModelUtil() {
		// avoid to be instantiated
	}

	/**
	 * Most of time, when the current type received is null, we need to deduce an anonymous type. This method
	 * helps in this task.
	 *
	 * @param unitName
	 *            The name of the current unit name
	 * @param currentTypeName
	 *            The name of the current type. The type is returned as it, if different from null.
	 * @param node
	 *            An ASTNode to give a symbolic location.
	 * @return The computed name or the current type name if different of null.
	 */
	public static String computeAnonymousTypeName(String unitName, String currentTypeName, IASTNode node) {
		if ("".equals(currentTypeName)) //$NON-NLS-1$
		{
			int enumOffset = node.getFileLocation().getNodeOffset();
			StringBuilder sb = new StringBuilder("Anonymous__"); //$NON-NLS-1$
			sb.append(unitName);
			sb.append("_"); //$NON-NLS-1$
			return sb.append(String.valueOf(enumOffset)).toString();
		}
		return currentTypeName;
	}

	/**
	 * Finds a {@link Classifier} inside a given {@link Package}.<br>
	 * Note that this class can be stored in nested packages that why we used eAllContents() to fetch the
	 * model.
	 *
	 * @param packageRef
	 *            The package in which the seek must be performed.
	 * @param name
	 *            The classifier name
	 * @return The classifier if found or null
	 */
	public static Classifier findClassifierInPackage(Package packageRef, final String name) {
		return (Classifier)UML2Util.findEObject(packageRef.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof Classifier) {
					return name.equals(((Classifier)eObject).getName());
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a {@link Class} inside a given {@link Package}.<br>
	 * Note that this class can be stored in nested packages that why we used eAllContents() to fetch the
	 * model.
	 *
	 * @param packageRef
	 *            The package in which the seek must be performed.
	 * @param name
	 *            The classifier name
	 * @return The classifier if found or null
	 */
	public static Class findClassInPackage(Package packageRef, final String name) {
		return (Class)UML2Util.findEObject(packageRef.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof Class) {
					return name.equals(((Classifier)eObject).getName());
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a {@link Class} inside a given {@link Package}.<br>
	 * Note that this class can be stored in nested packages that why we used eAllContents() to fetch the
	 * model.
	 *
	 * @param packageRef
	 *            The package in which the seek must be performed.
	 * @param name
	 *            The classifier name
	 * @return The classifier if found or null
	 */
	private static Type findTypeInClassifier(Classifier classifier, final String typeName) {
		return (Type)UML2Util.findEObject(classifier.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof Type) {
					Type candidate = (Type)eObject;
					return typeName.equals(candidate.getName());
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a given {@link DataType} into a given {@link Classifier}.
	 *
	 * @param classifier
	 *            : the classifier that owns the local DataType
	 * @param dataTypeName
	 *            : the name of the DataType we want to find
	 * @return the UML DataType Class found
	 */
	public static DataType findDataTypeInClassifier(Classifier classifier, final String dataTypeName) {
		return (DataType)UML2Util.findEObject(classifier.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof DataType) {
					DataType candidate = (DataType)eObject;
					return dataTypeName.equals(candidate.getName())
							&& !candidate.getOwnedAttributes().isEmpty();
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a given {@link DataType} typedef redefinition into a given {@link Classifier}.
	 *
	 * @param classifier
	 *            : the classifier that owns the local Enumeration
	 * @param redefinedDataType
	 *            : the name of the redefined data type we want to find
	 * @return the UML Enumeration found or null
	 */
	public static DataType findDataTypeRedefinitionInClassifier(Classifier classifier,
			final String redefinedDataType) {
		return (DataType)UML2Util.findEObject(classifier.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof DataType) {
					DataType candidate = (DataType)eObject;
					return redefinedDataType.equals(candidate.getName())
							&& candidate.getOwnedAttributes().isEmpty();
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a given {@link Enumeration} into a given {@link Classifier} .
	 *
	 * @param classifier
	 *            : the classifier that owns the local Enumeration
	 * @param enumName
	 *            : the name of the Enumeration we want to find
	 * @return the UML Enumeration found or null
	 */
	public static Enumeration findEnumerationInClassifier(Classifier classifier, final String enumName) {
		return (Enumeration)UML2Util.findEObject(classifier.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof Enumeration) {
					Enumeration candidate = (Enumeration)eObject;
					return enumName.equals(candidate.getName()) && !candidate.getOwnedLiterals().isEmpty();
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a given {@link Enumeration} typedef redefinition into a given {@link Classifier}.
	 *
	 * @param classifier
	 *            : the classifier that owns the local Enumeration
	 * @param redefinedEnumName
	 *            : the name of the redefined Enumeration we want to find
	 * @return the redefined Enumeration found or null
	 */
	public static Enumeration findEnumerationRedefinitionInClassifier(Classifier classifier,
			final String redefinedEnumName) {
		return (Enumeration)UML2Util.findEObject(classifier.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof Enumeration) {
					Enumeration candidate = (Enumeration)eObject;
					return redefinedEnumName.equals(candidate.getName())
							&& candidate.getOwnedLiterals().isEmpty();
				}
				return false;
			}
		});
	}

	/**
	 * Finds by name a given {@link Usage} going from a {@link Classifier} to another.
	 *
	 * @param classifier
	 *            : the classifier from which the usage starts.
	 * @param distantClassifierName
	 *            : the name of the distant classifier (i.e the name of the included library)
	 * @return the Usage found or null
	 */
	public static Usage findUsage(final Classifier classifier, final String distantClassifierName) {
		return (Usage)UML2Util.findEObject(classifier.getClientDependencies(), new EObjectMatcher() {

			public boolean matches(EObject eObject) {
				if (eObject instanceof Usage) {
					Usage usage = (Usage)eObject;
					return usage.getClient(classifier.getName()) != null
							&& usage.getSupplier(distantClassifierName) != null;
				}
				return false;
			}
		});
	}

	/**
	 * Gets a data type
	 *
	 * @param manager
	 *            The model manager
	 * @param myClass
	 *            The classifier for which the type is expected
	 * @param typeName
	 *            The type name
	 * @return The type if found or create a new one inside type pck if not found at any time.
	 */
	public static Type getType(ModelManager manager, Classifier myClass, String typeName) {
		// type is first serached inside the type package
		Type myType = manager.findDataTypeInTypesPck(typeName);
		if (myType == null) {
			// then type is search inside the classifier
			myType = ModelUtil.findTypeInClassifier(myClass, typeName);
		}
		if (myType == null) {
			// type is search elsewhere in the model or the type is created
			// inside Type pkg if not found
			return manager.getDataType(typeName);
		}
		return myType;
	}

	public static Operation getReferredOperation(Classifier classifier, String operationName,
			EList<Type> types, EList<Element> visited) {
		Operation operation = classifier.getOperation(operationName, null, types, true);
		if (operation != null) {
			return operation;
		}

		// mark this classifier as visited
		visited.add(classifier);

		// otherwise, depedencies must be explored until we find what you're
		// looking for.
		for (Classifier supplier : getSupplierClassifiers(classifier)) {
			if (!visited.contains(supplier)) {
				// explore recursively
				operation = getReferredOperation(supplier, operationName, types, visited);
				if (operation != null && operation.getMethod(operationName) == null) {
					return operation;
				}
			}
		}
		return null;
	}

	/**
	 * In charge of exploring and find a referred behavior inside a model.
	 *
	 * @param classifier
	 *            the classifier representing the starting point of our search.
	 * @param behaviorName
	 *            The name of the searched behavior
	 * @param visited
	 *            List of explorer dependencies
	 * @return The opaque behavior that can be in another module, <code>null</code> if not found.
	 */
	public static OpaqueBehavior getReferredBehavior(Classifier classifier, String behaviorName,
			EList<Classifier> visited) {
		OpaqueBehavior behavior = null;
		// if the classifier is a class, we can hope find the corresponding
		// behavior inside.
		if (classifier instanceof BehavioredClassifier) {
			behavior = (OpaqueBehavior)((BehavioredClassifier)classifier).getOwnedBehavior(behaviorName,
					false, UMLPackage.Literals.OPAQUE_BEHAVIOR, false);
			if (behavior != null) {
				return behavior;
			}
		}

		// mark this classifier as visited
		visited.add(classifier);

		// otherwise, depedencies must be explored until we find what you're
		// looking for.
		for (Classifier client : getClientClassifiers(classifier)) {
			if (!visited.contains(client)) {
				// explore recursively
				behavior = getReferredBehavior(client, behaviorName, visited);
				if (behavior != null && behavior.getSpecification() == null) {
					return behavior;
				}
			}
		}
		return null;
	}

	/**
	 * Gets a collection of clients classifiers whose the classifier given in parameter is the supplier.<br>
	 * Example : <br>
	 * <ul>
	 * <li>B includes A</li>
	 * <li>C includes A</li>
	 * <li>A includes itself</li>
	 * </ul>
	 * will return 'A', 'B' and 'C'.
	 *
	 * @param classifier
	 *            A given classifier
	 * @return a collection of classifiers using the given classifier given in parameter.
	 */
	private static Collection<Classifier> getClientClassifiers(Classifier classifier) {
		EList<NamedElement> clients = new BasicEList<NamedElement>();
		for (Setting setting : UML2Util.getInverseReferences(classifier)) {
			if (setting.getEObject() instanceof Usage
					&& setting.getEStructuralFeature() == UMLPackage.Literals.DEPENDENCY__SUPPLIER) {
				Usage usage = (Usage)setting.getEObject();
				clients.addAll(usage.getClients());
			}
		}
		return EcoreUtil.<Classifier> getObjectsByType(clients, UMLPackage.Literals.CLASSIFIER);
	}

	/**
	 * Gets a collection of supplier classifiers whose the classifier given in parameter is the client.<br>
	 * Example : <br>
	 * <ul>
	 * <li>A includes B</li>
	 * <li>A includes C</li>
	 * <li>A includes itself</li>
	 * </ul>
	 * will return 'A', 'B' and 'C'.
	 *
	 * @param classifier
	 *            A given classifier
	 * @return a collection of classifiers using the given classifier given in parameter.
	 */
	private static Collection<Classifier> getSupplierClassifiers(Classifier classifier) {
		EList<NamedElement> suppliers = new BasicEList<NamedElement>();
		for (Dependency dependency : classifier.getClientDependencies()) {
			suppliers.addAll(dependency.getSuppliers());
		}
		return EcoreUtil.<Classifier> getObjectsByType(suppliers, UMLPackage.Literals.CLASSIFIER);
	}

	/**
	 * Sets the visibility of this element accordingly to the event received and the kind of unit processed.
	 * This method can be used in most cases.
	 *
	 * @param element
	 *            An UML element for which the right visiblity must be set
	 * @param translationUnit
	 *            The translation unit
	 * @param type
	 *            The type of the received event
	 */
	public static void setVisibility(NamedElement element, ITranslationUnit tu, EventType type) {
		if (element != null) {
			if (type == EventType.ADD) {
				element.setVisibility(tu.isHeaderUnit() ? VisibilityKind.PUBLIC_LITERAL
						: VisibilityKind.PRIVATE_LITERAL);
			} else if (type == EventType.REMOVE) {
				element.setVisibility(tu.isHeaderUnit() ? VisibilityKind.PRIVATE_LITERAL
						: VisibilityKind.PUBLIC_LITERAL);
			}
		}
	}

	/**
	 * Sets the right visibility for a given classifier. For that, we look for the reverse annotation and set
	 * the visibility consequently.
	 *
	 * @param classifier
	 *            the classifier for which visibility must be set
	 */
	public static void setVisibility(Classifier classifier) {
		if (AnnotationUtil.isSameTypeBetweenCandHUnits(classifier)) {
			classifier.setVisibility(VisibilityKind.PRIVATE_LITERAL);
		} else {
			classifier.setVisibility(VisibilityKind.PUBLIC_LITERAL);
		}

	}

	/**
	 * Test if a given UML model element can be removed from the model. This test is performed regarding the
	 * visibility of the element before being updated.
	 *
	 * @param element
	 *            The UML element to test
	 * @return <code>true</code> if the element can be removed from the model, <code>false</code> otherwise.
	 */
	public static boolean isRemovable(NamedElement element) {
		return !element.getVisibility().equals(VisibilityKind.PUBLIC);
	}

	@SuppressWarnings("unchecked")
	public static void redefineType(Element existing, Element newElement) {
		ECrossReferenceAdapter crossAdpater = ECrossReferenceAdapter.getCrossReferenceAdapter(existing);
		if (crossAdpater != null) {
			for (Setting aSetting : crossAdpater.getInverseReferences(existing, true)) {
				EObject eObject = aSetting.getEObject();
				if (aSetting.getEStructuralFeature() instanceof EReference) {
					EReference ref = (EReference)aSetting.getEStructuralFeature();
					if (!ref.isContainment() && !ref.isUnsettable() && ref.isChangeable()) {
						if (ref.isMany() && !ref.isDerived()) {
							EList<EObject> candidates = new BasicEList<EObject>((EList<EObject>)eObject.eGet(
									aSetting.getEStructuralFeature(), true));
							if (candidates.contains(existing)) {
								candidates.set(candidates.indexOf(existing), newElement);
								eObject.eSet(ref, candidates);
							}
						} else if (eObject.eGet(aSetting.getEStructuralFeature(), true).equals(existing)) {
							eObject.eSet(aSetting.getEStructuralFeature(), newElement);
						}
					}
				} else if (aSetting.getEStructuralFeature() instanceof EAttribute) {
					EAttribute ref = (EAttribute)aSetting.getEStructuralFeature();
					if (eObject.eGet(ref, true).equals(existing)) {
						if (!ref.isUnsettable() && ref.isChangeable()) {
							eObject.eSet(aSetting.getEStructuralFeature(), newElement);
						}
					}
				}
			}
		}
	}

	/**
	 * Indicates if a given {@link Element} is still referenced by another model element through a structural
	 * feature.
	 *
	 * @param existing
	 *            A model element of the model
	 * @return <code>true</code> if the element is not referenced anymore, <code>false</code> if at least one
	 *         reference have been found.
	 */
	public static boolean isNotReferencedAnymore(Element existing) {
		for (Setting aSetting : UML2Util.getInverseReferences(existing)) {
			if (aSetting.getEStructuralFeature() == UMLPackage.Literals.TYPED_ELEMENT__TYPE) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Destroy a given classifier and all redefined classifiers when their are not used anymore.
	 *
	 * @param classifier
	 *            A classifier
	 */
	public static void destroy(Classifier classifier) {
		EList<Classifier> toDestroy = new BasicEList<Classifier>();
		destroy(classifier, toDestroy);
		for (Classifier c : toDestroy) {
			c.destroy();
		}
	}

	/**
	 * Destroy a given classifier and all redefined classifiers when their are not used anymore.
	 *
	 * @param classifier
	 *            A classifier
	 * @param selected
	 *            The selected elements that should be destroyed
	 */
	private static void destroy(Classifier classifier, EList<Classifier> selected) {
		for (Classifier c : classifier.getRedefinedClassifiers()) {
			if (isNotReferencedAnymore(c)) {
				destroy(c, selected);
			}
		}
		selected.add(classifier);
	}

	/**
	 * When a C or H file is deleted from the workspace, all model objects with a visibility set to private
	 * must be deleted from the class
	 *
	 * @param startingPoint
	 * @param visibility
	 * @param manager
	 */
	public static void deleteAllVisibleObjects(Classifier startingPoint, VisibilityKind visibility,
			ModelManager manager) {
		EList<Element> elementsToDestroy = new BasicEList<Element>();

		IModelSynchronizer synchronizer = SynchronizersManager.getSynchronizer();
		if (synchronizer instanceof IDiagramSynchronizer) {
			// retrieve first all the associations the element is the source
			for (DirectedRelationship link : startingPoint.getSourceDirectedRelationships()) {
				if (((NamedElement)link).getVisibility().equals(visibility)) {
					for (Element target : link.getTargets()) {
						if (target.getTargetDirectedRelationships().size() == 1) {
							if (target.getTargetDirectedRelationships().size() > 0) {
								DirectedRelationship relation = target.getTargetDirectedRelationships()
										.get(0);
								if (relation.getSources().size() > 0) {
									if (relation.getSources().get(0).equals(startingPoint)) {
										((IDiagramSynchronizer)synchronizer).removeRepresentation(target,
												manager);
										if (!startingPoint.equals(target)) {
											// Avoid Circular reference
											elementsToDestroy.add(target);
										}
									}
								}
							}
						}
					}
					((IDiagramSynchronizer)synchronizer).removeRepresentation(link, manager);
					link.destroy();
				}
			}
		}

		if (startingPoint instanceof Class) {
			for (Behavior function : ((Class)startingPoint).getOwnedBehaviors()) {
				if (function instanceof OpaqueBehavior) {
					for (Parameter parameter : function.getOwnedParameters()) {
						elementsToDestroy.add(parameter);
					}
				}
			}
		}
		if (startingPoint instanceof Class || startingPoint instanceof Interface) {
			for (Property property : startingPoint.getAttributes()) {
				elementsToDestroy.add(property);
			}
			for (Operation operation : startingPoint.getOperations()) {
				for (Parameter parameter : operation.getOwnedParameters()) {
					elementsToDestroy.add(parameter);
				}
			}
		}
		int sizeList = elementsToDestroy.size();

		for (int i = sizeList - 1; i > -1; i--) {
			elementsToDestroy.get(i).destroy();
		}

		Package typesPackage = manager.getTypePackage();
		EList<Element> types = typesPackage.getOwnedElements();
		int size = types.size();
		for (int i = size - 1; i > -1; i--) {
			if (types.get(i) instanceof Type) {
				if (isNotReferencedAnymore(types.get(i))) {
					types.get(i).destroy();
				}
			}
		}

		if (synchronizer instanceof IDiagramSynchronizer) {
			for (NamedElement elt : EcoreUtil.<NamedElement> getObjectsByType(startingPoint.eContents(),
					UMLPackage.eINSTANCE.getNamedElement())) {
				if (elt.getVisibility().equals(visibility)) {
					// remove graphically the element...
					((IDiagramSynchronizer)synchronizer).removeRepresentation(elt, manager);

					// ...then the model part.
					elt.destroy();
				}
			}
		}
	}

	public static Interface findInterfaceFromPackage(Package packageRef, final String classifierName) {
		return (Interface)UML2Util.findEObject(packageRef.eAllContents(), new EObjectMatcher() {
			public boolean matches(EObject eObject) {
				if (eObject instanceof Interface) {
					return classifierName.equals(((Classifier)eObject).getName());
				}
				return false;
			}
		});
	}

	public static Classifier findMatchingClassifier(ModelManager manager, ITranslationUnit translationUnit,
			String classifierName) {
		Package srcPackage = manager.getSourcePackage();
		Classifier matchingClassifier = findClassifierInPackage(srcPackage, classifierName);
		// if the C unit already exists, we don't need to create an intermediate
		// interface
		if (translationUnit.isHeaderUnit() && matchingClassifier == null) {
			// create or get an interface
			matchingClassifier = (Interface)srcPackage.getPackagedElement(classifierName, false,
					UMLPackage.Literals.INTERFACE, true);
		} else if (translationUnit.isSourceUnit()
				&& (matchingClassifier == null || matchingClassifier instanceof Interface)) {
			// create or get a class
			matchingClassifier = (Class)srcPackage.getPackagedElement(classifierName, false,
					UMLPackage.Literals.CLASS, true);
		}
		return matchingClassifier;
	}
}
