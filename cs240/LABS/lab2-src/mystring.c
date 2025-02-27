
#include <stdlib.h>
#include "mystring.h"

// Type "man string" to see what every function expects.

int mystrlen(char * s) {
  int i=0;
  while (s[i] != '\0'){
    i++;
  }
  return i;
}

char * mystrcpy(char * dest, char * src) {
	int i = 0;
	while (src[i] != '\0'){
	  dest[i] = src[i];
	  i++;
	}
	dest[i] = '\0';
	return dest;
}

char * mystrcat(char * dest, char * src) {
  int i = mystrlen(dest);
  int destAndsource = mystrlen(dest) + mystrlen(src);
  int j = 0;
  int k = 0;
  for(j=i ; j<destAndsource ; j++){
    dest[j] = src [k];
    k++;
  }
  dest[j] = '\0';
  return dest;
}

int mystrcmp(char * s1, char * s2) {
  int i = 0;
  if(mystrlen(s1)<mystrlen(s2)){
    return -1;
  }
  else if(mystrlen(s1) == mystrlen(s2)){
    return 0;
  }
  else return 1;
  
  for(i = 0; i<mystrlen(s1); i++){
    if(s1[i]<s2[i]){
      return -1;
    }
    else if(s2[i]<s1[i]){
      return 1;
    }
  }
    return 0;
}

char * mystrdup(char * s) {
	char * duplicate = malloc(mystrlen(s) + 1);

	int i=0;
	while(s[i]!='\0'){
		duplicate[i] = s[i];
		i++;
	}
	duplicate[i+1] = '\0';
	return duplicate;
}

char * mystrstr(char * hay, char * needle){
  int i;
  int j;
  int k = -1;
  char* string = malloc(mystrlen(hay));
  for(i=0;i<mystrlen(hay);i++){
    for(j=0;j<mystrlen(needle);j++){
      if(hay[i+j]!=needle[j]){
	break;
      }
      else{
	if(j == mystrlen(needle)-1){
	  k = i;
	}
	string[j]=needle[j];
      }
    }
  }
  if(k!=-1){
    j = mystrlen(needle);
  for(i = k+mystrlen(needle);i<mystrlen(hay);i++){
    string[j]=hay[i];
    j++;
  }
  }
  else {
    return NULL;
  }
  return string;
}

char*mymemcpy(char* dest,char*src,int n){
  int i;
  for(i=0;i<n;i++){
    dest[i] = src[i];
  }
  return dest;
}
