/**
 * model/InterfaceSynchronous.java
 *
 * File generated from the  uml Interface
 */
package model.params;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import model.SharedData;
import org.eclipse.umlgen.rtsj.framework.ParameterSet;

public class InterfaceSynchronous_monService2_Params implements ParameterSet {
	public SharedData param;
	
	public InterfaceSynchronous_monService2_Params(boolean provider) {
	}

	public void readObject(ArgsBuffer argsBuffer) {
		param.readObject(argsBuffer);
	}

	public void writeObject(ArgsBuffer argsBuffer) {
		param.writeObject(argsBuffer);
	}

	public int byteSize() {
		return 0; // Synchrone case (not used)
	}
}