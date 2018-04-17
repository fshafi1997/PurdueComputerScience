
#include <stdio.h>
#include <stdlib.h>

//
// Separates the file into words
//
 
#define MAXWORD 200
char word[MAXWORD];
int wordLength;

// It returns the next word from fd.
// If there are no more more words it returns NULL. 
char * nextword(FILE * fd) {
  char c;
  int i=0;
  int wordCheck = 0;
	
  
  while((c=fgetc(fd))!=-1){
		// While it is not EOF and it is a non-space char
	      if(c!=' ' && c!='\n' && c!='\r' && c!='\t'){	  
		// store character in word
		word[i] = c;
		i++;
	      }
	      // if char is not in word return word so far
	      if(i!=0){
	      if(c==' ' || c=='\n' || c=='\r' || c=='\t'){
		  word[i]='\0';
		  return word;
	      }
	      }
  }
  if(i>0){
    word[i] = '\0';
    return word;
  }
		//
	// Return null if there are no more words
	return NULL;
}
