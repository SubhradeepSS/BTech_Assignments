%{
    #include <ctype.h>
    #include <stdio.h>
    #include <stdlib.h>

	char stack[100];
    int top_idx=0;
    void push(char);
	void pop();
    char top();
    int priority(char);

    char postfix_output[100];
    int postfix_idx=0;

    char input_data[100];
    int input_len = 1;
	
%}


%%
. {
    input_data[input_len++] = yytext[0];
}  
%%


int yywrap(){
  return 1;
}

void infixToPrefix() {
    input_data[0] = '(';
    input_data[input_len++] = ')';
 
    for (int i = 0; i < input_len; i++) {
        if (isalpha(input_data[i]) || isdigit(input_data[i]))
            postfix_output[postfix_idx++] = input_data[i];
 
        else if (input_data[i] == '(')
            push('(');
 
        else if (input_data[i] == ')') {
            while (top() != '(') {
                postfix_output[postfix_idx++] = top();
                pop();
            }
 
            pop();
        }
 
        // Operator found
        else
        {
            if (!isalpha(top()) && !isdigit(top()))
            {
                if(input_data[i] == '^')
                {
                      while (priority(input_data[i]) <= priority(top()))
                       {
                         postfix_output[postfix_idx++] = top();
                         pop();
                       }
                     
                }
                else
                {
                    while (priority(input_data[i]) < priority(top()))
                       {
                         postfix_output[postfix_idx++] = top();
                         pop();
                       }
                     
                }
 
                // Push current Operator on stack
                push(input_data[i]);
            }
        }
    }
}

int main(){
    yylex();

    printf("\nInput Infix expression: ");
    for(int i=1; i<input_len; i++){
        printf("%c", input_data[i]);
    }

    // reverse input
    int l=1, r=input_len-1;
    while(l<r){
        char t = input_data[l];
        input_data[l] = input_data[r];
        input_data[r] = t;
        l++;
        r--;
    }

    // Replace ( with ) and vice versa
    for (int i = 1; i < input_len; i++) {
        if (input_data[i] == '(') {
            input_data[i] = ')';
        }
        else if (input_data[i] == ')') {
            input_data[i] = '(';
        }
    }

    infixToPrefix();

    printf("\nOutput prefix expression: ");

    for(int i=postfix_idx-1; i>=0; i--){
        printf("%c", postfix_output[i]);
    }

    printf("\n\n");
}

void pop(){
 top_idx--;
}

char top(){
   return stack[top_idx-1];
}
  
void push(char c){
   stack[top_idx++]=c;
}

int priority(char C){
    if (C == '-' || C == '+')
        return 1;
    else if (C == '*' || C == '/')
        return 2;
    else if (C == '^')
        return 3;
    return 0;
}