/**
 * @file SolarResource.h
 * @brief Solar resource class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>
#include <cassert>
#include <math.h>

#include "ESDEObject.h"
#include "ESDEObjectData.h"
#include "SolarResourceData.h"
#include "SolarResource.h"
#include "ESDEMath.h"
#include "SolarFunctions.h"

/** 
 * Default constructor. 
 */
SolarResource::SolarResource() {
    SetObjectType( ID_SOLAR_RESOURCE );
}
SolarResource::SolarResource(unsigned int id) {
    SetObjectType( ID_SOLAR_RESOURCE );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
SolarResource::SolarResource(const SolarResource &orig) {
    SetObjectType( ID_SOLAR_RESOURCE );
    SolarResource(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
SolarResource::SolarResource(const SolarResource &orig, bool all) {
    SetObjectType( ID_SOLAR_RESOURCE );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
SolarResource::~SolarResource() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void SolarResource::Init(unsigned int numTimesteps) {
    m_numTimesteps = numTimesteps;
    m_timestepHourlyFraction = 8760 / numTimesteps;
    InitTimeseries(m_numTimesteps);
}
/**
 * Prepares data and functions for the next timestep. 
 */
void SolarResource::UpdateToNextTimestep() {
    IncrementTimeseriesIterators();
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void SolarResource::SetData( SolarResourceData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<SolarResourceInput &>(data) );
    SetTimeseries( static_cast<SolarResourceTimeseries &>(data) );
    SetSummary( static_cast<SolarResourceSummary &>(data) );
}
/**
 * Sets identification information for the object and members. 
 * @param ident - identification information. 
 */
void SolarResource::SetIdentification( ESDEIdentification &ident ) { 
    m_identObject = ident; 
    m_identInput = ident;
    m_identTimeseries = ident;
    m_identSummary = ident;
}
/**
 * Simulates the solar resource. 
 */
void SolarResource::Simulate() {
    // TODO: add functionality to indicate if we need to simulate GHI data
    
    SimulateGlobalHorizontalRadiation();
    SimulateSun();
}
/**
 * Simulates global horizontal radiation (GHI). This is called if there is insufficient
 * GHI data for the time steps simulated. The function simulates (generates) synthetic
 * data for missing values in the dataset. 
 */
void SolarResource::SimulateGlobalHorizontalRadiation() {
    // TODO: add functionality to identify how much data need be simulated, and how it be simulated
    // TODO: add functionality for various algorithms in the solar simulator
    
    // TODO: simulate synthetic global radiation -- synthetic calculations -- using standard algorithms
}
/**
 * Simulates the sun with extraterrestrial and global components. 
 */
void SolarResource::SimulateSun() {
    m_timestepHourlyFraction = m_globalHorizontalRadiation.size() / double(m_numTimesteps);
    
    double centerOfTimeStepInMinutesFromNewYears;
    double dayOfYear;
    unsigned int minutesPerDay = 1440; // 24 * 60
    int timeStepInMinutes = m_timestepHourlyFraction * 60.0f;
    double hourOfYear, hourOfDay, localTime, solarTime;
    double hourAngle, sunsetHourAngle, solarDeclination; 
    
    // radiation 
    double G_ext, G_glob, diffuseRatio;
    
    double latitude = m_Location.GetLatitudeAsDecimal();
    latitude = ConvertDegreesToRadians( latitude );

    for( int iTimestep=0; iTimestep<m_globalHorizontalRadiation.size(); ++iTimestep )  {
        
        // calculate time 
        centerOfTimeStepInMinutesFromNewYears = double(iTimestep * timeStepInMinutes) + double(timeStepInMinutes)/2.0; 
        dayOfYear = centerOfTimeStepInMinutesFromNewYears / ((double)minutesPerDay) ;
        hourOfYear = centerOfTimeStepInMinutesFromNewYears / 60.0;
        hourOfDay = hourOfYear - double(24*int(hourOfYear/24.0));
        localTime = hourOfDay;
        solarTime = CalculateSolarTime( localTime, m_Location.GetTimezone(), m_Location.GetLongitudeAsDecimal(), dayOfYear );
        
        // calculate solar angles
        hourAngle = CalculateHourAngle( solarTime );
        m_hourAngle.at(iTimestep) = hourAngle; 
        solarDeclination = CalculateSolarDeclination( dayOfYear );
        m_solarDeclination.at(iTimestep) = solarDeclination;
        
        sunsetHourAngle = CalculateSunsetHourAngle( latitude, solarDeclination );

        // grab the global horizontal radiation
        G_glob  = m_globalHorizontalRadiation.at(iTimestep);
        
        static int count = 0;
        
        // check if the sun is up
        if( G_glob > 0 ) { // sun is up
            
            m_zenithAngle.at( iTimestep ) = CalculateZenithAngle( latitude, solarDeclination, hourAngle );
            m_altitude.at( iTimestep ) = CalculateSolarAltitude( m_zenithAngle.at( iTimestep ) );
            m_azimuth.at( iTimestep ) = CalculateSolarAzimuth( latitude, solarDeclination, m_altitude.at( iTimestep ), hourAngle );

            // calculate extraterrestrial horizontal radiation
            G_ext = CalculateExtraterrestrialHorizontalRadiationOverTimestep( 
                    latitude, dayOfYear, solarTime, sunsetHourAngle, solarDeclination, m_timestepHourlyFraction );
            
            m_extHorizontalRadiation.at( iTimestep ) = G_ext;
            
            // occasionally measured GHI at very low GHI occurs when simulated extraterrestrial data is zero or smaller
            if ( G_glob > G_ext ) { 
                G_glob = 0.8 * G_ext; // fudge global horizontal data
                m_clearnessIndex.at(iTimestep) = 0.8; // set ficticious clearness index so don't get "nan"
            }
            else {
                m_clearnessIndex.at(iTimestep) = G_glob / G_ext; 
            }
            
            diffuseRatio = CalculateDiffuseRatio_Erbs( m_clearnessIndex.at(iTimestep) );
            m_globalDiffuseRadiation.at(iTimestep) = G_glob * diffuseRatio;
            m_globalDirectRadiation.at(iTimestep) = G_glob - m_globalDiffuseRadiation.at(iTimestep);
        }
        else { // no sun 
            m_extHorizontalRadiation.at( iTimestep ) = 0;
            m_globalDiffuseRadiation.at(iTimestep) = 0;
            m_globalDirectRadiation.at(iTimestep) = 0; 
            m_zenithAngle.at( iTimestep ) = PI/2.0;
            m_altitude.at( iTimestep ) = 0.0;
            if( hourOfDay > 12.0 && hourOfDay < 24.0 )
                m_azimuth.at( iTimestep ) = PI;
            else
                m_azimuth.at( iTimestep ) = -PI;
        }
    }
}
/** 
 * Calculate summary data from timestep data. 
 */   
void SolarResource::CalculateSummaryData() {
    unsigned int iMonth, iHour, iSun;
    
    // calculate monthly details
    for( iMonth=0; iMonth<12; ++iMonth ) {
        m_averageGlobalHorizontal.at(iMonth) = 0;
        m_averageGlobalDirect.at(iMonth) = 0;
        m_averageClearness.at(iMonth) = 0;
        iSun = 0;
        
        for( iHour=GetFirstHourOfMonth(iMonth); iHour<=GetLastHourOfMonth(iMonth); ++iHour ) {
            m_averageGlobalHorizontal.at(iMonth) += m_globalHorizontalRadiation.at(iHour) / double(GetDaysInMonth(iMonth)) / 1000.0;
            m_averageGlobalDirect.at(iMonth) += m_globalDirectRadiation.at(iHour) / double(GetDaysInMonth(iMonth)) / 1000.0;
            if( m_clearnessIndex.at(iHour) > 0.0 ) { // just count if sun is up
                m_averageClearness.at(iMonth) += m_clearnessIndex.at(iHour);
                ++iSun;
            }
        }
        m_averageClearness.at(iMonth) /= double(iSun);
    }  
}
/**
 * Calculates clearness index from global horizontal and extraterrestrial horizontal.
 */
void SolarResource::CalculateClearnessIndex() {
    for( unsigned int i=0; i<m_numTimesteps; ++i ) {
        if( m_extHorizontalRadiation.at(i) > 0 ) {
            m_clearnessIndex.at(i) = m_globalHorizontalRadiation.at(i) / m_extHorizontalRadiation.at(i);
        }
        else { // sun is down
            m_clearnessIndex.at(i) = 0.0;
        }
    }
}
/**
 * Calculates direct and diffuse components to global horizontal radiation. 
 */
void SolarResource::CalculateSolarComponents() {
    double diffuseRatio;
    for( unsigned int i=0; i<m_numTimesteps; ++i ) {
        diffuseRatio = CalculateDiffuseRatio_Erbs( m_clearnessIndex.at(i) ); // default to Erbs calculation
        m_globalDiffuseRadiation.at(i) = m_globalHorizontalRadiation.at(i) * diffuseRatio;
        m_globalDirectRadiation.at(i) = m_globalHorizontalRadiation.at(i) - m_globalDiffuseRadiation.at(i);
    }
}

/** HERE and below */

