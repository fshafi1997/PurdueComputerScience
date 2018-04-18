#ifndef  INCLUDE_SERVER_HH_
#define INCLUDE_SERVER_HH_
#include "http_messages.hh"
#include "socket.hh"
#include "tcp.hh"

class Server {
 private:
    SocketAcceptor const& _acceptor;

 public:
    explicit Server(SocketAcceptor const& acceptor);
    void run_linear() const;
    void run_fork() const;
    void run_thread_pool(const int num_threads) const;
    void run_thread() const;
    void process_Request(const Socket_t& sock, HttpRequest* const request) const;
    void processResponse(const Socket_t& sock, char* protocol, char* filePath,
        int code, char* fileType, int dir, int headerCheck) const;
    void handle(const Socket_t& sock) const;
    void threadPool() const;
};

#endif  // INCLUDE_SERVER_HH_
