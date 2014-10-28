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
struct PersonDat {
	char *name;
	char *address;
	int YearOfBirth;
	int MonthOfBirth;
	int DayOfBirth;
};

struct PersonalData myFriends[] = {
	{
		"Someone",
		"Somewhere",
		1965,
		5,
		12
	},
	{
		"John Doe",
		"21 Nowhere Street",
		1980,
		1,
		1
	},
	{
		"Luke Skywalker",
		"In a galaxy far far away",
		-1,
		-1,
		-1
	}
};


