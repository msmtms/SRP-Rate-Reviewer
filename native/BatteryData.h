/**
 * @file BatteryData.h
 * @brief Data for an electrochemical battery. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef BATTERYDATA_H
#define	BATTERYDATA_H


#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>
#include <math.h>

#include "ESDEConstants.h"
#include "ESDEIdentification.h"
#include "ESDEObjectData.h"

struct BatteryInput : public ESDEObjectInput {
    /**
    * @struct BatteryInput [BatteryData.h]
    * @brief Battery input data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o technical data
     o cost data
     o battery type list
     o battery model list
     o init values to zero
     */
    
public:
    /**
     * Default constructor. 
     */
    BatteryInput() {
        m_identInput.m_objectType = ID_BATTERY;
        m_batteryType = LEADACID;
        m_batteryModel = SIMPLE;
        
        m_initialCapacityAsFraction = 1.0;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    BatteryInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_BATTERY;
        m_identInput.m_objectID = objectID;
        m_batteryType = LEADACID;
        m_batteryModel = SIMPLE;
        
        m_initialCapacityAsFraction = 1.0;
    }
    
    inline void SetInput( BatteryInput &data ) {
        m_identInput = data.m_identInput;
        
        m_batteryType = data.m_batteryType;
        m_batteryModel = data.m_batteryModel;
        
        m_nominalCapacity = data.m_nominalCapacity;
        m_roundtripEfficiency = data.m_roundtripEfficiency;
        m_initialCapacityAsFraction = data.m_initialCapacityAsFraction;
        m_minCapacityAsFraction = data.m_minCapacityAsFraction;
        m_maxCRate = data.m_maxCRate;
        
        m_maxCapacityAsEnergy = data.m_maxCapacityAsEnergy;
        m_minCapacityAsEnergy = data.m_minCapacityAsEnergy;
        m_chargeEfficiency = data.m_chargeEfficiency;
        m_dischargeEfficiency = data.m_dischargeEfficiency;
        m_maxRatedChargePower = data.m_maxRatedChargePower;
        m_maxRatedDischargePower = data.m_maxRatedDischargePower;
        
    }
    
    inline BatteryInput* GetInputData() {
        return(this);
    }
    
public: // enums
    
    enum BATTERY_TYPE {
        LEADACID = 0, 
        LIION
    };
    
    enum BATTERY_MODEL {
        SIMPLE = 0,
        KIBAM
    };
    
    inline void ComputeBatteryParameters() {
        m_maxCapacityAsEnergy = m_nominalCapacity;
        m_minCapacityAsEnergy = m_maxCapacityAsEnergy * m_minCapacityAsFraction;
        m_chargeEfficiency = sqrt(m_roundtripEfficiency);
        m_dischargeEfficiency = sqrt(m_roundtripEfficiency);
        m_maxRatedChargePower = m_maxCapacityAsEnergy * m_maxCRate;
        m_maxRatedDischargePower = m_maxCapacityAsEnergy * m_maxCRate;
    }
    
    inline void SetNominalCapacity( double nominalCapacity ) {m_nominalCapacity = nominalCapacity;}
    inline double GetNominalCapacity() { return(m_nominalCapacity); }
    inline void SetRoundtripEfficiency( double roundtripEfficiency ) {m_roundtripEfficiency = roundtripEfficiency / 100.0;}
    inline double GetRoundtripEfficiency() { return(m_roundtripEfficiency); }
    inline void SetMinCapacityAsPercentage( double minCapacityAsPercentage ) {m_minCapacityAsFraction = minCapacityAsPercentage / 100.0;}
    inline double GetMinCapacityAsPercentage() { return(m_minCapacityAsFraction); }
    inline void SetMaxCRate( double maxCRate ) {m_maxCRate = maxCRate;}
    inline double GetMaxCRate() { return(m_maxCRate); }
    
