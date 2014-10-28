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
package C;

import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import C.model.Class3;
import C.model.communication.EthernetComClass3;

// Start of user code to add imports for AppliC

// End of user code

public class AppliC {

	public static CommunicationLayer communicationLayer = new CommunicationLayer();
	
	public static void start_all() {
		class3.start_all();
	}
	
	public static void init(int freq_OBC) {
		// Start of user code to prepare init() operation (pre connections)
		 	
		// End of user code
			class3.initPortsGenerator("class3", communicationLayer);
		
			communicationLayer.registerComProtocol("class3", new EthernetComClass3(communicationLayer));
		
		makeConnections();
		// Start of user code to complete init() operation (post connections)
		
		// End of user code
		start_all();
	}
	
	private static void makeConnections() {
		communicationLayer.setSynchronousConnection("class3:monPort1", "class2:monPort2");
	}

	public static Class3 class3 = new Class3();
// Start of user code to add fields for AppliC

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