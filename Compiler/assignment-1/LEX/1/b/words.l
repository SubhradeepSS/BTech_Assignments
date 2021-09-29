%{
  int count = 0;
%}

%%

[\s]+ // Ignore Whitespaces

[a-zA-Z]*[aeiouAEIOU][a-zA-Z]*[t][a-zA-Z]*[aeiouAEIOU][a-zA-Z]*  {
  count++;
  printf("%s\n", yytext);
}

[a-zA-Z]*[aeiouAEIOU][a-zA-Z]*[t][a-zA-Z]*  {
  count++;
  printf("%s\n", yytext);
}

[a-zA-Z]*[t][a-zA-Z]*[aeiouAEIOU][a-zA-Z]*  {
  count++;
  printf("%s\n", yytext);
}

. {}

%%


int yywrap(){
  return 1;
}

int main() {
  printf("\n\nFollowing are the required words:\n");
  yylex();
  printf("\n%d words have at least 1 vowel and the letter 't'\n\n", count);
}