/**
 * @file SolarPVData.h
 * @brief Solar PV input and output data. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

/* TODO 
 o using a separate name variable for each struct is excessive, try replace
 */
    
#ifndef SOLARPVDATA_H
#define	SOLARPVDATA_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEConstants.h"
#include "ESDEIdentification.h"
#include "ESDEObjectData.h"

struct SolarPVInput : public ESDEObjectInput {
    /**
    * @struct SolarPVInput [SolarPVData.h]
    * @brief Solar PV input data.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o technical data
     o cost data
     x design variable -- in ESDEObjectInput
     x scenario variable -- ESDEObjectInput
     */
    
public:
    /**
     * Default constructor. 
     */
    SolarPVInput() {
        m_identInput.m_objectType = ID_SOLAR_PV;
        m_groundReflectance = 0.2;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarPVInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_SOLAR_PV ;
        m_identInput.m_objectID = objectID ;
    }

    inline void SetInput( SolarPVInput &data ) {
        m_identInput = data.m_identInput;
        m_capacity = data.m_capacity;
        m_azimuthInput = data.m_azimuthInput;
        m_slopeInput = data.m_slopeInput;
        m_groundReflectance = data.m_groundReflectance;
    }
    
    inline SolarPVInput* GetInputData() {
        return(this);
    }
    
    inline void SetCapacity( double capacity ) {m_capacity = capacity;}
    inline double GetCapacity() { return(m_capacity); }
    inline void SetAzimuth( double azimuth ) {m_azimuthInput = azimuth;}
    inline double GetAzimuthy() { return(m_azimuthInput); }
    inline void SetSlope( double slope ) {m_slopeInput = slope;}
    inline double GetSlope() { return(m_slopeInput); }
    
protected:
    double m_capacity;      /**< Capcaity [kW]. */
    double m_azimuthInput;  /**< Azimuth [degrees]. */
    double m_slopeInput;    /**< Slope [degrees]. */
    double m_groundReflectance; /** Ground reflectance [fractional]. */
};

