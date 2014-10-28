/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Obeo - initial API and implementation
 *******************************************************************************/
printf("A");

// Before 1
int i = 10;
// Before 2
if (/*inline 1*/ i < 5) { // Same line 1
	// Inside then clause
	printf("C");
	// Last line of then clause
}

printf("B");
