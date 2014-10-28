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
 * model/InterfaceAsynchronous.java
 *
 * File generated from the  uml Interface
 */
package model.params;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import model.SharedData;
import org.eclipse.umlgen.rtsj.framework.ParameterSet;

public class InterfaceAsynchronous_monService2_Params implements ParameterSet {
	public SharedData param1;
	
	public InterfaceAsynchronous_monService2_Params(boolean provider) {
		if (provider) {
			param1 = new SharedData();
		}
	}

	public void readObject(ArgsBuffer argsBuffer) {
		param1.readObject(argsBuffer);
	}

	public void writeObject(ArgsBuffer argsBuffer) {
		param1.writeObject(argsBuffer);
	}

	public int byteSize() {
		return 21;
	}
}