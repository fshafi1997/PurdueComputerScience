#include <cstdio>
#include <unistd.h>
#include "shell.hh"
#include <time.h>
#include <signal.h>
#include <string.h>
#include <stdlib.h>


#include <sys/types.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <sys/wait.h>

int yyparse(void);

void Shell::prompt() {
    time_t rawtime;
    struct tm * timeinfo;
    time ( &rawtime );
    timeinfo = localtime ( &rawtime );
    if ( isatty(0) ) {
	char buffer[3000];
	getcwd(buffer,sizeof(buffer));
	char * found = strstr(buffer,"fshafi");
	const char * tilda = "~/";
	char * normal = strdup("Farhan's shell");
	char * promp = getenv("PROMPT");
	char * date = strdup(asctime (timeinfo));
	char * justTime;
	const char s[5] = " ";
	char *token;

	// get the first token 
	token = strtok(date, s);

	// walk through other tokens 
	int counter =0;
	while( token != NULL ) {
	    if(counter==3){
		justTime=strdup(token);
		break;
	    }
	    counter++;
	    token = strtok(NULL, s);
	}

	if(promp!=NULL){
	    printf("\033[22;31m%s\033[0m ",promp);
	}
	else{
	    printf("\033[01;36m%s:\033[0m\033[01;33m[%s]\033[0m\033[22;32m[%s%s]\033[0m\033[01;33m>\033[0m",normal,justTime,tilda,found);
	}
	fflush(stdout);
    }
}



void signal_Handler(int sig) {
    //control c detection
    if (sig == SIGINT) {
	printf("\n");
	Shell::prompt();
    }
    //zombie elimination
    else if(sig == SIGCHLD) {
	while(waitpid(-1, 0, WNOHANG) > 0){
//	    printf("[PID] exited.");
	}
    }
}

int main(/*int argc, char* argv[]*/) {
    //x=argc;
    //char * res = realpath(argv[0],Shell::path); 
    struct sigaction signalAction;
    signalAction.sa_handler = &signal_Handler;
    sigemptyset(&signalAction.sa_mask);
    signalAction.sa_flags = SA_RESTART;

    int signalR = sigaction(SIGCHLD, &signalAction, NULL );
    if ( signalR ) {
	perror( "sigaction" );
	exit( -1 );
    }
    signalR = sigaction(SIGINT, &signalAction, NULL );

    Shell::prompt();
    yyparse();
}

Command Shell::_currentCommand;
