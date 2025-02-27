
#include <stdio.h>
#include "stack.h"
#include <stdlib.h>

int top=0;
double value;
double stack[MAXSTACK];

void stack_clear() 
{
  top = 0;
}

double stack_pop()
{
	// Add implementation here
  if(stack_top() == 0){
    printf("Stack has gone below limit");
  }
  else{
    top--;
  }
  return stack[top];
}

void stack_push(double val)
{
	// Add implementation here
  if(stack_top() == stack_max()){
    printf("Stack has gone above limit");
  }
  else{
    stack[top++] = val;
  }
}

void stack_print()
{
  int i;
  printf("Stack:\n");
  for(i=0;i<top;i++){
    printf("%d: %f\n",i,stack[i]);
    }
  if(top==0){
    printf("Stack is empty");
  }
}

int stack_top()
{
  return top;
}

int stack_max()
{
  return MAXSTACK;
}

int stack_is_empty()
{
  return top == 0;
}


