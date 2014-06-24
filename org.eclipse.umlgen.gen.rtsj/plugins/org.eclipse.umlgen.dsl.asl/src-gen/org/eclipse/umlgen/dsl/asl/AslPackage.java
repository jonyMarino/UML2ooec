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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.umlgen.dsl.asl.AslFactory
 * @model kind="package"
 * @generated
 */
public interface AslPackage extends EPackage {
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
	String eNAME = "asl";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/umlgen/dsl/asl";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "asl";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AslPackage eINSTANCE = org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.impl.LibraryImpl <em>Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.impl.LibraryImpl
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getLibrary()
	 * @generated
	 */
	int LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Architectural Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__ARCHITECTURAL_STYLES = 1;

	/**
	 * The feature id for the '<em><b>Configuration Repositories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY__CONFIGURATION_REPOSITORIES = 2;

	/**
	 * The number of structural features of the '<em>Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.impl.ArchitecturalStyleImpl <em>Architectural Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.impl.ArchitecturalStyleImpl
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getArchitecturalStyle()
	 * @generated
	 */
	int ARCHITECTURAL_STYLE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_STYLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_STYLE__CONFIGURATIONS = 1;

	/**
	 * The number of structural features of the '<em>Architectural Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHITECTURAL_STYLE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.Decoration <em>Decoration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.Decoration
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getDecoration()
	 * @generated
	 */
	int DECORATION = 6;

	/**
	 * The number of structural features of the '<em>Decoration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECORATION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.impl.ConfigurationImpl
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__NAME = DECORATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__PARAMETERS = DECORATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = DECORATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.impl.ParameterImpl
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 5;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = DECORATION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.impl.GenericParamImpl <em>Generic Param</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.impl.GenericParamImpl
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getGenericParam()
	 * @generated
	 */
	int GENERIC_PARAM = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PARAM__NAME = PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PARAM__VALUE = PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PARAM__REFERENCES = PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Generic Param</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_PARAM_FEATURE_COUNT = PARAMETER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.umlgen.dsl.asl.impl.ConfigurationRepositoryImpl <em>Configuration Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.umlgen.dsl.asl.impl.ConfigurationRepositoryImpl
	 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getConfigurationRepository()
	 * @generated
	 */
	int CONFIGURATION_REPOSITORY = 4;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_REPOSITORY__CONFIGURATIONS = 0;

	/**
	 * The number of structural features of the '<em>Configuration Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_REPOSITORY_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.Library <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Library
	 * @generated
	 */
	EClass getLibrary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.asl.Library#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Library#getName()
	 * @see #getLibrary()
	 * @generated
	 */
	EAttribute getLibrary_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.umlgen.dsl.asl.Library#getArchitecturalStyles <em>Architectural Styles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Architectural Styles</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Library#getArchitecturalStyles()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_ArchitecturalStyles();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.umlgen.dsl.asl.Library#getConfigurationRepositories <em>Configuration Repositories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration Repositories</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Library#getConfigurationRepositories()
	 * @see #getLibrary()
	 * @generated
	 */
	EReference getLibrary_ConfigurationRepositories();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.ArchitecturalStyle <em>Architectural Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Architectural Style</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.ArchitecturalStyle
	 * @generated
	 */
	EClass getArchitecturalStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.asl.ArchitecturalStyle#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.ArchitecturalStyle#getName()
	 * @see #getArchitecturalStyle()
	 * @generated
	 */
	EAttribute getArchitecturalStyle_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.umlgen.dsl.asl.ArchitecturalStyle#getConfigurations <em>Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Configurations</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.ArchitecturalStyle#getConfigurations()
	 * @see #getArchitecturalStyle()
	 * @generated
	 */
	EReference getArchitecturalStyle_Configurations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.asl.Configuration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Configuration#getName()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.umlgen.dsl.asl.Configuration#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Configuration#getParameters()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Parameters();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.GenericParam <em>Generic Param</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Param</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.GenericParam
	 * @generated
	 */
	EClass getGenericParam();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.asl.GenericParam#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.GenericParam#getName()
	 * @see #getGenericParam()
	 * @generated
	 */
	EAttribute getGenericParam_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.umlgen.dsl.asl.GenericParam#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.GenericParam#getValue()
	 * @see #getGenericParam()
	 * @generated
	 */
	EAttribute getGenericParam_Value();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.umlgen.dsl.asl.GenericParam#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>References</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.GenericParam#getReferences()
	 * @see #getGenericParam()
	 * @generated
	 */
	EReference getGenericParam_References();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.ConfigurationRepository <em>Configuration Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Repository</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.ConfigurationRepository
	 * @generated
	 */
	EClass getConfigurationRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.umlgen.dsl.asl.ConfigurationRepository#getConfigurations <em>Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configurations</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.ConfigurationRepository#getConfigurations()
	 * @see #getConfigurationRepository()
	 * @generated
	 */
	EReference getConfigurationRepository_Configurations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.umlgen.dsl.asl.Decoration <em>Decoration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decoration</em>'.
	 * @see org.eclipse.umlgen.dsl.asl.Decoration
	 * @generated
	 */
	EClass getDecoration();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AslFactory getAslFactory();

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
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.impl.LibraryImpl <em>Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.impl.LibraryImpl
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getLibrary()
		 * @generated
		 */
		EClass LIBRARY = eINSTANCE.getLibrary();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY__NAME = eINSTANCE.getLibrary_Name();

