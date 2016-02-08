/* 
 * File:   ConverterData.h
 * Author: Nate
 *
 * Created on November 2, 2015, 1:33 AM
 */

#ifndef CONVERTERDATA_H
#define	CONVERTERDATA_H



#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEConstants.h"
#include "ESDEIdentification.h"
#include "ESDEObjectData.h"

struct ConverterInput : public ESDEObjectInput {
    
public:
    /**
     * Default constructor. 
     */
    ConverterInput() {
        m_identInput.m_objectType = ID_CONVERTER;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ConverterInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_CONVERTER ;
        m_identInput.m_objectID = objectID ;
    }

    inline void SetInput( ConverterInput &data ) {
        m_identInput = data.m_identInput;
        m_capacity = data.m_capacity;
        m_efficiency = data.m_efficiency;
    }
    
    inline ConverterInput* GetInputData() {
        return(this);
    }
    
    inline void SetCapacity( double capacity ) {m_capacity = capacity;}
    inline double GetCapacity() { return(m_capacity); }
    inline void SetEfficiencyAsPercentage( double efficiency ) {m_efficiency = efficiency/100.0; }
    inline void SetEfficiencyAFraction( double efficiency ) {m_efficiency = efficiency;}
    inline double GetEfficiencyAsFraction() { return(m_efficiency); }
    inline double GetEfficiencyAsPercentage() { return(m_efficiency * 100.0); }
    
protected:
    double m_capacity;      /**< Capcaity [kW]. */
    double m_efficiency;    /**< Efficiency [fractional]. */
};

struct ConverterTimeseries : public ESDEObjectTimeseries {

public:    
    /**
     * Default constructor. 
     */
    ConverterTimeseries() {
        m_identTimeseries.m_objectType = ID_CONVERTER;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ConverterTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_CONVERTER;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the timestep data and setup iterators. 
    * @param numTimesteps - number of timesteps in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors if empty
        if( m_powerInput1To2.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_powerInput1To2 = tmp;
            m_powerOutput1To2 = tmp;
//            m_powerInput2To1 = tmp;
//            m_powerOutput2To1 = tmp;
        }
        
        // setup iterator
        SetupTimeseriesIterators();
    }
    inline void SetTimeseries( ConverterTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_powerInput1To2  = data.m_powerInput1To2;
        m_powerOutput1To2 = data.m_powerOutput1To2;
//        m_powerInput2To1  = data.m_powerInput2To1;
//        m_powerOutput2To1 = data.m_powerOutput2To1;
        
        SetupTimeseriesIterators();
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline ConverterTimeseries* GetTimeseriesData() {
        return(this);
    }
    /**
    * Setup iterators for time series data.
    */
    inline void SetupTimeseriesIterators() {
        m_iterPowerInput1To2  = m_powerInput1To2.begin();
        m_iterPowerOutput1To2 = m_powerOutput1To2.begin();
//        m_iterPowerInput2To1  = m_powerInput2To1.begin();
//        m_iterPowerOutput2To1 = m_powerOutput2To1.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterPowerInput1To2;
        ++m_iterPowerOutput1To2;
//        ++m_iterPowerInput2To1;
//        ++m_iterPowerOutput2To1;
    }
    /**
    * Clear timestep data. 
    */
    inline void ClearTimeseries() {
        m_powerInput1To2.clear();
        m_powerOutput1To2.clear();
//        m_powerInput2To1.clear();
//        m_powerOutput2To1.clear();
    }
    /**
    * Output the header in the time series data file. 
    * @param fout - file stream for output data. 
    */
    inline void OutputTimeseriesHeaderToFile( std::ofstream &fout ) {
        // use name if available, or object ID if not
        if( m_identTimeseries.m_objectName.size() > 0 ) {
            fout << m_identTimeseries.m_objectName.c_str() << " input [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " output [kW],";
//            fout << m_identTimeseries.m_objectName.c_str() << " DC-to-AC input [kW],";
//            fout << m_identTimeseries.m_objectName.c_str() << " DC-to-AC output [kW],";
//            fout << m_identTimeseries.m_objectName.c_str() << " AC-to-DC input [kW],";
//            fout << m_identTimeseries.m_objectName.c_str() << " AC-to-DC output [kW],";
        }
        else { // assume bi-directional inverter
            fout << m_identTimeseries.m_objectID << " input [kW],";
            fout << m_identTimeseries.m_objectID << " output [kW],";
//            fout << m_identTimeseries.m_objectID << " DC-to-AC input [kW],";
//            fout << m_identTimeseries.m_objectID << " DC-to-AC output [kW],";
//            fout << m_identTimeseries.m_objectID << " AC-to-DC input [kW],";
//            fout << m_identTimeseries.m_objectID << " AC-to-DC output [kW],";
        }
    }
    /**
    * Output a single timestep in the time series data.
    * @param fout - file stream for output data. 
    * @param time - timestep to select from time series data.  
    */
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_powerInput1To2.at(time) << ",";
        fout << m_powerOutput1To2.at(time) << ",";
//        fout << m_powerInput2To1.at(time) << ",";
//        fout << m_powerOutput2To1.at(time) << ",";
    }
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time, double numSystems ) {
        fout << m_powerInput1To2.at(time) * numSystems << ",";
        fout << m_powerOutput1To2.at(time) * numSystems << ",";
//        fout << m_powerInput2To1.at(time) << ",";
//        fout << m_powerOutput2To1.at(time) << ",";
    }
    
    inline void OutputSimpleTimeseriesHeaderToFile( std::ofstream &fout ) {
        fout << "Inverter AC,";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_powerOutput1To2.at(time) << ",";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time, double numSystems ) {
        fout << m_powerOutput1To2.at(time) * numSystems << ",";
    }
    
