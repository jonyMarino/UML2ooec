struct mystruct1 {
	char c;
};

typedef struct mystruct1 type1;

struct mystruct2 {
	type1 a;
	type1 *b;
};
