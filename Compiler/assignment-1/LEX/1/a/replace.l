%{
  #include <stdio.h>
  #include <stdlib.h>
  #define DATA_SIZE 10000

%}


%%

[e] {
  printf("y");
}

. { 
  printf("%c", yytext[0]);
}

%%


int yywrap(){
  return 1;
}

int main() {
  printf("\nAll 'e' in paragraph has been replaced by 'y': \n\n");

  yylex();

}