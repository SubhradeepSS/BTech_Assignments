.model small

.data
	s_1 db "Enter string input: $"
	s_enter_string db 32 dup('$')
	b_entered_alpha db 26 dup(00h)
.code

inputChar proc
	mov ah, 01h
	int 21h
	ret
inputChar endp

outputChar proc
	mov ah, 02h
	int 21h
	ret
outputChar endp

getString proc
	lea si, s_enter_string
    	lea di, b_entered_alpha
    	xor ax, ax
    l1: 
        call inputChar
		xor ah, ah

        cmp al, 13
        je exit

	; Check for space
	cmp al, 20h
	je append

	; Check for caps
	cmp al, 61h
	jge subtract
	add al, 20h

    subtract:
    	mov bx, ax
	sub bx, 61h

	cmp byte ptr [di + bx], 01h
	je skip

	mov byte ptr [di + bx], 01h

    append:
        mov [si], al
        inc si

    skip:
        jmp l1
    
    exit:
	inc si

        ret
getString endp

printString proc
	mov ah, 09h
	int 21h
	ret
printString endp

newLine proc
	mov dl, 0ah
	call outputChar
	ret
newLine endp

main proc
	mov ax, @data
	mov ds, ax

	lea di, b_entered_alpha
	xor ax, ax
m_l1:	cmp ax, 26	
	je m_l2

	mov bx, ax
	mov byte ptr [di + bx], 00h

	inc ax
	jmp m_l1

m_l2:
	lea dx, s_1
	call printString
	call getString

	lea dx, s_enter_string
	call printString
	call newLine

	mov ah, 4ch
	int 21h
main endp

end main