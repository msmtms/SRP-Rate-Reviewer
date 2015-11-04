#include "Logger.h"

#include <ctime>
#include <cstdlib>
#include <iostream>

using namespace std;

Logger* Logger::m_pInstance = NULL;
const string Logger::PROCESS_LOG_NAME   = "Process.log";
const string Logger::ERROR_LOG_NAME     = "Error.log";

Logger* Logger::Instance() {
   if (!m_pInstance)   
        m_pInstance = new Logger;

   return m_pInstance;
}

Logger::Logger() {
    m_path = "";
    m_write_level = 0;
    m_is_ready = false;
}

Logger::~Logger() {
    close_files();
    
    delete m_pInstance;
}

void Logger::close_files() {
    if(m_process_fout.is_open())
        m_process_fout.close();
   if(m_error_fout.is_open())
        m_error_fout.close();
}

void Logger::setLogPath(string path) {
    cout << "PATH " << path << endl;
    close_files();
    string temp1 = path, temp2 = path, temp3 = path;
    m_process_fout.open(temp1.append(PROCESS_LOG_NAME).c_str());
    m_error_fout.open(temp2.append(ERROR_LOG_NAME).c_str());
    
    if(m_process_fout.is_open()
       && m_error_fout.is_open() )
    {
        m_path = path;
        m_is_ready = true;
    }
}

void Logger::writeToLogFile(std::string message, unsigned int level) {
    string stime= "";
    getDateTime(stime);

    std::cout << message.c_str() << "\n";
    
    if(level & m_write_level) {
        if(level == PROCESS) {
            m_process_fout << stime << "-" << message << endl;
            m_process_fout.flush();
        }
        else if(level == ERROR) {
            m_error_fout << stime << "-" << message << endl;
            m_error_fout.flush();
        }
    }
}
void Logger::writeToLogFile(std::string message, unsigned int level, string location) {
    string stime= "";
    getDateTime(stime);

    std::cout << message.c_str() << "\n";
    
    if(level & m_write_level) {
        if(level == PROCESS) {
            m_process_fout << stime << "-" << message << ":" << location << endl;
            m_process_fout.flush();
        }
        else if(level == ERROR) {
            m_error_fout << stime << "-" << message << ":" << location << endl;
            m_error_fout.flush();
        }
    }
}
void Logger::setWriteLevel(const unsigned int level) {
    m_write_level = level;
}

void Logger::getDateTime(string &stime) {
   time_t t = time(0);   // get time now
    struct tm * now = localtime( & t );
    char buffer [10];
    
    //itoa(now->tm_mon + 1, buffer, 10);
    snprintf(buffer, sizeof(buffer), "%d", now->tm_mon + 1);
    stime.append(buffer);
    stime.append("/");
    //itoa(now->tm_mday, buffer, 10);
    snprintf(buffer, sizeof(buffer), "%d", now->tm_mday);
    stime.append(buffer);
    stime.append("/");
    //itoa(now->tm_year + 1900, buffer, 10);
    snprintf(buffer, sizeof(buffer), "%d", now->tm_year + 1900);
    stime.append(buffer);
    stime.append(" ");
    
    //itoa(now->tm_hour, buffer, 10);
    snprintf(buffer, sizeof(buffer), "%d", now->tm_hour);
    stime.append(buffer);
    stime.append(":");
    //itoa(now->tm_min, buffer, 10);
    if( now->tm_min < 10 ) {
        snprintf(buffer, sizeof(buffer), "%d", 0);
    }
    stime.append(buffer);
    snprintf(buffer, sizeof(buffer), "%d", now->tm_min);
    stime.append(buffer);    
}

