
#include <stdio.h>
#include <stdlib.h>

int
main(int argc, char ** argv) {
	int c;
	int lines=0;

	FILE* fd = fopen(argv[1],"r");
	if(fd==NULL){
	  printf("Cannot open file %s\n", argv[1]);
	  exit(1);
	}
 	while((c=fgetc(fd))!=EOF){
	  if(c=='\n'){
	    lines++;
	  }
	  //printf("%c",c);
	}
	printf("Program that counts lines.\nTotal lines: %d\n", lines);
	exit(0);
}
