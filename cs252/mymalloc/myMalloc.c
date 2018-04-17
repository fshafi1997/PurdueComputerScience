#include <errno.h>
#include <pthread.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "myMalloc.h"
#include "printing.h"

/* Due to the way assert() prints error messges we use out own assert function
 * for deteminism when testing assertions
 */
#ifdef TEST_ASSERT
inline static void assert(int e) {
    if (!e) {
       fprintf(stderr, "Assertion Failed!\n");
       exit(1);
   }
}
#else
#include <assert.h>
#endif

/*
 * Mutex to ensure thread safety for the freelist
 */
static pthread_mutex_t mutex;

/*
 * Array of sentinel nodes for the freelists
 */
header freelistSentinels[N_LISTS];

/*
 * Pointer to the second fencepost in the most recently allocated chunk from
 * the OS. Used for coalescing chunks
 */
header * lastFencePost;

/*
 * Pointer to maintian the base of the heap to allow printing based on the
 * distance from the base of the heap
 */
void * base;

/*
 * List of chunks allocated by  the OS for printing boundary tags
 */
header * osChunkList [MAX_OS_CHUNKS];
size_t numOsChunks = 0;

/*
 * direct the compiler to run the init function before running main
 * this allows initialization of required globals
 */
static void init (void) __attribute__ ((constructor));

// Helper functions for manipulating pointers to headers
static inline header * get_header_from_offset(void * ptr, ptrdiff_t off);
static inline header * get_left_header(header * h);
static inline header * ptr_to_header(void * p);

// Helper functions for allocating more memory from the OS
static inline void initialize_fencepost(header * fp, size_t left_size);
static inline void insert_os_chunk(header * hdr);
static inline void insert_fenceposts(void * raw_mem, size_t size);
static header * allocate_chunk(size_t size);

// Helper functions for freeing a block
static inline void deallocate_object(void * p);

// Helper functions for allocating a block
static inline header * allocate_object(size_t raw_size);

// Helper functions for verifying that the data structures are structurally
// valid
static inline header * detect_cycles();
static inline header * verify_pointers();
static inline bool verify_freelist();
static inline header * verify_chunk(header * chunk);
static inline bool verify_tags();

// Library initialization
static void init();

/**
 * @brief Helper function to retrieve a header pointer from a pointer and an
 *        offset
 *
 * @param ptr base pointer
 * @param off number of bytes from base pointer where header is located
 *
 * @return a pointer to a header offset bytes from pointer
 */
static inline header * get_header_from_offset(void * ptr, ptrdiff_t off) {
    return (header *)((char *) ptr + off);
}

/**
 * @brief Helper function to get the header to the right of a given header
 *
 * @param h original header
 *
 * @return header to the right of h
 */
header * get_right_header(header * h) {
    return get_header_from_offset(h, h->size);
}

/**
 * @brief Helper function to get the header to the left of a given header
 *
 * @param h original header
 *
 * @return header to the right of h
 */
inline static header * get_left_header(header * h) {
    return get_header_from_offset(h, -h->left_size);
}

/**
 * @brief Fenceposts are marked as always allocated and may need to have
 * a left object size to ensure coalescing happens properly
 *
 * @param fp a pointer to the header being used as a fencepost
 * @param left_size the size of the object to the left of the fencepost
 */
inline static void initialize_fencepost(header * fp, size_t left_size) {
    fp->allocated = FENCEPOST;
    fp->size = ALLOC_HEADER_SIZE;
    fp->left_size = left_size;
}

/**
 * @brief Helper function to maintain list of chunks from the OS for debugging
 *
 * @param hdr the first fencepost in the chunk allocated by the OS
 */
inline static void insert_os_chunk(header * hdr) {
    if (numOsChunks < MAX_OS_CHUNKS) {
       osChunkList[numOsChunks++] = hdr;
   }
}

/**
 * @brief given a chunk of memory insert fenceposts at the left and
 * right boundaries of the block to prevent coalescing outside of the
 * block
 *
 * @param raw_mem a void pointer to the memory chunk to initialize
 * @param size the size of the allocated chunk
 */
