/**
 * This file contains the primary logic for your server. It is responsible for
 * handling socket communication - parsing HTTP requests and sending HTTP responses
 * to the client. 
 */
#include "server.hh"
#include <sys/resource.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <dirent.h>
#include <string.h>
#include <inttypes.h>
#include <fcntl.h>
#include <time.h>
#include <dlfcn.h>
#include <link.h>
#include <errno.h>

#include <iostream>
#include <thread>
#include <fstream>
#include <functional>
#include <sstream>
#include <vector>
#include <tuple>
#include <string>
#include <algorithm>
#include <cstdio>
#include <ctime>

#include "tcp.hh"
#include "http_messages.hh"
#include "errors.hh"
#include "misc.hh"
#include "routes.hh"



using namespace std;
int auth = 0;
int cgi, stats, logs;
time_t start = time(0);
std::clock_t startMaxT;
double duration;
int numRequests = 0;
std::string requestQ;
typedef void (*httprunfunc)(int ssock, const char* querystring);

Server::Server(SocketAcceptor const& acceptor) : _acceptor(acceptor) { }

void Server::run_linear() const {
  while (1) {
    Socket_t sock = _acceptor.accept_connection();
    handle(sock);
  }
}

void Server::run_fork() const {
  // TODO: Task 1.4
  while (1) {
    Socket_t sock = _acceptor.accept_connection();
    int ret;
    if ( sock != NULL ) {
      ret = fork();
      if ( ret == 0 ) {
        // Process request.
        handle(sock);
        exit(0);
      }
    }
    wait3(0, 0, NULL);
    while (waitpid(-1, NULL, WNOHANG) > 0) {}
  }
}

void Server::run_thread() const {
  while (1) {
    Socket_t sock = _acceptor.accept_connection();
    if ( sock != NULL ) {
      std::thread(&Server::handle, this, std::move(sock)).detach();
    }
  }
}

void Server::threadPool() const {
  while (1) {
    Socket_t sock = _acceptor.accept_connection();
    handle(sock);
  }
}

void Server::run_thread_pool(const int num_threads) const {
  // TODO: Task 1.4
  int len = num_threads;
  std::thread myThreads[len];

  for (int x = 0; x < num_threads-1; x+=1) {
    myThreads[x] = std::thread(&Server::threadPool, this);
  }

  threadPool();
}

void eraseSubStr(std::string & mainStr, const std::string & toErase) {
  // Search for the substring in string
  size_t pos = mainStr.find(toErase);

  if (pos != std::string::npos) {
    // If found then erase it from string
    mainStr.erase(pos, toErase.length());
  }
}
// splitting with string delim
vector<string> split(string s, string delimiter) {
  size_t pos_start = 0, pos_end, delim_len = delimiter.length();
  string token;
  vector<string> res;
  while ((pos_end = s.find(delimiter, pos_start)) != string::npos) {
    token = s.substr(pos_start, pos_end - pos_start);
    pos_start = pos_end + delim_len;
    res.push_back(token);
  }
  res.push_back(s.substr(pos_start));
  return res;
}


