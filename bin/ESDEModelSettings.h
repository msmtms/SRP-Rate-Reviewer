/**
 * @file ESDEModelSettings.h
 * @brief Model settings for an ESDE model.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

/* TODO
 o commenting for Doxygen
 x add start and end times for simulation
 o add accessors for start and end times of a simulation -- may not need to since it is currently a struct
 o calculate timestep hourly fraction based upon numtimeps and start/end time of simulation
 */

#ifndef ESDEMODELSETTINGS_H
#define	ESDEMODELSETTINGS_H

#include <iostream>

#include "ESDEConstants.h"
#include "ESDEObject.h"

/******************************************************************************/
struct ESDEModelSettings : public ESDEObject, public ESDEModelSettingsData {
   /**
    * @struct ESDEModelSettings [ESDEModelSettings.h]
    * @brief Model settings for an ESDE model.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    ESDEModelSettings() {;}
};


#endif	/* ESDEMODELSETTINGS_H */

