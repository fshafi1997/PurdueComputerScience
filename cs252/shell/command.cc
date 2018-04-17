/*
 * CS252: Shell project
 *
 * Template file.
 * You will need to add more code here to execute the command table.
 *
 * NOTE: You are responsible for fixing any bugs this code may have!
 *
 */

#include <cstdio>
#include <cstdlib>
#include <string.h>
#include <iostream>
#include <signal.h>
#include "command.hh"
#include "shell.hh"

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <pwd.h>

#define PATH_MAX  100

int bPID =0;
int status;
std::string* stored;

Command::Command() {
    // Initialize a new vector of Simple Commands
    _simpleCommands = std::vector<SimpleCommand *>();

    _outFile = NULL;
    _inFile = NULL;
    _errFile = NULL;
    _background = false;
}

void Command::insertSimpleCommand( SimpleCommand * simpleCommand ) {
    // add the simple command to the vector
    _simpleCommands.push_back(simpleCommand);
}

void Command::clear() {
    if(_errFile==_outFile){
	_errFile = NULL;
    }


    // deallocate all the simple commands in the command vector
    for (auto simpleCommand : _simpleCommands) {
	delete simpleCommand;
    }


    // remove all references to the simple commands we've deallocated
    // (basically just sets the size to 0)
    _simpleCommands.clear();

    if ( _outFile ) {
	delete _outFile;
    }
    _outFile = NULL;

    if ( _inFile ) {
	delete _inFile;
    }
    _inFile = NULL;

    if ( _errFile ) {
	delete _errFile;
    }
    _errFile = NULL;
    appendFlag=0;
    _outFile = 0;
    _inFile = 0;
    _counterIn = 0;
    _counterOut = 0;
    _background = false;
}

void Command::print() {
    printf("\n\n");
    printf("              COMMAND TABLE                \n");
    printf("\n");
    printf("  #   Simple Commands\n");
    printf("  --- ----------------------------------------------------------\n");

    int i = 0;
    // iterate over the simple commands and print them nicely
    for ( auto & simpleCommand : _simpleCommands ) {
	printf("  %-3d ", i++ );
	simpleCommand->print();
    }

    printf( "\n\n" );
    printf( "  Output       Input        Error        Background\n" );
    printf( "  ------------ ------------ ------------ ------------\n" );
    printf( "  %-12s %-12s %-12s %-12s\n",
	    _outFile?_outFile->c_str():"default",
	    _inFile?_inFile->c_str():"default",
	    _errFile?_errFile->c_str():"default",
	    _background?"YES":"NO");
    printf( "\n\n" );
}

