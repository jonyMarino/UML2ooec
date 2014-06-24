void f() {
	printf("before label");
	come_here :
		printf("right after label");
		goto come_here;
	printf("end of function");
}
