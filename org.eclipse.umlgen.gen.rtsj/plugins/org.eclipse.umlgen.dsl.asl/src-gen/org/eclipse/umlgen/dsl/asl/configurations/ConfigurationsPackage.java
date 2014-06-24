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
package org.eclipse.umlgen.dsl.asl.configurations;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.umlgen.dsl.asl.AslPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.umlgen.dsl.asl.configurations.ConfigurationsFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigurationsPackage extends EPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) 2012, 2014 CNES and others.\r\nAll rights reserved. This program and the accompanying materials \r\nare made available under the terms of the Eclipse Public License v1.0 \r\nwhich accompanies this distribution, and is available at \r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n  \r\nContributors:\r\n     Cedric Notot (Obeo) - initial API and implementation";

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "configurations";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/umlgen/dsl/conf";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "conf";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigurationsPackage eINSTANCE = org.eclipse.umlgen.dsl.asl.configurations.impl.ConfigurationsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.configurations.impl.ProtocolCommunicationImpl <em>Protocol Communication</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.configurations.impl.ProtocolCommunicationImpl
	 * @see org.eclipse.umlgen.dsl.asl.configurations.impl.ConfigurationsPackageImpl#getProtocolCommunication()
	 * @generated
	 */
	int PROTOCOL_COMMUNICATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_COMMUNICATION__NAME = AslPackage.CONFIGURATION__NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_COMMUNICATION__PARAMETERS = AslPackage.CONFIGURATION__PARAMETERS;

	/**
	 * The number of structural features of the '<em>Protocol Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_COMMUNICATION_FEATURE_COUNT = AslPackage.CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.configurations.ProtocolCommunication <em>Protocol Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol Communication</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.configurations.ProtocolCommunication
	 * @generated
	 */
	EClass getProtocolCommunication();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigurationsFactory getConfigurationsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.configurations.impl.ProtocolCommunicationImpl <em>Protocol Communication</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.configurations.impl.ProtocolCommunicationImpl
		 * @see org.eclipse.umlgen.dsl.asl.configurations.impl.ConfigurationsPackageImpl#getProtocolCommunication()
		 * @generated
		 */
		EClass PROTOCOL_COMMUNICATION = eINSTANCE.getProtocolCommunication();

	}

} //ConfigurationsPackage
