

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ElectricVehicle.h"
#include "ElectricVehicleData.h"
#include "ESDEObject.h"
#include "ESDEMath.h"

/** 
 * Default constructor. 
 */
ElectricVehicle::ElectricVehicle() {
    SetObjectType( ID_ELECTRIC_VEHICLE );
}
ElectricVehicle::ElectricVehicle(unsigned int id) {
    SetObjectType( ID_ELECTRIC_VEHICLE );
    SetObjectID( id );
}
/** 
 * Copy constructor. TODO. 
 * @param orig class data to be copied. 
 */
ElectricVehicle::ElectricVehicle(const ElectricVehicle &orig) {
    SetObjectType( ID_ELECTRIC_VEHICLE );
    ElectricVehicle(orig, false); // only copy properties data
}
/**
 * Copy constructor. TODO. 
 * @param orig - class data to be copied. 
 * @param all - if TRUE then copy all data, if FALSE then copy only properties data. 
 */
ElectricVehicle::ElectricVehicle(const ElectricVehicle &orig, bool all) {
    SetObjectType( ID_ELECTRIC_VEHICLE );
    // TODO: create a new object ID and object name (append copy to end of name)
    // TODO: use "all" variable to determine what to copy... perhaps overload equivalence operator
}
/** 
 * Deconstructor. 
 */
ElectricVehicle::~ElectricVehicle() {
    ClearTimeseries();
}
/** 
 * Initialization. 
 */ 
void ElectricVehicle::Init(unsigned int numTimesteps) {
    m_numTimesteps = numTimesteps;
    m_timestepHourlyFraction = 8760 / numTimesteps;
    InitTimeseries(m_numTimesteps);
    
    // initialize electric vehicle to zero (when it is not at the house)
//    (*m_iterCapacityAsEnergy) = 0;
//    (*m_iterCapacityAsFraction) = 0;
    m_currentCapacityAsEnergy = m_maxCapacityAsEnergy;
    
    // set charger power
    if( m_chargerType == LEVEL_1 ) {
        m_chargerPowerRated = chargePowerLevel1;
    }
    else { // m_chargerType == LEVEL_2 
        m_chargerPowerRated = chargePowerLevel2;
    }
}
/**
 * Prepares data and functions for the next timestep. 
 */
void ElectricVehicle::UpdateToNextTimestep() {
    m_currentCapacityAsEnergy = (*m_iterCapacityAsEnergy);
    IncrementTimeseriesIterators();
}
/**
 * Sets input, time series, and summary data for the object.
 * @param data - all input and output data. 
 */
void ElectricVehicle::SetData( ElectricVehicleData &data ) {
    m_identObject = data.m_identInput; // sets identification information from the input object
    SetInput( static_cast<ElectricVehicleInput &>(data) );
    SetTimeseries( static_cast<ElectricVehicleTimeseries &>(data) );
    SetSummary( static_cast<ElectricVehicleSummary &>(data) );
}

