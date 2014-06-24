/**
 * model/Interface2.java
 *
 * File generated from the  uml Interface
 */
package model.params;

import org.eclipse.umlgen.rtsj.framework.ArgsBuffer;
import org.eclipse.umlgen.rtsj.framework.ParameterSet;

public class Interface2_Signal3_Params implements ParameterSet {
	public int paramInteger;
	
	public Interface2_Signal3_Params(boolean provider) {
	}

	public void readObject(ArgsBuffer argsBuffer) {
		paramInteger = argsBuffer.readInteger();
	}

	public void writeObject(ArgsBuffer argsBuffer) {
		argsBuffer.writeInteger(paramInteger);
	}

	public int byteSize() {
		return 4;
	}
}