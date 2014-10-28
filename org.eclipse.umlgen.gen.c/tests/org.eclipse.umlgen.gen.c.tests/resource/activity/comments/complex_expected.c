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
// before 1
printf/*inline 1*/("A");

// before 2
printf("C");// same line 1
printf("D1");/* same line 2*/
printf("D2");/* same line 3*/

// before 3

// before 4
if (a > /*inline 2*/10) { /*same line 4*/
	return;
}
if (/*inline 3*/a > 10) {
	return;
	// last line 1
} else {
	// Before 4.1
	printf("D3");
	// Last line 1.1
}

// before 5
for(int i=0;/*inline 4*/ i<10/*inline 5*/; i++/*inline 6*/) /*inline 7*/{ // same line 5

	// last line 2
}
										// before 6
printf("j");/*same line 6*/printf("k");

printf("r");/*same line 7*/printf("s");/*same
line 8*/printf/*inline 8*/("t");printf("u");// same line 9

/*before 7*/printf("a");printf("b");

// before 8
switch(a) { // same line 10
// before 9
case 0 : // same line 11
	printf("case 0");
	break;
case 1 : // same line 12
	printf("case 1");
	// before 10
	break;
case 2 :
	printf("case 2");
	break;
	// last line 3.1
default :
	printf("default");
	break;
	// last line 3
}

// last line 4

/* last line 5 */
