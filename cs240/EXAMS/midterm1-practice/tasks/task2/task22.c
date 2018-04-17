
/*
 *  CS 24000 First mid-semester exam.
 *   Problem #2-1
 *
 *  int mystrcmp(const char * str1, const char * str2) 
 *
 *  Arguments:
 *		char * str1 -- a pointer to a string
 *		char * str2 -- a pointer to a second string
 *		
*  Function return: 
 *      int indicating if the strings are equal (0), 
 *		or first nonmatching character in str1 is less than in str2(-1), or
 *		first nonmatching character in str1 is greater than in str2(1).
 *
 *  Description:
 *		The function considers letter case and white spaces. It returns an integer less 
 *		than, equal to, or greater than zero if str11 is found, respectively, to be less
 *		than, to match, or be greater than str2. 
 *		For example, mystrcmp("Hello", "hello") = -1,  mystrcmp("Hello ", " Hello") = 1.  
 *		You are not allowed to use any routines including strcmp of the C String library.
 *
 */
 
 /* ======================== Start your edits here =========================== */
 
 /*
  *  NOTE NOTE NOTE NOTE NOTE
  *  You are not allowed to add any other libraries or library includes in addition
  *   to <stdlib.h> other than <math.h> (if you believe you need it).
  */

 #include <stdlib.h>
 
 /* 
  * Complete the function below for the exam
  */
int mystrlen(const char * s1){
  int x;
  while(s1[x]!='\0'){
    x++;
  }
  return x;
}

int mystrcmp(const char * str1, const char * str2) {
  int i = 0;
  int greater;
  if(mystrlen(str1)>=mystrlen(str2)){
    greater = mystrlen(str1);
      }
  else{
    greater = mystrlen(str2);
  }

  for(i=0;i<greater;i++){
    if(str1[i] == '\0'){
      return -1;
    }
    if(str2[i] == '\0'){
      return 1;
    }
      if(str1[i]<str2[i]){
	return -1;
      }
      else if(str1[i]>str2[i]){
	return 1;
      }
  }
  return 0;
}
    
  
 /* ======================== End your edits here =========================== */

 /*
 ************************************************************************
  *  Do NOT change any of the lines between here and the end of the file!
 ************************************************************************
  */

#include <stdio.h>

char *Usage = "Usage: %s <string> <string2>\n";

int main(int argc, char *argv[])
{
	int result;
	
	if (argc != 3) 
	{
		fprintf(stderr, Usage, argv[0]);
		exit(1);
	}
	
	result = mystrcmp(argv[1], argv[2]);
	
	printf("Strings are %s, return value is %d\n", result ? "different" : "the same", result);
	
	return 0;
}
	
	
