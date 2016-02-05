/**
 * @file SolarResourceData.h
 * @brief Solar resource input and output data. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef SOLARRESOURCEDATA_H
#define	SOLARRESOURCEDATA_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObjectData.h"
#include "ESDEConstants.h"
#include "ESDELocation.h"
//#include "ESDELoader.h"

struct SolarResourceInput : public ESDEObjectInput {
    /**
    * @struct SolarResourceInput [SolarResourceData.h]
    * @brief Solar resource input data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o data inputs to calculate synthetic solar
     x location data
     */
    
public:
    /**
     * Default constructor. 
     */
    SolarResourceInput() {
        m_identInput.m_objectType = ID_SOLAR_RESOURCE;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarResourceInput( unsigned int objectID ) {
        m_identInput.m_objectType = ID_SOLAR_RESOURCE;
        m_identInput.m_objectID = objectID;
    }
    /**
     * Destructor.
     */
    ~SolarResourceInput() {
        ;
    }
    /**
     * Sets input data.
     * @param data - input data.
     */
    inline void SetInput( SolarResourceInput &data ) {
        m_identInput = data.m_identInput;
        m_Location = data.m_Location;
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline SolarResourceInput* GetInputData() {
        // TODO
        return(this);
    }
    
public: // accessors
    /** 
     * Sets location.
     * @param Location - location of the energy system.
     */
    inline void SetLocation( ESDELocation &Location ) {m_Location = Location;}
    /** 
     * Gets location.
     * @return location of the energy system.
     */
    inline ESDELocation & GetLocation() { return(m_Location); }
    
protected:
    ESDELocation m_Location;   /**< Location data. */
};

struct SolarResourceTimeseries : public ESDEObjectTimeseries {
    /**
    * @struct SolarResourceTimeseries [SolarResourceData.h]
    * @brief Solar resource timestep data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    /**
     * Default constructor. 
     */
    SolarResourceTimeseries() {
        m_identTimeseries.m_objectType = ID_SOLAR_RESOURCE;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarResourceTimeseries( unsigned int objectID ) {
        m_identTimeseries.m_objectType = ID_SOLAR_RESOURCE;
        m_identTimeseries.m_objectID = objectID;
    }
    /**
    * Initialize the time series data and setup iterators. 
    * @param numTimesteps - number of timesteps in the simulation. 
    */
    inline void InitTimeseries( unsigned int numTimesteps ) {
        // initialize vectors only if empty
        if( m_globalHorizontalRadiation.size() == 0 ) {
            std::vector<double> tmp( numTimesteps, 0.0);
            m_globalHorizontalRadiation = tmp;
            m_globalDirectRadiation = tmp;
            m_globalDiffuseRadiation = tmp;
            m_clearnessIndex = tmp;
            m_altitude = tmp;
            m_azimuth = tmp;
            m_zenithAngle = tmp;
            m_solarDeclination = tmp;
            m_hourAngle = tmp;
            m_extHorizontalRadiation = tmp;
        }
        
        // setup iterator
        SetupTimeseriesIterators();
    }
    /**
     * Copy time series data onto blank struct. 
     * @return copy of time series data. 
     */
    inline SolarResourceTimeseries* GetTimeseriesData() {
        return(this);
    }
    /**
    * Setup iterators for time series data.
    */
    inline void SetupTimeseriesIterators() {
        m_iterGlobalHorizontal = m_globalHorizontalRadiation.begin();
        m_iterGlobalDirect = m_globalDirectRadiation.begin();
        m_iterGlobalDiffuse = m_globalDiffuseRadiation.begin();
        m_iterClearnessIndex = m_clearnessIndex.begin();
        m_iterAltitude = m_altitude.begin();
        m_iterAzimuth = m_azimuth.begin();
        m_iterZenith = m_zenithAngle.begin();
        m_iterDeclination = m_solarDeclination.begin();
        m_iterHourAngle = m_hourAngle.begin();
        m_iterExtHorizontal = m_extHorizontalRadiation.begin();
    }
    /**
    * Updates the iterators to the next timestep. 
    */
    inline void IncrementTimeseriesIterators() {
        ++m_iterGlobalHorizontal;
        ++m_iterGlobalDirect;
        ++m_iterGlobalDiffuse;
        ++m_iterClearnessIndex;
        ++m_iterAltitude;
        ++m_iterAzimuth;
        ++m_iterZenith;
        ++m_iterDeclination;
        ++m_iterHourAngle;
        ++m_iterExtHorizontal;
    }
    /**
    * Clear time series data. 
    */
    inline void ClearTimeseries() {
        m_globalHorizontalRadiation.clear();
        m_globalDirectRadiation.clear();
        m_globalDiffuseRadiation.clear();
        m_clearnessIndex.clear();
        m_altitude.clear();
        m_azimuth.clear();
        m_zenithAngle.clear();
        m_solarDeclination.clear();
        m_hourAngle.clear();
        m_extHorizontalRadiation.clear();
    }
    /**
     * Sets time series data.
     * @param data - time series data.
     */
    inline void SetTimeseries( SolarResourceTimeseries &data ) {
        m_identTimeseries = data.m_identTimeseries;
        m_globalHorizontalRadiation = data.m_globalHorizontalRadiation;
        m_globalDirectRadiation = data.m_globalDirectRadiation;
        m_globalDiffuseRadiation = data.m_globalDiffuseRadiation;
        m_clearnessIndex = data.m_clearnessIndex;
        m_altitude = data.m_altitude;
        m_azimuth = data.m_azimuth;
        m_zenithAngle = data.m_zenithAngle;
        m_solarDeclination = data.m_solarDeclination;
        m_hourAngle = data.m_hourAngle;
        m_extHorizontalRadiation = data.m_extHorizontalRadiation;
        
        SetupTimeseriesIterators();
    }
    /**
    * Output the header in the time series data file. 
    * @param fout - file stream for output data. 
    */
    inline void OutputTimeseriesHeaderToFile( std::ofstream &fout ) {
        // use name if available, or object ID if not
        if( m_identTimeseries.m_objectName.size() > 0 ) {
            fout << m_identTimeseries.m_objectName.c_str() << " global horizontal radiation [W/m2],";
            fout << m_identTimeseries.m_objectName.c_str() << " direct horizontal radiation [W/m2],";
            fout << m_identTimeseries.m_objectName.c_str() << " diffuse horizontal radiation [W/m2],";
            fout << m_identTimeseries.m_objectName.c_str() << " clearness index [-],";
        }
        else {
            fout << m_identTimeseries.m_objectID << " global horizontal radiation [W/m2],";
            fout << m_identTimeseries.m_objectID << " direct horizontal radiation [W/m2],";
            fout << m_identTimeseries.m_objectID << " diffuse horizontal radiation [W/m2],";
            fout << m_identTimeseries.m_objectID << " clearness index [-],";
        }
        
        // don't output other values, unless needed
    }
    /**
    * Output a single timestep in the time series data.
    * @param fout - file stream for output data. 
    * @param time - timestep to select from time series data.  
    */
    inline void OutputTimeseriesDataToFile( std::ofstream &fout, unsigned int iTime ) const {
        fout << m_globalHorizontalRadiation.at(iTime) << ",";
        fout << m_globalDirectRadiation.at(iTime) << ",";
        fout << m_globalDiffuseRadiation.at(iTime) << ",";
        fout << m_clearnessIndex.at(iTime) << ",";
        
        // don't output other values, unless needed
    }
        
public: // accessors

    inline void SetGlobalHorizontalRadiation( std::vector<double> &rad ) {m_globalHorizontalRadiation = rad; m_iterGlobalHorizontal = m_globalHorizontalRadiation.begin(); }
    inline std::vector<double> & GetGlobalHorizontalRadiation() { return(m_globalHorizontalRadiation); }
    inline void SetExteraterrestrialHorizontalRadiation( std::vector<double> &rad ) {m_extHorizontalRadiation = rad; m_iterExtHorizontal = m_extHorizontalRadiation.begin(); }
    inline std::vector<double> & GetExteraterrestrialHorizontalRadiation() { return(m_extHorizontalRadiation); }
    
    inline double GetCurrentGlobalHorizontalRadiation() { return(*m_iterGlobalHorizontal); }
    inline double GetCurrentGlobalDirectRadiation() { return(*m_iterGlobalDirect); }
    inline double GetCurrentGlobalDiffuseRadiation() { return(*m_iterGlobalDiffuse); }
    inline double GetCurrentClearnessIndex() { return(*m_iterClearnessIndex); }
    
    inline double GetCurrentAltitude() { return(*m_iterAltitude); }
    inline double GetCurrentZenithAngle() { return(*m_iterZenith); }
    inline double GetCurrentDeclination() { return(*m_iterDeclination); }
    inline double GetCurrentHourAngle() { return(*m_iterHourAngle); }
    inline double GetCurrentExteraterrestrialHorizontalRadiation() { return(*m_iterExtHorizontal); }
    
protected:
    // values output to file
    std::vector<double> m_globalHorizontalRadiation;        /**< Total global horizontal radiation (GHI) [W/m2]. */
    std::vector<double> m_globalDirectRadiation;            /**< Direct (or beam) component of the global horizontal radiation [W/m2]. */
    std::vector<double> m_globalDiffuseRadiation;           /**< Diffuse component of the global horizontal radiation [W/m2]. */
    std::vector<double> m_clearnessIndex;                   /**< Clearness index [-]. */
    
    std::vector<double>::iterator m_iterGlobalHorizontal;
    std::vector<double>::iterator m_iterGlobalDirect;
    std::vector<double>::iterator m_iterGlobalDiffuse;
    std::vector<double>::iterator m_iterClearnessIndex;
    
    // internal values, can output to file if needed
    std::vector<double> m_altitude;                         /**< Solar altitude [radians] and output to user [degrees]. */
    std::vector<double> m_azimuth;                          /**< Solar azimuth [radians] and output to user [degrees]. */
    std::vector<double> m_zenithAngle;                      /**< Solar zenith angle [radians] and output to user [degrees]. */
    std::vector<double> m_solarDeclination;                 /**< Solar declination [radians] and output to user [degrees]. */
    std::vector<double> m_hourAngle;                        /**< Solar hour angle [radians] and output to user [degrees]. */
    std::vector<double> m_extHorizontalRadiation;           /**< Total extraterrestrial horizontal radiation [W/m2]. */
 
    std::vector<double>::iterator m_iterAltitude;
    std::vector<double>::iterator m_iterAzimuth;
    std::vector<double>::iterator m_iterZenith;
    std::vector<double>::iterator m_iterDeclination;
    std::vector<double>::iterator m_iterHourAngle;
    std::vector<double>::iterator m_iterExtHorizontal;
};

struct SolarResourceSummary : public ESDEObjectSummary {
    /**
    * @struct SolarResourceSummary [SolarResourceData.h]
    * @brief Solar resource summary  data. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
    /* TODO
     o specify summary data (e.g., average daily irradiance per month)
     o finish get accessor
     */
public:
    /**
     * Default constructor
     */
    SolarResourceSummary() {
        m_identSummary.m_objectType = ID_SOLAR_RESOURCE;
        
        std::vector<double> tmp( 12, 0.0);
        m_averageGlobalHorizontal = tmp;
        m_averageGlobalDirect = tmp;
        m_averageClearness = tmp;
    }
    /**
     * Constructor.
     * @param objectID - object ID.
     */
    SolarResourceSummary( unsigned int objectID ) {
        m_identSummary.m_objectType = ID_SOLAR_RESOURCE;
        m_identSummary.m_objectID = objectID;
        
        std::vector<double> tmp( 12, 0.0);
        m_averageGlobalHorizontal = tmp;
        m_averageGlobalDirect = tmp;
        m_averageClearness = tmp;
    }
    /**
     * Gets copy of summary data. 
     * @return copy of summary data. 
     */
    inline SolarResourceSummary* GetSummaryData() {
//        SolarResourceSummary myCopy;
//        myCopy.m_identSummary = m_identSummary;
//        ;
        return(this);
    }
    /**
     * Sets summary data.
     * @param data - summary data.
     */
    inline void SetSummary( SolarResourceSummary &data ) {
        m_identSummary = data.m_identSummary;
        // TODO
    }
    /**
    * Output summary data to file
    * @param path - path for summary data 
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
        fout << "Object type: " << "Solar Resource" << "\n";
        fout << "Object ID: " << m_identSummary.m_objectID << "\n\n";
        
        fout << "Month\tGHI\tDNI\tClearness\n";
        fout << "----\t----\t----\t----\n";
        
        for( unsigned int iMonth=0; iMonth<12; ++iMonth ) {
            fout << iMonth << "\t";
            fout << m_averageGlobalHorizontal.at(iMonth) << "\t";
            fout << m_averageGlobalDirect.at(iMonth) << "\t";
            fout << m_averageClearness.at(iMonth) << "\n";
        }
        
        fout.close();
    }
    
    inline std::vector<double> & GetAverageGlobalHorizontal() { return(m_averageGlobalHorizontal); }
    inline std::vector<double> & GetAverageGlobalDirect() { return(m_averageGlobalDirect); }
    inline std::vector<double> & GetAverageClearness() { return(m_averageGlobalDirect); }
    
protected:
    std::vector<double> m_averageGlobalHorizontal;   /**< Daily global horizontal average by month [kWh/m2/day] */
    std::vector<double> m_averageGlobalDirect;       /**< Daily global direct average by month [kWh/m2/day] */
    std::vector<double> m_averageClearness;          /**< Daily clearness index average by month [-] */
};

struct SolarResourceData : public SolarResourceInput, public SolarResourceTimeseries, public SolarResourceSummary {
    /**
    * @struct SolarResourceData [SolarResourceData.h]
    * @brief Groups input, time series, and summary data into single object. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    SolarResourceData() {;}
};

#endif	/* SOLARRESOURCEDATA_H */

