/**
 * @file ESDEIdentification.h
 * @brief Data used to identify and differentiate ESDE objects.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ESDEIDENTIFICATION_H
#define	ESDEIDENTIFICATION_H

#include <iostream>
#include <fstream>

struct ESDEIdentification {
    /**
    * @struct ESDEIdentification [ESDEIdentification.h]
    * @brief Data used to identify and differentiate ESDE objects.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o add version number? 
     */
    ESDEIdentification() {
        m_objectType = 0;
        m_objectID = 0;
        m_objectName = "";
    }
    
    ESDEIdentification & operator=(const ESDEIdentification& ident) {
        m_objectType = ident.m_objectType;
        m_objectID = ident.m_objectID;
        m_objectName = ident.m_objectName;
        return(*this);
    }
    
    unsigned int m_objectType;      /*< Identifier of the type of object (e.g., immediate load, solar pv, wind resource). */
    unsigned int m_objectID;        /*< Identifier of the run-time object that is instantiated. */
    std::string m_objectName;       /*< Name of the object (set by user). */
};


#endif	/* ESDEIDENTIFICATION_H */

