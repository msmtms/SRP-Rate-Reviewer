/* 
 * File:   Converter.h
 * Author: Nate
 *
 * Created on November 2, 2015, 1:30 AM
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObject.h"
#include "ConverterData.h"
#include "Converter.h"
#include "ESDEMath.h"


/** 
 * Default constructor. 
 */
Converter::Converter() {
    SetObjectType( ID_CONVERTER );
}
Converter::Converter(unsigned int id) {
    SetObjectType( ID_CONVERTER );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
Converter::Converter(const Converter &orig) {
    SetObjectType( ID_CONVERTER );
    Converter(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
Converter::Converter(const Converter &orig, bool all) {
    SetObjectType( ID_CONVERTER );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
Converter::~Converter() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void Converter::Init(unsigned int numTimesteps) {
    m_numTimesteps = numTimesteps;
    m_timestepHourlyFraction = 8760 / numTimesteps;
    InitTimeseries(m_numTimesteps);
}
/**
 * Prepares data and functions for the next timestep. 
 */
void Converter::UpdateToNextTimestep() {
    IncrementTimeseriesIterators();
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void Converter::SetData( ConverterData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<ConverterInput &>(data) );
    SetTimeseries( static_cast<ConverterTimeseries &>(data) );
    SetSummary( static_cast<ConverterSummary &>(data) );
}

double Converter::CalculateOutput( double input ) {
    double output = CalculateOutputPossible(input);
    
//    // cannot output more power than rated capacity
//    if( CalculateOutputPossible( input ) > m_capacity ) {
//        output = m_capacity;
//    }
//    else {
//        output = CalculateOutputPossible( input );
//    }
    (*m_iterPowerInput1To2) = input;
    (*m_iterPowerOutput1To2) = output;
    return(output);
}
double Converter::CalculateOutputPossible( double input ) {
    double output = 0;
    double outputNoLimit = input * m_efficiency;
    
    // cannot output more power than rated capacity
    if( outputNoLimit > m_capacity ) {
        output = m_capacity;
    }
    else {
        output = outputNoLimit;
    }
    
    return(output);
}
 
/** 
 * Calculate summary data from timestep data. 
 */   
void Converter::CalculateSummaryData() {
    SummaryStatistics powerInput1To2 = CalculateSummaryStatistics( m_powerInput1To2, true );
    SummaryStatistics powerOutput1To2 = CalculateSummaryStatistics( m_powerOutput1To2, true );
    m_totalCapacity = this->m_capacity;
    m_totalInput1To2 = powerInput1To2.total * m_timestepHourlyFraction;
    m_totalOutput1To2 = powerOutput1To2.total * m_timestepHourlyFraction;
    m_totalLosses = m_totalInput1To2 - m_totalOutput1To2;
    m_operatingTime = powerInput1To2.operatingTime;
    m_capacityFactor = powerInput1To2.mean / m_capacity * 100.0;
}