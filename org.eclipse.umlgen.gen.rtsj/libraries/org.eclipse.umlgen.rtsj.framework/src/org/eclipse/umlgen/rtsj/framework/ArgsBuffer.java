/*******************************************************************************
 * Copyright (c) 2009, 2014 CNES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Topcased contributors and others - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.rtsj.framework;

public class ArgsBuffer {
	private byte[] elems;
	private int size;
	private int h;
	private int t;
	private int used;

	public ArgsBuffer(int size) {
		this.size = size; 
		used = 0;
		elems = new byte[size]; 
		h = t = 0;
	}

	public boolean check(int n) {
		return used + n <= size;
	}

	public void dequeue(int n) {
		used -= n;
		if (size != 0) {
			h = (h+n)%size;
		}
	}

	public void writeBoolean(boolean b) {
		writeByte(b?(byte)1:(byte)0);
	}

	public void writeByte(byte b) {
		if (used == size) return;
		used++;
		elems[t] = b;
		t = (t+1)%size;
	}

	public void writeCharacter(int v) {
		writeByte((byte) (0xff & (v >> 8)));
		writeByte((byte) (0xff & (v)));
	}

	public void writeShort(int v) {
		writeByte((byte) (0xff & (v >> 8)));
		writeByte((byte) (0xff & (v)));
	}

	public void writeInteger(int v) {
		writeByte((byte)(0xff & (v >> 24)));
		writeByte((byte)(0xff & (v >> 16)));
		writeByte((byte)(0xff & (v >>  8)));
		writeByte((byte)(0xff & v));
	}

	public void writeLong(long v) {
		writeByte((byte)(0xff & (v >> 56)));
		writeByte((byte)(0xff & (v >> 48)));
		writeByte((byte)(0xff & (v >> 40)));
		writeByte((byte)(0xff & (v >> 32)));
		writeByte((byte)(0xff & (v >> 24)));
		writeByte((byte)(0xff & (v >> 16)));
		writeByte((byte)(0xff & (v >>  8)));
		writeByte((byte)(0xff & v));
	}

	public void writeFloat(float f) { writeInteger(Float.floatToRawIntBits(f)); }

	public void writeDouble(double d) { writeLong(Double.doubleToRawLongBits(d)); }

	public void writeString(String s) {
		writeShort(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c <= '\u0080') 
				writeByte((byte) c);
			else if (c <= '\u07ff') {
				writeByte((byte) (0xc0 | (0x1f & (c >> 6))));
				writeByte((byte) (0x80 | (0x3f & c)));
			} else {
				writeByte((byte) (0xe0 | (0x0f & (c >> 12))));
				writeByte((byte) (0x80 | (0x3f & (c >> 6))));
				writeByte((byte) (0x80 | (0x3f & c)));
			}
		}	
	}



	public boolean readBoolean() {
		return readByte() != 0;
	}

	public byte readByte() {
		if (used == 0) return 0;
		used--;
		byte b = elems[h];
		h = (h+1)%size;
		return b;
	}

	public byte readByteAt(int i) {
		if (used == 0) return 0;
		byte b = elems[(h+i)%size];
		return b;
	}


	public char readCharacter() {
		byte a = readByte();
		byte b = readByte();
		return (char) ((a << 8) | (b & 0xff));
	}

	public short readShort() {
		byte a = readByte();
		byte b = readByte();
		return (short) ((a << 8) | (b & 0xff));	
	}

	public int readInteger() {
		byte a = readByte();
		byte b = readByte();
		byte c = readByte();
		byte d = readByte();
		return (((a & 0xff) << 24) | ((b & 0xff) << 16) |
				((c & 0xff) << 8) | (d & 0xff));

	}

	public long readLong() {
		byte a = readByte();
		byte b = readByte();
		byte c = readByte();
		byte d = readByte();
		byte e = readByte();
		byte f = readByte();
		byte g = readByte();
		byte h = readByte();
		return (((long)(a & 0xff) << 56) |
				((long)(b & 0xff) << 48) |
				((long)(c & 0xff) << 40) |
				((long)(d & 0xff) << 32) |
				((long)(e & 0xff) << 24) |
				((long)(f & 0xff) << 16) |
				((long)(g & 0xff) <<  8) |
				(h & 0xff));
	}

	public float readFloat() { return Float.intBitsToFloat(readInteger()); }

	public double readDouble() { return Double.longBitsToDouble(readLong()); }

	public void readString(StringBuffer sb) {
		short n = readShort();
		sb.setLength(n);
		for (int i = 0; i < n; i++) {
			char u;
			int a = readByte();
			if ((a & 0x80) == 0) u = (char) a;
			else if ((a & 0xe0) == 0xc0) {
				int b = readByte();
				u = (char) (((a&0x1F) << 6) | (b & 0x3F));
			} else {
				int b = readByte();
				int c = readByte();
				u = (char) (((a&0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F));
			}
			sb.setCharAt(i,u);
		}
	}

	public int getSize() {
		return size;
	}

	public int getUsed() {
		return used;
	}

	public void copy (ArgsBuffer arg){
    	int argSize = arg.getUsed();
    	for (int i = 0 ; i < argSize ; i++){
    		writeByte(arg.readByteAt(i));
    	}
    }
	
	public void copy (ArgsBuffer arg, int offset){
		int argSize = arg.getUsed();
		if (used + argSize > size) {
			return;
		}
		if (argSize > 0) {
			//Decalage des octets apres offset
			int max = t;
			int min = (h + offset) % size;
			for (int i = max ; i >= min; i--){
				if (i < 0) {
					i = size - 1;
				}
				int desti = (i + argSize) % size;
				elems[desti] = elems[i];
			}
			t = (t + argSize) % size;
			used += argSize;
			//Copie par insertion
			for (int i = 0 ; i < argSize ; i++){
				elems[(h + offset + i) % size] = arg.readByteAt(i);
			}
		}
	}

}