inline static void insert_fenceposts(void * raw_mem, size_t size) {
    // Convert to char * before performing operations
    char * mem = (char *) raw_mem;

    // Insert a fencepost at the left edge of the block
    header * leftFencePost = (header *) mem;
    initialize_fencepost(leftFencePost, ALLOC_HEADER_SIZE);

    // Insert a fencepost at the right edge of the block
    header * rightFencePost = get_header_from_offset(mem, size - ALLOC_HEADER_SIZE);
    initialize_fencepost(rightFencePost, size - 2 * ALLOC_HEADER_SIZE);
}

/**
 * @brief Allocate another chunk from the OS and prepare to insert it
 * into the free list
 *
 * @param size The size to allocate from the OS
 *
 * @return A pointer to the allocable block in the chunk (just after the
 * first fencpost)
 */
static header * allocate_chunk(size_t size) {
    void * mem = sbrk(size);

    insert_fenceposts(mem, size);
    header * hdr = (header *) ((char *)mem + ALLOC_HEADER_SIZE);
    hdr->allocated = UNALLOCATED;
    hdr->size = size - 2 * ALLOC_HEADER_SIZE;
    hdr->left_size = ALLOC_HEADER_SIZE;

    return hdr;
}

/**
 * @brief Helper allocate an object given a raw request size from the user
 *
 * @param raw_size number of bytes the user needs
 *
 * @return A block satisfying the user's request
 */
//Method used to roundup request size
size_t roundUp(size_t numToRound, int multiple)
{
    if (multiple == 0)
       return numToRound;

   int remainder = abs(numToRound) % multiple;
   if (remainder == 0)
       return numToRound;
   else
       return numToRound + multiple - remainder;
}

//method to get ceiling of a number
int ceil2(double num) {
    int inum = (int)num;
    if (num == (double)inum) {
       return inum;
   }
   return inum + 1;
}

