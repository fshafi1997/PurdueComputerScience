#ifndef command_hh
#define command_hh

#include "simpleCommand.hh"

// Command Data Structure

struct Command {
  std::vector<SimpleCommand *> _simpleCommands;
  std::string * _outFile;
  std::string * _inFile;
  std::string * _errFile;
  bool _background;
  int _counterOut;
  int _counterIn;
  int appendFlag;

  Command();
  void insertSimpleCommand( SimpleCommand * simpleCommand );
  void expanding();
  void clear();
  void print();
  void execute();
  int checkForBuiltIn(int i);

  static SimpleCommand *_currentSimpleCommand;
};

#endif
