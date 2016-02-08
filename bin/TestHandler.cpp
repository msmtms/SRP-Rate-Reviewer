/**
 * @file TestHandler.cpp
 * @brief Test class for unit and integration testing.   
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <fstream>
#include <iostream>
#include <vector>
#include <iterator>
#include <map>
#include <ctime>

#include "TestHandler.h"
#include "ESDELocation.h"
#include "SolarResource.h"
#include "ESDELoader.h"
#include "SolarField.h"
#include "EnergySystem.h"
#include "Converter.h"
#include "ElectricVehicle.h"

/**
 * Run test. 
 * @param testID - id of test to run
 * @return TRUE if succeed
 */
bool TestHandler::RunTest( unsigned int testID ) {
    
    bool success = false; 
    
    switch( testID ) {
        
        // custom
        case TEST_CUSTOM :
            success = TestCustom();
            break;
            
        // loads
        case TEST_IMMEDIATE_LOAD :
            success = TestImmediateLoad();
            break;
            
        // components
        case TEST_SOLAR_PV :
            success = TestSolarPV();
            break;
            
        case TEST_BATTERY :
            success = TestBattery();
            break;
            
        case TEST_ELECTRIC_VEHICLE :
            success = TestElectricVehicle();
            break;
          
        case TEST_SOLAR_FIELD :
            success = TestSolarField();
            break;
            
        case TEST_POWER_TOWER :
            success = TestPowerTower();
            break;
            
        case TEST_CONVERTER :
            success = TestConverter();
            break;
            
        // resources
        case TEST_SOLAR_RESOURCE :
            success = TestSolarResource();
            break;    

        // data model
            
        // system computations
        case TEST_ENERGY_SYSTEM :
            success = TestEnergySystem();
            break;    

    }

    return(success);
}
/**
 * Custom test
 * @return TRUE if succeed
 */
bool TestHandler::TestCustom() {
    // TODO
    return(true);
}
/**
 * Unit test of immediate load functionality. 
 * @return TRUE if succeed
 */
bool TestHandler::TestImmediateLoad() {
    
    ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput( std::string("./data/input/Load.txt") );
    
    // calculate load statistics
    ImmediateLoad *immediateLoad = new ImmediateLoad();
    immediateLoad->Init(); // need to initialize time series
    immediateLoad->SetData( loadData );
    
    // read-in and output summary data
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( loadData.GetLoadRequested().size() );
    energySystem.AddImmediateLoad( immediateLoad );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}
/**
 * Unit test of solar resource functionality. 
 * @return TRUE if succeed
 */
bool TestHandler::TestSolarResource() {
    /* TODO
     o calculate synthetic solar data
     o read in timestep (global horizontal)
        o calculate clearness index from GHI (need to know time of year, etc.)
     */
    
    SolarResourceData data = ESDELoader::LoadSolarResourceInput( std::string("./data/input/Location.txt"), std::string("./data/input/SolarResource.txt") );
        
    // read data and set for solar resource
    SolarResource *solarResource = new SolarResource();  
    solarResource->SetData( data );
    
    // simulate (what is missing)
    solarResource->SimulateSun();
    
    // output data to file
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( data.GetGlobalHorizontalRadiation().size() );
    energySystem.AddSolarResource( solarResource );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}
/**
 * Unit test of solar PV functionality. 
 * @return TRUE if succeed
 */
bool TestHandler::TestSolarPV() {
    // read data and set for solar resource
    SolarResourceData dataSolar = ESDELoader::LoadSolarResourceInput( std::string("./data/input/Location.txt"), std::string("./data/input/SolarResource.txt") );
    unsigned int numTimesteps = dataSolar.GetGlobalHorizontalRadiation().size();
    
    SolarResource *solarResource = new SolarResource();
    solarResource->SetData( dataSolar );
    
    // simulate (what is missing)
    solarResource->SimulateSun();
    
    // read data and set for solar field
    SolarPVData dataPV = ESDELoader::LoadSolarPVInput( std::string("./data/input/SolarPV.txt") );
    
    // simulate solar PV
    SolarPV *solarPV = new SolarPV();
    solarPV->SetData( dataPV );
    solarPV->Init(); // need to initialize time series
    solarPV->SetSolarResource( solarResource );
    
    // simulate solar field
    for( unsigned int iTime=0; iTime<numTimesteps; ++iTime ) {
        solarPV->Simulate();

        solarResource->UpdateToNextTimestep();
        solarPV->UpdateToNextTimestep();
    }
    
    // read-in and output summary data
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( numTimesteps );
    energySystem.AddSolarResource( solarResource );
    energySystem.AddSolarPV( solarPV );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}
