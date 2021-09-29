%%

[ \t\n]	;
repeat 	return REPEAT;
until	return UNTIL;
not	    return NOT;
and	    return AND;
or	    return OR;
[a-zA-Z][_a-zA-Z0-9]* return ID;
[0-9]+	return NUMBER;
">"	    return GT;
"<"	    return LT;
":="	return EQU;
"="	    return EQ;
"<>"	return NE;
">="	return GE;
"<="	return LE;
"+"	    return ADD;
"-"	    return SUB;
"*"	    return MUL;
"/"	    return DIV;
";"	    return SC;
"{"	    return OB;
"}"	    return CB;
"("	    return OP;
")"	    return CP;
"++"	return INCR;
"--"	return DECR;

%%