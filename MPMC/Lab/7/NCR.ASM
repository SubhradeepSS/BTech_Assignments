data segment
	n dw 5
	r dw 2
	ncr dw ?
data ends
code segment
assume cs:code,ds:data
start:
	mov ax,data
	mov ds,ax
	mov ax,n
	mov bx,n
	sub bx,1
	call fact
	push ax
	mov ax,r
	mov bx,r
	sub bx,1
	call fact
	push ax
	mov ax,n
	mov bx,r
	sub ax,bx
	mov bx,ax
	sub bx,1
	call fact
	push ax
	pop cx
	pop bx
	pop ax
	div bx
	div cx
	mov ncr,ax
	jmp end1
fact:
	cmp bx,0
	jz result
	mul bx
	sub bx,1
	call fact
result:
	ret
end1:
	int 3
code ends
end start