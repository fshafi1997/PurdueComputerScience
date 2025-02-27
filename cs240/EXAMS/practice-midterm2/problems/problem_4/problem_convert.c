
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#define MAX_DIGITS 32

// Problem (1/2)
/*****************************************************************************
 * TODO: convert a number from the given base to decimal
 *
 * Parameters: number -- the number you are converting an integer
 *             base   -- the base of the number you are given
 * 
 * Return: The number as an integer
 *
 * Return Type: int
*****************************************************************************/
int numValue(char num){
  if (num >= '0' && num <= '9'){
    return (int)num - '0';
  }
  else{
    return (int)num - 'A' + 10;
  }
}
int toInteger(char * number, int base) {
// Write Your Code Here
  int length = strlen(number);
  int pwr = 1;
  int x;
  int result = 0;

  for(x = length-1; x >= 0; x--){
    if(numValue(number[x]) >= base){
      printf("Wrong digit in number.\n");
      exit(1);
    }

    result = result + numValue(number[x])*pwr;
    pwr = pwr*base;
  }

  return result;
  
}

// Problem (2/2)
/*****************************************************************************
 * TODO: convert a number from the given base to decimal
 *
 * Parameters: number -- the number you are converting a string
 *             base   -- the base you are converting the numebr to
 * 
 * Return: The number as a string in base "base"
 *
 * Return Type: char *
*****************************************************************************/
void reverseString(char * string){
  int length = strlen(string);
  int x;

  for (x = 0; x<length/2; x++){
    char copy = string[x];
    string[x] = string[length-x-1];
    string[length-x-1] = copy;
  }
}
char charValue(int num){
  if(num >= 0 && num <= 9){
    return (char)(num + '0');
  }
  else{
    return (char)((num -10) + 'A');
  }
}
char * toBase(int number, int base) {
  int index = 0;
  char*result = malloc(10);
  while(number>0){
    result[index++] = charValue(number%base);
    number = number/base;
  }

  result[index] = '\0';

  reverseString(result);

  return result;
  
}
