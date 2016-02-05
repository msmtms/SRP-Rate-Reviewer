/**
 * @file ImmediateLoad.cpp
 * @brief Immediate load class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ImmediateLoadData.h"
#include "ImmediateLoad.h"
#include "ESDEMath.h"

/** 
 * Default constructor. 
 */
ImmediateLoad::ImmediateLoad() {
    SetObjectType( ID_IMMEDIATE_LOAD );
}
ImmediateLoad::ImmediateLoad(unsigned int id) {
    SetObjectType( ID_IMMEDIATE_LOAD );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
ImmediateLoad::ImmediateLoad(const ImmediateLoad &orig) {
    SetObjectType( ID_IMMEDIATE_LOAD );
    ImmediateLoad(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
ImmediateLoad::ImmediateLoad(const ImmediateLoad &orig, bool all) {
    SetObjectType( ID_IMMEDIATE_LOAD );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
ImmediateLoad::~ImmediateLoad() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void ImmediateLoad::Init() {
    InitTimeseries(m_numTimesteps);
}
/**
 * Prepares data and functions for the next timestep. 
 */
void ImmediateLoad::UpdateToNextTimestep() {
    IncrementTimeseriesIterators();
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void ImmediateLoad::SetData( ImmediateLoadData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<ImmediateLoadInput &>(data) );
    SetTimeseries( static_cast<ImmediateLoadTimeseries &>(data) );
    SetSummary( static_cast<ImmediateLoadSummary &>(data) );
}
/** 
 * Calculate summary data from timestep data. 
 */   
void ImmediateLoad::CalculateSummaryData() {
    SummaryStatistics loadServedSummary = CalculateSummaryStatistics( m_loadServed, true );
    m_maxLoadServed = loadServedSummary.max;
    m_minLoadServed = loadServedSummary.min;
    m_avgLoadServed = loadServedSummary.mean;
    m_totalLoadServedPeriod = loadServedSummary.total * m_timestepHourlyFraction;
    m_totalLoadServedDay = m_totalLoadServedPeriod / 365.0;
    m_loadFactor = m_avgLoadServed / m_maxLoadServed;   
}
