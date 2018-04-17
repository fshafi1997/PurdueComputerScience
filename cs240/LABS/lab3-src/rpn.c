


#include <string.h>
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <math.h>

#include "rpn.h"
#include "nextword.h"
#include "stack.h"

#define PI 3.14159265

double rpn_eval(char * fileName, double x) {
  double a,b,c,temporary,doubleNumber;
  int intNumber;
  FILE*fd;
  fd=fopen(fileName,"r");

  char*word;
  while(word = nextword(fd)){
    intNumber = atoi(word);
    doubleNumber = atof(word);

    if(strcmp(word,"+") == 0){
      if(stack_top()>=2){
	stack_push(stack_pop()+stack_pop());
      }
      else{
	printf("Stack underflow\n");
	exit(1);
      }
    }
    else if(strcmp(word,"*") == 0){
      stack_push(stack_pop()*stack_pop());
    }
    else if(strcmp(word,"-") == 0){
     temporary = stack_pop();
      stack_push(stack_pop()-temporary);
    }
    else if(strcmp(word,"/") == 0){
      temporary = stack_pop();
      if(temporary != 0.0){
	stack_push(stack_pop()/temporary);
      }
      else{
	printf("Cannot be divided by 0");
      }
    }
    else if(strcmp(word,"cos") == 0){
      stack_push(cos(stack_pop()));
    }
    else if(strcmp(word,"sin") == 0){
      stack_push(sin(stack_pop()));
    }
    else if(strcmp(word,"pow") == 0){
      stack_push(pow(stack_pop(),stack_pop()));
    }
    else if(strcmp(word,"log") == 0){
      stack_push(log(stack_pop()));
    }
    else if(strcmp(word,"exp") == 0){
      stack_push(exp(stack_pop()));
    }
    else if(strcmp(word,"x") == 0){
      stack_push(x);
    }
    else {
      stack_push(doubleNumber);
    }
  }
  if(stack_top()==1){
  double result = stack_pop();
    return result;
  }
  else{
    printf("Elements remain in the stack\n");
    exit(1);
  }
}

