/**
 * @file ESDEDataModel.h
 * @brief ESDE data model with input and output data.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */


#ifndef ESDEDATAMODEL_H
#define	ESDEDATAMODEL_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEConstants.h"
#include "ESDEObject.h"
#include "ESDEObjectData.h"
#include "ESDEModelSettingsData.h"
#include "EnergySystem.h"
#include "SolarResource.h"
#include "SolarResourceData.h"
#include "ESDELocation.h"

    
/** TODO
 o solar resource
 o notes
 o system - ratepayer
 o system - grid
 
 LATER
 o only one solar resource allowed
 o may need handlers
 */

/******************************************************************************/
class ESDEDataModel : public ESDEObject {
   /**
    * @class ESDEDataModel [ESDEDataModel.h]
    * @brief ESDE data model with input and output data.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public: 
    ESDEDataModel() :
        m_ESDEModelSettingsInput(0)
    {
        SetObjectType( ID_ESDE_DATA_MODEL );
        m_modelVersion = "0.0.1";
        
        // start with blank solar resource
        m_solarResource = new SolarResource();
        m_grid = new EnergySystem();
    }
    ~ESDEDataModel() {;}
    
    inline void SetModelVersion( std::string modelVersion ) {m_modelVersion = modelVersion;}
    std::string GetModelVersion() const {return (m_modelVersion);}
    
    /* unique accessors for each  */
    unsigned int SetESDEModelSettings( ESDEModelSettingsInput *modelSettingsInput );
    ESDEModelSettingsInput* GetESDEModelSettings() { return(m_ESDEModelSettingsInput); }

    void AddSolarResource( SolarResource *solarResource ) {
        if( !m_solarResource ) {
            m_solarResource = new SolarResource();
        }
        m_solarResource = solarResource; // TODO: memory leak
    }
    void AddSolarResource() {
        if( !m_solarResource ) {
            m_solarResource = new SolarResource();
        }
    }
    SolarResource* GetSolarResource() {
        if( !m_solarResource ) {
            m_solarResource = new SolarResource();
        }
        return(m_solarResource);
    }
    
    unsigned int AddEnergySystem() {
        ++m_currentSystemID;
        EnergySystem *energySystem = new EnergySystem(m_currentSystemID);
        m_energySystemList.insert( std::make_pair( m_currentSystemID, energySystem ) );
        m_energySystemList.insert( std::make_pair( m_currentSystemID, energySystem ) );
        return( m_currentSystemID );
    }
    EnergySystem* GetEnergySystem( unsigned int id ) { return(m_energySystemList[id]); }
    
    EnergySystem* GetGrid() { return(m_grid); }

private:

    // model-level data
    std::string m_modelVersion;    
    ESDEModelSettingsInput *m_ESDEModelSettingsInput;
    
    SolarResource* m_solarResource;
    
    std::map< unsigned int, EnergySystem* > m_energySystemList;     /**< List of all EnergySystems. */
    unsigned int m_currentSystemID;                                 /**< Unique ID of current ESDE EnergySystem */
    EnergySystem* m_grid;                                           /**< Energy system for the grid */
    
    // SolarPVData (includes properties, timeseries, summary), design variables, scenario variables
};


#endif	/* ESDEDATAMODEL_H */