static inline header * allocate_object(size_t raw_size) {
    // TODO implement allocation

    // Remove the following three lines when implementing this function

    // The (void) line is telling the compiler that it is OK not to use the
    // variable raw_size, otherwise a warning would be thrown
    if (raw_size == 0)
    {
       return NULL;
   }
    //Rounding off
   size_t numToRound = raw_size + ALLOC_HEADER_SIZE;
   size_t roundedSize = roundUp(numToRound,8);
   if (roundedSize<sizeof(header))
   {
       roundedSize = sizeof(header);
   }

   int listToUse;
   if (roundedSize > (N_LISTS-1)*8)
   {
       listToUse = N_LISTS-1;
   }
   else{
       listToUse = (roundedSize/8) - 1;
   }

   header * freeList = &freelistSentinels[listToUse];
   header * current;

    // checking if freelist empty
    // iterating to next one
   while(freeList->next == freeList && listToUse < N_LISTS - 1) {
       listToUse++;
       freeList = &freelistSentinels[listToUse];
   }

   for (current = freeList->next; current != freeList || listToUse != N_LISTS - 1; current = current->next) {
	//for first case
	//and If the block is larger than the request size, but the remainder is too small to be allocated on its own

       if (current->size == roundedSize || ((current->size - roundedSize)<=sizeof(header))) {
	    //Changing state of allocation
           current->allocated = 1;

	    //Removing the block from the freelist
           current->next->prev = current->prev;
           current->prev->next = current->next;
           return (header *)(current->data);
       }

	//If the block is larger than the request size and the remainder is large enough to be allocated on its own,
       else if (current->size > roundedSize) {

	    //current size minus from rounded size
           current->size = current->size - roundedSize;

	    //pointer at splitting point
           header * newHeader = (header *) ((char *) current + current->size);

	    //values giving to header
           newHeader->size = roundedSize;
           newHeader->left_size = current->size;
           newHeader->allocated = 1;

	    //updating old header left_size
           header * right =(header *) ((char *) newHeader + roundedSize);
	    //checking for dummy header
           if (right->size != 0)
           {
              right->left_size = roundedSize;
          }

          int newListToUse;
          if ((current->size - ALLOC_HEADER_SIZE) > (N_LISTS-1)*8)
          {
              newListToUse = N_LISTS-1;
          }
          else{
              newListToUse = ((current->size - ALLOC_HEADER_SIZE)/8) - 1;
          }

          if (newListToUse != listToUse) {
              current->next->prev = current->prev;
              current->prev->next = current->next;
		//current->next->left_size = current->prev->size;
              get_right_header(current)->left_size = current->size;

              freeList = &freelistSentinels[newListToUse];
              current->next=freeList->next;
              current->next->prev = current;
              freeList->next = current;
              current->prev = freeList;
          }

          return (header *)(newHeader->data);
      }

  }

  int x = ARENA_SIZE;
  if(ARENA_SIZE < roundedSize) {
   double z = (double)(roundedSize) / ARENA_SIZE;
   double y = ceil2(z);
   x = ARENA_SIZE * y;
}
header * newHeader = allocate_chunk(x);

    //coalescing
header * leftFencePostNew = get_left_header(newHeader);
header * rightFencePostOld = get_left_header(leftFencePostNew);
if (rightFencePostOld != NULL && rightFencePostOld->allocated == FENCEPOST)
{
	header * forChecking = get_left_header(rightFencePostOld);
	if (forChecking->allocated == UNALLOCATED)
	{
       int oldListOfForChecking = ((forChecking->size - ALLOC_HEADER_SIZE)/8)-1;

       forChecking->size = forChecking->size + rightFencePostOld->size + leftFencePostNew->size + newHeader->size;

       int whereShouldItBe = ((forChecking->size - ALLOC_HEADER_SIZE)/8)-1;

	    //Removing the block from the old freelist
       forChecking->next->prev = forChecking->prev;
       forChecking->prev->next = forChecking->next;

	    //putting in new list
       if (oldListOfForChecking!=whereShouldItBe)
       {
          if (whereShouldItBe > N_LISTS-1)
          {
              whereShouldItBe = N_LISTS-1;
          }

          freeList = &freelistSentinels[whereShouldItBe];
          forChecking->next=freeList->next;
          forChecking->next->prev = forChecking;
          freeList->next = forChecking;
          forChecking->prev = freeList;
      }

      header * lastFencePost = get_right_header(forChecking);
      lastFencePost->left_size = forChecking->size;

	    //check index and change it if needed

  }
  else{
   rightFencePostOld->size = rightFencePostOld->size + leftFencePostNew->size + newHeader->size;
   header * lastFencePost = get_right_header(rightFencePostOld);
   lastFencePost->left_size = rightFencePostOld->size;
   rightFencePostOld->allocated = UNALLOCATED;

   rightFencePostOld->next = freeList;
   rightFencePostOld->prev = freeList->prev;
   freeList->prev = rightFencePostOld;
   rightFencePostOld->prev->next = rightFencePostOld;
}
}
else{
	insert_os_chunk(get_left_header(newHeader));
	newHeader->next = freeList;
	newHeader->prev = freeList->prev;
	freeList->prev = newHeader;
	newHeader->prev->next = newHeader;
}

return allocate_object(raw_size);
}

/**
 * @brief Helper to get the header from a pointer allocated with malloc
 *
 * @param p pointer to the data region of the block
 *
 * @return A pointer to the header of the block
 */
static inline header * ptr_to_header(void * p) {
    return (header *)((char *) p - ALLOC_HEADER_SIZE); //sizeof(header));
}

/**
 * @brief Helper to manage deallocation of a pointer returned by the user
 *
 * @param p The pointer returned to the user by a call to malloc
 */
