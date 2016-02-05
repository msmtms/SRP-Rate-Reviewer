/**
 * @file ESDEDateTime.h
 * @brief Date and time object for ESDE simulations. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */


#ifndef ESDEDATATIME_H
#define	ESDEDATATIME_H

struct ESDEDateTime {
    /**
    * @struct ESDEDateTime [ESDEModelSettingsData.h]
    * @brief Date and time object for ESDE simulations. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /**
     * Constructor. Default to January 1, 1980. 
     */
    ESDEDateTime() {
        m_year = 1980;
        m_month = 0;
        m_day = 0;
        m_hour = 0;
        m_minute = 0;
        m_second = 0;
    };
    /**
     * Set date data. 
     * @param year year of date. 
     * @param month month of date. 
     * @param day day of date. 
     */
    inline void SetDate(int year, int month, int day) {m_year = year; m_month = month; m_day = day;};
    /**
     * Set time data. 
     * @param year year of date. 
     * @param month month of date. 
     * @param day day of date. 
     */
    inline void SetTime(int hour, int minute, int second) {m_hour = hour; m_minute = minute; m_second = second;};
    
    int m_year;
    int m_month;
    int m_day;
    int m_hour;
    int m_minute;
    int m_second;
};


#endif	/* ESDEDATATIME_H */

