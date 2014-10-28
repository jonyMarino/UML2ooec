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
 * model/RootClass.java
 *
 * File generated from the  uml Class
 */
package B.model;
import B.model.*;
import org.eclipse.umlgen.rtsj.annotations.*;


import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.*;
import B.model.communication.EthernetComClass2;
import C.model.communication.EthernetComClass3;
import B.model.Class2;
import C.model.Class3;

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
		class2.start_all();
		class3.start_all();
	}
	
	public static void init(int freq_OBC) {
		// Start of user code to prepare init() operation (pre connections)
		 	
		// End of user code
			class2.initPortsGenerator("class2", communicationLayer);
			class3.initPortsGenerator("class3", communicationLayer);
		
			communicationLayer.registerComProtocol("class2", new EthernetComClass2(communicationLayer));
			communicationLayer.registerComProtocol("class3", new EthernetComClass3(communicationLayer));
		
		makeConnections();
		// Start of user code to complete init() operation (post connections)
		
		// End of user code
		start_all();
	}
	
	private static void makeConnections() {
		communicationLayer.setSynchronousConnection("class3:monPort1", "class2:monPort2");
		String[] class2MonPort2DestComp = new String[1];
		class2MonPort2DestComp[0] = "class3";
		communicationLayer.setComponentMap("class2:monPort2", class2MonPort2DestComp);
	}
	
	
	
	public static Class2 class2 = new Class2();
	public static Class3 class3 = new Class3();
	// Start of user code to add fields for RootClass
	
	// End of user code
	
	public void readObject(ArgsBuffer argsBuffer) {
		class2.readObject(argsBuffer);
		class3.readObject(argsBuffer);
	}
	
	public void writeObject(ArgsBuffer argsBuffer) {
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