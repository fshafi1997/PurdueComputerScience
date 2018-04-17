
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void filedump(char * p , int len) {
  char string [17];
  int i =0;
  int k = 0;
  int counter = 16;
  FILE * fd = stdout;
  
      char * x = 0x0000000000000000;
  
  fprintf(fd, "0x%016lX: ", (unsigned long) x);
  for (i=0; i < len; i++) {

    if ( i % 16 == 0 && i != 0){
      string[k] = '\0';
      fprintf(fd, " %s", string);
      k=0;
      fprintf(fd,"\n");


      fprintf(fd, "0x%016lX: ", (unsigned long) x + counter);
      counter+=16;
    }

    int c = p[i]&0xFF; // Get value at [p]. The &0xFF is to make sure you truncate to 8bits or one byte.
    if (c>=32 && c < 127){
      string[k] = p[i];
    }
    else{
      string[k] = '.';
    }
    k++;
    // Print first byte as hexadecimal
    fprintf(fd, "%02X ", c);
    // Print first byte as character. Only print characters >= 32 that are the printable characters.

  }

  if ( i % 16 == 0 && i != 0){
    string[k] = '\0';
    fprintf(fd, " %s", string);
    k=0;
    fprintf(fd,"\n");

    counter+=16;
  }

  if (k !=0){
    int j = 0;
    string[k] = '\0';
    for (j = k; j < 16; j ++){
      fprintf(fd, "   ");
    }
    fprintf(fd, " %s\n", string);
  }
}

int main(int argc, char **argv) {
  
  FILE * fin =  fopen(argv[1],"r");
  if(fin==NULL){
    printf("Error opening file \"invalidfile\"\n");
    exit(1);
  }
  
  fseek(fin, 0L, SEEK_END);
  int fileSize = ftell(fin);
  fseek(fin, 0L, SEEK_SET);

  char * x = (char*)malloc(fileSize);
  
  fread(x,1,fileSize,fin);

  int len = 0;
  if(argc==3){
    if(fileSize<atoi(argv[2])){
      len = fileSize;
    }
    else{
      len = atoi(argv[2]);
    }
  }
  else{
    len = fileSize;
  }

  filedump(x,len);
}


