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
#include <sys/stat.h>
#include <unistd.h>
#include <stdlib.h>

#include "SolarResourceData.h"
#include "ImmediateLoadData.h"
#include "SolarFieldData.h"
#include "SolarPVData.h"
#include "Converter.h"
#include "Battery.h"
#include "ElectricVehicle.h"
#include "EnergySystem.h"
#include "Logger.h"
#include "RateSchedule.h"

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
        
        if( std::ifstream( fnameLocation.c_str() ).good() ) {
            fin.open( fnameLocation );
            ESDELocation location;

            fin >> str; fin >> val; location.SetLatitudeAsDecimal(val);
            fin >> str; fin >> val; location.SetLongitudeAsDecimal(val);
            fin >> str; fin >> val; location.SetTimezone(val);
            solarData.SetLocation( location );

            fin.close();
        }

        // set timeseries data based on filename extention type
        // TODO: verify that data is in correct format (csv vs. tab-delimited)
        // TODO: create method to read-in CSV data (boost library with tokenizers)

        std::vector<double> G_global;

        if( std::ifstream( fnameTimeseries.c_str() ).good() ) {
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
        }
        
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
        ident.m_objectName = std::string("Total load");
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
    static inline EnergySystem LoadEnergySystem( std::string ratepayerDir, std::string ratepayerName, SolarResourceData &solarData ) {
        EnergySystem systemData;
        
        // set name, input directory,  output directory
        systemData.SetName( ratepayerName );
        std::string inputDir = ratepayerDir + ratepayerName + "/input/";
        std::string outputDir = ratepayerDir + ratepayerName + "/output/";
        systemData.SetInputDirectory( inputDir );
        systemData.SetOutputDirectory( outputDir );
        
        Logger::Instance()->writeToLogFile(std::string( "Reading in \"" ) + ratepayerName + std::string( "\" data from " ) + inputDir, Logger::PROCESS);
        

        std::string tempDir;
        
        tempDir = inputDir + std::string("rpg.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            
            std::ifstream fin;
            std::string str;
            double val;
            
            fin.open( tempDir );
            std::getline( fin, str );            
            str.erase( 0, 17 ); // discard name, that's already set above in this function
            std::getline( fin, str );
            str.erase( 0, 15 );
            systemData.SetRateScheduleName( str );
            std::getline( fin, str );
            str.erase( 0, 21 );
            val = atof( str.c_str() );
            systemData.SetNumSystems( val );
            fin.close();
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Energy system \"" ) + ratepayerName + std::string( "\" is missing \"rpg.txt\"" ), Logger::PROCESS);
        }
        
        
        tempDir = inputDir + std::string("Load.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput( tempDir );
            systemData.AddImmediateLoad( loadData );
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Energy system \"" ) + ratepayerName + std::string( "\" is missing \"Load.txt\"" ), Logger::PROCESS);
        }
        
        tempDir = inputDir + std::string("SolarPV.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            SolarPVData dataSolarPV = ESDELoader::LoadSolarPVInput( tempDir );
            systemData.AddSolarPV( dataSolarPV );
            systemData.AddSolarResource( solarData );
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Energy system \"" ) + ratepayerName + std::string( "\" is missing \"SolarPV.txt\"" ), Logger::PROCESS);
        }
        
        tempDir = inputDir + std::string("Inverter.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            ConverterData dataConverter = ESDELoader::LoadConverterInput( tempDir );
            systemData.AddConverter( dataConverter );
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Energy system \"" ) + ratepayerName + std::string( "\" is missing \"Inverter.txt\"" ), Logger::PROCESS);
        }
        
        tempDir = inputDir + std::string("Battery.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            BatteryData dataBattery = ESDELoader::LoadBatteryInput( tempDir );
            systemData.AddBattery( dataBattery );
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Energy system \"" ) + ratepayerName + std::string( "\" is missing \"Battery.txt\"" ), Logger::PROCESS);
        }
        
        tempDir = inputDir + std::string("ElectricVehicle.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput( tempDir );
            systemData.AddElectricVehicle( dataElectricVehicle );
        }
        else {
            Logger::Instance()->writeToLogFile(std::string( "Energy system \"" ) + ratepayerName + std::string( "\" is missing \"ElectricVehicle.txt\"" ), Logger::PROCESS);
        }
        
        return(systemData);
    }

    static inline RateSchedule LoadRateSchedule( std::string rateDir, std::string rateScheduleName ) {
        RateSchedule rateSchedule;
        rateSchedule.m_name = rateScheduleName;
        
        std::string rateScheduleDir = rateDir + rateScheduleName + std::string( "/" );
        
        Logger::Instance()->writeToLogFile(std::string( "Reading in \"" ) + rateScheduleName + std::string( "\" rate schedule data from " ) + rateDir, Logger::PROCESS);
        
        std::string tempDir;
        std::ifstream fin;
        std::string str;
        std::string tempStr;
        std::string tempStr2;
        size_t pos = 0;
        size_t pos2 = 0;
        double val;
        int myInt;
            
        // read in meter.txt
        tempDir = rateScheduleDir + std::string("meter.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
        
            std::string delimiter = "\t";
            
            fin.open( tempDir );
            
            // net metering
            std::getline( fin, str );   
            pos = str.find(delimiter);
            str.erase(0, pos + delimiter.length());
            rateSchedule.m_netMeteringID = atoi( str.c_str() );
            
            // over production credit
            std::getline( fin, str );   
            pos = str.find(delimiter);
            str.erase(0, pos + delimiter.length());
            rateSchedule.m_overproductionCredit = atof( str.c_str() );
            
            // interconnection charge
            std::getline( fin, str );   
            pos = str.find(delimiter);
            str.erase(0, pos + delimiter.length());
            rateSchedule.m_interconnectionCharge = atof( str.c_str() );
            
            fin.close();
        }
        
        // read in rates.txt
        tempDir = rateScheduleDir + std::string("rates.txt");
        if( std::ifstream(tempDir.c_str()).good() ) {
            
            std::string nameDelimiter = "\t";
            std::string chargeDelimiter = "%";
            std::string tierDelimiter = ";";
            std::string valueDelimiter = ",";
            
            fin.open( tempDir );
            
            pos = 0;
            std::string token;
            std::string tempToken;
            
            int id = 0;
            
            while( !fin.eof() ) { // until end of file
                Rate rate;
                
                std::getline( fin, str ); // read in a line
                
                // set name of rate
                pos = str.find(nameDelimiter);
                rate.m_name = str.substr(0, pos);  // grab name
                rate.m_id = id;
                str.erase(0, pos + nameDelimiter.length()); // just take what remains
                
                // set energy charge
                pos = str.find(chargeDelimiter);
                tempStr = str.substr(0, pos);   // grab energy charge
                str.erase(0, pos + chargeDelimiter.length()); // just take what remains
                               
                if( (pos = tempStr.find(tierDelimiter)) == std::string::npos ) { // not tiered
                    pos = tempStr.find(valueDelimiter);
                    rate.m_energyPrice.insert( std::make_pair( atof(tempStr.substr(0,pos).c_str()), atof(tempStr.substr(pos).c_str() )) );
                }
                else { // tiered
                    // loop through tiers
                    while ((pos = tempStr.find(tierDelimiter)) != std::string::npos) {
                        pos2 = tempStr.find(valueDelimiter);
                        rate.m_energyPrice.insert( std::make_pair( atof(tempStr.substr(0,pos2).c_str()), atof(tempStr.substr(pos2 + valueDelimiter.length(),pos-pos2).c_str() )) );
                        
                        tempStr.erase(0, pos + tierDelimiter.length());
                        if( tempStr.size() > 0 ) {
                            tempToken = tempStr;
                        }
                    }
                    // add info from last tier
                    tempStr = tempToken;
                    pos2 = tempStr.find(valueDelimiter);
                    rate.m_energyPrice.insert( std::make_pair( atof(tempStr.substr(0,pos2).c_str()), atof(tempStr.substr(pos2 + valueDelimiter.length()).c_str() )) );
                }
                
                // set feed-in tariff
                pos = str.find(chargeDelimiter);
                tempStr = str.substr(0, pos);   // grab feed-in tariff charge
                str.erase(0, pos + chargeDelimiter.length()); // just take what remains
                
                if( (pos = tempStr.find(tierDelimiter)) == std::string::npos ) { // not tiered
                    pos = tempStr.find(valueDelimiter);
                    rate.m_feedinTariff.insert( std::make_pair( atof(tempStr.substr(0,pos).c_str()), atof(tempStr.substr(pos).c_str() )) );
                }
                else { // tiered
                    // loop through tiers
                    while ((pos = tempStr.find(tierDelimiter)) != std::string::npos) {
                        pos2 = tempStr.find(valueDelimiter);
                        rate.m_feedinTariff.insert( std::make_pair( atof(tempStr.substr(0,pos2).c_str()), atof(tempStr.substr(pos2 + valueDelimiter.length(),pos-pos2).c_str() )) );
                        
                        tempStr.erase(0, pos + tierDelimiter.length());
                        if( tempStr.size() > 0 ) {
                            tempToken = tempStr;
                        }
                    }
                    // add info from last tier
                    tempStr = tempToken;
                    pos2 = tempStr.find(valueDelimiter);
                    rate.m_feedinTariff.insert( std::make_pair( atof(tempStr.substr(0,pos2).c_str()), atof(tempStr.substr(pos2 + valueDelimiter.length()).c_str() )) );
                }
                
                // set demand charge
                tempStr = str;   // last remaining section of the line is the demand charge
                if( (pos = tempStr.find(tierDelimiter)) == std::string::npos ) { // not tiered
                    pos = tempStr.find(valueDelimiter);
                    rate.m_demandCharge.insert( std::make_pair( atof(tempStr.substr(0,pos).c_str()), atof(tempStr.substr(pos).c_str() )) );
                }
                else { // tiered
                    // loop through tiers
                    while ((pos = tempStr.find(tierDelimiter)) != std::string::npos) {
                        pos2 = tempStr.find(valueDelimiter);
                        rate.m_demandCharge.insert( std::make_pair( atof(tempStr.substr(0,pos2).c_str()), atof(tempStr.substr(pos2 + valueDelimiter.length(),pos-pos2).c_str() )) );
                        
                        tempStr.erase(0, pos + tierDelimiter.length());
                        if( tempStr.size() > 0 ) {
                            tempToken = tempStr;
                        }
                    }
                    // add info from last tier
                    tempStr = tempToken;
                    pos2 = tempStr.find(valueDelimiter);
                    rate.m_demandCharge.insert( std::make_pair( atof(tempStr.substr(0,pos2).c_str()), atof(tempStr.substr(pos2 + valueDelimiter.length()).c_str() )) );
                }

                ++id; // increment to next rate ID
                
                rateSchedule.m_rates.insert( std::make_pair( id, rate) );

                
            }
            fin.close();
        }
        
        // TODO: read in monthly.txt    
        
        return(rateSchedule);
    }

};


#endif	/* ESDELOADER_H */