void Server::process_Request(const Socket_t& sock, HttpRequest* const request) const {
  std::string theRequest = sock->readline();
  numRequests += 1;
  startMaxT = std::clock();
  char* filePath = NULL;
  char* requestType = NULL;
  char* fileType = NULL;
  char* protocol = NULL;
  char* root = NULL;
  int indexLoop = 0;
  cgi = 0;
  stats = 0;
  logs = 0;

  while (theRequest.compare("\r\n") != 0) {
    if (indexLoop == 0) {
      vector <string> splitResult;
      std::string token;
      while (token != theRequest) {
        token = theRequest.substr(0, theRequest.find_first_of(" "));
        theRequest = theRequest.substr(theRequest.find_first_of(" ") + 1);
        splitResult.push_back(token);
      }
      request->method = splitResult[0];
      request->request_uri = splitResult[1];

      if (strstr(splitResult[1].c_str(), "cgi-bin") != 0) {
        cgi = 1;
      }

      string httpV = splitResult[2];
      eraseSubStr(httpV, "\r\n");
      request->http_version = httpV;

      std::string theRequest22 = splitResult[1];

      int maxLength = 1024;

      filePath = reinterpret_cast<char*>(malloc(maxLength + 1));
      fileType = reinterpret_cast<char*>(malloc(maxLength + 1));
      protocol = reinterpret_cast<char*>(malloc(9));

      memset(filePath, '\0', maxLength + 1);
      memset(fileType, '\0', maxLength + 1);
      memset(protocol, '\0', 9);


      memcpy(protocol, (char*)"HTTP/1.0", 8);

      // the current character
      unsigned char newChar = 0;

      // the last character
      unsigned char lastChar = 0;

      int n = 0;
      int z = 0;

      if (strcmp(request->method.c_str(), "GET") == 0) {
        int x = 0;
        int y = 0;
        int fileExt = 0;

        for (std::string::size_type i = 0; i < theRequest22.size() && newChar != ' '; ++i) {
          newChar = theRequest22[i];
          if (fileExt) {
            fileType[y] = newChar;
            y+=1;
          }
          if (newChar == '.') {
            fileExt = 1;
          }
          filePath[x] = newChar;
          x+=1;
        }

        for (std::string::size_type i = 0; i < theRequest22.size(); ++i) {
          newChar = theRequest22[i];

          if (newChar == 10 && lastChar == 13) {
            newChar = theRequest22[i+1];
            if (newChar == 13) {
              newChar = theRequest22[i+1];
              if (newChar == 10) {
                break;
              }
            }
          }
          lastChar = newChar;
        }
        if (cgi == 1) {
          if (strstr(request->request_uri.c_str(), "?") != NULL) {
            vector <string> theName = split(request->request_uri, "?");
            vector <string> theName2 = split(theName[0], "/");
            std::vector<int>::size_type size = theName2.size();
            std::string forRoot = "http-root-dir/cgi-bin/" + theName2[size-1];
            root = strdup(forRoot.c_str());
          } else {
            std::string forRoot = "http-root-dir" + request->request_uri;
            root = strdup(forRoot.c_str());
          }
        } else {
          if (strcmp(filePath, "/") == 0) {
            std::string forRoot = "http-root-dir/htdocs/index.html";
            root = strdup(forRoot.c_str());
            strcpy(fileType, "html");
          } else if (filePath[strlen(filePath) - 1] == '/') {
            strcat(filePath, "index.html");
            strcpy(fileType, "html");
            std::string fileP = std::string(filePath);
            std::string forRoot = "http-root-dir/htdocs" + fileP;
            root = strdup(forRoot.c_str());
          } else {
            std::string fileP = std::string(filePath);
            if (strcmp(fileP.c_str(), "/stats") == 0) {
              stats = 1;
            }
            if (strcmp(fileP.c_str(), "/logs") == 0) {
              logs = 1;
            }
            std::string forRoot = "http-root-dir/htdocs" + fileP;
            root = strdup(forRoot.c_str());
          }
        }
      } else {
        return;
      }

      int j = open(root, O_RDONLY);
      if (j <= 0 && strstr(root, "index.html")) {
        int x = strlen(root)-1;
        while (root[x] != '/') {
          root[x] = '\0';
          x -= 1;
        }
      }
      close(j);

    } else {
      std::string delimiter = ": ";
      vector <string> splitResult = split(theRequest, delimiter);
      string headerName = splitResult[0];
      string headerValue = splitResult[1];
      eraseSubStr(headerValue, "\r\n");
      request->headers.insert(std::pair<string, string>(headerName, headerValue));
    }
    // to read next line in loop
    indexLoop++;
    theRequest = sock->readline();
  }


  vector <string> headerValues;
  vector <string> headerKeys;

  if (!request->headers.empty()) {
    for (auto const& p : request->headers) {
      headerKeys.push_back(p.first);
      headerValues.push_back(p.second);
    }
  }

  // clear this variable
  setenv("REQUEST_METHOD", "GET", 1);
  char *cwd;
  cwd = strdup(request->request_uri.c_str());
  char * envVar = strstr(cwd, "?");
  if (envVar != NULL) {
    vector <string> theQuery = split(std::string(cwd), "?");
    request->request_uri = theQuery[0];
    request->query = theQuery[1];
    requestQ = theQuery[1];
    setenv("QUERY_STRING", theQuery[1].c_str(), 1);

    if (strstr(theQuery[1].c_str(), "&") != NULL) {
      vector <string> thePairs = split(theQuery[1], "&");
      for (std::vector<int>::size_type i = 0; i != thePairs.size(); i++) {
        if (strstr(thePairs[i].c_str(), "=") != NULL) {
          vector <string> keyValue = split(thePairs[i], "=");
          setenv(keyValue[0].c_str(), keyValue[1].c_str(), 1);
        }
      }
    } else {
      vector <string> keyValue = split(theQuery[1], "=");
      setenv(keyValue[0].c_str(), keyValue[1].c_str(), 1);
    }
  } else {
    setenv("QUERY_STRING", "", 1);
  }

  request->print();

  // auth checking then sending the response
  if (std::find(headerKeys.begin(), headerKeys.end(), "Authorization") != headerKeys.end()) {
    std::string delimiter = " ";
    std::string toAuth;
    toAuth = request->headers.find("Authorization")->second;
    vector <string> toAuthResult = split(toAuth, delimiter);
    eraseSubStr(toAuthResult[1], "\r\n");

    std::string encoded = "Y3MyNTI6ZmluYWxUZXN0";

    if (toAuthResult[1] == "Y3MyNTI6ZmluYWxUZXN0") {
      auth = 1;
      DIR *pDir = opendir(root);
      if (pDir) {
        processResponse(sock, protocol, root, 200, fileType, 1, 1);
        closedir(pDir);
      } else {
        int f = open(root, O_RDONLY);
        if (f > 0) {
          printf("root is %s[200]\n", root);
          processResponse(sock, protocol, root, 200, fileType, 0, 1);
        } else {
          printf("root is %s[404]\n", root);
          processResponse(sock, protocol, root, 404, fileType, 0, 0);
        }
        close(f);
      }
    } else {
      processResponse(sock, protocol, root, 401, fileType, 0, 0);
    }
  } else {
    processResponse(sock, protocol, root, 401, fileType, 0, 0);
  }



  if (root != NULL && root != 0)
    free(root);
  else
    root = NULL;

  if (cwd != NULL && cwd!= 0)
    free(cwd);
  else
    cwd = NULL;

  if (fileType != NULL && fileType != 0)
    free(fileType);
  else
    fileType = NULL;

  if (filePath != NULL && filePath != 0)
    free(filePath);
  else
    filePath = NULL;

  if (protocol != NULL && protocol != 0)
    free(protocol);
  else
    protocol = NULL;
}