//environment variable expansion
void Command::expanding(){
    for(unsigned int x=0;x<_simpleCommands.size();x++){
	for(unsigned int y=0;y<_simpleCommands.at(x)->_arguments.size();y++){
	    std::string* expansion = new std::string();
	    //expanding this one
	    const char * argument = _simpleCommands.at(x)->_arguments.at(y)->c_str();
	    char* arg11 = strdup(argument);

	    if (strchr(argument, '$') && strchr(argument, '{')) {
		int i = 0;
		int j = 0;
		int flag=1;

		//converting environment variable to its values
		while(argument[i] != '\0') {
		    flag=1;
		    if (argument[i] == '$') {
			char* finaL = (char*)malloc(strlen(argument));
			i+=2;
			while (argument[i] != '}') {
			    finaL[j] = argument[i];
			    i++;
			    j++;
			}
			finaL[j] = '\0';
			if(strcmp(finaL,"$")!=0 && strcmp(finaL,"?")!=0 && strcmp(finaL,"!")!=0 && strcmp(finaL,"_")!=0 && strcmp(finaL,"SHELL")!=0){
			    char* toUse = getenv(finaL);
			    std::string toUse2 = std::string(toUse);
			    expansion->append(toUse2);
			}
			//TODO:special expansions
			if(strcmp(finaL,"SHELL")==0){
			    char actualpath [PATH_MAX];
			    char * path = realpath("/proc/self/exe",actualpath);
			    std::string* newString = new std::string(path);
			    _simpleCommands.at(x)->_arguments.at(y) = newString;
			    flag=0;
			}
			else if(strcmp(finaL,"?")==0){
			    std::string guess = std::to_string(status);
			    std::string* toGive = new std::string(guess);
			    _simpleCommands.at(x)->_arguments.at(y) = toGive;
			    flag=0;
			}
			else if(strcmp(finaL,"$")==0){
			    std::string guess = std::to_string(getpid());
			    std::string* toGive = new std::string(guess);
			    _simpleCommands.at(x)->_arguments.at(y) = toGive;
			    flag=0;
			} 
			else if(strcmp(finaL,"_")==0){
			    _simpleCommands.at(x)->_arguments.at(y) = stored;
			    flag=0;
			}
			else if(strcmp(finaL,"!")==0){
			    std::string guess = std::to_string(bPID);
			    std::string* toGive = new std::string(guess);
			    _simpleCommands.at(x)->_arguments.at(y) = toGive;
			    flag=0;
			}
			
			free(finaL);
			j = 0;
		    }
		    else {
			char* finaL = (char*)malloc(strlen(argument));
			while(argument[i] != '\0' && argument[i] != '$') {
			    finaL[j] = argument[i];
			    i++;
			    j++;
			}
			finaL[j] = '\0';
			std::string toUse3 = std::string(finaL);
			expansion->append(toUse3);;
			free(finaL);
			j=0;
			i--;
		    }
		    i++;
		}
		if(flag==1){
		    _simpleCommands.at(x)->_arguments.at(y) = expansion;
		}//freeing expansion
	    }
	    //checking tilde
	    if(argument[0] == '~'){
		if(argument[1] == '\0'){
		    struct passwd *pw = getpwuid(getuid());
		    const char * directory = pw->pw_dir;
		    strcpy(arg11,directory);
		    std::string* newString = new std::string(arg11);
		    _simpleCommands.at(x)->_arguments.at(y) = newString;
		}
		else{
		    const char * temp1 = argument;
		    int len = strlen(argument);
		    char * subDir = (char*)calloc(sizeof(char), (len+20));
		    char * copy = subDir;
		    temp1++;
		    for(; *temp1 != '\0';subDir++,temp1++){
			if(*temp1 == '/'){
			    break;
			}
			*subDir = *temp1;
		    }
		    --subDir = '\0';
		    subDir = copy;
		    if(*temp1 == '\0'){
			struct passwd *pw = getpwnam(subDir);
			const char * directory = pw->pw_dir;
			argument = strdup(directory);
			std::string* newString = new std::string(argument);
			_simpleCommands.at(x)->_arguments.at(y) = newString;
		    }else{
			char * t = strcat(getpwnam(subDir)->pw_dir,temp1);
			argument = strdup(t); 
			std::string* newString = new std::string(argument);
			_simpleCommands.at(x)->_arguments.at(y) = newString;
		    }
		    free(subDir);
		}
	    }
	    free(arg11);
	}//here
	//saving the last argument of the last command
	stored = new std::string(_simpleCommands.at(x)->_arguments.back()->c_str());
    }
}



