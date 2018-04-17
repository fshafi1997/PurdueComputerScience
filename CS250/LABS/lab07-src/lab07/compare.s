.data
first: .word 0
second: .word 0
inputFormat: .string "%d"
outputFormat: .string "%d and %d are equal.\n"

print1: .asciz "Enter the first number: "
print2: .asciz "Enter the second number: "
outFormGreater: .string "%d is strictly greater than %d by %d.\n" 

.text

.global main
main:
  push {fp, lr}
  ldr r0, =print1
  bl printf
  ldr r0, =inputFormat
  ldr r1, =first
  bl scanf//This is reading in the string

  ldr r0, =print2
  bl printf

  ldr r0, =inputFormat
  ldr r1, =second
  bl scanf

  ldr r0, =first
  ldr r0, [r0]
  ldr r1, =second
  ldr r1, [r1]
  cmp r0, r1
  beq equal

  blt smaller
  bgt bigger

  print:
    ldr r0, =outFormGreater
    ldr r1, [r1]
    ldr r2, [r2]
    sub r3, r1, r2
    bl printf

  exit:
    pop {fp,pc}
  
  bigger:
    ldr r1, =first
    ldr r2, =second
    bl print

  equal:
    ldr r0, =outputFormat
    ldr r1, =first
    ldr r1, [r1]
    ldr r2, =first
    ldr r2, [r2]
    bl printf
    bl exit

  smaller:
    ldr r1, =second
    ldr r2, =first
    bl print
