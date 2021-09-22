%%
[0-9]+ {
    yylval=atoi(yytext); return NUMBER;
}

[-+*/] {
    return yytext[0];
}

. {

}

\n {
    yyterminate();
}
%%