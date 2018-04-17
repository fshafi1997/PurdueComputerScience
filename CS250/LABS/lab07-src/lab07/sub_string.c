#include<stdio.h>
#include<stdlib.h>

char * sub_string(char*in_string, int start_index, int end_index){
  int len = end_index - start_index;

  char*out_string = (char*)malloc(sizeof(char)*(len+1));

  int i;
  for(i=start_index;i<end_index && (*in_string!='\0');i++){
    *out_string = *(in_string+i);
    out_string++;
  }

  *out_string ='\0';

  return out_string - len;
}
