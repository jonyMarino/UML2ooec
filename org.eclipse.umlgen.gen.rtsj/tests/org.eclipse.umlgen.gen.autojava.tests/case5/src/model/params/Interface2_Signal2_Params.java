/**
 * model/Interface2.java
 *
 * File generated from the  uml Interface
 */
package model.params;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import model.SharedData;
import org.eclipse.umlgen.rtsj.framework.ParameterSet;

public class Interface2_Signal2_Params implements ParameterSet {
	public SharedData param;
	
	public Interface2_Signal2_Params(boolean provider) {
	}

	public void readObject(ArgsBuffer argsBuffer) {
		param.readObject(argsBuffer);
	}

	public void writeObject(ArgsBuffer argsBuffer) {
		param.writeObject(argsBuffer);
	}

	public int byteSize() {
		return 21;
	}
}