lex def.l
gcc lex.yy.c
./a.out < input2.txt | sed '/^$/d'