protected:
    unsigned int m_batteryType;             /**< Battery chemistry. @see BATTERY_TYPE */
    unsigned int m_batteryModel;            /**< Battery model. @see BATTERY_MODEL */
    
    /* these are read in */
    double m_nominalCapacity;               /**< Nominal capacity [kWh]. */
    double m_roundtripEfficiency;           /**< Roundtrip efficiency [fraction] and reported to user [%]. */ 
    double m_initialCapacityAsFraction;     /**< Initial capacity [fraction] and reported to user [%]. */ 
    double m_minCapacityAsFraction;         /**< Minimum capacity [fraction] and reported to user [%]. */ 
    double m_maxCRate;                      /**< Maximum C-Rate [1/hr]. */
    
    /* can be computed or read in */
    double m_maxCapacityAsEnergy;           /**< Maximum energy content in the storage [kWh]. */
    double m_minCapacityAsEnergy;           /**< Minumum energy content in the storage [kWh]. */
    double m_chargeEfficiency;              /**< Charging efficiency [fraction] and reported to user [%]. */ 
    double m_dischargeEfficiency;           /**< Discharging efficiency [fraction] and reported to user [%]. */ 
    double m_maxRatedChargePower;           /**< Maximum charge power [kW]. */
    double m_maxRatedDischargePower;        /**< Maximum discharge power [kW]. */
};

