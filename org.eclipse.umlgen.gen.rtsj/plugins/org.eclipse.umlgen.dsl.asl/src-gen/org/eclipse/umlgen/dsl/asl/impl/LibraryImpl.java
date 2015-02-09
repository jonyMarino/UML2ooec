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
package org.eclipse.umlgen.dsl.asl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.umlgen.dsl.asl.ArchitecturalStyle;
import org.eclipse.umlgen.dsl.asl.AslPackage;
import org.eclipse.umlgen.dsl.asl.ConfigurationRepository;
import org.eclipse.umlgen.dsl.asl.Library;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.umlgen.dsl.asl.impl.LibraryImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.umlgen.dsl.asl.impl.LibraryImpl#getArchitecturalStyles <em>Architectural Styles</em>}</li>
 *   <li>{@link org.eclipse.umlgen.dsl.asl.impl.LibraryImpl#getConfigurationRepositories <em>Configuration Repositories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LibraryImpl extends EObjectImpl implements Library {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2012, 2014 CNES and others.\r\nAll rights reserved. This program and the accompanying materials \r\nare made available under the terms of the Eclipse Public License v1.0 \r\nwhich accompanies this distribution, and is available at \r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n  \r\nContributors:\r\n     Cedric Notot (Obeo) - initial API and implementation";

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArchitecturalStyles() <em>Architectural Styles</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchitecturalStyles()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitecturalStyle> architecturalStyles;

	/**
	 * The cached value of the '{@link #getConfigurationRepositories() <em>Configuration Repositories</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationRepositories()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigurationRepository> configurationRepositories;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AslPackage.Literals.LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AslPackage.LIBRARY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArchitecturalStyle> getArchitecturalStyles() {
		if (architecturalStyles == null) {
			architecturalStyles = new EObjectContainmentEList<ArchitecturalStyle>(ArchitecturalStyle.class, this, AslPackage.LIBRARY__ARCHITECTURAL_STYLES);
		}
		return architecturalStyles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConfigurationRepository> getConfigurationRepositories() {
		if (configurationRepositories == null) {
			configurationRepositories = new EObjectContainmentEList<ConfigurationRepository>(ConfigurationRepository.class, this, AslPackage.LIBRARY__CONFIGURATION_REPOSITORIES);
		}
		return configurationRepositories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AslPackage.LIBRARY__ARCHITECTURAL_STYLES:
				return ((InternalEList<?>)getArchitecturalStyles()).basicRemove(otherEnd, msgs);
			case AslPackage.LIBRARY__CONFIGURATION_REPOSITORIES:
				return ((InternalEList<?>)getConfigurationRepositories()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AslPackage.LIBRARY__NAME:
				return getName();
			case AslPackage.LIBRARY__ARCHITECTURAL_STYLES:
				return getArchitecturalStyles();
			case AslPackage.LIBRARY__CONFIGURATION_REPOSITORIES:
				return getConfigurationRepositories();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AslPackage.LIBRARY__NAME:
				setName((String)newValue);
				return;
			case AslPackage.LIBRARY__ARCHITECTURAL_STYLES:
				getArchitecturalStyles().clear();
				getArchitecturalStyles().addAll((Collection<? extends ArchitecturalStyle>)newValue);
				return;
			case AslPackage.LIBRARY__CONFIGURATION_REPOSITORIES:
				getConfigurationRepositories().clear();
				getConfigurationRepositories().addAll((Collection<? extends ConfigurationRepository>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AslPackage.LIBRARY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AslPackage.LIBRARY__ARCHITECTURAL_STYLES:
				getArchitecturalStyles().clear();
				return;
			case AslPackage.LIBRARY__CONFIGURATION_REPOSITORIES:
				getConfigurationRepositories().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AslPackage.LIBRARY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AslPackage.LIBRARY__ARCHITECTURAL_STYLES:
				return architecturalStyles != null && !architecturalStyles.isEmpty();
			case AslPackage.LIBRARY__CONFIGURATION_REPOSITORIES:
				return configurationRepositories != null && !configurationRepositories.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //LibraryImpl
