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
int a = 1;
while(a < 100) {
	printf("a is %d \n", a);
	int b = 1;
	while(b < 100) {
		printf("b is %d \n", b);
		b = b * 2;
	}
	a++;
}