		/**
		 * The meta object literal for the '<em><b>Architectural Styles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__ARCHITECTURAL_STYLES = eINSTANCE.getLibrary_ArchitecturalStyles();

		/**
		 * The meta object literal for the '<em><b>Configuration Repositories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY__CONFIGURATION_REPOSITORIES = eINSTANCE.getLibrary_ConfigurationRepositories();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.impl.ArchitecturalStyleImpl <em>Architectural Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.impl.ArchitecturalStyleImpl
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getArchitecturalStyle()
		 * @generated
		 */
		EClass ARCHITECTURAL_STYLE = eINSTANCE.getArchitecturalStyle();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARCHITECTURAL_STYLE__NAME = eINSTANCE.getArchitecturalStyle_Name();

		/**
		 * The meta object literal for the '<em><b>Configurations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARCHITECTURAL_STYLE__CONFIGURATIONS = eINSTANCE.getArchitecturalStyle_Configurations();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.impl.ConfigurationImpl
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__NAME = eINSTANCE.getConfiguration_Name();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__PARAMETERS = eINSTANCE.getConfiguration_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.impl.GenericParamImpl <em>Generic Param</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.impl.GenericParamImpl
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getGenericParam()
		 * @generated
		 */
		EClass GENERIC_PARAM = eINSTANCE.getGenericParam();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_PARAM__NAME = eINSTANCE.getGenericParam_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_PARAM__VALUE = eINSTANCE.getGenericParam_Value();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_PARAM__REFERENCES = eINSTANCE.getGenericParam_References();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.impl.ConfigurationRepositoryImpl <em>Configuration Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.impl.ConfigurationRepositoryImpl
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getConfigurationRepository()
		 * @generated
		 */
		EClass CONFIGURATION_REPOSITORY = eINSTANCE.getConfigurationRepository();

		/**
		 * The meta object literal for the '<em><b>Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_REPOSITORY__CONFIGURATIONS = eINSTANCE.getConfigurationRepository_Configurations();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.impl.ParameterImpl
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '{@link org.eclipse.umlgen.dsl.asl.Decoration <em>Decoration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.umlgen.dsl.asl.Decoration
		 * @see org.eclipse.umlgen.dsl.asl.impl.AslPackageImpl#getDecoration()
		 * @generated
		 */
		EClass DECORATION = eINSTANCE.getDecoration();

	}

} //AslPackage
