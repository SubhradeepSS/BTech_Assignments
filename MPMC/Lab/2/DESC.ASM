data segment
  arr db 5h, 3h, 2h, 4h, 1h
  sz db 05h ; size = 5
data ends

code segment
assume cs:code, ds:data
start:
  mov ax, data
  mov ds, ax
  
  ; Store size of array in CH & CL
  mov ch, sz
  dec ch

  ; This loop runs from i = 0 to size - 1
InitInnerLoop:
  ; initalize the inner loop again
  mov cl, sz
  dec cl
  lea si, arr

InnerLoop:
  ; This loop runs from j = 0 to size - 1
  ; Swaps if arr[j] > arr[j + 1]
  mov al, [si]
  mov bl, [si + 1]
  cmp al, bl
  
  jnc Continue ; Descending
  mov dl, [si + 1] 
  xchg [si], dl
  mov [si + 1], dl

Continue:
  inc si
  dec cl
  jnz InnerLoop
  dec ch
  jnz InitInnerLoop

  ; Interrupt
  int 3

code ends
end start
