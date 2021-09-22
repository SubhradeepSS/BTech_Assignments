%{
  #include <stdio.h>
  #include <stdlib.h>
  #define DATA_SIZE 10000
  char data[DATA_SIZE];
  int curr_idx = 0;
%}


%%

[e] {
  data[curr_idx++] = 'y';
}

. { 
  data[curr_idx++] = yytext[0];
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

  printf("\nAll 'e' in input paragraph succesfully replaced with 'y'\n");
}