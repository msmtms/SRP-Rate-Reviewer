/* 
 * File:   ElectricVehicleData.h
 * Author: Nate
 *
 * Created on November 3, 2015, 2:37 PM
 */

#ifndef ELECTRICVEHICLEDATA_H
#define	ELECTRICVEHICLEDATA_H


#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>
#include <math.h>

#include "ESDEConstants.h"
#include "ESDEIdentification.h"
#include "ESDEObjectData.h"

static const double chargePowerLevel1 = 3.3;
static const double chargePowerLevel2 = 6.6;

struct ElectricVehicleInput : public ESDEObjectInput {
    /**
    * @struct ElectricVehicleInput [ElectricVehicleInput.h]
    * @brief Electric vehicle input data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    /**
     * Default constructor. 
     */
    ElectricVehicleInput() {
        m_identInput.m_objectType = ID_BATTERY;
        m_chargerType = LEVEL_1;
        m_chargingStrategy = AVG_CHARGE;
        
//        m_chargePowerLevel1 = 3.3; // kW
//        m_chargePowerLevel2 = 6.6; // kw
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ElectricVehicleInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_BATTERY;
        m_identInput.m_objectID = objectID;
        
        m_chargerType = LEVEL_1;
        m_chargingStrategy = AVG_CHARGE;
        
//        m_chargePowerLevel1 = 3.3; // kW
//        m_chargePowerLevel2 = 6.6; // kw
    }
    
    inline void SetInput( ElectricVehicleInput &data ) {
        m_identInput = data.m_identInput;

        m_chargerType = data.m_chargerType;
        m_chargingStrategy = data.m_chargingStrategy;
        
        m_maxCapacityAsEnergy = data.m_maxCapacityAsEnergy;
        m_chargeEfficiency = data.m_chargeEfficiency;
        m_startingSOC = data.m_startingSOC;
        m_endingSOC = data.m_endingSOC;
        m_startTime = data.m_startTime;
        m_endTime = data.m_endTime;       
    }
    
    inline ElectricVehicleInput* GetInputData() {
        return(this);
    }
    
public: // enums
    
    enum CHARGER_TYPE {
        LEVEL_1 = 0, 
        LEVEL_2
    };
    
    enum CHARGING_STRATEGY {
        MAX_CHARGE = 0,
        AVG_CHARGE
    };
    
    inline void SetChargerType( unsigned int chargerType ) {m_chargerType = chargerType;}
    inline unsigned int GethargerType() { return(m_chargerType); }
    inline void SetChargingStrategy( unsigned int chargingStrategy ) {m_chargingStrategy = chargingStrategy;}
    inline unsigned int GetChargingStrategy() { return(m_chargingStrategy); }
    inline void SetMaxCapacityAsEnergy( double maxCapacityAsEnergy ) {m_maxCapacityAsEnergy = maxCapacityAsEnergy;}
    inline double GetMaxCapacityAsEnergy() { return(m_maxCapacityAsEnergy); }
    
    inline void SetChargeEfficiencyAsPercentage( double chargeEfficiencyAsPercentage ) {m_chargeEfficiency = chargeEfficiencyAsPercentage / 100.0;}
    inline double GetChargeEfficiencyAsPercentage() { return(m_chargeEfficiency); }
    inline void SetStartingSOCAsPercentage( double startingSOCAsPercentage ) {m_startingSOC = startingSOCAsPercentage / 100.0;}
    inline double GetStartingSOCAsPercentage() { return(m_startingSOC); }
    inline void SetEndingSOCAsPercentage( double endingSOCAsPercentage ) {m_endingSOC = endingSOCAsPercentage / 100.0;}
    inline double GetEndingSOCAsPercentage() { return(m_endingSOC); }
    
    inline void SetStartTime( double startTime ) {m_startTime = startTime;}
    inline double GetStartTime() { return(m_startTime); }
    inline void SetEndTime( double endTime ) {m_endTime = endTime;}
    inline double GetEndTime() { return(m_endTime); }
    
protected:
    
