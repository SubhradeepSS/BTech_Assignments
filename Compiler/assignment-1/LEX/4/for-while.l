%{
	#include<string.h>
	char init[100],cond[100],upd[100];
	int ic=0, cc=0, uc=0;
%}

%s forbegin forcond forupd forblock forstmt

%%
for\( {
	BEGIN forbegin;
}

<forbegin>[^;]* {
	strcpy(init, yytext);
	int j;
	for(j=0; j<strlen(init); j++) {
		if(init[j]!=' ')
			break;
	}

	int k;
	for(k=strlen(init)-1; k>=0; k--) {
		if(init[k]!=' ')
			break;
	}
	if(k>j)
		ic=k-j;
}

<forbegin>[;] {
	BEGIN forcond;
}

<forcond>[^;]* {
	strcpy(cond,yytext);
	int j;
	for(j=0; j<strlen(cond); j++) {
		if(cond[j]!=' ')
			break;
	}

	int k;
	for(k=strlen(cond)-1; k>=0; k--) {
		if(cond[k]!=' ')
			break;
	}
	if(k>j)
		cc=k-j;
}

<forcond>[;] {
	BEGIN forupd;
}

<forupd>[^)]* {
	strcpy(upd, yytext);
	int j;
	for(j=0; j<strlen(upd); j++) {
		if(upd[j]!=' ')
			break;
	}

	int k;
	for(k=strlen(upd)-1; k>=0; k--) {
		if(upd[k]!=' ')
			break;
	}
	if(k>j)
		uc=k-j;
}

<forupd>\)\; {
	if(ic>=1) {
		fwrite(init,strlen(init),1,yyout);
		fwrite(";\n",2,1,yyout);
	}

	fwrite("while(",6,1,yyout);	

	if(cc>=1) 
		fwrite(cond,strlen(cond),1,yyout);
	else
		fwrite("true",4,1,yyout);

	fwrite(") {",3,1,yyout);
	if(uc>=1) {
		fwrite(upd,strlen(upd),1,yyout);
		fwrite(";\n}",3,1,yyout);
	} 
	else {
		fwrite("\n}",2,1,yyout);
	}

	BEGIN 0;
	strcpy(init,"");
	strcpy(cond,"");
	strcpy(upd,"");
	ic=0;
	cc=0;
	uc=0;
}
	
<forupd>\) {
	if(ic>=1) {
		fwrite(init,strlen(init),1,yyout);
		fwrite(";\n",2,1,yyout);
	}

	fwrite("while(",6,1,yyout);	
	if(cc>=1) 
		fwrite(cond,strlen(cond),1,yyout);
	else
		fwrite("true",4,1,yyout);

	fwrite(") {",3,1,yyout);
	BEGIN forstmt;
}

<forupd>\)[ \t\n]*\{ {
	if(ic>=1) {
		fwrite(init,strlen(init),1,yyout);
		fwrite(";\n",2,1,yyout);
	}

	fwrite("while(",6,1,yyout);	
	if(cc>=1) 
		fwrite(cond,strlen(cond),1,yyout);
	else
		fwrite("true",4,1,yyout);

	fwrite(") {",3,1,yyout);
	BEGIN forblock;
}
	
<forstmt>; {
	fwrite(";\n",3,1,yyout);

	if(uc>=1) {
		fwrite(upd,strlen(upd),1,yyout);
		fwrite(";\n}",3,1,yyout);
	} 
	else {
		fwrite("\n}",2,1,yyout);
	}

	BEGIN 0;
	strcpy(init,"");
	strcpy(cond,"");
	strcpy(upd,"");
	ic=0;
	cc=0;
	uc=0;
}
	
<forblock>\{[^}]*\} {
	fwrite(yytext,yyleng,1,yyout);
}
	
<forblock>\} {
	if(uc>=1) {
		fwrite(upd,strlen(upd),1,yyout);
		fwrite(";\n}",3,1,yyout);
	} 
	else {
		fwrite("\n}",2,1,yyout);
	}

	BEGIN 0;
	strcpy(init,"");
	strcpy(cond,"");
	strcpy(upd,"");
	ic=0;
	cc=0;
	uc=0;
}
%%

int yywrap(){
  return 1;
}

int main(int argc, char *argv[]) {
    yyin = fopen(argv[1], "r");
    yylex();
    fclose(yyin);
}