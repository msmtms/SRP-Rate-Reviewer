/**
 * @file ImmediateLoadData.h
 * @brief Immediate load input and output data. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef IMMEDIATELOADDATA_H
#define	IMMEDIATELOADDATA_H


#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEConstants.h"
#include "ESDEObjectData.h"
#include "Logger.h"

struct ImmediateLoadInput : public ESDEObjectInput {

    /**
    * @struct ImmediateLoadInput [ImmediateLoadData.h]
    * @brief Immediate load input data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o technical data
     o cost data
     */
    
public:
    /**
     * Default constructor. 
     */
    ImmediateLoadInput() {
        m_identInput.m_objectType = ID_IMMEDIATE_LOAD;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ImmediateLoadInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_IMMEDIATE_LOAD;
        m_identInput.m_objectID = objectID;
    }
    
    /**
     * Sets input data.
     * @param data - input data.
     */
    inline void SetInput( ImmediateLoadInput &data ) {
        m_identInput = data.m_identInput;
    }
    inline ImmediateLoadInput* GetInputData() {
    return(this);
    }
    
protected:
    ; // data
};

struct ImmediateLoadTimeseries : public ESDEObjectTimeseries {
    /**
    * @struct ImmediateLoadTimeseries [ImmediateLoadData.h]
    * @brief Immediate load timestep data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO 
     x requested load
     x controlled load
     x served load
     */

public:
    /**
     * Default constructor. 
     */
    ImmediateLoadTimeseries() {
        m_identTimeseries.m_objectType = ID_IMMEDIATE_LOAD;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ImmediateLoadTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_IMMEDIATE_LOAD;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the time series data and setup iterators. 
    * @param numTimesteps - number of time series in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors if empty
        if( m_loadRequested.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_loadRequested = tmp;
            m_loadServed = tmp;
//            m_loadCurtailed = tmp;
//            m_loadUnmet = tmp;
        }
        // setup iterator
        SetupTimeseriesIterators();
    }
    
    inline void SetTimeseries( ImmediateLoadTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_loadRequested = data.m_loadRequested;
        m_loadServed = data.m_loadServed;
//        m_loadCurtailed = data.m_loadCurtailed;
//        m_loadUnmet = data.m_loadUnmet;
    }
    
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline ImmediateLoadTimeseries* GetTimeseriesData() {
        return(this);
    }
    /**
    * Setup iterators for time series data.
    */
    inline void SetupTimeseriesIterators() {
        m_iterLoadRequested = m_loadRequested.begin();
        m_iterLoadServed = m_loadServed.begin();
//        m_iterLoadCurtailed = m_loadCurtailed.begin();
//        m_iterLoadUnmet = m_loadUnmet.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterLoadRequested;
        ++m_iterLoadServed;
//        ++m_iterLoadCurtailed;
//        ++m_iterLoadUnmet;
    }
    /**
    * Clear time series data. 
    */
    inline void ClearTimeseries() {
        m_loadRequested.clear();
        m_loadServed.clear();
//        m_loadCurtailed.clear();
//        m_loadUnmet.clear();
    }
    /**
    * Output the header in the time series data file. 
    * @param fout - file stream for output data. 
    */
    inline void OutputTimeseriesHeaderToFile( std::ofstream &fout ) {
        // use name if available, or object ID if not
        if( m_identTimeseries.m_objectName.size() > 0 ) {
            fout << m_identTimeseries.m_objectName.c_str() << " requested [kW],";
            fout << m_identTimeseries.m_objectName.c_str() << " served [kW],";
//            fout << m_identTimeseries.m_objectName.c_str() << " curtailed [kW],";
//            fout << m_identTimeseries.m_objectName.c_str() << " unmet [kW],";
        }
        else {
            fout << m_identTimeseries.m_objectID << " requested [kW],";
            fout << m_identTimeseries.m_objectID << " served [kW],";
//            fout << m_identTimeseries.m_objectID << " curtailed [kW],";
//            fout << m_identTimeseries.m_objectID << " unmet [kW],";
        }
    }
    /**
    * Output a single timestep in the time series data.
    * @param fout - file stream for output data. 
    * @param time - timestep to select from time series data.  
    */
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_loadRequested.at(time) << ",";
        fout << m_loadServed.at(time) << ",";
//        fout << m_loadCurtailed.at(time) << ",";
//        fout << m_loadUnmet.at(time) << ",";
    }
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time, double numSystems ) {
        fout << m_loadRequested.at(time) * numSystems << ",";
        fout << m_loadServed.at(time) * numSystems << ",";
//        fout << m_loadCurtailed.at(time) << ",";
//        fout << m_loadUnmet.at(time) << ",";
    }
    inline void OutputSimpleTimeseriesHeaderToFile( std::ofstream &fout ) {
        if( m_identTimeseries.m_objectName.size() > 0 ) {
            fout << m_identTimeseries.m_objectName.c_str() << ",";
        }
        else {
            fout << "Total load,";
        }
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_loadServed.at(time) << ",";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time, double numSystems ) {
        fout << m_loadServed.at(time) * numSystems << ",";
    }
    inline void AddLoadToTimeseries( std::vector<double> &load ) {
        if( m_loadServed.size() == load.size() ) {
            for( size_t i=0; i<m_loadServed.size(); ++i ) {
                m_loadServed.at(i) += load.at(i);
            }
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Load array sizes do not match and cannot be added to Grid" ), Logger::ERROR);
        }
    }
    