    /* these are read in */
    unsigned int m_chargerType;             /**< Charger type. @see CHARGER_TYPE */
    unsigned int m_chargingStrategy;            /**< Charging strategy. @see CHARGING_STRATEGY */
    double m_maxCapacityAsEnergy;           /**< Maximum energy content in the storage [kWh]. */
    double m_chargeEfficiency;              /**< Charging efficiency [fraction] and reported to user [%]. */ 
    double m_startingSOC;                   /**< Starting SOC for charging [fraction] and reported to user [%]. */
    double m_endingSOC;                     /**< Ending SOC for charging [fraction] and reported to user [%]. */
    double m_startTime;                     /**< Starting time for charging. */
    double m_endTime;                       /**< Ending time for charging. */
    
protected:
    double m_chargerPowerRated;             /**< rated power according to charger level */
};

struct ElectricVehicleTimeseries : public ESDEObjectTimeseries {
    /**
    * @struct ElectricVehicleTimeseries [ElectricVehicleTimeseries.h]
    * @brief Electric vehicle time series data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    /**
     * Default constructor. 
     */
    ElectricVehicleTimeseries() {
        m_identTimeseries.m_objectType = ID_BATTERY;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ElectricVehicleTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_BATTERY;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the time series data and setup iterators. 
    * @param numTimesteps - number of time series in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors if empty
        if( m_chargePower.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_chargePower = tmp;
            m_capacityAsEnergy = tmp;
            m_capacityAsFraction = tmp;
        }
        
        // setup iterator
        SetupTimeseriesIterators();
    }
    inline void SetTimeseries( ElectricVehicleTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_chargePower = data.m_chargePower;
        m_capacityAsEnergy = data.m_capacityAsEnergy;
        m_capacityAsFraction = data.m_capacityAsFraction;
        
        SetupTimeseriesIterators();
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline ElectricVehicleTimeseries* GetTimeseriesData() {
        return(this);
    }
    
    /**
    * Clear time series data. 
    */
    inline void ClearTimeseries() {
        m_chargePower.clear();
        m_capacityAsEnergy.clear();
        m_capacityAsFraction.clear();
    }
    /**
    * Setup time series iterators. 
    */
    inline void SetupTimeseriesIterators() {
        m_iterChargePower = m_chargePower.begin();
        m_iterCapacityAsEnergy = m_capacityAsEnergy.begin();
        m_iterCapacityAsFraction = m_capacityAsFraction.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterChargePower;
        ++m_iterCapacityAsEnergy;
        ++m_iterCapacityAsFraction;
    }
    
    /**
    * Output the header in the time series data file. 
    * @param fout - file stream for output data. 
    */
    inline void OutputTimeseriesHeaderToFile( std::ofstream &fout ) {
        // use name if available, or object ID if not
        if( m_identTimeseries.m_objectName.size() > 0 ) {
            fout << m_identTimeseries.m_objectName.c_str() << " charge power [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " capacity as energy [kWh],";
            fout << m_identTimeseries.m_objectName.c_str() << " capacity as fraction [%],";
        }
        else {
            fout << m_identTimeseries.m_objectID << " charge power [kW],";
            fout << m_identTimeseries.m_objectID << " capacity as energy [kWh],";
            fout << m_identTimeseries.m_objectID << " capacity as fraction [%],";
        }
    }
    /**
    * Output a single timestep in the time series data.
    * @param fout - file stream for output data. 
    * @param time - timestep to select from time series data.  
    */
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_chargePower.at(time) << ",";
        fout << m_capacityAsEnergy.at(time) << ",";
        fout << (m_capacityAsFraction.at(time) * 100.0) << ",";
    }
    
    inline void OutputSimpleTimeseriesHeaderToFile( std::ofstream &fout ) {
        fout << "Electric vehicle,";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_chargePower.at(time) << ",";
    }
    
    inline double GetCurrentChargePower() { return(*m_iterChargePower); }
    
protected:
    std::vector<double> m_chargePower;          /**< Actual charge power in the timestep [kW]. */
    std::vector<double> m_capacityAsEnergy;     /**< Energy content in the storage [kWh]. */
    std::vector<double> m_capacityAsFraction;   /**< Energy content in the storage [fraction] and reported to user [%]. */
    
    std::vector<double>::iterator m_iterChargePower;
    std::vector<double>::iterator m_iterCapacityAsEnergy;
    std::vector<double>::iterator m_iterCapacityAsFraction;
};

