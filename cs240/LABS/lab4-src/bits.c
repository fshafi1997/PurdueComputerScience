

#include <stdio.h>
#include <string.h>

// It prints the bits in bitmap as 0s and 1s.
void printBits(unsigned int bitmap){
	// Add your code here
  int i=31;
  int j=0;
  while(i>=0){
    j = bitmap >> i;
    if(j&1){
      printf("1");
    }
    else{
      printf("0");
    }
    i--;
  }
  printf("\n");

  i=31;
  while(i>=0){
    int x =  i%10;
    printf("%d",x);
    i--;
  }
  printf("\n");
}


// Sets the ith bit in *bitmap with the value in bitValue (0 or 1)
void setBitAt( unsigned int *bitmap, int i, int bitValue ) {
	// Add your code here
  if (bitValue == 1){
    unsigned mask = (1<<i);
    *bitmap = *bitmap|mask;
  }
  else{
    unsigned mask = (1<<i);
    unsigned mask0 = ~mask;
    *bitmap = (*bitmap&mask0);
  }
}

// It returns the bit value (0 or 1) at bit i
int getBitAt( unsigned int bitmap, unsigned int i) {
	// Add your code here
  int j;
  j = bitmap >> i;
  if (j & 1){
    return 1;
  }
  else{
    return 0;
  }
}

// It returns the number of bits with a value "bitValue".
// if bitValue is 0, it returns the number of 0s. If bitValue is 1, it returns the number of 1s.
int countBits( unsigned int bitmap, unsigned int bitValue) {
	// Add your code here
  int i = 31;
  int j;
  int counter = 0;
  while (i >= 0){
    j = bitmap >> i;
    if (bitValue){
      if (j & 1){
	counter ++;
      }
    }
    else if(!(j & 1)){
	  counter ++;
    }
    i--;
  }
  return counter;
}

// It returns the number of largest consecutive 1s in "bitmap".
// Set "*position" to the beginning bit index of the sequence.
int maxContinuousOnes(unsigned int bitmap, int * position) {
	// Add your code here
  int j;
  int counter = 0;
  int max = 0;
  int start = 0;
  int i = 31;
  while (i >= 0){
    j = bitmap >> i;
    if((j & 1) && counter == 0){
      counter ++;
    }
    else if(!(j & 1)){
      if(counter > max){
	max = counter;
	start = i;
	counter = 0;
      }
      else{
	counter = 0;
      }
    }
    else if((j & 1) && counter != 0){
      counter ++;
    }
    i--;
  }
  *position = start + 1;
  return max;
}