struct SolarPVTimeseries : public ESDEObjectTimeseries {
    /**
    * @struct SolarPVTimeseries [SolarPVData.h]
    * @brief Solar PV time series data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO 
     o add angle of incidence? 
     o include dedicated inverter separately 
     o replace Copy with an overloaded copy operator
     */

public:    
    /**
     * Default constructor. 
     */
    SolarPVTimeseries() {
        m_identTimeseries.m_objectType = ID_SOLAR_PV;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarPVTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_SOLAR_PV;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the timestep data and setup iterators. 
    * @param numTimesteps - number of timesteps in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors if empty
        if( m_powerOutput.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_slope = tmp;
            m_azimuth = tmp;
            m_incidentAngle = tmp;
            m_incidentRadiation = tmp;
            m_cellTemperature = tmp;
            m_powerOutput = tmp;
        }
        
        // setup iterator
        SetupTimeseriesIterators();
    }
    inline void SetTimeseries( SolarPVTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_slope = data.m_slope;
        m_azimuth = data.m_azimuth;
        m_incidentAngle = data.m_incidentAngle;
        m_incidentRadiation = data.m_incidentRadiation;
        m_cellTemperature = data.m_cellTemperature;
        m_powerOutput = data.m_powerOutput;
        
        SetupTimeseriesIterators();
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline SolarPVTimeseries* GetTimeseriesData() {
        return(this);
    }
    /**
    * Setup iterators for time series data.
    */
    inline void SetupTimeseriesIterators() {
        m_iterSlope = m_slope.begin();
        m_iterAzimuth = m_azimuth.begin();
        m_iterIncidentAngle = m_incidentAngle.begin();
        m_iterIncidentRadiation = m_incidentRadiation.begin();
        m_iterCellTemperature = m_cellTemperature.begin();
        m_iterPowerOutput = m_powerOutput.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterSlope;
        ++m_iterAzimuth;
        ++m_iterIncidentAngle;
        ++m_iterIncidentRadiation;
        ++m_iterCellTemperature;
        ++m_iterPowerOutput;
    }
    /**
    * Clear timestep data. 
    */
    inline void ClearTimeseries() {
        m_slope.clear();
        m_azimuth.clear();
        m_incidentAngle.clear();
        m_incidentRadiation.clear();
        m_cellTemperature.clear();
        m_powerOutput.clear();
    }
    /**
    * Output the header in the time series data file. 
    * @param fout - file stream for output data. 
    */
    inline void OutputTimeseriesHeaderToFile( std::ofstream &fout ) {
        // use name if available, or object ID if not
        if( m_identTimeseries.m_objectName.size() > 0 ) {
//            fout << m_identTimeseries.m_objectName.c_str() << " slope [degrees],";
//            fout << m_identTimeseries.m_objectName.c_str() << " azimuth [degrees],";
            fout << m_identTimeseries.m_objectName.c_str() << " incident radiation [kW/m2],";
//            fout << m_identTimeseries.m_objectName.c_str() << " cell temperature [C],";
            fout << m_identTimeseries.m_objectName.c_str() << " power output [kW],";
        }
        else {
//            fout << m_identTimeseries.m_objectID << " slope [degrees],";
//            fout << m_identTimeseries.m_objectID << " azimuth [degrees],";
            fout << m_identTimeseries.m_objectID << " incident radiation [kW/m2],";
//            fout << m_identTimeseries.m_objectID << " cell temperature [C],";
            fout << m_identTimeseries.m_objectID << " power output [kW],";
        }
    }
    /**
    * Output a single timestep in the time series data.
    * @param fout - file stream for output data. 
    * @param time - timestep to select from time series data.  
    */
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
//        fout << m_slope.at(time) << ",";
//        fout << m_azimuth.at(time) << ",";
        fout << m_incidentRadiation.at(time) << ",";
//        fout << m_cellTemperature.at(time) << ",";
        fout << m_powerOutput.at(time) << ",";
    }
    inline void OutputSimpleTimeseriesHeaderToFile( std::ofstream &fout ) {
        fout << "Solar PV,";
    }
    inline void OutputSimpleTimeseriesDataToFile( std::ofstream &fout, unsigned int time ) {
        fout << m_powerOutput.at(time) << ",";
    }
    
    inline double GetCurrentPowerOutput() { return(*m_iterPowerOutput); }
    
protected:
    std::vector<double> m_slope;                /**< Slope of the panel during computation [radians] and output to user [degrees]. Varies only if panel is tracking. */
    std::vector<double> m_azimuth;              /**< Azimuth of the panelduring computation [radians] and output to user [degrees]. Varies only if panel is tracking. */
    std::vector<double> m_incidentAngle;        /**< Angle of incidence [degrees]. */
    std::vector<double> m_incidentRadiation;    /**< Incident radiation on the solar panel [W/m2]. */
    std::vector<double> m_cellTemperature;      /**< Temperature of the solar cell [C]. Used in computations when the user wants to consider the effect of temperature on solar PV power output. */
    std::vector<double> m_powerOutput;          /**< Temperature of the solar cell [C]. Used in computations when the user wants to consider the effect of temperature on solar PV power output. */
    
    std::vector<double>::iterator m_iterSlope;
    std::vector<double>::iterator m_iterAzimuth;
    std::vector<double>::iterator m_iterIncidentAngle;
    std::vector<double>::iterator m_iterIncidentRadiation;
    std::vector<double>::iterator m_iterCellTemperature;
    std::vector<double>::iterator m_iterPowerOutput;
};