/**
 * Unit test of solar field functionality. 
 * @return TRUE if succeed
 */
bool TestHandler::TestSolarField() {
    
    /* TODO
     o read-in all solar data from file
     */
    
    // read data and set for solar resource
    SolarResourceData dataSolar = ESDELoader::LoadSolarResourceInput( std::string("./data/input/Location.txt"), std::string("./data/input/SolarResource.txt") );
    unsigned int numTimesteps = dataSolar.GetGlobalHorizontalRadiation().size();
    
    SolarResource *solarResource = new SolarResource();
    solarResource->SetData( dataSolar );
    
    // simulate (what is missing)
    solarResource->SimulateSun();
    
    // read data and set for solar field
    SolarFieldData dataField = ESDELoader::LoadSolarFieldInput( std::string("./data/input/SolarField.txt") );
    
    SolarField *solarField = new SolarField();
    solarField->SetData( dataField );
    solarField->Init(); // need to initialize time series
    solarField->SetSolarResource( solarResource );
    
    // simulate solar field
    for( unsigned int iTime=0; iTime<numTimesteps; ++iTime ) {
        solarField->Simulate();
        
        solarResource->UpdateToNextTimestep();
        solarField->UpdateToNextTimestep();
    }
    
    // read-in and output summary data
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( numTimesteps );
    energySystem.AddSolarResource( solarResource );
    energySystem.AddSolarField( solarField );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();

    return(true);
}
/**
 * Unit test of power tower functionality. 
 * @return TRUE if succeed
 */
bool TestHandler::TestPowerTower() {
    // TODO
    return(true);
}
/**
 * Unit test of converter functionality. 
 * @return TRUE if succeed
 */
bool TestHandler::TestConverter() {
    
    // read data
    ConverterData dataConverter = ESDELoader::LoadConverterInput( std::string("./data/input/Inverter.txt") );
    
    Converter *converter = new Converter();
    converter->SetData( dataConverter );
    converter->Init();
    
    // solar resource
    SolarResourceData dataSolar = ESDELoader::LoadSolarResourceInput( std::string("./data/input/Location.txt"), std::string("./data/input/SolarResource.txt") );
    unsigned int numTimesteps = dataSolar.GetGlobalHorizontalRadiation().size();
    
    SolarResource *solarResource = new SolarResource();
    solarResource->SetData( dataSolar );
    
    // simulate (what is missing)
    solarResource->SimulateSun();
    
    // solar PV
    SolarPVData dataPV = ESDELoader::LoadSolarPVInput( std::string("./data/input/SolarPV.txt") );
    
    SolarPV *solarPV = new SolarPV();
    solarPV->SetData( dataPV );
    solarPV->Init(); // need to initialize time series
    solarPV->SetSolarResource( solarResource );
    
    // simulate 
    double tmp = 0;
    for( unsigned int iTime=0; iTime<numTimesteps; ++iTime ) {
        
        solarPV->Simulate();
        tmp = converter->CalculateOutput( solarPV->GetCurrentPowerOutput() );

        solarResource->UpdateToNextTimestep();
        solarPV->UpdateToNextTimestep();
        converter->UpdateToNextTimestep();
    }
    
    // read-in and output summary data
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( numTimesteps );
    energySystem.AddSolarResource( solarResource );
    energySystem.AddSolarPV( solarPV );
    energySystem.AddConverter( converter );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}          

bool TestHandler::TestBattery() {
    
    // read data
    BatteryData dataBattery = ESDELoader::LoadBatteryInput( std::string("./data/input/Battery.txt") );
    unsigned int numTimesteps = 8760;
    
    Battery *battery = new Battery();
    battery->SetData( dataBattery );
    battery->Init();
    
    // simulate 
    double load = 0.1;
    for( unsigned int iTime=0; iTime<numTimesteps; ++iTime ) {
        
        if( iTime < 100 ) {
            battery->Power( load );
        }
        else {
            battery->Power( -0.1 * load );
        }
        battery->UpdateToNextTimestep();
    }
    
    // read-in and output summary data
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( numTimesteps );
    energySystem.AddBattery( battery );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}     
