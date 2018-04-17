
#include <stdlib.h>
#include "mystring.h"

int mystrlen(char * s) {
  int i=0;

  while(*s!='\0'){
    i++;
    s++;
  }
  return i;
}

char * mystrcpy(char * dest, char * src) {
  char * p = src;
  char * q = dest;
  while(*p!='\0'){
      *q = *p;
      p++; 
      q++;
  }
  *q = '\0';
    return dest; 
}

char * mystrcat(char * dest, char * src) {
  char *p,*q;
  p=dest;
  while(*p) { p++; }
  q=src;
  while(*q){
      *p=*q;
      p++;
      q++;
    }
  *p='\0';
  return dest;
}

int mystrcmp(char * s1, char * s2) {
  while(*s1 != '\0' && *s2 != '\0' && *s1 == *s2){
    s1 ++;
    s2 ++;
  }
  if(*s1 > *s2){
    return 1;
  }
  else if(*s1 < *s2){
    return -1;
  }
  else{
    return 0;
  }
}

char * mystrstr(char * hay, char * needle) {
  char *hayCpy = hay;
  char *needleCpy = needle;
  while(*hayCpy != '\0'){
    if(*hayCpy == *needleCpy){
      char *n = needleCpy;
      char *h = hayCpy;
      while(*n!='\0' && *h!='\0' && *n == *h){
        n++;
	h++;
      }
      if(*n == '\0'){
	return hayCpy;
      }
    }
    hayCpy ++;
  }
  return NULL;
}

char * mystrdup(char * s) {
  char *copy = malloc(mystrlen(s));
  mystrcpy(copy,s);
  return copy;
  free(copy);
}

char * mymemcpy(char * dest, char * src, int n){
  char * destCpy = dest;
  char * srcCpy = src;
  int counter = 1;
  while (*srcCpy != '\0' && counter < n){
    *destCpy = *srcCpy;
    destCpy++;
    srcCpy++;
    counter++;
  }
  return dest;
}

