%{
  int count = 0;
%}

%%

[\s]+ // Ignore Whitespaces

[a-zA-Z]*[aeiouAEIOU][a-zA-Z]*[t][a-zA-Z]*[aeiouAEIOU][a-zA-Z]*  {
  count++;
}

[a-zA-Z]*[aeiouAEIOU][a-zA-Z]*[t][a-zA-Z]*  {
  count++;
}

[a-zA-Z]*[t][a-zA-Z]*[aeiouAEIOU][a-zA-Z]*  {
  count++;
}

. {}

%%


int yywrap(){
  return 1;
}

int main() {
  yylex();
  printf("\n\n%d words have at least 1 vowel and the letter 't'\n", count);
}