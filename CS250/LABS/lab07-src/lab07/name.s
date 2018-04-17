.section .data
input: .asciz "%s" //This one will be used for the scanf
input2: .asciz "%s"
firstname: .space 100 // This is going to intialize the string
lastname: .space 100 //Same as above
printStatement: .asciz "Hello, %s %s.\n" // This is the print function
question1: .asciz "Enter your first name: " //question to print
question2: .asciz "Enter your last name: " //question to print

.section .text
.global main

main:
  push {r4-r9, fp, lr}
  ldr r0, =question1 //This is going to ask for the question1 above
  bl printf

  ldr r0, = input
  ldr r1, = firstname //This is going to read the first name
  bl scanf //Going to read the string

  ldr r0, =question2 //This is going to ask for the question2 above
  bl printf

  ldr r0, =input2
  ldr r1, =lastname // This is going to read the last name
  bl scanf //Going to read the string

  ldr r0, =printStatement //This will load the printStatement
  ldr r1, =firstname //first argument for the statement
  ldr r2, =lastname //second argument for the statement

  bl printf // finally the print

  pop {r4-r9, fp, pc}//Program is finished here
