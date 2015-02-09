/*******************************************************************************
 * Copyright (c) 2009, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Topcased contributors and others - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.rtsj.framework;


public class MonObjet {

	private int num1 ;
	private int num2 ;
	
	public void readObject(ArgsBuffer argsBuffer) {
		num1 = argsBuffer.readInteger() ;
		num2 = argsBuffer.readInteger() ;
	}

	public void writeObject(ArgsBuffer argsBuffer) {
		argsBuffer.writeInteger(num1);
		argsBuffer.writeInteger(num2);
	}

	public int byteSyze() {
		return 8 ;
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}
	
	
	
}
