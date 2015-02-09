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

import org.eclipse.umlgen.rtsj.framework.async.PortProviderAsync;

public class PortBuffer {
	
	PortProviderAsync[] pbuffer;
	int[] messagePriority;
	int h;
	int t;
	int left;
	int size;
	
	
	public PortBuffer(int size) {
		this.size = left = size; 
		t = 0;
		h = 0;
		pbuffer = new PortProviderAsync[size];
		messagePriority = new int[size];
	}
	
	public boolean empty() { return size == left; }
	public boolean full() { return left == 0; }
	
	 public void put(PortProviderAsync m, int mpriority) {
		 if (left == 0) return;
		 
		 //Recherche de la position d'insertion
		 int pos = t;
		 if (!empty()) {
			 for (int i = (t != 0) ? t - 1 : size - 1; ; i--) {
				 if (i < 0) {
					 i = size - 1;
				 }
				 if (mpriority < messagePriority[i]) {
					 pos = i;
					 int nexti = (i + 1)%size;
					 pbuffer[nexti] = pbuffer[i];
					 messagePriority[nexti] = messagePriority[i];
				 }
				 else {
					 break;
				 }
				 if (i == h) {
					 break;
				 }
			 }
		 }
		 pbuffer[pos] = m;
		 messagePriority[pos] = mpriority;
		 t = (t+1)%size;
		 left--;
		 
   }

	public PortProviderAsync get() {
			if (left == size) return null;
			PortProviderAsync m = pbuffer[h];
			h = (h+1)%size;
			left++;
			return m;
   }	
}