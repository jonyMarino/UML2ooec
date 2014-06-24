/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Cedric Notot (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.dsl.asl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.umlgen.dsl.asl.Library#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.umlgen.dsl.asl.Library#getArchitecturalStyles <em>Architectural Styles</em>}</li>
 *   <li>{@link org.eclipse.umlgen.dsl.asl.Library#getConfigurationRepositories <em>Configuration Repositories</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.umlgen.dsl.asl.AslPackage#getLibrary()
 * @model
 * @generated
 */
public interface Library extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012, 2014 CNES and others.\r\nAll rights reserved. This program and the accompanying materials \r\nare made available under the terms of the Eclipse Public License v1.0 \r\nwhich accompanies this distribution, and is available at \r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n  \r\nContributors:\r\n     Cedric Notot (Obeo) - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.umlgen.dsl.asl.AslPackage#getLibrary_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.umlgen.dsl.asl.Library#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Architectural Styles</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.umlgen.dsl.asl.ArchitecturalStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Architectural Styles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architectural Styles</em>' containment reference list.
	 * @see org.eclipse.umlgen.dsl.asl.AslPackage#getLibrary_ArchitecturalStyles()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArchitecturalStyle> getArchitecturalStyles();

	/**
	 * Returns the value of the '<em><b>Configuration Repositories</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.umlgen.dsl.asl.ConfigurationRepository}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration Repositories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration Repositories</em>' containment reference list.
	 * @see org.eclipse.umlgen.dsl.asl.AslPackage#getLibrary_ConfigurationRepositories()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConfigurationRepository> getConfigurationRepositories();

} // Library
