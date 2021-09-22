%{
	#include<stdio.h>
	void push(int val);
    int pop();
    int top();
    int yylex(void);
    void yyerror(const char *str);
	int stack[100];
	int i=0;
%}

%token NUMBER

%%

S: E { printf("Output of expression: %d\n",top()); };
E:	E E '+' { push(pop() + pop()); } |
	E E '-' { int t=pop(); push(pop() - t); } |
	E E '*' { push(pop() * pop()); } |
	E E '/' { int t=pop(); push(pop() / t); } |
	NUMBER { push(yylval); };	

%%

#include "lex.yy.c"

void push(int val) {
	stack[i] = val;
	i++;
}

int pop() {
	i--;
	return stack[i];
}

int top() {
	return stack[i-1];
} 

void yyerror(const char *str) {
	printf("Error in expression evaluation\n");
}

int main(int argc, char *argv[]) {
	yyin = fopen(argv[1], "r");
    yyparse();
    fclose(yyin);
}