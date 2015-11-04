/**
 * @file EnergySystem.h
 * @brief Generic energy system in the ESDE model.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ENERGYSYSTEM_H
#define	ENERGYSYSTEM_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEConstants.h"
#include "ESDEObject.h"
#include "ESDEObjectData.h"
#include "ImmediateLoad.h"
#include "SolarPV.h"
#include "SolarResource.h"
#include "SolarField.h"
#include "Converter.h"
#include "Battery.h"
#include "ElectricVehicle.h"

/** TODO
 o converter
 o battery
 o EV
 o dispatch
 o economics
 o demand response
 
 o later maybe create Handlers for various ESDE objects
 */

/******************************************************************************/
class EnergySystem : public ESDEObject {
   /**
    * @class EnergySystem [EnergySystem.h]
    * @brief Generic energy system in the ESDE model.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public: 
    EnergySystem();
    EnergySystem( unsigned int id );
    EnergySystem( unsigned int id, std::string inputDir );
    ~EnergySystem();
    
    inline void SetNumTimesteps(unsigned int numTimesteps) { m_numTimesteps = numTimesteps; }
    void Init(unsigned int numTimesteps);
    void UpdateToNextTimestep();
    void RunSimulation();
    
public: // add components
    /* slightly different accessors because there can be multiple of each */
    unsigned int AddImmediateLoad();
    unsigned int AddImmediateLoad( ImmediateLoad *immediateLoad, bool replaceObjectID = true );
    unsigned int AddImmediateLoad( ImmediateLoadData &data, bool replaceObjectID = true );
    ImmediateLoad* GetImmediateLoad( unsigned int id ) { return(m_immediateLoad[id]);}
    
    unsigned int AddSolarPV();
    unsigned int AddSolarPV( SolarPV *solarPV, bool replaceObjectID = true );
    unsigned int AddSolarPV( SolarPVData &data, bool replaceObjectID = true );
    SolarPV* GetSolarPV( unsigned int id ) { return(m_solarPV[id]);}
    
    unsigned int AddConverter();
    unsigned int AddConverter( Converter *converter, bool replaceObjectID = true );
    unsigned int AddConverter( ConverterData &data, bool replaceObjectID = true );
    Converter* GetConverter( unsigned int id ) { return(m_converter[id]);}
    
    unsigned int AddBattery();
    unsigned int AddBattery( Battery *battery, bool replaceObjectID = true );
    unsigned int AddBattery( BatteryData &data, bool replaceObjectID = true );
    Battery* GetBattery( unsigned int id ) { return(m_battery[id]);}
    
    unsigned int AddElectricVehicle();
    unsigned int AddElectricVehicle( ElectricVehicle *electricVehicle, bool replaceObjectID = true );
    unsigned int AddElectricVehicle( ElectricVehicleData &data, bool replaceObjectID = true );
    ElectricVehicle* GetElectricVehicle( unsigned int id ) { return(m_electricVehicle[id]);}
    
    unsigned int AddSolarField();
    unsigned int AddSolarField( SolarField *solarField, bool replaceObjectID = true );
    unsigned int AddSolarField( SolarFieldData &data, bool replaceObjectID = true );
    SolarField* GetSolarField( unsigned int id ) { return(m_solarField[id]);}
    
    unsigned int AddSolarResource();
    unsigned int AddSolarResource( SolarResource *solarResource, bool replaceObjectID = true );
    unsigned int AddSolarResource( SolarResourceData &data, bool replaceObjectID = true );
    SolarResource* GetSolarResource( unsigned int id ) { return(m_solarResource[id]);}
    
public: // data io
    inline void SetOutputDirectory( std::string dir ) {m_outputDir = dir;}
    inline std::string &GetOutputDirectory() {return(m_outputDir);}
    inline void SetInputDirectory( std::string dir ) {m_inputDir = dir;}
    inline std::string &GetInputDirectory() {return(m_inputDir);}
    
    void CalculateSummaryData();
    
    bool OutputSummaryDataToFile();
    bool OutputSummaryDataToFile( std::string &fname );
    bool OutputTimeseriesDataToFile( bool includeHeader = true );
    bool OutputSimpleTimeseriesDataToFile( bool includeHeader = true);
    bool OutputTimeseriesDataToFile( std::string &fname, bool includeHeader = true );
    bool OutputSimpleTimeseriesDataToFile( std::string &fname, bool includeHeader = true );
    
private:
    
    unsigned int m_numTimesteps;
    
    std::string m_outputDir;
    std::string m_inputDir;
    std::map< unsigned int, ESDEObject* > m_ESDEObjectList;         /**< List of all ESDE objects in this system. */
    unsigned int m_currentObjectID;                                 /**< Unique ID of current ESDE Object in the system */
    
    // loads
    std::map< unsigned int, ImmediateLoad* > m_immediateLoad;
    
    // components
    std::map< unsigned int, SolarPV* > m_solarPV;
    std::map< unsigned int, SolarField* > m_solarField;
    std::map< unsigned int, Converter* > m_converter;
    std::map< unsigned int, Battery* > m_battery;
    std::map< unsigned int, ElectricVehicle* > m_electricVehicle;
    
    // resources
    std::map< unsigned int, SolarResource* > m_solarResource;
    
private:
    // net load information
    std::vector<double> m_energyNet;                /**< Net energy into or out of the system. Negative if production exceeds use [kW]. */   
    std::vector<double>::iterator m_iterEnergyNet;
    
    double m_totalEnergyIn;         /**< Total energy into the system from the grid or external source [kWh/period]. */
    double m_totalEnergyOut;        /**< Total energy out of the system from the grid or external source [kWh/period]. */
    double m_totalEnergyNet;        /**< Total energy net in/out of the system. Negative if production exceeds use [kWh/period]. */
};

#endif	/* ENERGYSYSTEM_H */

