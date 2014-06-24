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

public class MBuffer {
    String[] buf;
    int[] priority;
    int[] argSize;
    int size;
    int h;
    int t;
    int left;

    public MBuffer(int size) { 
    	this.size = left = size; 
    	h = t = 0;
    	buf = new String[size];
    	priority = new int[size];
    	argSize = new int[size];
    }

    public boolean empty() { return size == left; }

    public boolean full() { return left == 0; }

    public int put(String m, int priority, int argsize) {
    	if (left == 0) return -1;
    	
    	//Recherche de la position d'insertion
		 int pos = t;
		 int cumulArgSize = 0;
		 if (!empty()) {
			 for (int i = (t != 0) ? t - 1 : size - 1; ; i--) {
				 if (i < 0) {
					 i = size - 1;
				 }
				 if (priority < this.priority[i]) {
					 pos = i;
					 int nexti = (i + 1)%size;
					 buf[nexti] = buf[i];
					 this.priority[nexti] = this.priority[i];
					 this.argSize[nexti] = this.argSize[i];
				 }
				 else {
					 cumulArgSize += this.argSize[i];
				 }
				 if (i == h) {
					 break;
				 }
			 }
		 }
		 buf[pos] = m;
		 this.priority[pos] = priority;
		 this.argSize[pos] = argsize;
		 t = (t+1)%size;
		 left--;
		 
		 return cumulArgSize;
    }

    public String get() {
    	if (left == size) return null;
    	String m = buf[h];
    	h = (h+1)%size;
    	left++;
    	return m;
    }
}
