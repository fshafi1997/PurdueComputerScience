
/*
 * CS-252
 * shell.y: parser for shell
 *
 * This parser compiles the following grammar:
 *
 *	cmd [arg]* [> filename]
 *
 * you must extend it to understand the complete shell grammar
 *
 */


%code requires
{
#include <string>
#include <string.h>

#if __cplusplus > 199711L
#define register      // Deprecated in C++11 so remove the keyword
#endif
}

%union
{
  char        *string_val;
  // Example of using a c++ type in yacc
  std::string *cpp_string;
}

%token <cpp_string> WORD
%token NOTOKEN TWOGREAT NEWLINE GREATGREATAMPERSAND GREATGREAT GREATAMPERSAND AMPERSAND GREAT PIPE LESS



%{
//#define yylex yylex
#include <cstdio>
#include "shell.hh"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <regex.h>
#include <sys/types.h>
#include <dirent.h>
#include <unistd.h>
#include <signal.h>
#include <sys/stat.h>

void yyerror(const char * s);
void expandWildcard(char* prefix, char* suffix);
int compareFunction(const void * f1, const void * f2);
int yylex();
char** arr;
int counter;
int maximumSize;
%}

%%

goal:
  commands
  ;

commands:
  command
  | commands command
  ;

command:
  simple_command
  ;

simple_command:
  command_and_args iomodifier_list background_optional NEWLINE {
//    printf("   Yacc: Execute command\n");
    Shell::_currentCommand.execute();
  }
  | pipe_list iomodifier_list background_optional NEWLINE {
//    printf("   Yacc: Execute command\n");
    Shell::_currentCommand.execute();
  }
  | NEWLINE
  | error NEWLINE { yyerrok; }
  ;

command_and_args:
  command_word argument_list {
    Shell::_currentCommand.
    insertSimpleCommand( Command::_currentSimpleCommand );
  }
  ;

argument_list:
  argument_list argument
  | /* can be empty */
  ;

argument:
WORD {
    //    printf("   Yacc: insert argument \"%s\"\n", $1->c_str());
    if( strchr($1->c_str(),'*') != NULL || strchr($1->c_str(),'?') != NULL && strstr($1->c_str(),"{?}")== NULL){
	maximumSize = 50;
	arr = (char **)malloc(sizeof(char*)* maximumSize);;
	counter = 0;
	char * my_argument = const_cast<char*> ( $1->c_str() );
	expandWildcard(NULL, my_argument);
	qsort(arr, counter, sizeof(char*), compareFunction);
	int i =0;
	while(i<counter){
	    std::string* newString = new std::string(arr[i]);
	    Command::_currentSimpleCommand->insertArgument(newString);
	    i++;
	}
	free(arr);
    }else{
	Command::_currentSimpleCommand->insertArgument( $1 );\
   }
}
;

command_word:
WORD {
    //    printf("   Yacc: insert command \"%s\"\n", $1->c_str());
    Command::_currentSimpleCommand = new SimpleCommand();
    Command::_currentSimpleCommand->insertArgument( $1 );
}
;

/*WHY IS THIS GIVING ERRORS*/
pipe_list:
pipe_list PIPE command_and_args
| command_and_args
;

iomodifier_opt:
  GREAT WORD {
  //  printf("   Yacc: insert output \"%s\"\n", $2->c_str());
    Shell::_currentCommand._outFile = $2;
    Shell::_currentCommand._counterOut++;
  }
  | GREATGREAT WORD {
 // printf("   GREATGREATWORD Yacc: insert output \"%s\"\n", $2->c_str());
  Shell::_currentCommand._outFile = $2;
  Shell::_currentCommand.appendFlag = 1;
  Shell::_currentCommand._counterOut++;
  }
  | GREATAMPERSAND WORD {
 // printf("   GREATAMPERSANDWORD Yacc: insert output \"%s\"\n", $2->c_str());
  Shell::_currentCommand._outFile = $2;
  Shell::_currentCommand._errFile = $2;
  Shell::_currentCommand._counterOut++;
  }
  | GREATGREATAMPERSAND WORD {
//  printf("   GREATGREATAMPERSANDWORD Yacc: insert output \"%s\"\n", $2->c_str());
  Shell::_currentCommand._outFile = $2;
  Shell::_currentCommand._errFile = $2;
  Shell::_currentCommand._counterOut++;
  Shell::_currentCommand.appendFlag = 1;
  }
  | LESS WORD {
 // printf("   LESSWORD Yacc: insert output \"%s\"\n", $2->c_str());
  Shell::_currentCommand._inFile = $2;
  Shell::_currentCommand._counterIn++;
  }
  | TWOGREAT WORD {
//  printf("   TWOGREAT Yacc: insert output \"%s\"\n", $2->c_str());
  Shell::_currentCommand._errFile = $2;
  }
  ;