bool TestHandler::TestElectricVehicle() {
    
    // read data
    ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput( std::string("./data/input/ElectricVehicle.txt") );
    unsigned int numTimesteps = 8760;
 
    ElectricVehicle *electricVehicle = new ElectricVehicle();
    electricVehicle->SetData( dataElectricVehicle );
    electricVehicle->Init();
    electricVehicle->CreateChargerLoadProfile(); // this creates the load profile for the entire year

    // don't need simulate, CreateChargerLoadProfile handles this
    
    // read-in and output summary data
    EnergySystem energySystem;
    energySystem.SetNumTimesteps( numTimesteps );
    energySystem.AddElectricVehicle( electricVehicle );
    energySystem.OutputTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}   
bool TestHandler::TestEnergySystem() {
    
    unsigned int numTimesteps = 8760;
    EnergySystem energySystem;
    
    // Step 1: add solar resource to system from main data directory
    SolarResourceData dataSolarResource = ESDELoader::LoadSolarResourceInput( std::string("./data/Location.txt"), std::string("./data/SolarResource.txt") );
    energySystem.AddSolarResource( dataSolarResource );
    
    // Step 2: add objects to energy system
    std::string systemName = "Strata 1";
    energySystem.SetInputDirectory( std::string("./data/Ratepayers/") + systemName + std::string("/input/"));
    energySystem.SetOutputDirectory( std::string("./data/Ratepayers/") + systemName + std::string("/output/"));
    
    ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput( std::string("./data/Ratepayers/") + systemName + std::string("/input/Load.txt") );
    energySystem.AddImmediateLoad( loadData );

    SolarPVData dataSolarPV = ESDELoader::LoadSolarPVInput( std::string("./data/Ratepayers/") + systemName + std::string("/input/SolarPV.txt") );
    energySystem.AddSolarPV( dataSolarPV );
    
    ConverterData dataConverter = ESDELoader::LoadConverterInput( std::string("./data/Ratepayers/") + systemName + std::string("input/Inverter.txt") );
    energySystem.AddConverter( dataConverter );
    
    BatteryData dataBattery = ESDELoader::LoadBatteryInput( std::string("./data/Ratepayers/") + systemName + std::string("input/Battery.txt") );
    energySystem.AddBattery( dataBattery );
    
    ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput( std::string("./data/Ratepayers/") + systemName + std::string("input/ElectricVehicle.txt") );
    energySystem.AddElectricVehicle( dataElectricVehicle );
    
    // Step 3: init energy system and all objects
    energySystem.Init(numTimesteps);
    
    // Step 4: run simulation
    energySystem.RunSimulation();

    // Step 5: output data
    energySystem.OutputTimeseriesDataToFile();
    energySystem.OutputSimpleTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}  
bool TestHandler::TestEnergySystem( std::string systemName ) {
    
    unsigned int numTimesteps = 8760;
    EnergySystem energySystem;
    
    // Step 1: add solar resource to system from main data directory
    SolarResourceData dataSolarResource = ESDELoader::LoadSolarResourceInput( std::string("./data/Location.txt"), std::string("./data/SolarResource.txt") );
    energySystem.AddSolarResource( dataSolarResource );
    
    // Step 2: add objects to energy system
    energySystem.SetInputDirectory( std::string("./data/Ratepayers/") + systemName + std::string("/input/"));
    energySystem.SetOutputDirectory( std::string("./data/Ratepayers/") + systemName + std::string("/output/"));
    
    ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput( std::string("./data/Ratepayers/") + systemName + std::string("/input/Load.txt") );
    energySystem.AddImmediateLoad( loadData );

    SolarPVData dataSolarPV = ESDELoader::LoadSolarPVInput( std::string("./data/Ratepayers/") + systemName + std::string("/input/SolarPV.txt") );
    energySystem.AddSolarPV( dataSolarPV );
    
    ConverterData dataConverter = ESDELoader::LoadConverterInput( std::string("./data/Ratepayers/") + systemName + std::string("input/Inverter.txt") );
    energySystem.AddConverter( dataConverter );
    
    BatteryData dataBattery = ESDELoader::LoadBatteryInput( std::string("./data/Ratepayers/") + systemName + std::string("input/Battery.txt") );
    energySystem.AddBattery( dataBattery );
    
    ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput( std::string("./data/Ratepayers/") + systemName + std::string("input/ElectricVehicle.txt") );
    energySystem.AddElectricVehicle( dataElectricVehicle );
    
    // Step 3: init energy system and all objects
    energySystem.Init(numTimesteps);
    
    // Step 4: run simulation
    energySystem.RunSimulation();

    // Step 5: output data
    energySystem.OutputTimeseriesDataToFile();
    energySystem.OutputSimpleTimeseriesDataToFile();
    energySystem.CalculateSummaryData();
    energySystem.OutputSummaryDataToFile();
    
    return(true);
}  