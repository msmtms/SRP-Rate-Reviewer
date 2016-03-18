/**
 * @file SolarField.cpp
 * @brief Sub-system of concentrating solar power system. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "SolarField.h"
#include "SolarFieldData.h"

/** 
 * Default constructor. 
 */
SolarField::SolarField() {
    SetObjectType( ID_SOLAR_FIELD );
}
SolarField::SolarField(unsigned int id) {
    SetObjectType( ID_SOLAR_FIELD );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
SolarField::SolarField(const SolarField &orig) {
    SetObjectType( ID_SOLAR_FIELD );
    SolarField(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
SolarField::SolarField(const SolarField &orig, bool all) {
    SetObjectType( ID_SOLAR_FIELD );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
SolarField::~SolarField() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void SolarField::Init(unsigned int numTimesteps) {
    m_numTimesteps = numTimesteps;
    m_timestepHourlyFraction = 8760 / numTimesteps;
    InitTimeseries(m_numTimesteps);
}
/**
 * Prepares data and functions for the next timestep. 
 */
void SolarField::UpdateToNextTimestep() {
    IncrementTimeseriesIterators();
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void SolarField::SetData( SolarFieldData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<SolarFieldInput &>(data) );
    SetTimeseries( static_cast<SolarFieldTimeseries &>(data) );
    SetSummary( static_cast<SolarFieldSummary &>(data) );
}
/**
 * Sets identification information for the object and members. 
 * @param ident - identification information. 
 */
void SolarField::SetIdentification( ESDEIdentification &ident ) { 
    m_identObject = ident; 
    m_identInput = ident;
    m_identTimeseries = ident;
    m_identSummary = ident;
}
/**
 * Runs simulation given input data.
 */
void SolarField::Simulate() {
    (*m_iterReceiverIncident) = m_numHeliostats * m_avgOpticalEfficiency 
            * m_solarResource->GetCurrentGlobalHorizontalRadiation() / 1000.0; // divide by 1000 for unit conversion W to kW
}
/** 
 * Calculate summary data from timestep data. 
 */   
void SolarField::CalculateSummaryData() {
    // TODO
}