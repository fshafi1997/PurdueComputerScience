
/*
 *  CS 24000 First mid-semester exam.
 *   Problem #22
 *
 *  int isPalindrome(char * str)
 *
 *  Arguments:
 *		char * str -- a pointer to a string
 *
 *  Return:
 *		1 if the string is a palindrome, 0 otherwise
 *		
 *  Description:
 *		Given a string of input, return 1 if str is a palindrome, 0 otherwise. A 
 * 		palindrome is a word, phrase, or sequence that reads the same backward as
 *		forward. The function ignores all white spaces (space, tab, newline or 
 * 		carriage return). For example, "madam" and "nurses run" are palindromes.
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

int mystlen(const char * str){
  int i = 0;
  while(str[i]!='\0'){
    i++;
  }
  return i;
}

int mystcmp(const char * str1, const char * str2) {
  int i = 0;
  int greater;
  if(mystlen(str1)>=mystlen(str2)){
    greater = mystlen(str1);
  }
  else{
    greater = mystlen(str2);
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


int isPalindrome(char * str){
  int length = mystlen(str);
  int i,j,counter=0;
  char * nospace = malloc(length);
  char * copy = malloc(length);

  for(j=0;j<length;j++){
    if(str[j]!=' ' && str[j]!='\t' && str[j]!='\r' && str[j]!='\n'){
      nospace[counter] = str[j];
      counter++;
    }
  }
  nospace[j]  = '\0';
  int lengthnew = mystlen(nospace);
  
  for(i=0;i<lengthnew;i++){
    copy[lengthnew-1-i] = nospace[i];
  }

  if(mystcmp(nospace,copy) != 0){
    return 0;
  }
  else return 1;  
}
  
 /* ======================== End your edits here =========================== */

 /*
 ************************************************************************
  *  Do NOT change any of the lines between here and the end of the file!
 ************************************************************************
  */

#include <stdio.h>
#include <assert.h>

char *Usage = "Usage: %s <string>\n";

int main(int argc, char *argv[])
{
	unsigned int result;
		
	if (argc != 2) 
	{
		fprintf(stderr, Usage, argv[0]);
		exit(1);
	}
	
	result = isPalindrome(argv[1]);

	printf("%s %s a palindrome\n", argv[1], (result == 1) ? "is" : "is not");
	
	return 0;
}