static inline void deallocate_object(void * p) {
    if (p == NULL)
    {
       return;
   }
    //pointer to header currenthaeder = current
   header * current = ptr_to_header(p);
    //left header
   header * left = get_left_header(current);
    //right header
   header * right = get_right_header(current);


   if (current->allocated != ALLOCATED)
   {
       printf("Double Free Detected\n");
       assert(false);
   }

    //pointer is to the start of free list
   int listToUse;

   if (current->size - ALLOC_HEADER_SIZE> (N_LISTS-1)*8)
   {
       listToUse = N_LISTS-1;
   }
   else{
       listToUse = ((current->size-ALLOC_HEADER_SIZE)/8) - 1;
   }

   header * freeList = &freelistSentinels[listToUse];

    //test double free not working without this line
   current->allocated = UNALLOCATED;
    //coalescing both sides
   if (((left->allocated == UNALLOCATED) && (right->allocated == UNALLOCATED))) {

	//increasing size of left node and getting index old list
       int oldList2use = ((left->size - ALLOC_HEADER_SIZE)/8)-1;

       left->size = left->size + current->size + right->size;

       right->next->prev = right->prev;
       right->prev->next = right->next;

	//changing size of right
       get_right_header(right)->left_size = left->size;

	//changing list
       int newList2use = ((left->size - ALLOC_HEADER_SIZE)/8)-1;
       if (oldList2use<N_LISTS-1)
       {
           if (newList2use!=oldList2use)
           {
               left->next->prev = left->prev;
               left->prev->next = left->next;

               if (newList2use > N_LISTS-1)
               {
                  newList2use = N_LISTS-1;
              }

              freeList = &freelistSentinels[newList2use];
              left->next=freeList->next;
              left->next->prev = left;
              freeList->next = left;
              left->prev = freeList;
          }
      }

  }
    //coalescing with left side
  else if (left->allocated == UNALLOCATED) {

	//increasing left header size and getting list old

   int oldList2use = ((left->size - ALLOC_HEADER_SIZE)/8)-1;

   left->size = left->size + current->size;

   get_right_header(left)->left_size = left->size;

	//changing list
   int newList2use = ((left->size - ALLOC_HEADER_SIZE)/8)-1;
   if (oldList2use<N_LISTS-1)
   {
       if (newList2use!=oldList2use)
       {
           left->next->prev = left->prev;
           left->prev->next = left->next;

           if (newList2use > N_LISTS-1)
           {
              newList2use = N_LISTS-1;
          }

          freeList = &freelistSentinels[newList2use];
          left->next=freeList->next;
          left->next->prev = left;
          freeList->next = left;
          left->prev = freeList;
      }
  }

}
    //coalescing with right
else if (right->allocated == UNALLOCATED) {

	//now increasing size of current front of rigt header and getting old list
	current->size = current->size + right->size;

	int oldList2use = ((right->size - ALLOC_HEADER_SIZE)/8)-1;

	//updating pointers
	//current->next = right->next;
	//current->prev = right->prev;

	right->prev->next = right->next;
	right->next->prev = right->prev;

	//unallocated now
	current->allocated = 0;

	get_right_header(right)->left_size = current->size;

	//changing list
	int newList2use = ((current->size - ALLOC_HEADER_SIZE)/8)-1;
    if (oldList2use<N_LISTS-1)
    {
       if (newList2use!=oldList2use)
       {
	    //current->next->prev = current->prev;
	    //current->prev->next = current->next;

           if (newList2use > N_LISTS-1)
           {
              newList2use = N_LISTS-1;
          }

          freeList = &freelistSentinels[newList2use];
          current->next=freeList->next;
          current->next->prev = current;
          freeList->next = current;
          current->prev = freeList;
      }
  }

}
    //No coalescing case
else {
	current->next = freeList->next;
	current->prev = freeList;
	current->next->prev = current;
	freeList->next = current;

	//unallocated now
	current->allocated = 0;
}

}

/**
 * @brief Helper to detect cycles in the free list
 * https://en.wikipedia.org/wiki/Cycle_detection#Floyd's_Tortoise_and_Hare
 *
 * @return One of the nodes in the cycle or NULL if no cycle is present
 */
static inline header * detect_cycles() {
    for (int i = 0; i < N_LISTS; i++) {
       header * freelist = &freelistSentinels[i];
       for (header * slow = freelist->next, * fast = freelist->next->next;
          fast != freelist;
          slow = slow->next, fast = fast->next->next) {
           if (slow == fast) {
              return slow;
          }
      }
  }
  return NULL;
}

/**
 * @brief Helper to verify that there are no unlinked previous or next pointers
 *        in the free list
 *
 * @return A node whose previous and next pointers are incorrect or NULL if no
 *         such node exists
 */
