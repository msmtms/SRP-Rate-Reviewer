/**
 * @file SolarPV.cpp
 * @brief Solar PV class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "SolarPVData.h"
#include "SolarPV.h"
#include "SolarFunctions.h"
#include "ESDEMath.h"
#include "SolarResourceData.h"
#include "SolarResource.h"

/** 
 * Default constructor. 
 */
SolarPV::SolarPV() {
    SetObjectType( ID_SOLAR_PV );
}
SolarPV::SolarPV(unsigned int id) {
    SetObjectType( ID_SOLAR_PV );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
SolarPV::SolarPV(const SolarPV &orig) {
    SetObjectType( ID_SOLAR_PV );
    SolarPV(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
SolarPV::SolarPV(const SolarPV &orig, bool all) {
    SetObjectType( ID_SOLAR_PV );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
SolarPV::~SolarPV() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void SolarPV::Init(unsigned int numTimesteps) {
    m_numTimesteps = numTimesteps;
    m_timestepHourlyFraction = 8760 / numTimesteps;
    InitTimeseries(m_numTimesteps);
    timestepInDays = 8760.0 / m_numTimesteps / 24.0;
    dayOfYear = timestepInDays / 2.0;
}
/**
 * Prepares data and functions for the next timestep. 
 */
void SolarPV::UpdateToNextTimestep() {
    IncrementTimeseriesIterators();
    dayOfYear += timestepInDays;
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void SolarPV::SetData( SolarPVData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<SolarPVInput &>(data) );
    SetTimeseries( static_cast<SolarPVTimeseries &>(data) );
    SetSummary( static_cast<SolarPVSummary &>(data) );
}
/**
 * Simulate. 
 */
void SolarPV::Simulate() {
    // assume fixed tilt for now
    (*m_iterSlope) = this->m_slopeInput;
    (*m_iterAzimuth) = this->m_azimuthInput;
    
    // calculate incident angle
    if( m_solarResource->GetTimeseriesData()->GetCurrentAltitude() > 0 ) { // sun is up
        (*m_iterIncidentAngle) = CalculateIncidentAngle( m_solarResource->GetLocation().GetLatitudeAsDecimal(), 
                                                        m_solarResource->GetCurrentDeclination(),
                                                        m_solarResource->GetCurrentHourAngle(),
                                                        (*m_iterSlope),
                                                        (*m_iterAzimuth) );  
    }
    else { // sun is down
        (*m_iterIncidentAngle) = 90.0;
    }

    // calculate incident radiation
    if( m_solarResource->GetCurrentGlobalHorizontalRadiation() > 0 ) { // sun is shining 
        
        double beamComponent, diffuseComponent, reflectiveComponent;

        double clearnessIndex = m_solarResource->GetCurrentClearnessIndex();
        double tiltRatio = CalculateTiltRatio( (*m_iterIncidentAngle), m_solarResource->GetCurrentZenithAngle() );
        double panelSlope = ConvertDegreesToRadians( (*m_iterSlope) );
        
        // beam component
        beamComponent = m_solarResource->GetCurrentGlobalDirectRadiation() * tiltRatio;
        
        // sometimes tilt ratio can be high and cause the above to skew results, so modify beam if that is the case
        double extraterrestrialNormalRadiation = CalculateExtraterrestrialNormalRadiation( dayOfYear );
        if( beamComponent > ( clearnessIndex * extraterrestrialNormalRadiation ) ) {
            beamComponent = ( clearnessIndex * extraterrestrialNormalRadiation );
        }
        
        // diffuse component
        double anisotropyIndex;
        if( m_solarResource->GetCurrentExteraterrestrialHorizontalRadiation() > 0 ) {
            anisotropyIndex = m_solarResource->GetCurrentGlobalDirectRadiation() / m_solarResource->GetCurrentExteraterrestrialHorizontalRadiation();
        }
        else
        {
            anisotropyIndex = .5; // a reasonable number just so that the computation runs
        }
        double horizontalBrighteningFactor = sqrt( m_solarResource->GetCurrentGlobalDirectRadiation() / m_solarResource->GetCurrentGlobalHorizontalRadiation() );
        
        diffuseComponent = m_solarResource->GetCurrentGlobalDiffuseRadiation() * anisotropyIndex * tiltRatio
                        + m_solarResource->GetCurrentGlobalDiffuseRadiation() * (1.0 - anisotropyIndex)
                        * ( ( 1.0 + cos( panelSlope ) ) / 2.0 ) * ( 1.0 + horizontalBrighteningFactor * pow( sin( panelSlope / 2.0 ), 3.0 ) );
        
        // reflective component
        reflectiveComponent = m_solarResource->GetCurrentGlobalHorizontalRadiation()
                            * m_groundReflectance * ( ( 1.0 - cos( panelSlope ) ) / 2.0 );
        
        (*m_iterIncidentRadiation) = beamComponent + diffuseComponent + reflectiveComponent;
    }
    else { // sun is not shining
        (*m_iterIncidentRadiation) = 0.0;
    }

    // TODO -- add temperature effects
    
    // calculate array power output
    (*m_iterPowerOutput) = (*m_iterIncidentRadiation) * m_capacity / 1000.0; // divide by 1000 because incident radiation in W/m2 and capacity in kW
}
/** 
 * Calculate summary data from timestep data. 
 */   
void SolarPV::CalculateSummaryData() {
    SummaryStatistics powerSummary = CalculateSummaryStatistics( m_powerOutput, true );
    m_totalCapacity = this->m_capacity;
    m_totalProduction = powerSummary.total * m_timestepHourlyFraction;
    m_meanPowerOutput = powerSummary.mean;
    m_meanDailyEnergy = m_meanPowerOutput * 24.0;
    m_maxPowerOutput = powerSummary.max;
    m_minPowerOutput = powerSummary.min;
    m_operatingTime = powerSummary.operatingTime;
    m_capacityFactor = m_meanPowerOutput / m_capacity * 100.0;
}