DIGIT [0-9]

%%
{DIGIT}+	{yylval=atoi(yytext); return NUMBER;}
[-+*/]		{return yytext[0];}
.		;
\n		yyterminate();
%%