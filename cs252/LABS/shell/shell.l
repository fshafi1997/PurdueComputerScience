
/*
 *
 * CS-252 Fall 2017
 * shell.l: lexical analyzer for shell
 * You have to extend it.
 *has tokens
 */

%{

#include <cstring>
#include "y.tab.hh"
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>
#include <assert.h>
#include <sys/prctl.h>


////////////  Start added code ///////////

    extern "C" char * read_line();


    int mygetc(FILE * f) {
	static char *p;
	char ch;

	if (!isatty(0)) {
	    // stdin is not a tty. Call real getc
	    return getc(f);
	}

	// stdin is a tty. Call our read_line.
	if (p==NULL || *p == 0) {
	    char * s = read_line();
	    p = s;
	}

	ch = *p;
	p++;

	return ch;
    }

#undef getc
#define getc(f) mygetc(f)

    /////////// End added code ///////////

static  void yyunput (int c,char *buf_ptr  );

void myunputc(int c) {
  unput(c);
}

%}
%x src
%%

"source"        BEGIN(src); 

<src>[ \t]*
<src>[^ \t\n]+ {
    yyin = fopen( yytext, "r");
    if (yyin == 0){
	printf("Unable to open the file %s. \n", yytext);
    }
    yypush_buffer_state(yy_create_buffer( yyin, YY_BUF_SIZE ));
    BEGIN(INITIAL);
}
<<EOF>> {
    yypop_buffer_state();
    if (YY_CURRENT_BUFFER == 0){
	yyterminate();
    }
}


\n 	{ return NEWLINE; }
[ \t] 	{ /* Discard spaces and tabs */ }
">"	{ return GREAT; }
">>"	{ return GREATGREAT; }
">&"	{ return GREATAMPERSAND; }
">>&"	{ return GREATGREATAMPERSAND; }
"<"	{ return LESS; }
"|" 	{ return PIPE; }
"&" 	{ return AMPERSAND; }
"2>"    { return TWOGREAT; }


\"[^\n\"]*\"    {
/*Quotes checking*/
  char* result = (char*)malloc(sizeof(char) * 1024);
  int i = 0;
  int j = 0;
  for(i = 0; yytext[i] != '\0'; i++){
      if(yytext[i] == '"'){
	  continue;
      }
  result[j] = yytext[i];
  j++;
}
yylval.cpp_string = new std::string(result);
return WORD;
}

[^ \t\n|><&]*\\[^ \t\n]* {
    /*escape checking*/
    char* yytextNEW = (char*)malloc(strlen(yytext)+1);
    int flag = 0;
    int index = 0;
    int length = strlen(yytext);

    for(int i=0; i<length; i++){
	if(yytext[i] == '\\' && flag == 0){
	    flag = 1;
	}
	else if(flag == 1){
	    yytextNEW[index] = yytext[i];
	    flag=0;
	    index++;
	}
	else{
	    yytextNEW[index] = yytext[i];
	    index= index+1;
	}
    }
    yytextNEW[index] = '\0';

    yylval.cpp_string = new std::string(yytextNEW);;
    return WORD;
}


[$(][^\n$]*[)] {
    /*Sub shell*/
    char * argument  = yytext + 2;
    argument[strlen(argument)-1 ]= '\n';
    int child_stdin[2];
    int child_stdout[2];
    if (pipe(child_stdin) < 0){
	perror("child_stdin");
	_exit(1);
    }
    if (pipe(child_stdout) < 0){
	perror("child_stdout");
	_exit(1);
    }
    char * argument2[1000];
    int ret = fork();

    if (ret == 0){
	close(child_stdin[0]);
	close(child_stdin[1]);
	char *temp = strtok(argument, " ");
	int i = 0;
	while(temp != NULL){
	    argument2[i] = strdup(temp);
	    temp = strtok(NULL, " ");
	    i++;
	}
	char * t2 = argument2[i-1];
	t2[strlen(t2)-1] = '\0';
	dup2(child_stdout[1],1);
	execvp(argument2[0], argument2);
    }
    if (ret> 0){
	dup2(child_stdout[0], 0);
	close(child_stdout[0]);
	close(child_stdout[1]);
	char array[1000];
	char t3 = 0;
	int j = 0;

	t3 = fgetc(stdin);

	for(j=0;t3!=EOF;j++){
	    if(t3=='\n'){
		array[j] = ' ';
		t3 = fgetc(stdin);
		continue;
	    }
	    array[j] = t3;
	    t3 = fgetc(stdin);
	}

	int index3=j-2;
	while(index3 >= 0){
	    myunputc(array[index3]);
	    index3--;
	}
    }
}


[^ \t\n|><&'"][^ \t\n|><&'"]*  {
    /* add $ after enviro expanison done*/
  /* Assume that file names have only alpha chars */
  yylval.cpp_string = new std::string(yytext);
  return WORD;
}
