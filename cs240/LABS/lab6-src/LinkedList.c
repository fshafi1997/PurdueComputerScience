

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "LinkedList.h"

//
// Initialize a linked list
//
void llist_init(LinkedList * list){
  list->head = NULL;
}

//
// It prints the elements in the list in the form:
// 4, 6, 2, 3, 8,7
//
void llist_print(LinkedList * list) {
	
	ListNode * e;

	if (list->head == NULL) {
		printf("{EMPTY}\n");
		return;
	}

	printf("{");

	e = list->head;
	while (e != NULL) {
		printf("%d", e->value);
		e = e->next;
		if (e!=NULL) {
			printf(", ");
		}
	}
	printf("}\n");
}

//
// Appends a new node with this value at the beginning of the list
//
void llist_add(LinkedList * list, int value) {
	// Create new node
	ListNode * n = (ListNode *) malloc(sizeof(ListNode));
	n->value = value;
	
	// Add at the beginning of the list
	n->next = list->head;
	list->head = n;
}

//
// Returns true if the value exists in the list.
//
int llist_exists(LinkedList * list, int value) {
  ListNode * a;
  if (list->head == NULL){
    return 0;
  }
  a = list->head;
  while(a != NULL){
    if (a->value == value){
      return 1;
    }
    a = a->next;
  }
  return 0;
}

//
// It removes the entry with that value in the list.
//
int llist_remove(LinkedList * list, int value) {
  ListNode * a;
  ListNode * previous;
  if(list->head == NULL){
    return 0;
  }
  a = list->head;
  
  if(a->value == value){
    list->head = a->next;
    free(a);
    return 1;
  }
  while(a->next != NULL){
    if(a->next->value == value){
      previous = a->next;
      a->next = a->next->next;
      free(previous);
	  return 1;
    }
    a = a->next;
  }
  
      
  return 0;
}

//
// It stores in *value the value that correspond to the ith entry.
// It returns 1 if success or 0 if there is no ith entry.
//
int llist_get_ith(LinkedList * list, int ith, int * value) {
  ListNode * a;
  if(list->head == NULL){
    return 0;
  }
  a = list->head;
  int counter = 0;
  while(a != NULL && counter < ith){
    a = a->next;
    counter++;
  }
  if(counter == ith){
    *value = a->value;
    return 1;
  }
  return 0;
}

//
// It removes the ith entry from the list.
// It returns 1 if success or 0 if there is no ith entry.
//
int llist_remove_ith(LinkedList * list, int ith) {
  ListNode * a;
  ListNode * previous;
  if(list->head == NULL){
    return 0;
  }
  a = list->head;
  int counter = 0;
  while(a != NULL && counter < ith - 1){
    a = a->next;
    counter ++;
  }
  if(counter == ith - 1){
    previous = a->next;
    a->next = a->next->next;
    free(previous);
    return 1;
  }
  return 0;
}

//
// It returns the number of elements in the list.
//
int llist_number_elements(LinkedList * list) {
  ListNode * a;
  if(list->head == NULL){
    return 0;
  }
  a = list->head;
  int counter = 0;
  while(a != NULL){
    counter ++;
    a = a->next;
  }
  return counter;
}


//
// It saves the list in a file called file_name. The format of the
// file is as follows:
//
// value1\n
// value2\n
// ...
//
int llist_save(LinkedList * list, char * file_name) {
  ListNode * a;
  FILE * file = fopen(file_name, "w");
  /*Ask why is it important to do this check was givig seg fault without it*/
  if(file == NULL){
    return 0;
    }
  a = list->head;
  while(a != NULL){
    fprintf(file, "%d\n", a->value);
    a = a->next;
  }
  fclose(file);
}

//
// It reads the list from the file_name indicated. If the list already has entries, 
// it will clear the entries.
//
int llist_read(LinkedList * list, char * file_name) {
  ListNode * a;
  FILE * file = fopen(file_name, "r");
  if(file == NULL){
    return 0;
  }
 
  int value = 0;
  while(fscanf(file, "%d\n", &value)!=EOF){
    llist_add(list, value);
  }
  return 1;
}


//
// It sorts the list. The parameter ascending determines if the
// order si ascending (1) or descending(0).
//
void llist_sort(LinkedList * list, int ascending) {
  ListNode * a;
  a = list->head;
  int length = llist_number_elements(list);
  int counter = 0;
  while (counter < length){
    int counter1 = 0;
    while (counter1 < length - counter){
      ListNode * b = a;
      int i = 0;
      while (i < counter1){
	b = b->next;
	i++;
      }
      if (ascending){
	if (a->value > b->value){
	  int temp = a->value;
	  a->value = b->value;
	  b->value = temp;
	}
      }
      else{
	if (a->value < b->value){
	  int temp = a->value;
	  a->value = b->value;
	  b->value = temp;
	}
      }
      counter1 ++;
    }
    a = a->next;
    counter ++;
  }
}

//
// It removes the first entry in the list and puts value in *value.
// It also frees memory allocated for the node
//

/*Understand both from help room*/


int llist_remove_first(LinkedList * list, int * value) {
  ListNode * a;
  if(list->head == NULL){
    return 0;
  }
  a = list->head;
  *value = a->value;
  list->head = a->next;
  free(a);
  return 1;
} 

//
// It removes the last entry in the list and puts value in *value/
// It also frees memory allocated for node.
//
int llist_remove_last(LinkedList * list, int *value) {
  ListNode * a;
  ListNode * previous;
  if(list->head == NULL){
    return 0;
  }
  a = list->head;
  while(a->next != NULL){
    previous = a;
    a = a->next;
  }
  *value = a->value;
  previous->next = NULL;
  free(a);
  return 1;
}

//
// Insert a value at the beginning of the list.
// There is no check if the value exists. The entry is added
// at the beginning of the list.
//
void llist_insert_first(LinkedList * list, int value) {
  llist_add(list, value);
}

//
// Insert a value at the end of the list.
// There is no check if the name already exists. The entry is added
// at the end of the list.
//
void llist_insert_last(LinkedList * list, int value) {
  ListNode * a;
  a = list->head;

  if(list->head==NULL){
  ListNode * b = (ListNode *)  malloc(sizeof(ListNode));
  b->value = value;
  b->next = NULL;
  list->head = b;
  }

   while(a->next != NULL){
    a = a->next;
  }
   ListNode * b = (ListNode *)  malloc(sizeof(ListNode));
   b->value = value;
    b->next = NULL;

  a->next = b;
}

//
// Clear all elements in the list and free the nodes
//
void llist_clear(LinkedList *list){
  ListNode * a;
  a = list->head;
  while (a != NULL){
    free(a);
    a = a->next;
  }
  list->head = NULL;
}
