#include <string.h>
#include <stdlib.h>
#include <stdio.h>
void mymemdump(FILE * fd, char * p , int len) {
	
	char string [17];
	int i =0;
	int k = 0;
	int counter = 16;

	fprintf(fd, "0x%016lX: ", (unsigned long) p);
	for (i=0; i < len; i++) {
		//if (i % 16 == 0){
		//fprintf(fd, "0x%016lX: ", (unsigned long) p + counter);
		//}
		// counter+=16;


		if ( i % 16 == 0 && i != 0){
		       
			string[k] = '\0';	
			fprintf(fd, " %s", string);
			k=0;
			fprintf(fd,"\n");

			fprintf(fd, "0x%016lX: ", (unsigned long) p + counter);
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



