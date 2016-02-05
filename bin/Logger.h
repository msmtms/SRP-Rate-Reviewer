/* 
 * File:   Logger.h
 * Author: Nate
 *
 * Created on April 8, 2013, 9:25 PM
 */

#ifndef LOGGER_H
#define	LOGGER_H

#include <fstream>
#include <string>

class Logger {   
    
public:
    static const std::string PROCESS_LOG_NAME;
    static const std::string ERROR_LOG_NAME;
    const static unsigned int PROCESS           = 1;
    const static unsigned int ERROR              = 2;
    
public:
    static Logger* Instance();
    ~Logger();
    void setLogPath(std::string path);
    std::string GetPath() { return m_path; };
    void setWriteLevel(const unsigned int level);
    void writeToLogFile(std::string message, unsigned int level);
    void writeToLogFile(std::string message, unsigned int level, std::string location);
    bool isReadyToWrite() { return m_path.length() != 0; };
    
private:
        Logger();  
        Logger(Logger const&){};    
        Logger& operator=(Logger const&){}; 
        void close_files();
        void getDateTime(std::string &time);
        
private:
        static Logger*   m_pInstance;
        std::string      m_path;
        std::ofstream    m_process_fout;
        std::ofstream    m_error_fout;
        bool             m_is_ready;
        unsigned int     m_write_level;
};

#endif	/* LOGGER_H */

