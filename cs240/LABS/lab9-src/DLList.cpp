#include "DLList.h"
#include "stdio.h"
#include "string.h"

/**
 * @brief      Constructor for the DLList.
 */
DLList::DLList()
{
	/** @todo Write a constructor for a dllist. Check slides! **/
	head = new DLNode();
	head->next = head;
	head->prev = head;
}

/**
 * @brief      Destructor for the DLList.
 */
DLList::~DLList()
{
	/** @todo Clean up your mess! **/
	DLNode * e = new DLNode();
	e = head->next;
	while(e!=head){
	    delete e;
	    e = e->next;
	}
}

/**
 * @brief      Print the DLList line by line.
 */
void DLList::print()
{
	/** @todo Print this list line by line **/
	DLNode * e = new DLNode();
	e = head->next;
	while(e!=head){
	    printf("%d\n",e->data);
	    e = e->next;
	}
}

/**
 * @brief      Sort and print the list.
 * 
 * This function should sort and print the list.
 * Note: the actual list is NOT to be modified.
 * In other words, it should only *print* in a
 * sorted order, not actually sort the list.
 */
void sortArray(int n,int * array) {
    int temp;
    int i,j;
    for(i=0;i<n;i++){
	for(j=i+1;j<n;j++){
	    if(*(array+i) > *(array+j)){
		temp = *(array+i);
		*(array+i) = *(array+j);
		*(array+j) = temp;
	    }   
	}   
    }   
}

void DLList::printSorted()
{
    /** @todo Print a sorted copy of this list **/
    int counter = 0;
    DLNode * e = head->next;
    while(e!=head){
	counter++;
	e = e->next;
    }
    int * array = new int[counter];
    e = head->next;
    int i = 0;
    while(e!=head){
	array[i] = e->data;
	i++;
	e = e->next;
    }
    sortArray(counter,array);
    
    for(int j = 0;j<i;j++){
	printf("%d\n",array[j]);
    }	
}

/**
 * @brief      Add to the front of the list.
 *
 * @param[in]  data  Item to add to front.
 */
void DLList::insertFront(int data)
{
	/** @todo Insert to the front of the list **/
	DLNode * e = new DLNode();
	e->data = data;

	e->next = head->next;
	e->prev = head;
	e->next->prev = e;
	head->next = e;

}

/**
 * @brief      Removes & stores the last element.
 *
 * The last element is removed and stored in the
 * referenced variable data.
 * 
 * @param      data  Thing in which we are storing the data.
 *
 * @return     True upon successful removal.
 */
bool DLList::removeLast(int & data)
{
	/** @todo Remove the last thing **/
	DLNode * e = head->prev;

	data = e->data;
	e->prev->next = head;
	head->next->prev = head;

	delete e;
}

/**
 * @brief      Difference of two lists.
 *
 * @param      list  Subtrahend.
 *
 * @return     Returns a pointer to the difference.
 */
DLList * DLList::difference(DLList & list)
{
    DLList * diff = new DLList();
    DLNode * e1  = head->next;
    DLNode * e2 = list.head->next;

    int flag = 0;

    //@todo Implement this function 
    while(e1!=head){
	e2 = list.head->next;
	flag = 0;
	while(e2!=list.head){
	    if(e1->data==e2->data){
		flag = 1;
	    }
	    e2 = e2->next;
	}
	if(flag==0){
	    diff->insertFront(e1->data);
	}
	e1 = e1->next;
    }
    return diff;
}

/**
 * @brief      Returns a sublist of items in a range.
 *
 * @param[in]  start  First index.
 * @param[in]  end    Second index.
 *
 * @return     Elements between first and second index.
 */
DLList * DLList::getRange(int start, int end)
{
	DLList * range = new DLList();
	/** @todo getRange **/
	return range;
}

/**
 * @brief      Intersection of this list and another list.
 *
 * @param      list  The other list.
 *
 * @return     Elements list shares with this one.
 */
DLList * DLList::intersection(DLList & list)
{
    DLList * inter = new DLList();
    DLNode * e1 = head->prev;
    DLNode * e2 = list.head->next;

    int flag = 0;
    /** @todo intersection **/
    while(e1!=head){
	e2 = list.head->next;
	flag = 0;
	while(e2!=list.head){
	    if(e1->data==e2->data){
		flag = 1;
	    }
	    e2 = e2->next;
	}
	if(flag==1){
	    inter->insertFront(e1->data);
	}
	e1 = e1->prev;
    }
    return  inter;
}

/**
 * @brief      Removes nodes in the start-end range.
 *
 * @param[in]  start  First node.
 * @param[in]  end    Second node.
 */
void DLList::removeRange(int start, int end)
{
	/** @todo Remove a range of elements **/
}
