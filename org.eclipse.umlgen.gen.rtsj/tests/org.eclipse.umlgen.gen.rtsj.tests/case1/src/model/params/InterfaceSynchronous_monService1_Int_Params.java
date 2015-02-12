/*******************************************************************************
 * Copyright (c) 2014, 2015 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     JF Rolland (ATOS) - initial API and implementation
 *******************************************************************************/
/**
 * model/InterfaceSynchronous.java
 *
 * File generated from the  uml Interface
 */
package model.params;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import org.eclipse.umlgen.rtsj.framework.ParameterSet;

public class InterfaceSynchronous_monService1_Int_Params implements ParameterSet {
	public int param1;
	
	public InterfaceSynchronous_monService1_Int_Params(boolean provider) {
	}

	public void readObject(ArgsBuffer argsBuffer) {
		param1 = argsBuffer.readInteger();
	}

	public void writeObject(ArgsBuffer argsBuffer) {
		argsBuffer.writeInteger(param1);
	}

	public int byteSize() {
		return 0; // Synchrone case (not used)
	}
}