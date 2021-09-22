%{
	#include<stdio.h>
    int yylex(void);
    void yyerror(const char *str);
%}

%token NUMBER ID SWITCH CASE DEFAULT BREAK AND OR NOT LT GT LE GE EQ NE ADD SUB EQU MUL DIV SC C OB CB OP CP INCR DECR

%%

switch:	SWITCH OP expr CP body { printf("No error in switch case for given input\n"); } ;
body:	OB cases CB ;
cases:	CASE expr C stmts BREAK SC cases |
	CASE expr C stmts BREAK SC |
	DEFAULT C stmts BREAK SC;
stmts:	stmt SC stmts |
	stmt SC |
	switch ;
stmt:	ID EQU expr;
expr:	expr ADD term |
	expr SUB term |
	term;
term:	term MUL f |
	term DIV f |
	f;
f:	ID | 
	NUMBER;  	

%%


#include "lex.yy.c"

void yyerror(const char *str) {
	printf("Error in switch case for given input\n");
}

int main(int argc, char *argv[]) {
    yyin = fopen(argv[1], "r");
    yyparse();
    fclose(yyin);
}