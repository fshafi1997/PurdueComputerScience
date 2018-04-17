#include <stdlib.h>
#include <string.h>
#include <stdio.h>
void printx(int);
void printd(int);

int myprintf(const char * format, ...){

  int index=0;
  int * stack = (int *) &format;     //pointer to the stack
  while(format[index]!='\0'){
    if(format[index]=='%'){

      index++;
      if(format[index] == '%'){//printing %
        putchar('%');
      }else if(format[index]=='c'){//printing character
        stack ++;
        char ch = (char) *stack;
        putchar(ch);      //printing character
      }else if(format[index]=='d'){
        stack ++;
        printd((int)*stack);        //calling printd function         
      }else if(format[index]=='s'){
        stack++;
        char * str = (char *) *stack;    
        int k =0;
        while(str[k]!='\0'){
          putchar(str[k]);
          k++;
        }
      }else{
        stack++;
        printx((int)*stack);
      }

    }else{
      putchar(format[index]);
    }
    index++;
  }


}
