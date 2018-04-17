
// String Implementation
// IMPORTANT: Do not use any of the functions in the string C runtime library
// Example. Do not use strcpy, strcmp, etc. Implement your own

// IMPORTANT: See the MyString.h file for a description of
// what each method needs to do.

#include <stdio.h>
#include "MyString.h"

// My own implementation of strlen
int
MyString::slength(const char *s) const
{
  // Add implementation here
  int len = 0;
  while(*s!='\0'){
      s++;
      len++;
  }
  return len;
}

// Initialize _s. Allocate memory for _s and copy s into _s
void
MyString::initialize(const char * s)
{
  // Add implementation here
  // Allocate memory for _s.
  _s = new char[slength(s)];
  // Copy s into _s
  int i = 0;
  while(*s!='\0'){
      _s[i] = *s++;
      i++;
  }
  _s[i] = '\0';
	
}

// Create a MyString from a C string
MyString::MyString(const char * s)
{
  initialize(s);
}

// Create a MyString from a copy of another string
MyString::MyString(const MyString &s)
{
  initialize(s._s);
}

// Create a MyString with an empty string
MyString::MyString()
{
  _s = new char[1];
  *_s = 0;
}

// Assignment operator. Without this operator the assignment is
// just a shallow copy that will copy the pointer _s. If the original _s
// goes away then the assigned _s will be a dangling reference.
MyString &
MyString::operator = (const MyString & other) {
  if (this != &other) // protect against invalid self-assignment
  {
    // deallocate old memory
    delete [] _s;

    // Initialize _s with the "other" object.
    initialize(other._s);

    // by convention, always return *this
    return *this;
  }
}

// Obtain a substring of at most n chars starting at location i
// if i is larger than the length of the string return an empty string.
    MyString
MyString::substring(int i, int n)
{
    // Add implementation here


    // Make sure that i is not beyond the end of string

    // Allocate memory for substring
    int len = slength(_s);
    char * substring = new char[len+1];


    // Copy characters of substring
    if(i>len){
	return "";
    }
    int j =0;
    int counter = 0;
    while(counter!=n){
	substring[j] = _s[i];
	i++;
	j++;
	counter++;
    }

    MyString sub(substring);

    // Return substring
    return sub;
}

// Remove at most n chars starting at location i
    void
MyString::remove(int i, int n)
{
    // Add implementation here

    // If i is beyond the end of string return
    int len = slength(_s);
    if(i>len){
	return;
    }
    // If i+n is beyond the end trunc string
    // Remove characters
    int sum = i + n;
    int counter = 0;
    if(sum>len){
	while(_s[i]!='\0'){
	    _s[i] = '\0';
	    i++;
	}
    }
    else{
	while(_s[i]!='\0'){
	    _s[i] = _s[i+n];
	    i++;
	    counter++;
	}
    }
}

// Return true if strings "this" and s are equal
bool
MyString::operator == (const MyString & s)
{
  // Add implementation here
    int len1 = slength(_s);
    int len2 = slength(s._s);
    int flag = 0;
    if(len1!=len2){
	return false;
    }
    if(len1 == len2){
	for(int i = 0;i<len1;i++){
	    if(_s[i] != s._s[i]){
		return false;
	    }
	}
    }
    return true;
}


// Return true if strings "this" and s are not equal
    bool
MyString::operator != (const MyString &s)
{
    // Add implementation here
     int len1 = slength(_s);
    int len2 = slength(s._s);
    int flag = 0;
    if(len1!=len2){
	return true;
    }
    if(len1 == len2){
	for(int i = 0;i<len1;i++){
	    if(_s[i] != s._s[i]){
		return true;
	    }
	}
    }
    return false;
}



// Return true if string "this" and s is less or equal
    bool
MyString::operator <= (const MyString &s)
{
    // Add implementation here
    int len1 = slength(_s);
    int len2 = slength(s._s);
    int flag = 0;
    if(len1<len2){
	return true;
    }
    else if(len1>len2){
	return false;
    }
    else if(len1==len2){
	for(int i = 0;i<len1;i++){
	    if(_s[i] <= s._s[i]){
		return true;
	    }
	    else if(_s[i] > s._s[i]){
		return false;
	    }
	}
	return false;
    }
}

// Return true if string "this" is greater than s
bool
MyString::operator > (const MyString &s)
{
  // Add implementation here
    int len1 = slength(_s);
    int len2 = slength(s._s);
    int flag = 0;
    if(len1!=len2){
	return false;
    }
    if(len1==len2){
	return false;
    }
    if(len1 > len2){
	for(int i = 0;i<len1;i++){
	    if(_s[i] < s._s[i]){
		return false;
	    }
	}
    }
    return true; 
}

// Return character at position i.  Return '\0' if out of bounds.
char
MyString::operator [] (int i)
{
  // Add implementation here
 int len = slength(_s);
 if(i>len){
     return '\0';
 }
 else return _s[i];
}

// Return C representation of string
const char *
MyString::cStr()
{
  // Add implementation here
  return _s;
}

// Get string length of this string.
int
MyString::length() const
{
  // Add implementation here
  return slength(_s);
}

// Destructor. Deallocate the space used by _s
MyString::~MyString()
{
  // Add implementation here
  delete [] _s;
}

// Concatanate two strings (non member method)
MyString operator + (const MyString &s1, const MyString &s2)
{
    // Add implementation here
    int len1 = s1.slength(s1._s);
    int len2 = s2.slength(s2._s);
    int sum = len1+len2;
    // Allocate memory for the concatenated string
    char * concat = new char[sum+1];	
    // Add s1
    int i = 0;
    int index = 0;
    while(s1._s[i]!='\0'){
	concat[index] = s1._s[i];
	index++;
	i++;
    }

    // Add s2
    int j =0;
    while(s2._s[j]!='\0'){
	concat[index] = s2._s[j];
	index++;
	j++;
    }
    concat[index] = '\0';
    MyString s(concat);
    return s;
}

