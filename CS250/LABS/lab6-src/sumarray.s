.data
numaddress: .word 0
format: .asciz "%d"
formatnew: .asciz "%d\n"
num: .word 0
count: .word 0
.text
.global main

main:

push {r4-r9,fp,lr}

mov r2, #0
mov r5, #0
loop:
ldr r0, =format
ldr r1, =numaddress
bl scanf

ldr r2, =numaddress
ldr r3, [r2]
add r4, r4, r3

add r5, r5, #1
cmp r5, #4
ble loop

ldr r0, =formatnew
mov r1, r4
bl printf



pop {r4-r9,fp,pc}
