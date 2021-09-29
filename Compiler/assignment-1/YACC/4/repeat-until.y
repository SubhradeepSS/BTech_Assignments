%{
	#include <stdio.h>
    #include <stdlib.h>
    int yylex(void);
    void yyerror(const char *str);
%}

%token NUMBER ID REPEAT UNTIL AND OR NOT LT GT LE GE EQ NE ADD SUB EQU MUL DIV SC OB CB OP CP INCR DECR

%%

repeat:	REPEAT body UNTIL cond SC { printf("No error in repeat-until syntax for given input\n"); exit(0); }
body: stmts |
	stmt SC ;
stmts:	stmt SC stmts |
	stmt SC |
    repeat
cond:	c AND cond |
	c OR cond |
	c ;
c:	f relop f |
	NOT f relop f ;
stmt:	ID EQU expr;
expr:	expr ADD term |
	expr SUB term |
	term;
term:	term MUL f |
	term DIV f |
	f;
f:	ID | 
	NUMBER;
relop:	LT | GT | LE | GE | EQ | NE;
		  	
%%


#include "lex.yy.c"

void yyerror(const char *str) {
	printf("Error in repeat-until syntax for given input\n");
}

int main(int argc, char *argv[]) {
    yyin = fopen(argv[1], "r");
    yyparse();
    fclose(yyin);
}