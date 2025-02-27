
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Problem (1/4)
/******************************************************************************
 * TODO: Print the array.
 *       The format should be (array index)(colon)(array element)(newline) 
 * 
 * Parameters: n -- the number of elements in the array
 *             array -- a double array
 *
 * Return: void 
 *
 * Return Type: void
 *****************************************************************************/
void printArray(int n, double * array) {
// Write Your Code Her

  int i=0;
  for(i=0;i<n;i++){
    printf("%d:%f\n",i,array[i]);
  }
}

// Problem (2/4)
/******************************************************************************
 * TODO: Return the minimum element of array 
 * 
 * Parameters: array -- a double array
 *             n -- the number of elements in the array
 *
 * Return: minimum element in array 
 *
 * Return Type: double
 *****************************************************************************/
double minArray(int n, double * array) {
// Write Your Code Here

  int i=0;
  double minimum = array[0];
  for(i=0;i<n;i++){
    if(array[i]<minimum){
      minimum = array[i];
    }
  }
  return minimum;
}

// Problem ( 3/4 ) 
/******************************************************************************
 * TODO: Reverse the given string 'str'. 
 * E.g. reverse_str("smile") should return "elims"
 * 
 * Parameters: str -- The given string to be reversed.
 *
 * Return: A pointer to str, str should be reversed 
 *
 * Return Type: char pointer
 *****************************************************************************/
char * reverse_str (char * str ) {
    // Write Your Code Here
  int length, c;
  char *begin, *end, temp;

  length = strlen(str);
  begin  = str;
  end    = str;

  for (c = 0; c < length - 1; c++){
    end++;
  }

  for (c = 0; c < length/2; c++)
    {
      temp   = *end;
      *end   = *begin;
      *begin = temp;

      begin++;
      end--;
    }
  return str;
}

// Problem ( 4/4 ) 
/******************************************************************************
 * TODO: Determine if the string str2 is a permutation of str1. A permutation
 * is the rearranging of characters in a different order. 
 * E.g. the string "act" is a permutation of "cat" 
 *
 * Hint: count the occurences of each letter
 * 
 * Parameters: str1 -- The original string
 *	       str2 -- Determine if this string is a permutation of str1 
 *
 * Return: 1 if str2 is a permutation
 *         0 if str2 is not a permutation
 *
 * Return Type: integer
 *****************************************************************************/
char * sort(char * x){
  int len1 = strlen(x);
  char * str = strdup(x);
  char temp;
  for(int i=0;i<len1;i++){
    for(int j=0;j<len1-i;j++){
      if(str[j]>str[j+1] && str[j+1]!='\0'){
	  temp = str[j];
	  str[j] = str[j+1];
	  str[j+1] = temp;
      }
    }
  }
  return str;
}
      

int is_permutation ( char * str1, char * str2 ) {
    // Write Your Code Here
  int len1 = strlen(str1);
  int len2 = strlen(str2);

  if(len1!=len2){
    return 0;
  }

  char * sorted1 = malloc(len1);
  sorted1 = sort(str1);

  char* sorted2 = malloc(len2);
  sorted2 = sort(str2);

  if(strcmp(sorted1,sorted2)==0){
    return 1;
  }
  else{
    return 0;
  }
			  
}