protected:
    std::vector<double> m_powerInput1To2;           /**< Power input from the converter from Node1 to Node2 [kW]. */
    std::vector<double> m_powerOutput1To2;          /**< Power output from the converter from Node1 to Node2 [kW]. */
//    std::vector<double> m_powerInput2To1;           /**< Power input from the converter from Node1 to Node2 [kW]. */
//    std::vector<double> m_powerOutput2To1;          /**< Power output from the converter from Node2 to Node1 [kW]. */
   
    std::vector<double>::iterator m_iterPowerInput1To2;
    std::vector<double>::iterator m_iterPowerOutput1To2;
//    std::vector<double>::iterator m_iterPowerInput2To1;
//    std::vector<double>::iterator m_iterPowerOutput2To1;
};

struct ConverterSummary : public ESDEObjectSummary {

public:
    /**
     * Default constructor
     */
    ConverterSummary() {
        m_identSummary.m_objectType = ID_SOLAR_PV;
        m_totalCapacity = 0;
        m_capacityFactor = 0;
        m_operatingTime = 0;
        m_totalInput1To2 = 0;
        m_totalOutput1To2 = 0;
        m_totalLosses = 0;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ConverterSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_SOLAR_PV;
        m_identSummary.m_objectID = objectID;
        m_totalCapacity = 0;
        m_capacityFactor = 0;
        m_operatingTime = 0;
        m_totalInput1To2 = 0;
        m_totalOutput1To2 = 0;
        m_totalLosses = 0;
    }
    
    inline void SetSummary( ConverterSummary &data ) {
        m_identSummary = data.m_identSummary;
        m_totalCapacity = data.m_totalCapacity;
        m_capacityFactor = data.m_capacityFactor;
        m_operatingTime = data.m_operatingTime;
        m_totalInput1To2 = data.m_totalInput1To2;
        m_totalOutput1To2 = data.m_totalOutput1To2;
        m_totalLosses = data.m_totalLosses;
    }
    /**
     * Copy summary data onto blank struct. 
     * @return copy of summary data. 
     */
    inline ConverterSummary* GetSummaryData() {
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
        fout << "Object type: " << "Converter" << "\n";
        fout << "Object ID: " << m_identSummary.m_objectID << "\n\n";
        
        fout << "Total capacity [kW]: " << m_totalCapacity << "\n";  
        fout << "Total input [kWh/period]: " << m_totalInput1To2 << "\n";
        fout << "Total output [kWh/period]: " << m_totalOutput1To2 << "\n";
        fout << "Total losses [kWh/period]: " << m_totalLosses << "\n";
        fout << "Capacity factor [%]: " << m_capacityFactor << "\n";
        fout << "Operating time [timesteps/period]: " << m_operatingTime << "\n";
        
        fout.close();
    }
    
protected:
    double m_totalCapacity;         /**< Total capacity of inverter [kW]. */
    double m_totalInput1To2;        /**< Total input DC to AC [kWh/period]. */
    double m_totalOutput1To2;       /**< Total output DC to AC [kWh/period]. */
    double m_totalLosses;           /**< Total losses [kWh/period]. */
    double m_capacityFactor;        /**< Capacity factor of the inverter [%]. */
    unsigned int m_operatingTime;   /**< Number of timesteps in which the inverter  has positive power output [timesteps/period]. */
};

struct ConverterData : public ConverterInput, public ConverterTimeseries, public ConverterSummary {
    ConverterData() {;}
};

#endif	/* CONVERTERDATA_H */

