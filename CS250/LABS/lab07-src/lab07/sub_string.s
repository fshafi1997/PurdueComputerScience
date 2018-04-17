.section .data
string: .asciz "%s" //For the string
.section .text
.global sub_string

sub_string:
  push {r4-r9, fp, lr}
  ldr r4, =string
  mov r3, #1

  startindex:
    cmp r3, r1
    beq subloop
    ldrb r5, [r0], #1
    add r3,r3, #1
    b startindex

  subloop:
    cmp r3,r2
    bgt result
    ldrb r5, [r0], #1
    strb r5, [r4], #1
    add r3,r3,#1
    b subloop

  result:
    ldr r0, =string
    bx lr
    pop {r4-r9, fp, lr}
