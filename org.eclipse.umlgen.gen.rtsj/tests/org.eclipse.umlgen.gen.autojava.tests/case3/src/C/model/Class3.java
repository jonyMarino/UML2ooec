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
 * model/Class3.java
 *
 * File generated from the  uml Class
 */
package C.model;
import B.model.*;
import org.eclipse.umlgen.rtsj.annotations.*;


import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import org.eclipse.umlgen.rtsj.framework.MBuffer;
import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import C.model.async.providers.Class3_monPort1_ProviderAsync;
import model.InterfaceAsynchronous;
import model.SharedData;
import model.*;

// Start of user code to add imports for Class3

// End of user code

/**
 * Description of the class Class3.
 *
 */

@Sporadic(period = 1000, phase = 0, priority = 20, deadline = 0, wcet = 0, bcet = 0, messages = 10)
public class Class3  implements InterfaceAsynchronous, CommunicationExceptionInterface {
	
	
	
	// Start of user code to add fields for Class3
	
	// End of user code
	
	public void readObject(ArgsBuffer argsBuffer) {
	}
	
	public void writeObject(ArgsBuffer argsBuffer) {
	}
	
	
	
	
	
	/**
	 * Constructor.
	 */
	public Class3() {
		super();
		// Start of user code for constructor Class3
		// End of user code
	}
	PortBuffer pbuffer = new PortBuffer(0);
	
	@ignore
	public void before() {
		// Start of user code for method Class3.before():
		
		// End of user code 
	}
	
	@ignore
	public void after() {
		// Start of user code for method Class3.after():
		
		// End of user code 
	}
	
	@ignore
	private final Object thread = new Object();
	
	
	public int getThreadPeriod() { return 1000; }
	public int getThreadPhase() { return 0; }
	public Object getThread() { return thread; }
	public int getThreadPriority() { return 20; }
	public PortBuffer getPbuffer() { return pbuffer; }
	
	
	
	@ignore
	public void start_all() {
		init();
	}
	
	@ignore
	public void body() {
		before();
		for (int count = 0; count < 10; count++) {
			synchronized(thread) {
				if (!pbuffer.empty()) {
					pbuffer.get().invokeNextOperation();
				} else {
					break;
				}
			}
		}
		after();
	}
	
	@ignore
	public void init() {
		// Start of user code for method Class3.init():
	
		// End of user code 
	}
	
	@ignore
	public void catchCommunicationException(String service, ArgsBuffer params) {
		// Start of user code for method Class3.catchCommunicationException(String,Argsbuffer):
		//TODO Fill Method
		// End of user code 
	}
	
	
	
	/**  CONNECTORS.  ***/
	protected Class3_monPort1_ProviderAsync monPort1;
	
	public void initPortsGenerator(String componentInstance, CommunicationLayer communicationLayer) {
		monPort1 = new Class3_monPort1_ProviderAsync(this, componentInstance + ":monPort1", 0, thread, pbuffer, communicationLayer, this);
	}
	
	
	public void monService1(int param1, int param2) {
		// Start of user code for method Class3.monService1(int param1, int param2): void
		// End of user code
	}
	public void monService2(SharedData param1) {
		// Start of user code for method Class3.monService2(SharedData param1): void
		// End of user code
	}
	
}