struct ElectricVehicleSummary : public ESDEObjectSummary {
    /**
    * @struct ElectricVehicleSummary [ElectricVehicleSummary.h]
    * @brief Electric vehicle summary data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    /**
     * Default constructor. 
     */
    ElectricVehicleSummary() {
        m_identSummary.m_objectType = ID_BATTERY;
        m_totalCapacity = 0;
        m_totalEnergyIn = 0;
        m_meanDailyEnergyIn = 0;
        m_totalLosses = 0;
        m_loadPortionAsFraction = 0;
        m_loadPortionAsPercentage = 0;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ElectricVehicleSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_BATTERY;
        m_identSummary.m_objectID = objectID;
        m_totalCapacity = 0;
        m_totalEnergyIn = 0;
        m_meanDailyEnergyIn = 0;
        m_totalLosses = 0;
        m_loadPortionAsFraction = 0;
        m_loadPortionAsPercentage = 0;
    }
    
    inline void SetSummary( ElectricVehicleSummary &data ) {
        m_identSummary = data.m_identSummary;
        m_totalCapacity = data.m_totalCapacity;
        m_totalEnergyIn = data.m_totalEnergyIn;
        m_meanDailyEnergyIn = data.m_meanDailyEnergyIn;
        m_totalLosses = data.m_totalLosses;
        m_loadPortionAsFraction = data.m_loadPortionAsFraction;
        m_loadPortionAsPercentage = data.m_loadPortionAsPercentage;
    }
    /**
     * Copy summary data onto blank struct. 
     * @return copy of summary data. 
     */
    inline ElectricVehicleSummary* GetSummaryData() {
        return(this);
    }
    
    /**
    * Output summary data to file
    * @param path - path for summary data.
    */
    inline void OutputSummaryToFile( std::string &path ) {
        // construct path name, use name if available, or object ID if not
        std::string dir = path;
        if( m_identSummary.m_objectName.size() > 0 ) {
            dir += "";
            dir += m_identSummary.m_objectName;
            dir += ".txt";
        }
        else {
            dir += "";
            dir += std::to_string( m_identSummary.m_objectID );
            dir += ".txt";
        }
        
        // output to file
        std::ofstream fout;
        fout.open( dir.c_str() );
        
        fout << "Object name: " << m_identSummary.m_objectName << "\n";
        fout << "Object type: " << "Electric vehicle" << "\n";
        fout << "Object ID: " << m_identSummary.m_objectID << "\n\n";
        
        fout << "Total capacity [kWh]: " << m_totalCapacity << "\n";
        fout << "Energy in [kWh/day]: " << m_meanDailyEnergyIn << "\n";
        fout << "Energy in [kWh/period]: " << m_totalEnergyIn << "\n";
        fout << "Losses [kWh/period]: " << m_totalLosses << "\n";
        fout << "Percentage of total load [%]: " << m_loadPortionAsPercentage << "\n";
        
        fout.close();
    }
    
    inline void CalculateLoadPortion( double otherLoads ) {
        if( otherLoads > 0 ) {
            m_loadPortionAsFraction = m_totalEnergyIn / (otherLoads + m_totalEnergyIn);
        }
        else {
            m_loadPortionAsFraction = 1.0;
        }
        m_loadPortionAsPercentage = m_loadPortionAsFraction * 100.0;
    }
    
protected:
    double m_totalCapacity;         /**< Total capacity of electric vehicle battery [kWh]. */
    double m_totalEnergyIn;         /**< Total input [kWh/period]. */
    double m_meanDailyEnergyIn;     /**< Mean daily energy into the vehicle [kWh/day]. */
    double m_totalLosses;           /**< Total losses [kWh/period]. */
    double m_loadPortionAsFraction; /**< Portion of annual loads attributed to EV [-]. */
    double m_loadPortionAsPercentage;      /**< Portion of annual loads attributed to EV [%]. */
};

struct ElectricVehicleData : public ElectricVehicleInput, public ElectricVehicleTimeseries, public ElectricVehicleSummary {
    /**
    * @struct ElectricVehicle [ElectricVehicle.h]
    * @brief Groups input, time series, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    ElectricVehicleData() {;}
};

#endif	/* ELECTRICVEHICLEDATA_H */

