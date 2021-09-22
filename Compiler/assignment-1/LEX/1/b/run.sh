lex words.l
gcc lex.yy.c
./a.out < input.txt | sed '/^$/d'