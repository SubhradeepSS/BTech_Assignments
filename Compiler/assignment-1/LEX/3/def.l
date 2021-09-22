%{
  #include <stdio.h>
  #include <stdlib.h>
  #include <string.h>
  #define DATA_SIZE 10000
  char data[DATA_SIZE];
  int curr_idx = 0;
%}


%%

[#define][a-zA-Z0-9_ ]* {
    int key=0;
    for(int i=7; i<strlen(yytext); i++){
        if(yytext[i] == ' '){
            if(!key) {
                key = 1;
            }
            else {
                data[curr_idx++] = ' ';
                data[curr_idx++] = '=';
                data[curr_idx++] = ' ';
            }
        }
        else {
            data[curr_idx++] = yytext[i];
        }
    }
}

[\n] {
    data[curr_idx++] = '\n';
}

%%


int yywrap(){
  return 1;
}

int main() {
  yylex();

  FILE * fPtr;
  fPtr = fopen("output.txt", "w");

  if(fPtr == NULL)
  {
      printf("\nUnable to create output file\n");
      exit(EXIT_FAILURE);
  }

  fputs(data, fPtr);
  fclose(fPtr);

  printf("All #define in input have been replaced with functions in output\n");
}