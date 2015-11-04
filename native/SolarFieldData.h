/**
 * @file SolarFieldData.h
 * @brief Solar field input and output data. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef SOLARFIELDDATA_H
#define	SOLARFIELDDATA_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEConstants.h"
#include "ESDEIdentification.h"
#include "ESDEObjectData.h"


struct SolarFieldInput : public ESDEObjectInput {
    /**
    * @struct SolarFieldInput [SolarFieldData.h]
    * @brief Solar field input data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o techncial data
     o cost data
     o initialize values in constructor
     */
    
public:
    /**
     * Default constructor. 
     */
    SolarFieldInput() {
        m_identInput.m_objectType = ID_SOLAR_FIELD;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarFieldInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_SOLAR_FIELD;
        m_identInput.m_objectID = objectID;
    }
    /**
     * Deconstructor.
     */
    ~SolarFieldInput() {
        ;
    }
    /**
     * Sets input data.
     * @param data - input data.
     */
    inline void SetInput( SolarFieldInput &data ) {
        m_identInput = data.m_identInput;
        m_numHeliostats = data.m_numHeliostats;
        m_avgOpticalEfficiency = data.m_avgOpticalEfficiency;
    }
    
    inline SolarFieldInput* GetInputData() {
//        SolarFieldSummary myCopy;
//        
//        myCopy.m_identSummary = m_identSummary;
//        ;
//    
        return(this);
    }
    
public: // accessors

    inline void SetNumHeliostats( unsigned int numHeliostats ) {m_numHeliostats = numHeliostats;}
    inline unsigned int GetNumHeliostats() { return(m_numHeliostats); }
    inline void SetAvergaOpticalEfficiency( double avgOpticalEfficiency ) {m_avgOpticalEfficiency = avgOpticalEfficiency;}
    inline double GetAvergaOpticalEfficiency() { return(m_avgOpticalEfficiency); }
    
protected:
    unsigned int m_numHeliostats;
    double m_avgOpticalEfficiency;
};

struct SolarFieldTimeseries : public ESDEObjectTimeseries {
    /**
    * @struct SolarFieldTimeseries [SolarFieldData.h]
    * @brief Solar field time series data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
 
public:
    /**
     * Default constructor. 
     */
    SolarFieldTimeseries() {
        m_identTimeseries.m_objectType = ID_SOLAR_FIELD;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarFieldTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_SOLAR_FIELD;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the time series data and setup iterators. 
    * @param numTimesteps - number of timesteps in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors only if empty
        if( m_receiverIncidentRadiation.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_receiverIncidentRadiation = tmp;
        }
        
        // setup iterator
        SetupTimeseriesIterators();
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline SolarFieldTimeseries* GetTimeseriesData() {
//        SolarFieldTimeseries myCopy;
//        
//        myCopy.m_identTimeseries = m_identTimeseries;
//        myCopy.m_receiverIncidentRadiation = m_receiverIncidentRadiation;
//    
        return(this);
    }
    /**
    * Setup iterators for time series data.
    */
    inline void SetupTimeseriesIterators() {
        m_iterReceiverIncident = m_receiverIncidentRadiation.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterReceiverIncident;
    }
    /**
    * Clear time series data. 
    */
    inline void ClearTimeseries() {
        m_receiverIncidentRadiation.clear();
    }
    /**
     * Sets time series data.
     * @param data - time series data.
     */
    inline void SetTimeseries( SolarFieldTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_receiverIncidentRadiation = data.m_receiverIncidentRadiation;
        
        SetupTimeseriesIterators();
    }
    /**
    * Output the header in the time series data file. 
    * @param fout - file stream for output data. 
    */
    inline void OutputTimeseriesHeaderToFile( std::ofstream &fout ) {
        // use name if available, or object ID if not
        if( m_identTimeseries.m_objectName.size() > 0 ) {
            fout << m_identTimeseries.m_objectName.c_str() << " receiver incident radiation [kW/m2],";
        }
        else {
            fout << m_identTimeseries.m_objectID << " receiver incident radiation [kW/m2],";
        }
        
        // don't output other values, unless needed
    }
    /**
    * Output a single timestep in the time series data.
    * @param fout - file stream for output data. 
    * @param time - timestep to select from time series data.  
    */
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int iTime ) const {
        fout << m_receiverIncidentRadiation.at(iTime) << ",";
        
        // don't output other values, unless needed
    }
    
protected:
    std::vector<double> m_receiverIncidentRadiation;    /**< Radiation incident on receiver [W/m2]. */
    
    std::vector<double>::iterator m_iterReceiverIncident;
};

struct SolarFieldSummary : public ESDEObjectSummary {
    /**
    * @struct SolarFieldSummary [SolarFieldData.h]
    * @brief Solar field summary data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    /**
     * Default constructor. 
     */
    SolarFieldSummary() {
        m_identSummary.m_objectType = ID_SOLAR_FIELD;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarFieldSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_SOLAR_FIELD;
        m_identSummary.m_objectID = objectID;
    }
    /**
     * Sets summary data.
     * @param data - summary data.
     */
    inline void SetSummary( SolarFieldSummary &data ) {
        m_identSummary= data.m_identSummary;
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline SolarFieldSummary* GetSummaryData() {
//        SolarFieldSummary myCopy;
//        
//        myCopy.m_identSummary = m_identSummary;
//        ;
//    
        return(this);
    }
    /**
    * Output summary data to file
    * @param path - path for summary data 
    */
    inline void OutputSummaryToFile( std::string &path ) {
        // TODO
    }
  
protected:
    ; // data
};

struct SolarFieldData : public SolarFieldInput, public SolarFieldTimeseries, public SolarFieldSummary {
    /**
    * @struct SolarFieldData [SolarFieldData.h]
    * @brief Groups input, time series, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    SolarFieldData() {;}
};


#endif	/* SOLARFIELDDATA_H */

