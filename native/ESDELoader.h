/**
 * @file ESDELoader.h
 * @brief Loads data into ESDE objects.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ESDELOADER_H
#define	ESDELOADER_H

#include <iostream>
#include <fstream>
#include <sstream>

#include "SolarResourceData.h"
#include "ImmediateLoadData.h"
#include "SolarFieldData.h"
#include "SolarPVData.h"
#include "Converter.h"
#include "Battery.h"
#include "ElectricVehicle.h"

/******************************************************************************/
struct ESDELoader {
/**
* @struct ESDELoader [ESDELoader.h]
* @brief Loads data into ESDE objects.
* @author Nathan Johnson
* @date 2014-09-29
* @version 0.0.1 - initial release
*/

    /**
     * Loads data for solar resource using timeseries data. 
     * @param fnameLocation - filename of the location data.
     * @param fnameTimeseries - filename of the timeseries data.
     * @return solar resource data loaded from file.
     */
    static inline SolarResourceData LoadSolarResourceInput( std::string fnameLocation, std::string fnameTimeseries ) {
        SolarResourceData solarData;

        std::ifstream fin;
        std::string str;
        double val;
        // load location data
        fin.open( fnameLocation );
        ESDELocation location;

        fin >> str; fin >> val; location.SetLatitudeAsDecimal(val);
        fin >> str; fin >> val; location.SetLongitudeAsDecimal(val);
        fin >> str; fin >> val; location.SetTimezone(val);
        solarData.SetLocation( location );

        fin.close();

        // set timeseries data based on filename extention type
        // TODO: verify that data is in correct format (csv vs. tab-delimited)
        // TODO: create method to read-in CSV data (boost library with tokenizers)

        std::vector<double> G_global;

        fin.open( fnameTimeseries );
    //    fin >> str; fin >> str; // read over the headers

        while( !fin.eof() ) {
            fin >> val;
            G_global.push_back(val);
        }

        solarData.InitTimeseries( G_global.size() );
        solarData.SetGlobalHorizontalRadiation( G_global );

        // set identification
        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_SOLAR_RESOURCE;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("SolarResource");
        solarData.m_identInput = ident;
        solarData.m_identTimeseries = ident;
        solarData.m_identSummary = ident;

        return(solarData);
    }
    /**
     * Loads data for solar field using input data. 
     * @param fnameInput - filename of input data. 
     * @return solar field data loaded from file.
     */
    static inline SolarFieldData LoadSolarFieldInput( std::string fnameInput ) {
        SolarFieldData fieldData;

        std::ifstream fin;
        std::string str;
        unsigned int myInt;
        double val;
        // load location data
        fin.open( fnameInput );

        fin >> str; fin >> myInt; fieldData.SetNumHeliostats(myInt);
        fin >> str; fin >> val; fieldData.SetAvergaOpticalEfficiency(val);

        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_SOLAR_FIELD;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("SolarField");
        fieldData.m_identInput = ident;
        fieldData.m_identTimeseries = ident;
        fieldData.m_identSummary = ident;

        return(fieldData);
    }
    /**
     * Loads data for solarPV using input data. 
     * @param fnameInput - filename of input data. 
     * @return solarPV data loaded from file.
     */
    static inline SolarPVData LoadSolarPVInput( std::string fnameInput ) {
        SolarPVData solarPVData;

        std::ifstream fin;
        std::string str;
        unsigned int myInt;
        double val;
        // load location data
        fin.open( fnameInput );

        fin >> str; fin >> val; solarPVData.SetCapacity(val);
        fin >> str; fin >> val; solarPVData.SetSlope(val);
        fin >> str; fin >> val; solarPVData.SetAzimuth(val);

        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_SOLAR_PV;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("SolarPV");
        solarPVData.m_identInput = ident;
        solarPVData.m_identTimeseries = ident;
        solarPVData.m_identSummary = ident;

        return(solarPVData);
    }
    static inline ImmediateLoadData LoadImmediateLoadInput(std::string fnameInput) {
        ImmediateLoadData loadData;

        std::ifstream fin;
        double val;
        // load data
        fin.open( fnameInput );

        std::vector<double> load;

        while( !fin.eof() ) {
            fin >> val;
            load.push_back(val);
        }

        loadData.InitTimeseries( load.size() );
        loadData.SetLoadRequested( load );
        loadData.SetLoadServed( load );

        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_IMMEDIATE_LOAD;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("Load");
        loadData.m_identInput = ident;
        loadData.m_identTimeseries = ident;
        loadData.m_identSummary = ident;

        return(loadData);
    }
    
    static inline ConverterData LoadConverterInput( std::string fnameInput ) {
        ConverterData converterData;

        std::ifstream fin;
        std::string str;
        double val;
        // load  data
        fin.open( fnameInput );

        fin >> str; fin >> val; converterData.SetCapacity(val);
        fin >> str; fin >> val; converterData.SetEfficiencyAsPercentage(val);
        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_CONVERTER;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("Converter");
        converterData.m_identInput = ident;
        converterData.m_identTimeseries = ident;
        converterData.m_identSummary = ident;

        return(converterData);
    }
    
    static inline BatteryData LoadBatteryInput( std::string fnameInput ) {
        BatteryData batteryData;

        std::ifstream fin;
        std::string str;
        double val;
        // load  data
        fin.open( fnameInput );

        fin >> str; fin >> val; batteryData.SetNominalCapacity(val);
        fin >> str; fin >> val; batteryData.SetRoundtripEfficiency(val);
        fin >> str; fin >> val; batteryData.SetMinCapacityAsPercentage(val);
        fin >> str; fin >> val; batteryData.SetMaxCRate(val);
        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_BATTERY;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("Battery");
        batteryData.m_identInput = ident;
        batteryData.m_identTimeseries = ident;
        batteryData.m_identSummary = ident;

        return(batteryData);
    }
    static inline ElectricVehicleData LoadElectricVehicleInput( std::string fnameInput ) {
        ElectricVehicleData evData;

        std::ifstream fin;
        std::string str;
        double val;
        unsigned int myInt;
        // load  data
        fin.open( fnameInput );

        fin >> str; fin >> myInt; evData.SetChargerType(myInt);
        fin >> str; fin >> myInt; evData.SetChargingStrategy(myInt);
        
        fin >> str; fin >> val; evData.SetMaxCapacityAsEnergy(val);
        fin >> str; fin >> val; evData.SetChargeEfficiencyAsPercentage(val);
        fin >> str; fin >> val; evData.SetStartingSOCAsPercentage(val);
        fin >> str; fin >> val; evData.SetEndingSOCAsPercentage(val);
        
        fin >> str; fin >> val; evData.SetStartTime(val);
        fin >> str; fin >> val; evData.SetEndTime(val);
        
        fin.close();

        ESDEIdentification ident;
        ident.m_objectType = ID_ELECTRIC_VEHICLE;
        ident.m_objectID = 0; // not used here, used in full system computations
        ident.m_objectName = std::string("ElectricVehicle");
        evData.m_identInput = ident;
        evData.m_identTimeseries = ident;
        evData.m_identSummary = ident;

        return(evData);
    }
};

#endif	/* ESDELOADER_H */

