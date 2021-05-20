data segment
    n db 0011b
    res db ?
data ends

code segment
assume cs:code, ds:data
start:
    mov ax, data
    mov ds, ax
    mov al, n
    mov bl, al
    shr al, 1
    xor bl, al
    mov res, bl
    int 3
code ends
end start