string readFile2(const string &fileName) {
  ifstream ifs(fileName.c_str(), ios::in | ios::binary | ios::ate);

  ifstream::pos_type fileSize = ifs.tellg();
  ifs.seekg(0, ios::beg);

  vector<char> bytes(fileSize);
  ifs.read(bytes.data(), fileSize);

  ifs.close();

  return string(bytes.data(), fileSize);
}

void RemoveLine(std::string& source, const std::string& to_remove) {
  size_t m = source.find(to_remove);
  size_t n = source.find_first_of("\n", m + to_remove.length());
  source.erase(m, n - m + 1);
}

// 0 means the file things should not happend and basic resposne made
void Server::processResponse(const Socket_t& sock, char* protocol, char* filePath, int code,
    char* fileType, int dir, int headerCheck) const {

  HttpResponse resp;
  resp.http_version = "HTTP/1.0";

  if (code == 200) {
    resp.status_code = 200;
    resp.reason_phrase = "OK";
  } else if (code == 404) {
    resp.status_code = 404;
    resp.reason_phrase = "File Not Found";
  } else if (code == 401) {
    resp.status_code = 401;
    resp.reason_phrase = "Unauthorized";
    resp.headers.insert(std::pair<string, string>(std::string("WWW-Authenticate: "),
      std::string("Basic realm=myhttpd-cs252")));
  }

  if (code != 401 && headerCheck == 1) {
    switch (fileType[0]) {
      case 'g': resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("image/gif")));
                break;
      case 'j': resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("image/jpeg")));
                break;
      case 't': resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("text/plain")));
                break;
      case 'h': resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("text/html")));
                break;
      case 'i': resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("image/x-icon")));
                break;
      case 's': resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("image/svg")));
                break;
      default: if (!dir) {
                 resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
        std::string("text/plain")));
               }
               break;
    }
  }

  if (stats) {
    double seconds_since_start = difftime(time(0), start);
    duration = (std::clock() - startMaxT) / static_cast<double>(CLOCKS_PER_SEC);
    std::ofstream out("http-root-dir/htdocs/stats");
    out << "Server owner is Farhan Shafi " << std::endl;
    out << "Number requests since start are " << std::to_string(numRequests) << std::endl;
    out << "Maximum service time is " << std::to_string(duration) << " seconds" << std::endl;
    out << "Minimum service time is 0.000000" << " seconds" << std::endl;
    out << "Program has been running for " << std::to_string(seconds_since_start)
      << " seconds" << std::endl;
    out.close();
  }

  std::ofstream outfile;
  outfile.open("http-root-dir/htdocs/logs", std::ios_base::app);
  outfile << "Data Log is " << std::endl;
  outfile << "source ip address is 127.0.0.1 " << std::endl;
  outfile << "route requested is " << std::string(filePath) << std::endl;
  outfile << "response code is " << std::to_string(code) << std::endl;
  outfile.close();

  if (code == 200 && headerCheck == 1) {
    if (cgi) {
      if (strstr(filePath, ".so") != NULL) {
        void * lib = dlopen(filePath, RTLD_LAZY);
        if ( lib == NULL ) {
          fprintf(stderr, "./hello.so not found\n");
          perror("dlopen");
          exit(1);
        }
        // Get function to print hello
        httprunfunc hello_httprun;

        hello_httprun = (httprunfunc) dlsym(lib, "httprun");
        if ( hello_httprun == NULL ) {
          perror("dlsym: httprun not found:");
          exit(1);
        }
        std::string response;
        int pipe_fd[2];
        if (pipe(pipe_fd) == -1) {
          perror("get_content_type pipe error");
          exit(-1);
        }
        int pid = fork();

        if (pid == -1) {
          perror("get_content_type fork error");
          exit(-1);
        }
        if (pid == 0) {
          close(pipe_fd[0]);  // close read end
          dup2(pipe_fd[1], STDOUT_FILENO);
          dup2(pipe_fd[1], STDOUT_FILENO);
          close(pipe_fd[1]);
          hello_httprun(1, requestQ.c_str());
        } else {
          close(pipe_fd[1]);  // close write end

          char buf;
          while (read(pipe_fd[0], &buf, 1) > 0) {
            response += buf;
          }
          close(pipe_fd[0]);  // close read end
        }

        std::string delimiter = std::string("\n");
        vector <string> firstLine = split(response, delimiter);
        vector <string> firstLineSplitted = split(firstLine[0], ": ");
        resp.headers.erase("Content-Type: ");
        resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
              firstLineSplitted[1]));
        RemoveLine(response, "Content-type:");
        resp.message_body = response;
      } else {
        FILE *fp = popen(filePath, "r");
        char buf[100];
        char *str = NULL;
        char *temp = NULL;
        unsigned int size = 1;
        unsigned int strlength;
        std::string contents = std::string("");

        if (NULL == fp) {
          perror("popen");
        }
        while (fgets(buf, sizeof(buf), fp) != NULL) {
          contents.append(buf);
        }
        fclose(fp);

        std::string delimiter = std::string("\n");
        vector <string> firstLine = split(contents, delimiter);
        vector <string> firstLineSplitted = split(firstLine[0], ": ");

        resp.headers.erase("Content-Type: ");
        resp.headers.insert(std::pair<string, string>(std::string("Content-Type: "),
              firstLineSplitted[1]));
        RemoveLine(contents, "Content-type:");

        resp.message_body = contents;
      }
    } else {
      if (dir == 0) {
        std::string filePath2method = std::string(filePath);
        std::string msgBodyFinal = readFile2(filePath2method);
        resp.message_body = msgBodyFinal;
      } else {
        char* directories = NULL;
        directories = reinterpret_cast<char*>(malloc(sizeof(char)*1024));
        memset(directories, '\0', 1024);
        strcpy(directories, "<html>\n\t<head>\n\t\t<title>Index of ");
        strcat(directories, filePath);
        strcat(directories, "</title>\n\t</head>\n\t<body>\n\t\t<h1>Index of ");
        strcat(directories, filePath);
        strcat(directories, "</h1>\n");

        if (strlen(directories) >= 896) {
          directories = reinterpret_cast<char*>(realloc(directories, 2048));
        }
        strcat(directories, "<table>""<tr>""<center><th>""Name"
            "</th></center>""</tr>""<tr>""<th colspan=\"5\">"
            "<hr>""</th>""</tr>");

        if (strlen(directories) >= 1920) {
          directories = reinterpret_cast<char*>(realloc(directories, 4096));
        }
        DIR* dir = opendir(filePath);
        struct dirent *ent = NULL;
        ent = (struct dirent *)malloc(sizeof(struct dirent));

        while (ent = readdir(dir)) {
          if (strlen(directories) >= strlen(directories)*(3/4)) {
            directories = reinterpret_cast<char*>(realloc
                (directories, strlen(directories)*2));
          }
          strcat(directories, "<tr><center><td><a href=\"");
          strcat(directories, ent->d_name);
          strcat(directories, "\">");
          strcat(directories, ent->d_name);
          strcat(directories, "</a></td></center></tr>");
        }
        strcat(directories, "<tr><th colspan=\"5\"><hr></th></tr></table></body></html>");
        std::string msgBodyDir = std::string(directories);
        resp.message_body = msgBodyDir;

        if (directories != NULL) {
          free(directories);
        }
        directories = NULL;

        if (ent != NULL) {
          free(ent);
        }
        ent = NULL;
      }
    }
  }

  // cout << resp.to_string() << endl;
  sock->write(resp.to_string());
}



void Server::handle(const Socket_t& sock) const {
  HttpRequest request;
  // TODO: implement parsing HTTP requests
  // recommendation:
  process_Request(sock, &request);
}
