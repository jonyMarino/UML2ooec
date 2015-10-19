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
 * model/Class1.java
 *
 * File generated from the  uml Class
 */
package model;
import org.eclipse.umlgen.rtsj.annotations.*;

// import javax.realtime.*;
// import AperiodicRealtimeEntry;
// import AperiodicRealtimeComponent;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import org.eclipse.umlgen.rtsj.framework.MBuffer;
import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.eventdata.receiver.Class1_Port3_ReceiverEventData;
import model.Interface2;
import model.SharedData;
import model.*;

// Start of user code to add imports for Class1

// End of user code

/**
 * Description of the class Class1.
 *
 */

@Sporadic(period = 1000, phase = 0, priority = 30, deadline = 0, wcet = 0, bcet = 0, messages = 10)
public class Class1  implements   CommunicationExceptionInterface, AperiodicRealtimeEntry {
	
	
	
	// Start of user code to add fields for Class1
	
	// End of user code
	
	public void readObject(ArgsBuffer argsBuffer) {
	}
	
	public void writeObject(ArgsBuffer argsBuffer) {
	}
	
	
	
	
	
	/**
	 * Constructor.
	 */
	public Class1() {
		super();
		// Start of user code for constructor Class1
		// End of user code
	}
	PortBuffer pbuffer = new PortBuffer(1);
	
	@ignore
	public void before() {
		// Start of user code for method Class1.before():
		
		// End of user code 
	}
	
	@ignore
	public void after() {
		// Start of user code for method Class1.after():
		
		// End of user code 
	}
	
	@ignore
	private AperiodicRealtimeComponent  thread = new AperiodicRealtimeComponent (this);
	
	
	@ignore
	public int getThreadPeriod() { return 1000; }
	@ignore
	public int getThreadPhase() { return 0; }
	@ignore
	public Object getThread() { return thread; }
	@ignore
	public int getThreadPriority() { return 30; }
	@ignore
	public PortBuffer getPbuffer() { return pbuffer; }
	
	
	@ignore
	public void schedule() { thread.schedule(); }
	@ignore
	public void deschedule() { thread.deschedule(); }
	
	@ignore
	public void start() { thread.start(); }
	
	@ignore
	public void start_all() {
		init();
		start();
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
		// Start of user code for method Class1.init():
	
		// End of user code 
	}
	
	@ignore
	public void catchCommunicationException(String service, ArgsBuffer params) {
		// Start of user code for method Class1.catchCommunicationException(String,Argsbuffer):
		//TODO Fill Method
		// End of user code 
	}
	
	
	
	/**  CONNECTORS.  ***/
	protected Class1_Port3_ReceiverEventData Port3;
	
	public void initPortsGenerator(String componentInstance, CommunicationLayer communicationLayer) {
		Port3 = new Class1_Port3_ReceiverEventData(componentInstance + ":Port3", communicationLayer);
	}
	
	public EventDataPort getPort3() {
		return Port3;
	}
	
	
}