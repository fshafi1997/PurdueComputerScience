
#include <stdio.h>

#include "array.h"

// Return sum of the array
double sumArray(int n, double * array) {
  double sum = 0;
  
  double * p = array;
  double * pend = p+n;

  while (p < pend) {
    sum += *p;
    p++;
  }

  return sum;
}

// Return maximum element of array
double maxArray(int n, double * array) {
  double * copy = array;
  double max = *array;

  while(*copy!='\0'){
    if(*copy>max){
      max = *copy;
    }
    copy++;
  }
  
  return max;
}

// Return minimum element of array
double minArray(int n, double * array) {
  double * copy = array;
  double min = *array;

  while(*copy!='\0'){
    if(*copy<min){
      min = *copy;
    }
    copy++;
  }

  return min;
}

// Find the position int he array of the first element x 
// such that min<=x<=max or -1 if no element was found
int findArray(int n, double * array, double min, double max) {
  int counter = 0;
  double * x = array;
  double * pos = x +n;
  while(x<pos){
    if(*x>=min && *x<=max){
      return counter;
    }
    x++;
    counter++;
  }
  return -1;
}

// Sort array without using [] operator. Use pointers 
// Hint: Use a pointer to the current and another to the next element
int sortArray(int n, double * array) {
   double temp;
   int i,j;
  for(i=0;i<n;i++){
    for(j=i+1;j<n;j++){
      if(*(array+i) > *(array+j)){
	temp = *(array+i);
	*(array+i) = *(array+j);
	*(array+j) = temp;
      }
    }
    }
}

// Print array
void printArray(int n, double * array) {
  double * copy = array;
  int counter = 0;
  while (*copy!='\0' && counter < n){
    printf("%d:%f\n", counter, *copy);
    copy++;
    counter++;
  }
}

