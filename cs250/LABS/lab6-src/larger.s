.data
i: .word 0
j: .word 0
formatIn: .string "%d"
format: .asciz "%d\n"

.text
.global main

main:
  push {fp,lr}
  ldr r0, =formatIn
  ldr r1, =i
  bl scanf

  ldr r0, =formatIn
      ldr r1, =j
      bl scanf

  ldr r1, =i
  ldr r2, [r1]
  ldr r1, =j
  ldr r3,[r1]
  cmp r2,r3
  bgt larger_than
  ldr r0, =format
      ldr r1, =j
      ldr r1, [r1]
      bl printf

  b miss

  larger_than:
  ldr r0, =format
  ldr r1, =i
  ldr r1, [r1]
  bl printf

  miss:

  pop {fp,pc}