iomodifier_list:
  iomodifier_list iomodifier_opt
  | iomodifier_opt
  |
  ;

background_optional:
  AMPERSAND {
    Shell::_currentCommand._background = 1;
  }
  |
  ;


/*From Slides*/
/*ask about ;*/

%%

void
yyerror(const char * s)
{
  fprintf(stderr,"%s", s);
}

int compareFunction(const void *f1, const void *f2) {
    const char *_f1 = *(const char **)f1;
    const char *_f2 = *(const char **)f2;
    return strcmp(_f1, _f2);
}

void 
expandWildcard(char * prefix, char * suffix){
    char * keep = (char *) malloc(strlen(suffix)+10);
    char * temp = suffix;
    char * directory = keep;

    if(temp[0]=='/'){
	*(keep++) = *(temp++);
    }

    for(*(temp); *temp != '/' && *temp; *(temp++)){
	*(keep++) = *(temp);
	*keep = '\0';
    }

    if (strchr(directory, '?') || strchr(directory, '*')) {
	if (!prefix){
	    if(suffix[0] == '/') {
		prefix = strdup("/");
		directory++;
	    }
	}  

	char * regular = (char *) malloc (2*strlen(suffix) + 10);
	char * dirTemp = directory;
	char * reg = regular;

	*(reg++) = '^';
	while (*dirTemp != '\0') {
	    if (*dirTemp == '*'){ 
		*(reg) = '.';
		reg++;
		*(reg) = '*';
		reg++;
	    }
	    else if (*dirTemp == '?') { 
		*(reg) = '.';
		reg++;
	    }
	    else if (*dirTemp == '.') { 
		*(reg) = '\\';
		reg++;
		*(reg) = '.'; 
		reg++;
	    }
	    else { 
		*(reg) = *dirTemp;
		reg++;
	    }
	    dirTemp++;
	}
	*(reg) = '$';
	reg++;
	*reg = '\0';

	regex_t regex;

	int checking = regcomp(&regex, regular, 0);

	if(checking > 0){
	    perror("compile");
	    return;
	}

	char * toOpen;
	DIR * dir;
	if(prefix == NULL){
	    toOpen = strdup(".\0");
	    dir = opendir(".\0");
	} else if(strcmp(prefix, "") == 0){
	    toOpen = strdup("/\0");
	    dir = opendir("/\0");
	} else {
	    toOpen = strdup(prefix);
	    dir = opendir(prefix);	
	}

	if(dir == NULL){
	    perror("opendir");
	    return;
	}	

	struct dirent * enter;
	regmatch_t found;

	for (int rnd=0 ;(enter = readdir(dir)) != NULL; rnd++) {
	    if (regexec(&regex, enter->d_name, 1, &found, 0)==0) {
		if (*temp!='\0') {
		    if (enter->d_type == DT_DIR) {
			char * newPrefix = (char *) malloc (200);
			if (!strcmp(toOpen, ".")){ 
			    newPrefix = strdup(enter->d_name);
			}
			else if (!strcmp(toOpen, "/")){
			    sprintf(newPrefix, "%s%s", toOpen, enter->d_name);
			}
			else {
			    sprintf(newPrefix, "%s/%s", toOpen, enter->d_name);
			}
			if(*temp == '/'){
			    temp++;
			}
			expandWildcard(newPrefix, temp);
		    }  
		} else {
		    if (counter == maximumSize) { 
			maximumSize = maximumSize*2; 
			arr = (char **) realloc (arr, maximumSize * sizeof(char *)); 
		    }
		    char * argument = (char *) malloc (200);
		    argument[0] = '\0';
		    if (prefix!=0){
			sprintf(argument, "%s/%s", prefix, enter->d_name);
		    }
		    if (enter->d_name[0] == '.') {
			if (suffix[0] == '.') {
			    if(argument[0] != '\0'){
				arr[counter++] = strdup(argument);
			    }
			    else{
				arr[counter++] = strdup(enter->d_name);
			    }
			}   
		    } else {
			if(argument[0] != '\0'){
			    arr[counter++] = strdup(argument);
			}
			else{
			    arr[counter++] = strdup(enter->d_name);
			}
		    }
		    free(argument);
		}   
	    }  
	}
	free(regular);
	regfree(&regex);
	closedir(dir);
    } else {
	char * sending = (char *) malloc (200);
	if (prefix!=0){
	    sprintf(sending, "%s/%s", prefix, directory);
	}
	else{
	    sending = strdup(directory);
	}
	if (*temp!='\0'){ 
	    expandWildcard(sending, ++temp);
	}
    }
}

#if 0
main()
{
    yyparse();
}
#endif