struct SolarPVSummary : public ESDEObjectSummary {
    /**
    * @struct SolarPVSummary [SolarPVData.h]
    * @brief Solar PV summary  data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o output summary data to file
     o possibly move CalculateSummaryData into this struct
     */

public:
    /**
     * Default constructor
     */
    SolarPVSummary() {
        m_identSummary.m_objectType = ID_SOLAR_PV;
        m_totalCapacity = 0;
        m_totalProduction = 0;
        m_capacityFactor = 0;
        m_operatingTime = 0;
        m_meanPowerOutput = 0;
        m_meanDailyEnergy = 0;
        m_maxPowerOutput = 0;
        m_minPowerOutput = 0;
        m_penetration = 0;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarPVSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_SOLAR_PV;
        m_identSummary.m_objectID = objectID;
        m_totalCapacity = 0;
        m_totalProduction = 0;
        m_capacityFactor = 0;
        m_operatingTime = 0;
        m_meanPowerOutput = 0;
        m_meanDailyEnergy = 0;
        m_maxPowerOutput = 0;
        m_minPowerOutput = 0;
        m_penetration = 0;
    }
    
    inline void SetSummary( SolarPVSummary &data ) {
        m_identSummary = data.m_identSummary;
        m_totalCapacity = data.m_totalCapacity;
        m_totalProduction = data.m_totalProduction;
        m_capacityFactor = data.m_capacityFactor;
        m_operatingTime = data.m_operatingTime;
        m_meanPowerOutput = data.m_meanPowerOutput;
        m_meanDailyEnergy = data.m_meanDailyEnergy;
        m_maxPowerOutput = data.m_maxPowerOutput;
        m_minPowerOutput = data.m_minPowerOutput;
        m_penetration = data.m_penetration;
    }
    /**
     * Copy summary data onto blank struct. 
     * @return copy of summary data. 
     */
    inline SolarPVSummary* GetSummaryData() {
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
        fout << "Object type: " << "Solar PV" << "\n";
        fout << "Object ID: " << m_identSummary.m_objectID << "\n\n";
        
        fout << "Total capacity [kW]: " << m_totalCapacity << "\n";
        fout << "Max power output [kW]: " << m_maxPowerOutput << "\n";
        fout << "Min power output [kW]: " << m_minPowerOutput << "\n";
        fout << "Avg power output [kW]: " << m_meanPowerOutput << "\n";
        fout << "Total production [kWh/period]: " << m_totalProduction << "\n";
        fout << "Avg daily production [kWh/day]: " << m_meanDailyEnergy << "\n";
        fout << "Capacity factor [%]: " << m_capacityFactor << "\n";
        fout << "Operating time [timesteps/period]: " << m_operatingTime << "\n";
        fout << "PV penetration [%]: " << m_penetration << "\n";
        
        fout.close();
    }
    
    inline void CalculatePenetration( double load ) {
        if( load > 0 )
            m_penetration = m_meanPowerOutput / load * 100.0;
    }

protected:
    double m_totalCapacity;         /**< Total capacity of the solar array [kW]. */
    double m_totalProduction;       /**< Total solar PV power production over the simulated period [kWh/period]. */
    double m_capacityFactor;        /**< Capacity factor of the solar array [%]. */
    unsigned int m_operatingTime;   /**< Number of timesteps in wihch the solar array has positive power output [timesteps/period]. */
    double m_meanPowerOutput;       /**< Mean power output of the solar array [kW]. */
    double m_meanDailyEnergy;       /**< Mean daily energy from the solar array [kWh/day]. */
    double m_maxPowerOutput;        /**< Maximum power output of the solar array [kW]. */
    double m_minPowerOutput;        /**< Minimum power output of the solar array [kW]. */
    double m_penetration;           /**< Average solar output divided by average load served [%]. */
};

struct SolarPVData : public SolarPVInput, public SolarPVTimeseries, public SolarPVSummary {
    /**
    * @struct SolarPVData [SolarPVData.h]
    * @brief Groups input, time series, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    SolarPVData() {;}
};

#endif	/* SOLARPVDATA_H */

