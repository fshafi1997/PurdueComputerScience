
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "WordTable.h"

#define isLetter(char) ((char>='A'&&char<='Z')||(char>='a'&&char<='z'))

// Initializes a word table
void wtable_init(WordTable * wtable)
{
	// Allocate and initialize space for the table
	wtable->nWords = 0;
	wtable->maxWords = 10;
	wtable->wordArray = (WordInfo *) malloc(wtable->maxWords * sizeof(WordInfo));
	for (int i = 0; i < wtable->maxWords; i++) {
		llist_init(&wtable->wordArray[i].positions);
	}
}

// Add word to the tableand position. Position is added to the corresponding linked list.
void wtable_add(WordTable * wtable, char * word, int position)
{
	// Find first word if it exists
	for (int i = 0; i < wtable->nWords; i++) {
		if ( strcmp(wtable->wordArray[i].word, word)== 0 ) {
			// Found word. Add position in the list of positions
			llist_insert_last(&wtable->wordArray[i].positions, position);
			return;
		}
	}

	// Word not found.
	// Make sure that the array has space.
	// Expand the wordArray here.

	if (wtable->nWords == wtable->maxWords){
	  wtable->wordArray=(WordInfo*)realloc(wtable->wordArray,wtable->maxWords*2*sizeof(WordInfo));
	  /* for (int i=wtable->maxWords; i < wtable->maxWords * 2; i ++){
	    llist_init(&wtable->wordArray[i].positions);
	    }*/
	   wtable->maxWords *= 2;
	}

	// Add new word and position
	wtable->wordArray[wtable->nWords].word = strdup(word);
	llist_insert_last(&wtable->wordArray[wtable->nWords].positions, position);
	wtable->nWords++;
}

// Print contents of the table.
void wtable_print(WordTable * wtable, FILE * fd)
{
	fprintf(fd, "------- WORD TABLE -------\n");

	// Print words
	for (int i = 0; i < wtable->nWords; i++) {
		fprintf(fd, "%d: %s: ", i, wtable->wordArray[i].word);
		llist_print( &wtable->wordArray[i].positions);
	}
}

// Get positions where the word occurs
LinkedList * wtable_getPositions(WordTable * wtable, char * word)
{
	// Write your code here
  for(int i = 0; i < wtable->nWords; i ++){
    if(strcmp(word, wtable->wordArray[i].word) == 0){
      return &wtable->wordArray[i].positions;
    }
  }
  return NULL;
}

//
// Separates the string into words
//

#define MAXWORD 200
char word[MAXWORD];
int wordLength;
int wordCount;
int charCount;
int wordPos;

// It returns the next word from stdin.
// If there are no more more words it returns NULL.
// A word is a sequence of alphabetical characters.
static char * nextword(FILE * fd) {
	// Write your code here
  
  char * nextWord = malloc(sizeof(char) * 100);
  char * j = nextWord;
  *nextWord = fgetc(fd);
  if(*nextWord == EOF){
    return NULL;
  }
  while(*nextWord!=EOF){
    if(!isLetter(*nextWord)){
      *nextWord = '\0';
	  return j;
    }
    nextWord ++;
    *nextWord = fgetc(fd);
  }
  return j;
}

// Conver string to lower case
void toLower(char *s) {

  int i = 0;
  if(s == NULL){
    return;
  }
  char chaar = s[i];
  while(chaar != '\0'){
    if(chaar >= 'A' && chaar <= 'Z'){
      chaar = (chaar - 'A') + 'a';
      s[i] = chaar;
    }
    i++;
    chaar = s[i];
  }
}


// Read a file and obtain words and positions of the words and save them in table.
int wtable_createFromFile(WordTable * wtable, char * fileName, int verbose){
  
	// Write your code here
  FILE * file = fopen(fileName, "r");
  if(file == NULL){
    return 0;
  }
  char * word;
  int position;
  int count = 0;
  position = ftell(file);
  word = nextword(file);
  while(!isLetter(*word)){
    position = ftell(file);
    word = nextword(file);
  }
  toLower(word);
  while(word != NULL){
    wtable_add(wtable, word, position);
    if(verbose == 1){
      printf("%d: word=%s, pos=%d\n",count, word, position);
    }
    count++;
    position = ftell(file);
    word = nextword(file);
    while(word!= NULL && !isLetter(*word) && *word == '\0'){
      position = ftell(file);
      word = nextword(file);
    }
    toLower(word);
  }

  return 0;
  
  
}

// Sort table in alphabetical order.
void wtable_sort(WordTable * wtable){
	// Write your code here
  int x = 0;
  while(x < wtable->nWords - 1){
    int y = x + 1;
    while(y < wtable->nWords){
      if(strcmp(wtable->wordArray[x].word,wtable->wordArray[y].word) >= 1){

	char * temp = wtable->wordArray[x].word;
	wtable->wordArray[x].word = wtable->wordArray[y].word;
	wtable->wordArray[y].word = temp;

	LinkedList tempo = wtable->wordArray[x].positions;
	wtable->wordArray[x].positions = wtable->wordArray[y].positions;
	wtable->wordArray[y].positions = tempo;
      }
      y++;
    }
    x++;
  }
}

// Print all segments of text in fileName that contain word.
// at most 200 character. Use fseek to position file pointer.
// Type "man fseek" for more info. 
int wtable_textSegments(WordTable * wtable, char * word, char * fileName){
	// Write your code here
  printf("===== Segments for word \"%s\" in book \"%s\" =====\n", word, fileName);
  
  LinkedList * temp = wtable_getPositions(wtable, word);
  ListNode * i;
  FILE * file = fopen(fileName, "r");
  i = temp->head;
  while(i!=NULL){
    printf("---------- pos=%d-----\n......", i->value);
    fseek(file, i->value, SEEK_SET);
    for(int x= 0; x < 200; x ++){
      int chr = fgetc(file);
      printf("%c", chr);
    }
    printf("......\n");
    i = i->next;
  }
}