static inline header * verify_pointers() {
    for (int i = 0; i < N_LISTS; i++) {
       header * freelist = &freelistSentinels[i];
       for (header * cur = freelist->next; cur != freelist; cur = cur->next) {
           if (cur->next->prev != cur || cur->prev->next != cur) {
              return cur;
          }
      }
  }
  return NULL;
}

/**
 * @brief Verify the structure of the free list is correct by checkin for
 *        cycles and misdirected pointers
 *
 * @return true if the list is valid
 */
static inline bool verify_freelist() {
    header * cycle = detect_cycles();
    if (cycle != NULL) {
       fprintf(stderr, "Cycle Detected\n");
       print_sublist(print_object, cycle->next, cycle);
       return false;
   }

   header * invalid = verify_pointers();
   if (invalid != NULL) {
       fprintf(stderr, "Invalid pointers\n");
       print_object(invalid);
       return false;
   }

   return true;
}

/**
 * @brief Helper to verify that the sizes in a chunk from the OS are correct
 *        and that allocated node's canary values are correct
 *
 * @param chunk AREA_SIZE chunk allocated from the OS
 *
 * @return a pointer to an invalid header or NULL if all header's are valid
 */
static inline header * verify_chunk(header * chunk) {
    if (chunk->allocated != FENCEPOST) {
       fprintf(stderr, "Invalid fencepost\n");
       print_object(chunk);
       return chunk;
   }

   for (; chunk->allocated != FENCEPOST; chunk = get_right_header(chunk)) {
       if (chunk->size  != get_right_header(chunk)->left_size) {
           fprintf(stderr, "Invalid sizes\n");
           print_object(chunk);
           return chunk;
       }
   }

   return NULL;
}

/**
 * @brief For each chunk allocated by the OS verify that the boundary tags
 *        are consistent
 *
 * @return true if the boundary tags are valid
 */
static inline bool verify_tags() {
    for (size_t i = 0; i < numOsChunks; i++) {
       header * invalid = verify_chunk(osChunkList[i]);
       if (invalid != NULL) {
           return invalid;
       }
   }

   return NULL;
}

/**
 * @brief Initialize mutex lock and prepare an initial chunk of memory for allocation
 */
static void init() {
    // Initialize mutex for thread safety
    pthread_mutex_init(&mutex, NULL);

#ifdef DEBUG
    // Manually set printf buffer so it won't call malloc when debugging the allocator
    setvbuf(stdout, NULL, _IONBF, 0);
#endif // DEBUG

    // Allocate the first chunk from the OS
    header * block = allocate_chunk(ARENA_SIZE);

    header * prevFencePost = get_header_from_offset(block, -ALLOC_HEADER_SIZE);
    insert_os_chunk(prevFencePost);

    lastFencePost = get_header_from_offset(block, block->size);

    // Set the base pointer to the beginning of the first fencepost in the first
    // chunk from the OS
    base = ((char *) block) - ALLOC_HEADER_SIZE; //sizeof(header);

    // Initialize freelist sentinels
    for (int i = 0; i < N_LISTS; i++) {
       header * freelist = &freelistSentinels[i];
       freelist->next = freelist;
       freelist->prev = freelist;
   }

    // Insert first chunk into the free list
   header * freelist = &freelistSentinels[N_LISTS - 1];
   freelist->next = block;
   freelist->prev = block;
   block->next = freelist;
   block->prev = freelist;
}

/*
 * External interface
 */
void * my_malloc(size_t size) {
    pthread_mutex_lock(&mutex);
    header * hdr = allocate_object(size);
    pthread_mutex_unlock(&mutex);
    return hdr;
}

void * my_calloc(size_t nmemb, size_t size) {
    return memset(my_malloc(size * nmemb), 0, size * nmemb);
}

void * my_realloc(void * ptr, size_t size) {
    void * mem = my_malloc(size);
    memcpy(mem, ptr, size);
    my_free(ptr);
    return mem;
}

void my_free(void * p) {
    pthread_mutex_lock(&mutex);
    deallocate_object(p);
    pthread_mutex_unlock(&mutex);
}

bool verify() {
    return verify_freelist() && verify_tags();
}
