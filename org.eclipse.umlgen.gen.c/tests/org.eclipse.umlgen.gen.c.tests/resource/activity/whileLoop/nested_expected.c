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