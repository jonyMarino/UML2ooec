/*******************************************************************************
 * Copyright (c) 2012, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
/**
 * model/Interface2.java
 *
 * File generated from the  uml Interface
 */
package model;

import org.eclipse.umlgen.rtsj.annotations.*;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;

/**
 * Description of the interface Interface2.
 *
 */

public interface Interface2  {




	/**
	 *  Description of the signal Signal3.
	 *
	 *
	 * @param paramInteger
	 */
	@reception
	void Signal3(int paramInteger);

	/**
	 *  Description of the signal Signal2.
	 *
	 *
	 * @param param
	 */
	@reception
	void Signal2(SharedData param);



}