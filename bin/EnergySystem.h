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
#include "RateSchedule.h"

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
    ImmediateLoad* GetImmediateLoad() { return(m_immediateLoad.begin()->second);}
    
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
    inline void SetName( std::string name ) {m_systemName = name;}
    inline std::string &GetName() {return(m_systemName);}
    inline void SetEnergyNetName( std::string name ) {m_energyNetName = name;}
    inline std::string &GetEnergyNetName() {return(m_energyNetName);}
    inline void SetRateScheduleName( std::string name ) {m_rateScheduleName = name;}
    inline std::string &GetRateScheduleName() {return(m_rateScheduleName);}
    inline void SetRateSchedule( RateSchedule &schedule ) {m_rateSchedule = schedule;}
    inline RateSchedule &GetRateSchedule() {return(m_rateSchedule);}
    inline void SetNumSystems( double num ) {m_numSystems = num;}
    inline double &GetNumSystems() {return(m_numSystems);}
    
    inline void SetOutputDirectory( std::string dir ) {m_outputDir = dir;}
    inline std::string &GetOutputDirectory() {return(m_outputDir);}
    inline void SetInputDirectory( std::string dir ) {m_inputDir = dir;}
    inline std::string &GetInputDirectory() {return(m_inputDir);}
    
    void CalculateSummaryData();
    void CalculateGridSummaryData();
    
    double GetGridTotalCharges() { return( m_totalCharges * m_numSystems ); }
    double GetGridEnergyCharges() { return( m_energyCharges * m_numSystems ); }
    double GetGridDemandCharges() { return( m_demandCharges * m_numSystems ); }
    double GetGridInterconnectionCharges() { return( m_interconnectionCharges * m_numSystems ); }
    
    void AddToGridTotalCharges( double val ) { m_totalCharges += val; }
    void AddToGridEnergyCharges( double val ) { m_energyCharges += val; }
    void AddToGridDemandCharges( double val ) { m_demandCharges += val; }
    void AddToGridInerconnectionCharges( double val ) { m_interconnectionCharges += val; }
    
    bool OutputSummaryDataToFile();
    bool OutputSummaryDataToFile( std::string &fname );
    bool OutputGridSummaryDataToFile();
    bool OutputGridSummaryDataToFile( std::string &fname );
    bool OutputTimeseriesDataToFile( bool includeHeader = true );
    bool OutputTimeseriesDataToFile( std::string &fname, bool includeHeader = true );
    bool OutputSimpleTimeseriesDataToFile( bool includeHeader = true);
    bool OutputSimpleTimeseriesDataToFile( std::string &fname, bool includeHeader = true );
    bool OutputGridTimeseriesDataToFile( bool includeHeader = true);
    bool OutputGridTimeseriesDataToFile( std::string &fname, bool includeHeader = true );
    
    std::vector<double>& GetEnergyNet() {return(m_energyNet);}
    std::vector<double>& GetAllSystemsTotalLoad() {return(m_allSystemsTotalLoad);}
    std::vector<double>& GetAllSystemsNetLoad() {return(m_allSystemsNetLoad);}
    
private:
    
    unsigned int m_numTimesteps;
    
    std::string m_outputDir;
    std::string m_inputDir;
    std::string m_systemName;
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
    std::string m_energyNetName;
    std::string m_rateScheduleName;
    RateSchedule m_rateSchedule;
    
    double m_numSystems;
    std::vector<double> m_allSystemsTotalLoad;
    std::vector<double> m_allSystemsNetLoad;
    
    double m_totalEnergyIn;         /**< Total energy into the system from the grid or external source [kWh/period]. */
    double m_totalEnergyOut;        /**< Total energy out of the system from the grid or external source [kWh/period]. */
    double m_totalEnergyNet;        /**< Total energy net in/out of the system. Negative if production exceeds use [kWh/period]. */
    
    // all computed from net load
    double m_maxLoadServed;         /**< Maximum (peak) load served [kW]. */
    double m_avgLoadServed;         /**< Average load served [kW]. */
    double m_totalLoadServedPeriod; /**< Total load served during the simulation period [kWh/period]. */
    double m_totalLoadServedDay;    /**< Total load served during the simulation period [kWh/day]. */
    double m_loadFactor;            /**< Load factor [-]. */
    
    // financial summary
    double m_totalCharges;              /**< Total energy charges [$/y]. */
    double m_energyCharges;             /**< Energy charges from $/kWh [$/y]. */
    double m_demandCharges;             /**< Demand charges from $/kW [$/y]. */
    double m_interconnectionCharges;    /**< Interconnection  charges from $/mo [$/y]. */
};

#endif	/* ENERGYSYSTEM_H */

