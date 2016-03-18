/**
 * @file Battery.cpp
 * @brief Battery class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "Battery.h"
#include "BatteryData.h"
#include "ESDEObject.h"
#include "ESDEMath.h"

/** 
 * Default constructor. 
 */
Battery::Battery() {
    SetObjectType( ID_BATTERY );
}
Battery::Battery(unsigned int id) {
    SetObjectType( ID_BATTERY );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
Battery::Battery(const Battery &orig) {
    SetObjectType( ID_BATTERY );
    Battery(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
Battery::Battery(const Battery &orig, bool all) {
    SetObjectType( ID_BATTERY );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
Battery::~Battery() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void Battery::Init(unsigned int numTimesteps) {
    m_numTimesteps = numTimesteps;
    m_timestepHourlyFraction = 8760 / numTimesteps;
    InitTimeseries(m_numTimesteps);
    
    ComputeBatteryParameters();
    
    // initialize battery values
    (*m_iterCapacityAsEnergy) = m_maxCapacityAsEnergy;
    (*m_iterCapacityAsFraction) = 1.0;
    m_currentCapacityAsEnergy = (*m_iterCapacityAsEnergy);
}
/**
 * Prepares data and functions for the next timestep. 
 */
void Battery::UpdateToNextTimestep() {
    m_currentCapacityAsEnergy = (*m_iterCapacityAsEnergy);
    IncrementTimeseriesIterators();
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void Battery::SetData( BatteryData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<BatteryInput &>(data) );
    SetTimeseries( static_cast<BatteryTimeseries &>(data) );
    SetSummary( static_cast<BatterySummary &>(data) );
}

double Battery::GetMaximumChargePower() {
    double maxChargePower, maxChargeEnergy;
    
    // check if max power is bound by available room in battery
    maxChargeEnergy = m_maxCapacityAsEnergy - m_currentCapacityAsEnergy;
    
    maxChargePower = maxChargeEnergy * ( 1.0 / m_timestepHourlyFraction );  /// account for timestep size in converting to power
    maxChargePower /= m_chargeEfficiency; // account for charge efficiency
    
    // check if power is bound by CRate limit
    maxChargePower = maxChargePower < m_maxRatedChargePower ? maxChargePower : m_maxRatedChargePower; 

    return(maxChargePower);
}
double Battery::GetMaximumDischargePower() {
    double maxDischargePower, maxDishargeEnergy;
    
    // check if max power is bound by available room in battery
    maxDishargeEnergy = m_currentCapacityAsEnergy - m_minCapacityAsEnergy;
    
    maxDischargePower = maxDishargeEnergy * ( 1.0 / m_timestepHourlyFraction );  // account for timestep size in converting to power
    maxDischargePower *= m_dischargeEfficiency; // account for charge efficiency
         
    // check if power is bound by CRate limit
    maxDischargePower = maxDischargePower < m_maxRatedDischargePower ? maxDischargePower : m_maxRatedDischargePower; 
    
    return(maxDischargePower);
}
void Battery::Power( double power ) { 
    // power has positive convention as output power
    
    (*m_iterMaxChargePower) =  GetMaximumChargePower();
    (*m_iterMaxDischargePower) = GetMaximumDischargePower();
    
    // double-check to see if power is too high
    if( power > 0 ) { // discharging
        (*m_iterDischargePower) = (*m_iterMaxDischargePower) < power ? (*m_iterMaxDischargePower) : power;
        (*m_iterOutputPower) = (*m_iterDischargePower);
        (*m_iterCapacityAsEnergy) = m_currentCapacityAsEnergy - (*m_iterDischargePower) / m_dischargeEfficiency;
    }
    else if( power < 0 ) { // charging
        (*m_iterChargePower) = (*m_iterMaxChargePower) < (-1.0 * power) ? (*m_iterMaxChargePower) : (-1.0 * power);
        (*m_iterOutputPower) = -1.0 * (*m_iterChargePower);
        (*m_iterCapacityAsEnergy) = m_currentCapacityAsEnergy + (*m_iterChargePower) * m_chargeEfficiency;
    }
    else { // no load applied
        (*m_iterChargePower) = 0;
        (*m_iterDischargePower) = 0;
        (*m_iterOutputPower) = 0;
        (*m_iterCapacityAsEnergy) = m_currentCapacityAsEnergy;
    }
    
    (*m_iterCapacityAsFraction) = (*m_iterCapacityAsEnergy) / m_maxCapacityAsEnergy;
}
/** 
 * Calculate summary data from timestep data. 
 */   
void Battery::CalculateSummaryData() {
    SummaryStatistics chargePower = CalculateSummaryStatistics( m_chargePower, true );
    SummaryStatistics dischargePower = CalculateSummaryStatistics( m_dischargePower, true );
    m_totalCapacity = this->m_nominalCapacity;
    
    m_totalEnergyIn = chargePower.total * m_timestepHourlyFraction;
    m_totalEnergyOut = dischargePower.total * m_timestepHourlyFraction;
    m_totalLosses = (m_totalEnergyIn * (1 - m_chargeEfficiency)) + (m_totalEnergyOut/m_dischargeEfficiency * (1 - m_dischargeEfficiency));
}
