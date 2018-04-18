/*
 * CS252: Systems Programming
 * Purdue University
 * Example that shows how to read one line with simple editing
 * using raw terminal.
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <termios.h>
#include <dirent.h>
#include <regex.h>

#define MAX_BUFFER_LINE 2048

extern void tty_raw_mode(void);

// Buffer where line is stored
int line_length,line_position;
char line_buffer[MAX_BUFFER_LINE];

// Simple history array
// This history does not change. 
// Yours have to be updated.
int history_index = 0;
char **history;
char **tabArray=NULL;
int history_length,lengthNow,tIndex=0,tUsed=0,tSize=0,t_Index=0;
char xx,yy,zz;

void read_line_print_usage()
{
    char * usage = "\n"
	" ctrl-?       Print usage\n"
	" Backspace    Deletes last character\n"
	" up arrow     See last command in the history\n";

    write(1, usage, strlen(usage));
}

/* 
 * Input a line with some basic editing.
 */
char * read_line() {

    struct termios orig_attr;
    tcgetattr(0,&orig_attr);
    // Set terminal in raw mode
    tty_raw_mode();

    line_length = 0;
    line_position = 0;

    // Read one line until enter is typed
    while (1) {

	// Read one character in raw mode.
	char ch;
	read(0, &ch, 1);

	if (ch>=32 && ch != 127 && ch!=27) {
	    // It is a printable character. 

	    // Do echo
	    int i;
	    for (i = line_length - 1; i >= line_position; i--){
		line_buffer[i+1] = line_buffer[i];
	    }
	    line_buffer[line_position] = ch; 
	    line_length++;
	    if (line_length==MAX_BUFFER_LINE-2){
		break; 
	    }
	    for (i = line_position; i < line_length; i++) {
		write(1,&line_buffer[i],1);
	    }
	    ch = 8;
	    for (i = line_position + 1; i < line_length; i++){
		write(1,&ch,1);
	    }
	    // If max number of character reached return.
	    // add char to buffer.
	    line_position++;
	}
	//home key ctrl-A
	else if (ch == 1) {
	    for (int i =0; i < line_position; i++) {
		ch = 8;
		write(1,&ch,1);
	    }
	    line_position =0;
	}
	//End key ctrl-E
	else if (ch == 5) {
	    int g = line_length - line_position;
	    for (int i = 0; i < g; i++) {
		xx=27,yy=91,zz=67;
		write(1,&xx,1); 
		write(1,&yy,1); 
		write(1,&zz,1);
	    }
	    line_position = line_length;
	}
	//delete key ctrl-D
	else if(ch ==4){
	    if (line_position == -1) {
	    }
	    else {
		char check = line_buffer[line_position];
		if (check == NULL) {
		    break;
		}
		else{ 
		    int j = line_position+1;
		    for (; j < line_length; j++) {
			char temp = line_buffer[j];
			line_buffer[j] = line_buffer[j+1];
			line_buffer[j-1] = temp;
		    }
		    int g = line_length - line_position;
		    for (j = 0; j < g; j++) {
			xx=27,yy=91,zz=67;
			write(1,&xx,1); 
			write(1,&yy,1); 
			write(1,&zz,1);
		    }
		    // Print backspaces
		    int i = 0;
		    for (i =0; i < line_length; i++) {
			ch = 8;
			write(1,&ch,1);
		    }
		    // Print spaces on top
		    for (i =0; i < line_length; i++) {
			ch = ' ';
			write(1,&ch,1);
		    }
		    // Print backspaces
		    for (i =0; i < line_length; i++) {
			ch = 8;
			write(1,&ch,1);
		    }	
		    // echo line
		    line_length--;
		    write(1, line_buffer, line_length);
		    // Print backspaces
		    for (i =0; i < line_length; i++) {
			ch = 8;
			write(1,&ch,1);
		    }	
		    line_buffer[line_length] ='\0';
		    for (j = 0; j < line_position; j++) {
			xx=27,yy=91,zz=67;
			write(1,&xx,1); 
			write(1,&yy,1); 
			write(1,&zz,1);
		    }
		}
	    }
	}
	else if(ch ==9){
	    if (tUsed == 0) {
		char saved[line_length+10];
		saved[0] = '^';
		int i = line_length - 1;
		while(i>=0){
		    if(line_buffer[i] == 32) {
			t_Index = i;
			break;
		    }
		    i--;
		}
		if (t_Index != 0) {
		    int tempSize = line_length - t_Index;
		    strncpy(saved + 1, line_buffer + t_Index + 1, tempSize);
		    saved[tempSize + 1] = '.';
		    saved[tempSize + 2] = '*';
		    saved[tempSize + 3] = '$';
		    saved[tempSize + 4] = '\0';
		}
		else {
		    strncpy(saved + 1, line_buffer, line_length);
		    saved[line_length] = '.';
		    saved[line_length + 1] = '*';
		    saved[line_length + 2] = '$';
		    saved[line_length + 3] = '\0';
		}
		regex_t regex;
		int checking = regcomp(&regex, saved, 0);
		if (checking != 0) {
		    perror("compile");
		    exit(1);
		}
		DIR * dir;
		dir = opendir(".");
		if(dir == NULL){
		    perror("opendir");
		    exit(1);
		}
		struct dirent * enter;
		regmatch_t found;
		int maxSize = 5;
		tSize = 0;
		tabArray = (char **)malloc(maxSize * sizeof(char *));
		for (int rnd=0 ;(enter = readdir(dir)) != NULL; rnd++) {
		    if (!regexec(&regex, enter->d_name, 1, &found, 0)) {
			if(tSize==maxSize){
			    maxSize=maxSize*2;
			    tabArray = (char **)realloc(tabArray, maxSize * sizeof(char *));
			}
			tabArray[tSize] = strdup(enter->d_name);
			tSize = tSize + 1;
		    }
		}
		tUsed = 1;
	    }
	    ch = 8;
	    for (int i=0; i < line_length; i++){
		write(1,&ch, 1);
	    }
	    ch = 32;
	    for (int i=0; i < line_length; i++){
		write(1,&ch, 1);
	    }
	    ch = 8;
	    for (int i=0; i < line_length; i++){
		write(1,&ch, 1); 
	    }
	    if (t_Index!=0) {
		char * word = tabArray[tIndex];
		for (unsigned i = 0; i < strlen(word); i++){
		    line_buffer[t_Index + i + 1] = word[i];
		}
		line_length = t_Index + strlen(tabArray[tIndex]) + 1;
	    }
	    else {
		strcpy(line_buffer, tabArray[tIndex]);
		line_length = strlen(line_buffer); 
	    }
	    write(1, line_buffer, line_length);
	    line_position = line_length;
	    if (tIndex < tSize - 1) {
		tIndex++;
	    }
	}
	else if (ch==10) {
	    // <Enter> was typed. Return line

	    // Print newline
	    if (history_length == 0) {
		history = malloc(50 * sizeof(char*));
	    }
	    history[history_length] = malloc((MAX_BUFFER_LINE) * sizeof(char));
	    strncpy(history[history_length], line_buffer, line_length);
	    history[history_length][line_length] = '\0';
	    history_length++;			
	    write(1,&ch,1);
	    break;
	}
	else if (ch == 31) {
	    // ctrl-?
	    read_line_print_usage();
	    line_buffer[0]=0;
	    break;
	}
	//8 is backspace key ctrl-H
	else if (ch == 8 || ch==127) {
	    // <backspace> was typed. Remove previous character read.
	    // Go back one character
	    if (line_position == 0) {
	    }
	    else {
		// shift all chars in buffer and add new one
		int i = line_position;
		for (; i < line_length; i++) {
		    char temp = line_buffer[i];
		    line_buffer[i] = line_buffer[i+1];
		    line_buffer[i-1] = temp;
		}
		int g = line_length - line_position;
		for (int i = 0; i < g; i++) {
		    xx=27,yy=91,zz=67;
		    write(1,&xx,1); 
		    write(1,&yy,1); 
		    write(1,&zz,1);
		}
		// Print backspaces
		for (int i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}
		// Print spaces on top
		for (int i =0; i < line_length; i++) {
		    ch = ' ';
		    write(1,&ch,1);
		}
		// Print backspaces
		for (int i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}	
		// echo line
		line_length--;
		write(1, line_buffer, line_length);
		// Print backspaces
		for (int i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}	
		line_buffer[line_length] ='\0';
		line_position--;
		for (int i = 0; i < line_position; i++) {
		    xx=27,yy=91,zz=67;
		    write(1,&xx,1); 
		    write(1,&yy,1); 
		    write(1,&zz,1);
		}
	    }
	}
	else if (ch==27) {
	    // Escape sequence. Read two chars more
	    //
	    // HINT: Use the program "keyboard-example" to
	    // see the ascii code for the different chars typed.
	    //
	    char ch1; 
	    char ch2;
	    read(0, &ch1, 1);
	    read(0, &ch2, 1);

	    //For left arrow key
	    if (ch1==91 && ch2==68) {
		if (line_position == 0) {
		    ;
		} 
		else {
		    ch = 8;
		    write(1,&ch,1);
		    line_position--;
		}
	    }
	    // for right arrow
	    else if (ch1==91 && ch2==67) {
		if (line_position == line_length) {
		    ;
		} 
		else {
		    write(1,&ch,1);	
		    write(1,&ch1,1);	
		    write(1,&ch2,1);	
		    line_position++;	
		}
	    }
	    //down arrow history
	    else if (ch1==91 && ch2 == 66) {
		int i;
		for (i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}
		for (i =0; i < line_length; i++) {
		    ch = ' ';
		    write(1,&ch,1);
		}
		for (i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}
		if (history_index > 1) {
		    history_index--;                    
		    strcpy(line_buffer, history[history_length - history_index]);
		}
		else {
		    strcpy(line_buffer, "");
		}
		line_length = strlen(line_buffer);
		line_position = line_length;
		write(1, line_buffer, line_length);
	    }
	    //up arown history
	    else if(ch1==91 && ch2==65){
		// Erase old line
		// Print backspaces
		if(history_length == 0){
		    continue;
		}
		if (history_length == history_index){
		    continue;
		}
		int i = 0;
		for (i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}
		// Print spaces on top
		for (i =0; i < line_length; i++) {
		    ch = ' ';
		    write(1,&ch,1);
		}
		// Print backspaces
		for (i =0; i < line_length; i++) {
		    ch = 8;
		    write(1,&ch,1);
		}	
		int difference = history_length - history_index;
		if (difference > 0){
		    history_index++;
		}
		difference = history_length - history_index;
		strcpy(line_buffer, history[difference]);
		line_length = strlen(line_buffer);
		line_position = line_length;
		write(1, line_buffer, line_length);
	    } 
	}
    }
    // Add eol and null char at the end of string
    line_buffer[line_length]=10;
    line_length++;
    line_buffer[line_length]=0;
    if (tUsed) {
	t_Index = 0;
	tUsed = 0;
	tIndex = 0;
	free(tabArray);
    }
    tcsetattr(0,TCSANOW,&orig_attr);
    return line_buffer;
}

