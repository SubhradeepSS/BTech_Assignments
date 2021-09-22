%{
	char stack[100];
	int top_idx=0;
	void push(char);
	void pop();
    char top();
    int priority(char c);
%}


%%
[a-zA-Z]? { 
    printf("%c", yytext[0]);
}

[+\-\*\/\(\)] {
    char op = yytext[0];
    if(top_idx==0 || op=='(') {
        push(op);
    }
        
    else if(op == ')') {
        while(top() != '('){
            printf("%c", top());
            pop();
        }
        pop();
    }
                   
    else if(priority(op) > priority(top())){
        push(op);
    }

    else if(priority(op) <= priority(top())) {
        while(priority(op) <= priority(top())){
            printf("%c", top());
            pop();
        }
        push(op);
    }
}           
%%


int yywrap(){
  return 1;
}

int main(){
    yylex();

    for(int i=top_idx-1; i>=0; i--) {
        if(stack[i]!='(' && stack[i]!=')'){
            printf("%c", stack[i]);
        }
    }
    
    top_idx=0; 
    printf("\n");
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

int priority(char c){
    switch(c){
        case '(':return 0;
        case ')':return 0;
        case '+':return 1;
        case '-':return 1;
        case '*':return 2;
        case '/':return 2;
    }
}