#include <stdio.h>
//

int
main(int argc, char **argv) {
  printf("Welcome to the High Low game...\n");

  int game = 1;
  int keepAsking = 1;
  
  while(game == 1){
    printf("Think of a number between 1 and 100 and press <enter>\n");
    getchar();
    int low = 1;
    int high = 100;
    int mid = 0;

    while(keepAsking == 1){
      mid = (low+high)/2;
    
      printf("Is it higher than %d? (y/n)\n",mid);
      char answer = getchar();
      getchar();

      if(answer == 'y'){
	low = mid+1;
      }
      else if(answer == 'n'){
	high = mid - 1;
      }
      else printf("Type y or n\n");

      if(high<low){
	keepAsking = 0;
      }
    }
    printf("\n>>>>>> The number is %d\n\n",low);
    printf("Do you want to continue playing (y/n)?\n");

    char answer2 = getchar();
    getchar();

    if(answer2 == 'y'){
      game = 1;
      keepAsking = 1;
    }
    else{
      game = 0;
      printf("Thanks for playing!!!\n");
    }
  }
}

