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
package org.eclipse.umlgen.dsl.eth;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.umlgen.dsl.asl.AslPackage;
import org.eclipse.umlgen.dsl.asl.configurations.ConfigurationsPackage;

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
 * @see org.eclipse.umlgen.dsl.eth.EthFactory
 * @model kind="package"
 * @generated
 */
public interface EthPackage extends EPackage {
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
	String eNAME = "eth";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/umlgen/dsl/eth";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eth";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EthPackage eINSTANCE = org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.eth.impl.EthernetConfRepositoryImpl <em>Ethernet Conf Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.eth.impl.EthernetConfRepositoryImpl
	 * @see org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl#getEthernetConfRepository()
	 * @generated
	 */
	int ETHERNET_CONF_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETHERNET_CONF_REPOSITORY__CONFIGURATIONS = AslPackage.CONFIGURATION_REPOSITORY__CONFIGURATIONS;

	/**
	 * The number of structural features of the '<em>Ethernet Conf Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETHERNET_CONF_REPOSITORY_FEATURE_COUNT = AslPackage.CONFIGURATION_REPOSITORY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.eth.impl.EthernetConfImpl <em>Ethernet Conf</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.eth.impl.EthernetConfImpl
	 * @see org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl#getEthernetConf()
	 * @generated
	 */
	int ETHERNET_CONF = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETHERNET_CONF__NAME = ConfigurationsPackage.PROTOCOL_COMMUNICATION__NAME;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETHERNET_CONF__PARAMETERS = ConfigurationsPackage.PROTOCOL_COMMUNICATION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Connectors</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETHERNET_CONF__CONNECTORS = ConfigurationsPackage.PROTOCOL_COMMUNICATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Ethernet Conf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ETHERNET_CONF_FEATURE_COUNT = ConfigurationsPackage.PROTOCOL_COMMUNICATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.eth.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.eth.impl.ContainerImpl
	 * @see org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 2;

	/**
	 * The feature id for the '<em><b>Ip Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__IP_ADDRESS = AslPackage.PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__PORT_NUMBER = AslPackage.PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Components</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__COMPONENTS = AslPackage.PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__NAME = AslPackage.PARAMETER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = AslPackage.PARAMETER_FEATURE_COUNT + 4;

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.eth.EthernetConfRepository <em>Ethernet Conf Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ethernet Conf Repository</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.EthernetConfRepository
	 * @generated
	 */
	EClass getEthernetConfRepository();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.eth.EthernetConf <em>Ethernet Conf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ethernet Conf</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.EthernetConf
	 * @generated
	 */
	EClass getEthernetConf();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.umlgen.dsl.eth.EthernetConf#getConnectors <em>Connectors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connectors</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.EthernetConf#getConnectors()
	 * @see #getEthernetConf()
	 * @generated
	 */
	EReference getEthernetConf_Connectors();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.eth.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.eth.Container#getIpAddress <em>Ip Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ip Address</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.Container#getIpAddress()
	 * @see #getContainer()
	 * @generated
	 */
	EAttribute getContainer_IpAddress();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.eth.Container#getPortNumber <em>Port Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port Number</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.Container#getPortNumber()
	 * @see #getContainer()
	 * @generated
	 */
	EAttribute getContainer_PortNumber();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.umlgen.dsl.eth.Container#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Components</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.Container#getComponents()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Components();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.eth.Container#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.umlgen.dsl.eth.Container#getName()
	 * @see #getContainer()
	 * @generated
	 */
	EAttribute getContainer_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EthFactory getEthFactory();

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
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.eth.impl.EthernetConfRepositoryImpl <em>Ethernet Conf Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.eth.impl.EthernetConfRepositoryImpl
		 * @see org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl#getEthernetConfRepository()
		 * @generated
		 */
		EClass ETHERNET_CONF_REPOSITORY = eINSTANCE.getEthernetConfRepository();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.eth.impl.EthernetConfImpl <em>Ethernet Conf</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.eth.impl.EthernetConfImpl
		 * @see org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl#getEthernetConf()
		 * @generated
		 */
		EClass ETHERNET_CONF = eINSTANCE.getEthernetConf();

		/**
		 * The meta object literal for the '<em><b>Connectors</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ETHERNET_CONF__CONNECTORS = eINSTANCE.getEthernetConf_Connectors();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.eth.impl.ContainerImpl <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.eth.impl.ContainerImpl
		 * @see org.eclipse.umlgen.dsl.eth.impl.EthPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Ip Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTAINER__IP_ADDRESS = eINSTANCE.getContainer_IpAddress();

		/**
		 * The meta object literal for the '<em><b>Port Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTAINER__PORT_NUMBER = eINSTANCE.getContainer_PortNumber();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__COMPONENTS = eINSTANCE.getContainer_Components();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTAINER__NAME = eINSTANCE.getContainer_Name();

	}

} //EthPackage
