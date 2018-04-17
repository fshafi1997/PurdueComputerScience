/*
 *  CS 24000 First mid-semester exam.
 *   Problem #3-3
 *
 *  void censorWord(char * str, char * badword)
 *
 *  Arguments:
 *		char * str -- a pointer to a string
 *      char * badword -- a word to replace in string
 *		
 *  Description:
 *		Given a string of input, replace all occurrences of the word badword in str 
 *		with X's. The word must have whitespace on both sides (space, tab, newline
 *		or carriage return), unless it is at the beginning/end of the string str, 
 *		and must match case. Word will not have any whitespace characters in it.
 *		For example, 
 *			str = "The penguin is in the theater"
 *			censorWord(str, "the");
 *			This would result in str pointing to "The penguin is in XXX theater"
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

int mystlen(char* str);

int mystlen(char * str){
  int i = 0;
  while(str[i]!='\0'){
    i++;
  }
  return i;
}


void censorWord(char * str, char * badword){
  int lenBad = mystlen(badword);
  int flag = 0,flag2 = 1;
  int i = 0,j = 0, k = 0;

  while(str[i] != '\0'){
    if(str[i] == badword[0]){
      if(i == 0){
	flag =1;
      }
      if(i!=0){
	if(str[i-1] == ' ' || str[i-1] == '\n' || str[i-1] == '\r' || str[i-1] == '\t'){
	  flag = 1;
	}
      }
    }
    if(flag == 1){
      if(str[i+lenBad] == ' ' || str[i+lenBad] == '\n' || str[i+lenBad] == '\r' || str[i+lenBad] == '\t' || str[i+lenBad] == '\0'){
	k=0;
	flag2 = 1;
	  while(badword[k]!='\0'){
	    if(str[i+k] != badword[k]){
	      flag2 = 0;
	    }
	    k++;
	  }
	  if(flag2 == 1){
	    for(j=i;j<(i+lenBad);j++){
	      str[j] = 'X';
	    }
	  }
      }
      flag = 0;
    }
    i++;
  }
}

  
 /* ======================== End your edits here =========================== */

 /*
 ************************************************************************
  *  Do NOT change any of the lines between here and the end of the file!
 ************************************************************************
  */

#include <stdio.h>
#include <assert.h>

char *Usage = "Usage: %s <string> <word>\n";

int main(int argc, char *argv[])
{
	extern char * strdup(const char *);
	char * copy;
	
	if (argc != 3) 
	{
		fprintf(stderr, Usage, argv[0]);
		exit(1);
	}
	assert(copy = strdup(argv[1]));
	censorWord(copy, argv[2]);

	printf("String \"%s\" after censorWord is \"%s\"\n", argv[1], copy);
	
	return 0;
}
	
	
