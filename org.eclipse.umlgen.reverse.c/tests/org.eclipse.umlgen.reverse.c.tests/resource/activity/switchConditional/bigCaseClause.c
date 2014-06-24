void f() {
	int grade;
	switch (grade) {
	case 1:
		if (grade == 10) {
			printf("grade == 10");
			return;
		}
		printf("A1\n");
		if (grade == 20) {
			printf("grade == 20");
			break;
		}
		printf("A2\n");
		break;
	case 2:
		printf("B\n");
	case 3:
		printf("C\n");
		break;
	default:
		printf("F\n");
	}
}
