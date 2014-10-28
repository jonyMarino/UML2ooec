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
package A;

import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import A.model.Class1;
import A.model.communication.EthernetComClass1;

// Start of user code to add imports for AppliA

// End of user code

public class AppliA {

	public static CommunicationLayer communicationLayer = new CommunicationLayer();
	
	public static void start_all() {
		class1.start_all();
	}
	
	public static void init(int freq_OBC) {
		// Start of user code to prepare init() operation (pre connections)
		 	
		// End of user code
			class1.initPortsGenerator("class1", communicationLayer);
		
			communicationLayer.registerComProtocol("class1", new EthernetComClass1(communicationLayer));
		
		makeConnections();
		// Start of user code to complete init() operation (post connections)
		
		// End of user code
		start_all();
	}
	
	private static void makeConnections() {
		String[] class2Port4Receivers = new String[1];
		class2Port4Receivers[0] = "class1:Port3";
		communicationLayer.setEventDataConnection("class2:Port4", class2Port4Receivers);
		communicationLayer.registerEventDataReceiverPort(class2Port4Receivers[0], class1.getPort3());
	}

	public static Class1 class1 = new Class1();
// Start of user code to add fields for AppliA

// End of user code

	/**
	 * Description of the method exec.
	 *
	 */
	public static void exec() {
		// Start of user code for method RootClass.exec():
		//TODO Fill method
		// End of user code
	}
	

}