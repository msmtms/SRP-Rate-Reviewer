/**
 * @file ESDEModelSettingsData.h
 * @brief Model settings data for an ESDE model.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

/* TODO
 o summary data
 */

#ifndef ESDEMODELSETTINGSDATA_H
#define	ESDEMODELSETTINGSDATA_H

#include "ESDEDateTime.h"

struct ESDEModelSettingsInput : public ESDEObjectInput {
    /**
    * @struct ESDEModelSettingsInput [ESDEModelSettingsData.h]
    * @brief Input data for model settings.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o store separate types of model settings in different classes? 
     o maybe store numTimesteps and hourlyFraction here, but need to consider further because it is already in ESDEObject
     o start time, end time, timestep size and then calculate numTimesteps and timestepHourlyFraction
     */
    
    /**
     * Default constructor. Defaults to one-year simulation with timestep size of one hour.
     */
    ESDEModelSettingsInput() {
        m_identInput.m_objectType = ID_ESDE_MODEL_SETTINGS;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ESDEModelSettingsInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_ESDE_MODEL_SETTINGS ;
        m_identInput.m_objectID = objectID ;
    }

    ESDEDateTime m_start;               /**< Starting date and time for a simulation. */
    ESDEDateTime m_end;                 /**< Ending date and time for a simulation. */
};

struct ESDEModelSettingsSummary : public ESDEObjectSummary {
    /**
    * @struct ESDEModelSettingsSummary [ESDEModelSettingsData.h]
    * @brief Summary data for the model. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o total computation time
     o total number of computations
     o errors
     o etc.
     */
    
    /**
     * Default constructor. Defaults to one-year simulation with timestep size of one hour.
     */
    ESDEModelSettingsSummary() {
        m_identSummary.m_objectType = ID_ESDE_MODEL_SETTINGS;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ESDEModelSettingsSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_ESDE_MODEL_SETTINGS ;
        m_identSummary.m_objectID = objectID ;
    }
};

struct ESDEModelSettingsData : public ESDEModelSettingsInput, public ESDEModelSettingsSummary {
    /**
    * @struct ESDEModelSettingsData [ESDEModelSettingsData.h]
    * @brief Groups input, timestep, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    ESDEModelSettingsData() {;}
};



#endif	/* ESDEMODELSETTINGSDATA_H */

