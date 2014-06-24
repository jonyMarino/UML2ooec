void f() {
	printf("before label");
	come_here :
		if (true) {
			printf("that's true");
		}
		printf("right after if");
		goto come_here;
	printf("end of function");
}
