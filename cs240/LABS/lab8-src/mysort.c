
#include "mysort.h"
#include <alloca.h>
#include <assert.h>
#include <string.h>
#include <stdlib.h>

//
// Sort an array of element of any type
// it uses "compFunc" to sort the elements.
// The elements are sorted such as:
//
// if ascending != 0
//   compFunc( array[ i ], array[ i+1 ] ) <= 0
// else
//   compFunc( array[ i ], array[ i+1 ] ) >= 0
//
// See test_sort to see how to use mysort.
//
void mysort( int n,                      // Number of elements
	     int elementSize,            // Size of each element
	     void * array,               // Pointer to an array
	     int ascending,              // 0 -> descending; 1 -> ascending
	     CompareFunction compFunc )  // Comparison function.
{
	// Add your code here
  void * tmp = malloc(elementSize);
  int i = 0;
  while (i < n - 1){
    int j = i + 1;
    while (j < n){
      void * e1 = (void*)((char*)array+i*elementSize);
      void * e2 = (void*)((char*)array+j*elementSize);
      if (ascending){
	if ((*compFunc)(e1,e2)>0){
	  memcpy(tmp, e1, elementSize);
	  memcpy(e1, e2, elementSize);
	  memcpy(e2, tmp, elementSize);
	}
      }
      else{
	if ((*compFunc)(e1, e2)<0){
	  memcpy(tmp, e1, elementSize);
	  memcpy(e1, e2, elementSize);
	  memcpy(e2, tmp, elementSize);
	}
      }
      j ++;
    }
    i ++;
  }
  free(tmp);
}