public: // accessors
    inline void SetLoadRequested( std::vector<double> &load ) {m_loadRequested = load; m_iterLoadRequested = m_loadRequested.begin(); }
    inline std::vector<double> & GetLoadRequested() { return(m_loadRequested); }
    
    inline void SetLoadServed( std::vector<double> &load ) {m_loadServed = load; m_iterLoadServed = m_loadServed.begin(); }
    inline std::vector<double> & GetLoadServed() { return(m_loadServed); }
    
    inline double GetCurrentLoadRequested() { return(*m_iterLoadRequested); }
    inline double GetCurrentLoadServed() { return(*m_iterLoadServed); }
    
protected:
    std::vector< double > m_loadRequested;      /**< Load requested [kW]. */
    std::vector< double > m_loadServed;         /**< Load served [kW]. Different from load requested and load controlled if there is unmet load. */
//    std::vector< double > m_loadCurtailed;      /**< Load curtailed [kW] during demand response events. */
//    std::vector< double > m_loadUnmet;          /**< Load that goes unmet [kW] if no power is available. */
    
    std::vector<double>::iterator m_iterLoadRequested;
    std::vector<double>::iterator m_iterLoadServed;
//    std::vector<double>::iterator m_iterLoadCurtailed;
//    std::vector<double>::iterator m_iterLoadUnmet;
};

struct ImmediateLoadSummary : public ESDEObjectSummary {
    /**
    * @struct ImmediateLoadSummary [ImmediateLoadData.h]
    * @brief Immediate load summary  data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o output summary data to file
     o possibly move CalculateSummaryData into this struct
     o calculate duty factor
     */

public:
    /**
     * Default constructor
     */
    ImmediateLoadSummary() {
        m_identSummary.m_objectType = ID_IMMEDIATE_LOAD;
        m_maxLoadServed = 0;
        m_minLoadServed = 0;
        m_avgLoadServed = 0;
//        m_totalLoadRequested = 0;
        m_totalLoadServedDay = 0;
        m_totalLoadServedPeriod = 0;
//        m_totalLoadCurtailed = 0;
//        m_totalLoadUnmet = 0;
//        m_operatingTime = 0;
        m_loadFactor = 0;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    ImmediateLoadSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_IMMEDIATE_LOAD;
        m_identSummary.m_objectID = objectID;
        m_maxLoadServed = 0;
        m_minLoadServed = 0;
        m_avgLoadServed = 0;
//        m_totalLoadRequested = 0;
        m_totalLoadServedDay = 0;
        m_totalLoadServedPeriod = 0;
//        m_totalLoadCurtailed = 0;
//        m_totalLoadUnmet = 0;
//        m_operatingTime = 0;
        m_loadFactor = 0;
    }
    
