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
 * model/Class2.java
 *
 * File generated from the  uml Class
 */
package model;
import org.eclipse.umlgen.rtsj.annotations.*;

import javax.realtime.*;
import fr.cnes.autojava.component.AperiodicRealtimeEntry;
import fr.cnes.autojava.component.AperiodicRealtimeComponent;

import org.eclipse.umlgen.rtsj.framework.*;
import org.eclipse.umlgen.rtsj.framework.types.*;
import model.eventdata.emitter.Class2_Port4_EmitterEventData;
import model.*;

// Start of user code to add imports for Class2

// End of user code

/**
 * Description of the class Class2.
 *
 */

@Sporadic(period = 1000, phase = 0, priority = 29, deadline = 0, wcet = 0, bcet = 0, messages = 10)
public class Class2  implements   CommunicationExceptionInterface, AperiodicRealtimeEntry {
	
	
	
	// Start of user code to add fields for Class2
	
	// End of user code
	
	public void readObject(ArgsBuffer argsBuffer) {
	}
	
	public void writeObject(ArgsBuffer argsBuffer) {
	}
	
	
	
	
	
	/**
	 * Constructor.
	 */
	public Class2() {
		super();
		// Start of user code for constructor Class2
		// End of user code
	}
	PortBuffer pbuffer = new PortBuffer(0);
	
	@ignore
	public void before() {
		// Start of user code for method Class2.before():
		
		// End of user code 
	}
	
	@ignore
	public void after() {
		// Start of user code for method Class2.after():
		
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
	public int getThreadPriority() { return 29; }
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
		after();
	}
	
	@ignore
	public void init() {
		// Start of user code for method Class2.init():
	
		// End of user code 
	}
	
	@ignore
	public void catchCommunicationException(String service, ArgsBuffer params) {
		// Start of user code for method Class2.catchCommunicationException(String,Argsbuffer):
		//TODO Fill Method
		// End of user code 
	}
	
	
	
	/**  CONNECTORS.  ***/
	protected Class2_Port4_EmitterEventData Port4;
	
	public void initPortsGenerator(String componentInstance, CommunicationLayer communicationLayer) {
		Port4 = new Class2_Port4_EmitterEventData(componentInstance + ":Port4", componentInstance, communicationLayer);
	}
	
	
	
}