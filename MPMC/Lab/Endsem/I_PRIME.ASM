.MODEL SMALL

.STACK 100H
.DATA
    MSG DB 13,10,"is a Prime No$"
    NMSG DB 13,10,"is not a Prime No$"
    MSG1 DB 13, 10, 'Enter input no:','$'
    NUM db 0
    VALUE DB 0

.CODE
    START: 
    MOV AX,@DATA
    MOV DS,AX
    mov ah,9    
    LEA DX,MSG1
    int 21h
    READ:
    mov ah,1    
    int 21h
    CMP AL,13
    JE ENDOFNUM
    MOV VALUE,AL
    SUB VALUE,48
    MOV AL,NUM
    MOV BL,10
    MUL BL

    ADD AL,VALUE
    MOV NUM,AL

    JMP READ
    ENDOFNUM:


    MOV AL,NUM
    MOV BL,02H      
    MOV DX,0000H    
    MOV AH,00H      

    ;Loop to check for Prime No
    L1:DIV BL
    CMP AH,00H      
    JNE NEXT
    INC BH          
    NEXT:CMP BH,02H 
    JE FALSE        
    INC BL          
    MOV AX,0000H    
    MOV DX,0000H   
    MOV AL,NUM     
    CMP BL,NUM      
    JNE L1          

    ;To display The given no is a Prime No
    TRUE: LEA DX,MSG
    MOV AH,09H     
    INT 21H
    JMP EXIT

    ;To display The given no is not a Prime No
    FALSE: LEA DX,NMSG
    MOV AH,09H      
    INT 21H


    EXIT:
    MOV AH,4CH
    INT 21H

END START