int a = 1;
do {
	printf("a is %d \n", a);
	int b = 1;
	do {
		printf("b is %d \n", b);
		b = b * 2;
	} while (b < 100);
	a++;
} while (a < 100);