double ElectricVehicle::GetMaximumChargePower() {
    double maxChargePower, maxChargeEnergy;
    
    // check if max power is bound by available room in battery
    maxChargeEnergy = m_maxCapacityAsEnergy - m_currentCapacityAsEnergy;
    
    maxChargePower = maxChargeEnergy * ( 1.0 / m_timestepHourlyFraction );  /// account for timestep size in converting to power
    maxChargePower /= m_chargeEfficiency; // account for charge efficiency
    
    // check if power is bound by charger limit
    maxChargePower = maxChargePower < m_chargerPowerRated ? maxChargePower : m_chargerPowerRated; 

    return(maxChargePower);
}
void ElectricVehicle::CreateChargerLoadProfile() {
    // this is a load requirement, not deferrable
    
    if( (m_startTime == m_endTime) || (m_startingSOC == m_endingSOC) ) { // no charging, zero load profile
        return;
    }
    
    double chargePower, maxChargePower, goalChargePower;
    unsigned int chargeTime = CalculateChargeHours();
    unsigned int startTime = (unsigned int)(m_startTime); // when to start charging as integer
    unsigned int endTime = (unsigned int)(m_endTime); // when to end charging as integer
    
    // goal charge power depends on charging strategy
    if( m_chargingStrategy == AVG_CHARGE ) {
        goalChargePower = (m_endingSOC - m_startingSOC) * m_maxCapacityAsEnergy / ((double)(chargeTime)); // kW
        goalChargePower /= m_chargeEfficiency; // account for efficiency
    }
    else { // MAX_CHARGE
        goalChargePower = m_chargerPowerRated;
    }
    
    static int count = 0;
    for( unsigned int iDay=0; iDay<365; ++iDay ) {
        for( unsigned int iHour=0; iHour<24; ++iHour ) {
            chargePower = 0.0;
            maxChargePower = GetMaximumChargePower();

            // charging ends before midnight
            if(startTime <= endTime) {
                if( iHour < startTime ) { // vehicle away from home
                    chargePower = 0.0;
                    m_currentCapacityAsEnergy = 0.0;
                }
                else if( (iHour >= startTime) && (iHour < endTime) ) { // vehicle at home and start charging
                    if( iHour == startTime ) {
                        m_currentCapacityAsEnergy = m_startingSOC * m_maxCapacityAsEnergy;
                        
                        // set the energy at the end of the last timestep as starting SOC
                        if( (iDay*24 + iHour) > 0 ) {
                            m_capacityAsEnergy.at( iDay*24 + iHour - 1) = m_startingSOC * m_maxCapacityAsEnergy;
                            m_capacityAsFraction.at( iDay*24 + iHour -1 ) = m_capacityAsEnergy.at( iDay*24 + iHour - 1) / m_maxCapacityAsEnergy;
                        }
                    }
                    chargePower = maxChargePower < goalChargePower ? maxChargePower : goalChargePower;
                }
                else { // iHour > endTime, vehicle away from home
                    chargePower = 0.0;
                    m_currentCapacityAsEnergy = 0.0;
                }
            } 
            else { // charging crosses midnight
                if( iHour < endTime ) { // vehicle at home and charging
                    chargePower = maxChargePower < goalChargePower ? maxChargePower : goalChargePower;
                }
                else if( (iHour >= endTime) && (iHour < startTime) ) { // vehicle away from home
                    chargePower = 0.0;
                    m_currentCapacityAsEnergy = 0.0;
                }
                else { // iHour > startTime, vehicle at home and start charging
                    if( iHour == startTime ) {
                        m_currentCapacityAsEnergy = m_startingSOC * m_maxCapacityAsEnergy;
                        
                        // set the energy at the end of the last timestep as starting SOC
                        if( (iDay*24 + iHour) > 0 ) { 
                            m_capacityAsEnergy.at( iDay*24 + iHour - 1 ) = m_startingSOC * m_maxCapacityAsEnergy; 
                            m_capacityAsFraction.at( iDay*24 + iHour -1 ) = m_capacityAsEnergy.at( iDay*24 + iHour - 1) / m_maxCapacityAsEnergy;
                        }
                    }
                    chargePower = maxChargePower < goalChargePower ? maxChargePower : goalChargePower;
                }
            }

            // set charge power and update energy levels
            m_chargePower.at( iDay*24 + iHour ) = chargePower;
            m_capacityAsEnergy.at( iDay*24 + iHour ) = m_currentCapacityAsEnergy + chargePower * m_chargeEfficiency;
            m_capacityAsFraction.at( iDay*24 + iHour ) = m_capacityAsEnergy.at( iDay*24 + iHour ) / m_maxCapacityAsEnergy;

            // save for next hour
            m_currentCapacityAsEnergy = m_capacityAsEnergy.at( iDay*24 + iHour );
            
            ++count;
        }
    }
        

//    if( m_chargingStrategy == AVG_CHARGE ) {
//        // average power consumption
//        double avgChargePower = (m_endingCapacity - m_startingCapacity) / ((double)(chargeTime)); // kW
//
//        // apply to load
//        for( unsigned int iDay=0; iDay<365; ++iDay ) {
//            for( unsigned int iHour=0; iHour<24; ++iHour ) {
//                chargePower = 0.0;
//                maxChargePower = GetMaximumChargePower();
//                
//                // charging ends before midnight
//                if(startTime <= endTime) {
//                    if( iHour < startTime ) { // vehicle away from home
//                        chargePower = 0.0;
//                    }
//                    else if( (iHour >= startTime) && (iHour < endTime) ) { // vehicle at  home and charging
//                        chargePower = maxChargePower < avgChargePower ? maxChargePower : avgChargePower;
//                    }
//                    else { // iHour > endTime, vehicle away from home
//                        chargePower = 0.0;
//                    }
//                } 
//                else { // charging crosses midnight
//                    if( iHour < endTime ) { // vehicle at home and charging
//                        chargePower = maxChargePower < avgChargePower ? maxChargePower : avgChargePower;
//                    }
//                    else if( (iHour >= endTime) && (iHour < startTime) ) { // vehicle away from home
//                        chargePower = 0.0;
//                    }
//                    else { // iHour > startTime, vehicle at home and charging
//                        chargePower = maxChargePower < avgChargePower ? maxChargePower : avgChargePower;
//                    }
//                }
//
//                // set charge power and update energy levels
//                m_chargePower.at( iDay*24 + iHour ) = chargePower;
//                m_capacityAsEnergy.at( iDay*24 + iHour ) = m_currentCapacityAsEnergy + chargePower * m_chargeEfficiency;
//                m_capacityAsFraction.at( iDay*24 + iHour ) = m_capacityAsEnergy.at( iDay*24 + iHour ) / m_maxCapacityAsEnergy;
//                
//                // save for next hour
//                m_currentCapacityAsEnergy = m_capacityAsEnergy.at( iDay*24 + iHour );
//            }
//        }
//    }
//    else { // m_chargingStrategy == MAX_CHARGE 
//        // charge fast as possible, within window
//
//        for( unsigned int iDay=0; iDay<365; ++iDay ) {
//            for( unsigned int iHour=0; iHour<24; ++iHour ) {
//                chargePower = 0.0;
//                maxChargePower = GetMaximumChargePower();
//                
//                // charging ends before midnight
//                if(startTime <= endTime) {
//                    if( iHour < startTime ) { // vehicle away from home
//                        chargePower = 0.0;
//                    }
//                    else if( (iHour >= startTime) && (iHour < endTime) ) { // vehicle at  home and charging
//                        chargePower = maxChargePower < m_chargerPowerRated ? maxChargePower : m_chargerPowerRated;
//                    }
//                    else { // iHour > endTime, vehicle away from home
//                        chargePower = 0.0;
//                    }
//                } 
//                else { // charging crosses midnight
//                    if( iHour < endTime ) { // vehicle at home and charging
//                        chargePower = maxChargePower < m_chargerPowerRated ? maxChargePower : m_chargerPowerRated;
//                    }
//                    else if( (iHour >= endTime) && (iHour < startTime) ) { // vehicle away from home
//                        chargePower = 0.0;
//                    }
//                    else { // iHour > startTime, vehicle at home and charging
//                        chargePower = maxChargePower < m_chargerPowerRated ? maxChargePower : m_chargerPowerRated;
//                    }
//                }
//
//                // set charge power and update energy levels
//                m_chargePower.at( iDay*24 + iHour ) = chargePower;
//                m_capacityAsEnergy.at( iDay*24 + iHour ) = m_currentCapacityAsEnergy + chargePower * m_chargeEfficiency;
//                m_capacityAsFraction.at( iDay*24 + iHour ) = m_capacityAsEnergy.at( iDay*24 + iHour ) / m_maxCapacityAsEnergy;
//                
//                // save for next hour
//                m_currentCapacityAsEnergy = m_capacityAsEnergy.at( iDay*24 + iHour );
//            }
//        }
//    }
}
unsigned int ElectricVehicle::CalculateChargeHours() {
    if( m_startTime < m_endTime ) {
        return( (unsigned int)(m_endTime - m_startTime) );
    }
    else { // charge over midnight hour
        return( (unsigned int)(24 + m_endTime - m_startTime) );
    }
        
}
double ElectricVehicle::GetChargerLoad() {
    return(*m_iterChargePower);
}
/** 
 * Calculate summary data from timestep data. 
 */   
void ElectricVehicle::CalculateSummaryData() {
    SummaryStatistics chargePower = CalculateSummaryStatistics( m_chargePower, true );
    m_totalCapacity = this->m_maxCapacityAsEnergy;
    
    m_totalEnergyIn = chargePower.total * m_timestepHourlyFraction;
    m_meanDailyEnergyIn = m_totalEnergyIn / 365.0;
    m_totalLosses = (m_totalEnergyIn * (1 - m_chargeEfficiency));    
}
