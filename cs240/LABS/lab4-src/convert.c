#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


int numValue(char num){
  if (num >= '0' && num <= '9'){
    return (int)num - '0';
  }
  else{
    return (int)num - 'A' + 10;
  }
}

char charValue(int num){
  if(num >= 0 && num <= 9){
    return (char)(num + '0');
  }
  else{
    return (char)((num -10) + 'A');
  }
}

int toDecimal(char * string, int baseto){
  int length = strlen(string);
  int pwr = 1;
  int x;
  int result = 0;

  for(x = length-1; x >= 0; x--){
    if(numValue(string[x]) >= baseto){
      printf("Wrong digit in number.\n");
      exit(1);
    }
    
    result = result + numValue(string[x])*pwr;
    pwr = pwr*baseto;
  }

  return result;
}

void reverseString(char * string){
  int length = strlen(string);
  int x;

  for (x = 0; x<length/2; x++){
      char copy = string[x];
      string[x] = string[length-x-1];
      string[length-x-1] = copy;
    }
}

char * fromDecimal(char result[], int base, int number){
  int index = 0;

  while(number>0){
    result[index++] = charValue(number%base);
    number = number/base;
  }

  result[index] = '\0';

  reverseString(result);

  return result;
}



int main(int argc, char ** argv){
  if(argc!=4){
    printf("Usage:  convert <basefrom> <baseto> <number>\n");
    exit(1);
  }
  
  int basefrom = atoi(argv[1]);
  int baseto = atoi(argv[2]);
  int number = atoi(argv[3]);
  char converted[64];
  int decimal;

  printf("Number read in base %d: %s\n",basefrom,argv[3]);
  
  if(basefrom == 10){
    fromDecimal(converted,baseto,number);
    printf("Converted to base %d: %d\n",10,number);
    if(baseto == 10){
      printf("Converted to base %d: %d\n",10,number);
      exit(1);
    }
  }
  else{
    decimal = toDecimal(argv[3],basefrom);
    fromDecimal(converted,baseto,decimal);
    printf("Converted to base %d: %d\n",10,decimal);
    if(baseto == 10){
      printf("Converted to base %d: %d\n",10,decimal);
      exit(1);
    }
  }

  
  printf("Converted to base %d: %s\n",baseto,converted);
  
}
  
