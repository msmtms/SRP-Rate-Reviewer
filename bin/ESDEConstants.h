/**
 * @file ESDEConstants.h
 * @brief Constants used in an ESDE simulation.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ESDECONSTANTS_H
#define	ESDECONSTANTS_H

/* TODO
 o specify IDs for energy type
 o move model version from ESDEDataModel to here? 
 */

/* IDs of ESDE object types */

#define ID_ESDE_BLANK 0

// simulation-level data
#define ID_ESDE_DATA_MODEL 1
#define ID_ESDE_INPUT_DATA 2
#define ID_ESDE_OUTPUT_DATA 3
#define ID_ESDE_TIMESTEP_DATA 4
#define ID_ESDE_SUMMARY_DATA 5

// model-level input and output data
#define ID_ESDE_MODEL_SETTINGS 10

// simulation types
#define ID_ESDE_CALCULATE_SOLAR 100
#define ID_ESDE_CALCULATE_ENERGY_SYSTEMS 101

// system type
#define ID_ENERGY_SYSTEM 1000
#define ID_GRID 1001
#define ID_CONSUMER 1002

// loads
#define ID_IMMEDIATE_LOAD 2000

// generators
#define ID_SOLAR_PV 3000
#define ID_SOLAR_FIELD 3001
#define ID_POWER_TOWER 3002
#define ID_POWER_TOWER_THERMOCHEMICAL 3003

// storage
#define ID_STORAGE 4000
#define ID_BATTERY 4001
#define ID_ELECTRIC_VEHICLE 4002

// converters
#define ID_CONVERTER 5000

// resources
#define ID_SOLAR_RESOURCE 6000
#define ID_THERMAL_RESOURCE 6001

#endif	/* ESDECONSTANTS_H */