struct BatteryTimeseries : public ESDEObjectTimeseries {
    /**
    * @struct BatteryTimeseries [BatteryData.h]
    * @brief Battery time series data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    /**
     * Default constructor. 
     */
    BatteryTimeseries() {
        m_identTimeseries.m_objectType = ID_BATTERY;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    BatteryTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_BATTERY;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the time series data and setup iterators. 
    * @param numTimesteps - number of time series in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors if empty
        if( m_maxChargePower.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_maxChargePower = tmp;
            m_chargePower = tmp;
            m_maxDischargePower = tmp;
            m_dischargePower = tmp;
            m_outputPower = tmp;
            m_capacityAsEnergy = tmp;
            m_capacityAsFraction = tmp;
        }
        
        // setup iterator
        SetupTimeseriesIterators();
    }
    inline void SetTimeseries( BatteryTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_outputPower = data.m_outputPower;
        m_maxChargePower = data.m_maxChargePower;
        m_chargePower = data.m_chargePower;
        m_maxDischargePower = data.m_maxDischargePower;
        m_dischargePower = data.m_dischargePower;
        m_capacityAsEnergy = data.m_capacityAsEnergy;
        m_capacityAsFraction = data.m_capacityAsFraction;
        
        SetupTimeseriesIterators();
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline BatteryTimeseries* GetTimeseriesData() {
        return(this);
    }
    
    /**
    * Clear time series data. 
    */
    inline void ClearTimeseries() {
        m_outputPower.clear();
        m_maxChargePower.clear();
        m_chargePower.clear();
        m_maxDischargePower.clear();
        m_dischargePower.clear();
        m_capacityAsEnergy.clear();
        m_capacityAsFraction.clear();
    }
    /**
    * Setup time series iterators. 
    */
    inline void SetupTimeseriesIterators() {
        m_iterOutputPower = m_outputPower.begin();
        m_iterMaxChargePower = m_maxChargePower.begin();
        m_iterChargePower = m_chargePower.begin();
        m_iterMaxDischargePower = m_maxDischargePower.begin();
        m_iterDischargePower = m_dischargePower.begin();
        m_iterCapacityAsEnergy = m_capacityAsEnergy.begin();
        m_iterCapacityAsFraction = m_capacityAsFraction.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterMaxChargePower;
        ++m_iterChargePower;
        ++m_iterMaxDischargePower;
        ++m_iterDischargePower;
        ++m_iterOutputPower;
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
            fout << m_identTimeseries.m_objectName.c_str() << " output power [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " max charge power [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " charge power [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " max discharge power [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " discharge power [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " capacity as energy [kWh],";
            fout << m_identTimeseries.m_objectName.c_str() << " capacity as fraction [%],";
        }
        else {
            fout << m_identTimeseries.m_objectID << " output power [kW],";
            fout << m_identTimeseries.m_objectID << " max charge power [kW],";
            fout << m_identTimeseries.m_objectID << " charge power [kW],";
            fout << m_identTimeseries.m_objectID << " max discharge power [kW],";
            fout << m_identTimeseries.m_objectID << " discharge power [kW],";
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
        fout << m_outputPower.at(time) << ",";
        fout << m_maxChargePower.at(time) << ",";
        fout << m_chargePower.at(time) << ",";
        fout << m_maxDischargePower.at(time) << ",";
        fout << m_dischargePower.at(time) << ",";
        fout << m_capacityAsEnergy.at(time) << ",";
        fout << (m_capacityAsFraction.at(time) * 100.0) << ",";
    }
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time, double numSystems ) {
        fout << m_outputPower.at(time) * numSystems << ",";
        fout << m_maxChargePower.at(time) * numSystems << ",";
        fout << m_chargePower.at(time) * numSystems << ",";
        fout << m_maxDischargePower.at(time) * numSystems << ",";
        fout << m_dischargePower.at(time) * numSystems << ",";
        fout << m_capacityAsEnergy.at(time) * numSystems << ",";
        fout << (m_capacityAsFraction.at(time) * 100.0) << ",";
    }
    inline void OutputSimpleTimeseriesHeaderToFile( std::ofstream &fout ) {
        fout << "Battery,";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_outputPower.at(time) << ",";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time, double numSystems ) {
        fout << m_outputPower.at(time) * numSystems << ",";
    }
    
protected:
    std::vector<double> m_outputPower;          /**< Actual output power in the timestep [kW]. Positive for output power, negative for input power. */
    std::vector<double> m_maxChargePower;       /**< Maximum charge power in the timestep [kW]. */
    std::vector<double> m_chargePower;          /**< Actual charge power in the timestep [kW]. */
    std::vector<double> m_maxDischargePower;    /**< Maximum discharge power in the timestep [kW]. */
    std::vector<double> m_dischargePower;       /**< Actual discharge power in the timestep [kW]. */
    std::vector<double> m_capacityAsEnergy;     /**< Energy content in the storage [kWh]. */
    std::vector<double> m_capacityAsFraction;   /**< Energy content in the storage [fraction] and reported to user [%]. */
    
    std::vector<double>::iterator m_iterOutputPower;
    std::vector<double>::iterator m_iterMaxChargePower;
    std::vector<double>::iterator m_iterChargePower;
    std::vector<double>::iterator m_iterMaxDischargePower;
    std::vector<double>::iterator m_iterDischargePower;
    std::vector<double>::iterator m_iterCapacityAsEnergy;
    std::vector<double>::iterator m_iterCapacityAsFraction;
};

struct BatterySummary : public ESDEObjectSummary {
    /**
    * @struct BatterySummary [BatteryData.h]
    * @brief Battery summary data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    /**
     * Default constructor. 
     */
    BatterySummary() {
        m_identSummary.m_objectType = ID_BATTERY;
        m_totalCapacity = 0;
        m_totalEnergyIn = 0;
        m_totalEnergyOut = 0;
        m_totalLosses = 0;
        m_autonomy = 0;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    BatterySummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_BATTERY;
        m_identSummary.m_objectID = objectID;
        m_totalCapacity = 0;
        m_totalEnergyIn = 0;
        m_totalEnergyOut = 0;
        m_totalLosses = 0;
        m_autonomy = 0;
    }
    
    inline void SetSummary( BatterySummary &data ) {
        m_identSummary = data.m_identSummary;
        m_totalCapacity = data.m_totalCapacity;
        m_totalEnergyIn = data.m_totalEnergyIn;
        m_totalEnergyOut = data.m_totalEnergyOut;
        m_totalLosses = data.m_totalLosses;
        m_autonomy = data.m_autonomy;
    }
    /**
     * Copy summary data onto blank struct. 
     * @return copy of summary data. 
     */
    inline BatterySummary* GetSummaryData() {
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
        fout << "Object type: " << "Battery" << "\n";
        fout << "Object ID: " << m_identSummary.m_objectID << "\n\n";
        
        fout << "Total capacity [kWh]: " << m_totalCapacity << "\n";
        fout << "Energy in [kWh/period]: " << m_totalEnergyIn << "\n";
        fout << "Energy out [kWh/period]: " << m_totalEnergyOut << "\n";
        fout << "Losses [kWh/period]: " << m_totalLosses << "\n";
        fout << "Autonomy [h]: " << m_autonomy << "\n";
        
        fout.close();
    }
    
    inline void CalculateAutonomy( double load ) {
        if( load > 0 )
            m_autonomy = m_totalCapacity / load;
    }
    
protected:
    double m_totalCapacity;         /**< Total capacity of battery [kWh]. */
    double m_totalEnergyIn;         /**< Total input [kWh/period]. */
    double m_totalEnergyOut;        /**< Total output [kWh/period]. */
    double m_totalLosses;           /**< Total losses [kWh/period]. */
    double m_autonomy;              /**< Time the battery will last at average load [hr]. */
};

struct BatteryData : public BatteryInput, public BatteryTimeseries, public BatterySummary {
    /**
    * @struct BatteryData [BatteryData.h]
    * @brief Groups input, time series, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    BatteryData() {;}
};

#endif	/* BATTERYDATA_H */

