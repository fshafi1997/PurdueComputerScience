#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern char*sub_string(char*,int,int);

int main(){
  int start_index; //To get startindex
  int end_index; //To get end index
  char in_string[64];
  char*out_string; //The output string

  printf("Enter a string: ");
  scanf("%s", in_string);
  printf("Enter the start index: ");
  scanf("%d", &start_index);
  printf("Enter the end index: ");
  scanf("%d",&end_index);

  out_string=sub_string(in_string,start_index,end_index);
  printf("The substring of the given string is '%s'\n",out_string);
  return 0;
}
