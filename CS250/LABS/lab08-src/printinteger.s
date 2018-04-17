.global     printx
.global     printd

printx:
  push {lr}       //pushing the link register onto the top of stack
  mov r8, $0      //initializing r8 to 0 
  mov r7, r0      

mainLoop:
  and r1, r7, #0x000F @bit multiplication
  push {r1}       //pushing r1 to stack
  mov r1, $1      //storing 1 in r1
  add r8, r8, r1      //incrementing stack size by r1
  lsr r7, r7, $4      //shifting right by 4 getting next digit
  cmp r7, $0     
  bne mainLoop       

printingTheValue:
  cmp r8 ,$0      //if stack empty -> do not proceed
  beq last           //if stack empty -> done and branch to q
  pop {r2}        //getting value from stack and storing in r2
  sub r8, r8, $1      //decreasing the current size of stack by 1
  cmp r2, $10     //checking if r2 is in 0-9
  blt less       //if r2less than 9  branch to over to print 0...9
  add r0, r2, $55     //if r2greter than equal to9  get the ASCII for A...F and store in r0
  bl putchar     
  bl printingTheValue     

less:
  add r0, r2, $48     
  bl putchar      
  bl printingTheValue     

//below this code for printd

printd:
  push {lr}       //pushing link register onto top of stack      
  mov r6, $0      //initializing the size of stack to 0
  mov r7, r0      //storing argument into r7
  cmp r7, $0      
  blt negative         
  beq print0     

division:
  cmp r7, $0      //comparing r7 with 0
  beq printTheValue2     
  ldr r1, =#0xCCCCCCCD    //load r1 with 0xCCCCCCCD
  smull r1, r2, r1, r7    //dividing r1 by 10
  add r2, r2, r7      //adding r7 to r2
  mov r8, r2, lsr #3  //storing right shifted value of r2 by 3 into r8
  mov r5, $10    
  mul r10, r8, r5     
  sub r9, r7, r10    
  push {r9}       
  add r6, r6, $1      
  mov r7, r8    
  bl division       //call  to divide again

negative:
  mov r0, $45     //storing 45 in r0
  bl putchar      //printing - sign
  mov r4, $-1     //storing -1 into r4
  mul r7, r4, r7      //finding the - value of r7
  bl division        

printTheValue2:
  cmp r6, $0      //checking if stack is empty
  beq last           
  pop {r9}        
  sub r6, r6, $1      
  mov r0, r9      
  add r0, r0, $48     //getting the ASCII of r0
  bl putchar      //printing to screen
  bl printTheValue2      

print0:
  mov r0, $48     //storing ASCII 48 into r0
  bl putchar      //printing 0 
  bl last            

last:
  pop {lr}        //pop value on top into link register
  bx lr           //return to main
