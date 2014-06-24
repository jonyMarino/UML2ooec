/**
 * Comment before the tested function
 */
void f() {
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
}// Comment out of function body

/**
 * Comment after the tested function
 */
void g() {
	printf("B\n");
}