    inline void SetSummary( ImmediateLoadSummary &data ) {
        m_identSummary = data.m_identSummary;
        m_maxLoadServed = data.m_maxLoadServed;
        m_minLoadServed = data.m_minLoadServed;
        m_avgLoadServed = data.m_avgLoadServed;
//        m_totalLoadRequested = data.m_totalLoadRequested;
        m_totalLoadServedDay = data.m_totalLoadServedDay;
        m_totalLoadServedPeriod = data.m_totalLoadServedPeriod;
//        m_totalLoadCurtailed = data.m_totalLoadCurtailed;
//        m_totalLoadUnmet = data.m_totalLoadUnmet;
//        m_operatingTime = data.m_operatingTime;
        m_loadFactor = data.m_loadFactor;
    }
    
    /**
     * Gets copy of summary data. 
     * @return copy of summary data. 
     */
    inline ImmediateLoadSummary* GetSummaryData() {
        return(this);
    }
    /**
    * Output summary data to file
    * @param path - path for summary data
    */
    inline void OutputSummaryToFile( std::string &path ) {
        // construct path name, use name if available, or object ID if not
        std::string dir = path;
        
        // TODO: this is an override for now
        dir += "Load.txt";
            
//        if( m_identSummary.m_objectName.size() > 0 ) {
//            dir += "";
//            dir += m_identSummary.m_objectName;
//            dir += ".txt";
//        }
//        else {
//            dir += "";
//            dir += std::to_string( m_identSummary.m_objectID );
//            dir += ".txt";
//        }
        
        // output to file
        std::ofstream fout;
        fout.open( dir.c_str() );
        
        fout << "Object name: " << m_identSummary.m_objectName << "\n";
        fout << "Object type: " << "Immediate Load" << "\n";
        fout << "Object ID: " << m_identSummary.m_objectID << "\n\n";
        
        fout << "Peak load served [kW]: " << m_maxLoadServed << "\n";
        fout << "Minimum load served [kW]: " << m_minLoadServed << "\n";
        fout << "Average load served [kW]: " << m_avgLoadServed << "\n";
        fout << "Energy use [kWh/day]: " << m_totalLoadServedDay << "\n";
        fout << "Energy use [kWh/period]: " << m_totalLoadServedPeriod << "\n";
        fout << "Load factor [-]: " << m_loadFactor << "\n";
//        fout << "Total load requested [kWh/period]: " << m_totalLoadRequested << "\n";
//        fout << "Total load served [kWh/period]: " << m_totalLoadServed << "\n";
//        fout << "Total load curtailed [kWh/period]: " << m_totalLoadCurtailed << "\n";
//        fout << "Total load unmet [kWh/period]: " << m_totalLoadUnmet << "\n";
//        fout << "Operating time [timesteps/period]: " << m_operatingTime << "\n";
        
        fout.close();
    }
    
    inline double GetMaxLoadServed() {return(m_maxLoadServed); };
    inline double GetMinLoadServed() {return(m_minLoadServed); };
    inline double GetAvgLoadServed() {return(m_avgLoadServed); };
    inline double GetTotalLoadServedPeriod() {return(m_totalLoadServedPeriod); };
    inline double GetTotalLoadServedDay() {return(m_totalLoadServedDay); };
    inline double GetLoadFactor() {return(m_loadFactor); };
    
protected:  
    double m_maxLoadServed;         /**< Maximum (peak) load served [kW]. */
    double m_minLoadServed;         /**< Minimum load served [kW]. */
    double m_avgLoadServed;         /**< Average load served [kW]. */
//    double m_totalLoadRequested;    /**< Total load requested during the simulation period [kWh/period]. */
    double m_totalLoadServedPeriod; /**< Total load served during the simulation period [kWh/period]. */
    double m_totalLoadServedDay;    /**< Total load served during the simulation period [kWh/period]. */
//    double m_totalLoadCurtailed;    /**< Total load curtailed during the simulation period [kWh/period]. */
//    double m_totalLoadUnmet;        /**< Total load unmet during the simulation period [kWh/period]. */
//    unsigned int m_operatingTime;   /**< Number of timesteps in wihch the load is nonzero [timesteps/period]. */
    double m_loadFactor;            /**< Load factor [-]. */
};
struct ImmediateLoadData : public ImmediateLoadInput, public ImmediateLoadTimeseries, public ImmediateLoadSummary {
    /**
    * @struct ImmediateLoadData [ImmediateLoadData.h]
    * @brief Groups input, time series, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    ImmediateLoadData() {;}
};

#endif	/* IMMEDIATELOADDATA_H */

