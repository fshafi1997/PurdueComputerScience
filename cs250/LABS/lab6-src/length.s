.data
formatStr: .asciz "%s"
formatInt:.asciz "%d\n"
string: .word  0

.text

.global main
main:
push {r4-r9, fp, lr}
ldr r0, =formatStr
ldr r1, =string
bl scanf
ldr r2, =string

mov r5, #0

loop: 
add r5, r5, #1
add r2, r2, #1
ldr r3, [r2]
cmp r3,#0
bne loop

ldr r0, =formatInt
mov r1, r5
bl printf
pop {r4-r9, fp, pc}
