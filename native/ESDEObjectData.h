/**
 * @file ESDEObjectData.h
 * @brief Data common to all ESDE objects. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

/* TOOD
 o this seems excessive for ensuring that each object type (input, timeseries, summary, object) has ID information
 o include virtual functions to be overridden in child classes
 */

#ifndef ESDEOBJECTDATA_H
#define	ESDEOBJECTDATA_H

#include <iostream>
#include <fstream>

#include "ESDEIdentification.h"

struct ESDEObjectInput {
    /**
    * @struct ESDEObjectInput [ESDEObjectData.h]
    * @brief Common input data for all ESDE objects.  
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o create ScenarioVariable (map)
     o create DesignVariable (single object)
     */
    
    ESDEIdentification m_identInput;  /*< Identification information for the type of object. */
};

struct ESDEObjectTimeseries {
    /**
    * @struct ESDEObjectTimeseries [ESDEObjectData.h]
    * @brief Common timeseries data for all ESDE objects.  
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /**
    * Updates the iterators to the next timestep. 
    */
    inline virtual void IncrementTimeseriesIterators() {;}
    
    ESDEIdentification m_identTimeseries;  /*< Identification information for the type of object. */
};

struct ESDEObjectSummary {
    /**
    * @struct ESDEObjectSummary [ESDEObjectData.h]
    * @brief Common summary data for all ESDE objects.  
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    ESDEIdentification m_identSummary;  /*< Identification information for the type of object. */
};

#endif	/* ESDEOBJECTDATA_H */

