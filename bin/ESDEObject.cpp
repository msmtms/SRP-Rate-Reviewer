/**
 * @file ESDEObject.cpp
 * @brief All ESDE objects are derived from this parent class.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include "ESDEObject.h"

/**
 * Default constructor.
 */
ESDEObject::ESDEObject() {
    m_identObject.m_objectID = 0;
    m_identObject.m_objectName = "";
    
    m_numTimesteps = 8760; // default to hourly analysis
    m_timestepHourlyFraction = 1.0; // default to hourly analysis
}

/**
 * Copy constructor. TODO. 
 */
ESDEObject::ESDEObject(const ESDEObject& orig) {
    ESDEObject();
}

/**
 * Destructor. TODO.
 */
ESDEObject::~ESDEObject() {
    // nothing yet to destroy
}

