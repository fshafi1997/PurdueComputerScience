.section .data

input: .asciz "%s" //This will be used for scanf
inputNumber: .asciz "%d" //Used for the number
srcString: .asciz "" //Intializing the string
question1: .asciz "Enter a string: "
question2: .asciz "Enter the start index: "
question3: .asciz "Enter the end index: "
printStatement: .asciz "The substring of the given string is '%s'\n"
sIndex: .word 0 //This is the start index
eIndex: .word 0 //This is the end index
result: .asciz "" //This is the output of substring mehtod

.section .text

.global main

main:
  push {r4-r9, fp, lr}
  ldr r0, =question1 //This asks the first question
  bl printf

  ldr r0, =input
  ldr r1, =srcString //Reads the string
  bl scanf //Reads the string

  ldr r5, = srcString

  ldr r0, =question2 //This asks for the second question
  bl printf

  ldr r0, = inputNumber
  ldr r1, = sIndex //Reading the start index
  bl scanf //REading
  ldr r6, =sIndex
  ldr r6, [r6]

  ldr r0, =question3 //This asks for the third question
  bl printf

  ldr r0, = inputNumber
  ldr r1, = eIndex //This is the end index
  bl scanf //REads the end index
  ldr r7, =eIndex
  ldr r7, [r7]

  mov r0, r5
  mov r1, r6
  mov r2, r7
  bl sub_string

  mov r1, r0

  ldr r0, =printStatement
  bl printf

  pop {r4-r9, fp, pc}