void Command::execute() {
    //TODO:Iterate through all and call expanding method on each
    expanding();


    // Don't do anything if there are no simple commands
    if ( _simpleCommands.size() == 0 ) {
	Shell::prompt();
	return;
    }

    //Checcking for the exit command
    if (!strcmp(_simpleCommands[0]->_arguments[0]->c_str(), "exit")) {
	printf("\033[22;31mGood Bye!\033[0m\n");
	exit(1);
    }


    /*ambiguous output redirect test case*/
    if (_counterIn > 1 || _counterOut > 1) {
	printf("Ambiguous output redirect.\n");
	clear();
	Shell::prompt();
	return;
    }

    //checking set env
    if(strcmp(_simpleCommands[0]->_arguments[0]->c_str(), "setenv") == 0){ 
	int error = setenv(_simpleCommands[0]->_arguments[1]->c_str(), _simpleCommands[0]->_arguments[2]->c_str(), 1);
	if (error != 0){
	    perror("setenv");
	}
	clear();
	Shell::prompt();
	return;
    }
    //checking unsetenv
    else if(strcmp(_simpleCommands[0]->_arguments[0]->c_str(), "unsetenv") == 0) {
	int error = unsetenv(_simpleCommands[0]->_arguments[1]->c_str());
	if (error != 0){
	    perror("unsetenv");
	}

	clear();
	Shell::prompt();
	return;
    }
    //checking cd
    if (!strcmp(_simpleCommands[0]->_arguments[0]->c_str(), "cd")) {
	int error;
	if (_simpleCommands[0]->_arguments.size() == 1 || strcmp(_simpleCommands[0]->_arguments[1]->c_str(),"${HOME}")==0) {
	    error = chdir(getenv("HOME"));
	} else {
	    error = chdir(_simpleCommands[0]->_arguments[1]->c_str());
	}

	if (error != 0) {
	    fprintf(stderr, "cd: can't cd to %s\n", _simpleCommands[0]->_arguments[1]->c_str());
	    //printf("cd: no such file or directory: hhhj\n");
	    // perror("chdir");
	}

	clear();
	Shell::prompt();
	return;
    }

    // Print contents of Command data structure
    //not neeeded for test case
    //print();

    // Add execution here
    // For every simple command fork a new process
    // Setup i/o redirection
    // and call exec

    int tempIn = dup(0);
    int tempOut = dup(1);
    int tempError = dup(2);
    int fdIn;
    int fdOut;
    int fdError;
    int ret;

    if(_inFile){
	fdIn = open(_inFile->c_str(), O_RDONLY);
    }
    else{
	fdIn = dup(tempIn);
    }

    if(_errFile){
	if(appendFlag){
	    fdError = open(_errFile->c_str(), O_WRONLY | O_CREAT | O_APPEND, 0600);
	}
	else{
	    fdError = open(_errFile->c_str(), O_WRONLY | O_CREAT | O_TRUNC, 0600);
	}
	if(fdError<0){
	    perror("open");
	    exit(1);
	}
    }
    else{
	fdError = dup(tempError);
    }
    dup2(fdError,2);
    close(fdError);

    for(unsigned i=0; i<_simpleCommands.size();i++){
	//changing file descriptor
	dup2(fdIn,0);
	close(fdIn);

	//checking if its the last command
	if(i== _simpleCommands.size()-1){
	    if(_outFile){
		if(appendFlag){
		    fdOut = open(_outFile->c_str(), O_WRONLY | O_CREAT | O_APPEND, 0600);
		}
		else{
		    fdOut = open(_outFile->c_str(), O_WRONLY | O_CREAT | O_TRUNC, 0600);
		}
	    }
	    else{
		fdOut = dup(tempOut);
	    }
	}
	else{
	    //if not the last command
	    int fdPipe[2];
	    pipe(fdPipe);
	    fdOut = fdPipe[1];
	    fdIn = fdPipe[0];
	}
	//redirecting the output
	dup2(fdOut,1);
	close(fdOut);

	ret = fork();//execution
	if (ret == 0) {

	    if (!strcmp(_simpleCommands[i]->_arguments[0]->c_str(), "printenv")) {
		char ** environment = environ;
		while (*environment!=NULL) {
		    printf("%s\n", *environment);
		    environment++;
		}
		exit(0);
	    }


	    // Child Process
	    char ** arr = new char*[_simpleCommands.at(i)->_arguments.size()];
	    size_t j; 
	    for(j= 0; j < _simpleCommands.at(i)->_arguments.size(); j++){
		arr[j] = strdup(_simpleCommands.at(i)->_arguments[j]->c_str());
	    }
	    arr[j] = NULL;

	    execvp(_simpleCommands.at(i)->_arguments[0]->c_str(), arr);
	    perror("execvp");
	    exit(1);
	} else if (ret < 0) {
	    perror("fork");
	    return;
	}
    }
    dup2(tempIn, 0);
    dup2(tempOut, 1);
    dup2(tempError, 2);
    close(tempIn);
    close(tempOut);
    close(tempError);

    if(!_background) {
	//	waitpid(ret, NULL, 0);
	int returnCode;
	if ( waitpid(ret, &returnCode, 0) == -1 ) {
	    perror("waitpid failed");
	}
	const int es = WEXITSTATUS(returnCode);
	status = es;
	clear();
    }
    else{
	bPID = ret;
    }

    // Clear to prepare for next command
    clear();

    // Print new prompt
    Shell::prompt();
}

SimpleCommand * Command::_currentSimpleCommand;














