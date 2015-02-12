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
 * model/RootClass.java
 *
 * File generated from the  uml Class
 */
package model;
import org.eclipse.umlgen.rtsj.annotations.*;


import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.*;

// Start of user code to add imports for RootClass

// End of user code

/**
 * Description of the class RootClass.
 *
 */

@Root
public class RootClass   {
	public static CommunicationLayer communicationLayer = new CommunicationLayer();
	
	public static void start_all() {
		class1.start_all();
		class2.start_all();
		class3.start_all();
	}
	
	public static void init(int freq_OBC) {
		// Start of user code to prepare init() operation (pre connections)
		 	
		// End of user code
			class1.initPortsGenerator("class1", communicationLayer);
			class2.initPortsGenerator("class2", communicationLayer);
			class3.initPortsGenerator("class3", communicationLayer);
		
		
		makeConnections();
		// Start of user code to complete init() operation (post connections)
		
		// End of user code
		start_all();
	}
	
	private static void makeConnections() {
		String[] class2Port4Receivers = new String[2];
		class2Port4Receivers[0] = "class3:Port5";
		class2Port4Receivers[1] = "class1:Port3";
		communicationLayer.setEventDataConnection("class2:Port4", class2Port4Receivers);
		communicationLayer.registerEventDataReceiverPort(class2Port4Receivers[0], class3.getPort5());
		communicationLayer.registerEventDataReceiverPort(class2Port4Receivers[1], class1.getPort3());
	}
	
	
	
	public static Class1 class1 = new Class1();
	public static Class2 class2 = new Class2();
	public static Class3 class3 = new Class3();
	// Start of user code to add fields for RootClass
	
	// End of user code
	
	public void readObject(ArgsBuffer argsBuffer) {
		class1.readObject(argsBuffer);
		class2.readObject(argsBuffer);
		class3.readObject(argsBuffer);
	}
	
	public void writeObject(ArgsBuffer argsBuffer) {
		class1.writeObject(argsBuffer);
		class2.writeObject(argsBuffer);
		class3.writeObject(argsBuffer);
	}
	
	
	
	
	
	/**
	 * Constructor.
	 */
	public RootClass() {
		super();
		// Start of user code for constructor RootClass
		// End of user code
	}
	/**
	 * Description of the method exec.
	 *
	 */
	public static void exec() {
		// Start of user code for method RootClass.exec():
		//TODO Fill method
		// End of user code
	}
	
	
	
	/**  CONNECTORS.  ***/
	
	
	
	
}