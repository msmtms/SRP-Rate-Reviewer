/**
 * @file EnergySystem.cpp
 * @brief Generic energy system in the ESDE model.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <vector>
#include <iterator>
#include <map>

#include "EnergySystem.h"
#include "ESDEConstants.h"
#include "ESDEObject.h"
#include "ESDEObjectData.h"
#include "ESDEModelSettingsData.h"
#include "ImmediateLoad.h"
#include "ImmediateLoadData.h"
#include "SolarPV.h"
#include "SolarResource.h"
#include "ESDEMath.h"


/**
 * Default constructor.
 */
EnergySystem::EnergySystem() {
    SetObjectType( ID_ENERGY_SYSTEM );
    m_currentObjectID = 0; // this will start IDs at 1, 0 means that it nothing has been assigned
    m_energyNetName = std::string( "Net load");
    
    // default directories (works for a single system)
    m_inputDir = std::string("./data/input/");
    m_outputDir = std::string("./data/output/");
    
    m_rateScheduleName = std::string( "" );
    m_numSystems = 1;
    
    m_totalCharges = 0;
    m_energyCharges = 0;
    m_demandCharges = 0;
    m_interconnectionCharges = 0;
    
    //m_ESDEModelSettingsInput->m_identInput.m_objectID = m_currentObjectID; this is messy
}
EnergySystem::EnergySystem( unsigned int id ) {
    SetObjectType( ID_ENERGY_SYSTEM );
    SetObjectID( id );
    m_currentObjectID = 1; // begin at 1, 0 means that it hasn't been assigned
    m_energyNetName = std::string( "Net load");
    
    // default directories (works for a single system)
    m_inputDir = std::string("./data/input/");
    m_outputDir = std::string("./data/output/");
    
    m_rateScheduleName = std::string( "" );
    m_numSystems = 1;
    
    m_totalCharges = 0;
    m_energyCharges = 0;
    m_demandCharges = 0;
    m_interconnectionCharges = 0;
    //m_ESDEModelSettingsInput->m_identInput.m_objectID = m_currentObjectID; this is messy
}
EnergySystem::EnergySystem( unsigned int id, std::string inputDir ) {
    SetObjectType( ID_ENERGY_SYSTEM );
    SetObjectID( id );
    m_currentObjectID = 1; // begin at 1, 0 means that it hasn't been assigned
    m_energyNetName = std::string( "Net load");
    
    // default directories (works for a single system)
    m_inputDir = inputDir;
    
    // setup output directory to use same file structure as input directory
    std::string s = inputDir;
    std::string delimiter = "/";

    size_t pos = 0;
    std::string token;
    std::string tempToken;
    while ((pos = s.find(delimiter)) != std::string::npos) {
        token = s.substr(0, pos);
        s.erase(0, pos + delimiter.length());
        if( s.size() > 0 ) {
            tempToken = s;
        }
    }
    s = tempToken; // just in case there is a "/" at end of input dir, which there should be

    m_outputDir = std::string("./data/");
    m_outputDir.append(inputDir);
    m_outputDir.append("/output/");
    
    m_rateScheduleName = std::string( "" );
    m_numSystems = 1;
    
    m_totalCharges = 0;
    m_energyCharges = 0;
    m_demandCharges = 0;
    m_interconnectionCharges = 0;
}

/**
 * Default destructor.
 */
EnergySystem::~EnergySystem() {
    ;
}

void EnergySystem::Init(unsigned int numTimesteps) {
    
    m_numTimesteps = numTimesteps;
    
    // init EnergySystem members
    if( m_energyNet.size() == 0 ) {
        std::vector<double> tmp( m_numTimesteps, 0.0);
        m_energyNet = tmp;
        m_allSystemsTotalLoad = tmp;
        m_allSystemsNetLoad = tmp;
    }
    m_iterEnergyNet = m_energyNet.begin();
    
    // init rest of system
    std::map< unsigned int, ImmediateLoad* >::iterator iterImmediateLoad;
    for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) {
        iterImmediateLoad->second->Init();
    }
    std::map< unsigned int, SolarResource* >::iterator iterSolar;
    for( iterSolar=m_solarResource.begin(); iterSolar!=m_solarResource.end(); ++iterSolar ) {
        iterSolar->second->Init();
    }
    std::map< unsigned int, SolarPV* >::iterator iterSolarPV;
    for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) {
        iterSolarPV->second->Init();
    }
    std::map< unsigned int, Converter* >::iterator iterConverter;
    for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) {
        iterConverter->second->Init();
    }
    std::map< unsigned int, Battery* >::iterator iterBattery;
    for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) {
        iterBattery->second->Init();
    }
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) {
        iterElectricVehicle->second->Init();
    }
    std::map< unsigned int, SolarField* >::iterator iterSolarField;
    for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) {
        iterSolarField->second->Init();
    }
    
    // assign solar resource to solar generators
    if( m_solarResource.size() > 0 ) {
        SolarResource *resource = m_solarResource.begin()->second;
        
        if( m_solarPV.size() > 0 ) {
            m_solarPV.begin()->second->SetSolarResource( resource );
        }
        if( m_solarField.size() > 0 ) {
            m_solarField.begin()->second->SetSolarResource( resource );
        }
    }
}

/**
 * Prepares data and functions for the next timestep. 
 */
void EnergySystem::UpdateToNextTimestep() {
    
    // update EnergySystem iterators
    ++m_iterEnergyNet;
    
    // update rest of system
    std::map< unsigned int, ImmediateLoad* >::iterator iterImmediateLoad;
    for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) {
        iterImmediateLoad->second->UpdateToNextTimestep();
    }
    std::map< unsigned int, SolarResource* >::iterator iterSolar;
    for( iterSolar=m_solarResource.begin(); iterSolar!=m_solarResource.end(); ++iterSolar ) {
        iterSolar->second->UpdateToNextTimestep();
    }
    std::map< unsigned int, SolarPV* >::iterator iterSolarPV;
    for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) {
        iterSolarPV->second->UpdateToNextTimestep();
    }
    std::map< unsigned int, Converter* >::iterator iterConverter;
    for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) {
        iterConverter->second->UpdateToNextTimestep();
    }
    std::map< unsigned int, Battery* >::iterator iterBattery;
    for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) {
        iterBattery->second->UpdateToNextTimestep();
    }
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) {
        iterElectricVehicle->second->UpdateToNextTimestep();
    }
    std::map< unsigned int, SolarField* >::iterator iterSolarField;
    for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) {
        iterSolarField->second->UpdateToNextTimestep();
    }
}

void EnergySystem::RunSimulation() {
    // simulate all renewables
    std::map< unsigned int, SolarResource* >::iterator iterSolar;
    for( iterSolar=m_solarResource.begin(); iterSolar!=m_solarResource.end(); ++iterSolar ) {
        iterSolar->second->SimulateSun();
    }
    // calculate EV charging profile for the year
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) {
        iterElectricVehicle->second->CreateChargerLoadProfile();
    }
    
    // make things simpler by creating a temporary pointer instead of loop through iterators all the time
    ImmediateLoad* immediateLoad = 0;           if( m_immediateLoad.size() > 0 ) { immediateLoad = m_immediateLoad.begin()->second;}
    SolarPV* solarPV = 0;                       if( m_solarPV.size() > 0 ) { solarPV = m_solarPV.begin()->second;}
//    SolarField* solarField = 0;                 if( m_solarField.size() > 0 ) { solarField = m_solarField.begin()->second; }
    Converter* converter = 0;                   if( m_converter.size() > 0 ) { converter = m_converter.begin()->second;}
    Battery* battery = 0;                       if( m_battery.size() > 0 ) { battery = m_battery.begin()->second;}
    ElectricVehicle* electricVehicle = 0;       if( m_electricVehicle.size() > 0 ) { electricVehicle = m_electricVehicle.begin()->second;}
    SolarResource* solarResource = 0;           if( m_solarResource.size() > 0 ) { solarResource = m_solarResource.begin()->second;}
    
    // run system simulation
    double inverterOutput, batteryPower, solarPVOutput, inverterInput;
    unsigned int hourOfDay;
    for( unsigned int iTime=0; iTime<m_numTimesteps; ++iTime ) {
        // TODO: needs lots of conditional logic here to handle various use cases
        
        inverterOutput = 0;
        batteryPower = 0;
        solarPVOutput = 0;
        
        if( !solarPV ) { // no generators or additional loads (vehicle)... battery only charges from solarPV
            (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed();
        }
        else if( solarPV && converter && !battery ) { // solar generator
            solarPV->Simulate();
            inverterOutput = converter->CalculateOutput( solarPV->GetCurrentPowerOutput() );
            (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
        }
        else if( solarPV && converter && battery ) { // solar and battery
            solarPV->Simulate();
            
            // here is the dispatch logic... 
            // - charge battery with solar
            // - send excess to meet load
            // - discharge battery during TOU period battery to avoid high grid price... assume 3-6PM
            
            hourOfDay = iTime%24;
            
            if( hourOfDay < 15 || hourOfDay > 17 ) { // outside TOU, don't use battery
                batteryPower = battery->GetMaximumChargePower();
                if( batteryPower > 0 ) {
                    solarPVOutput = solarPV->GetCurrentPowerOutput();
                    
                    if( solarPVOutput > batteryPower ) { // more solar than can put into battery
                        battery->Power( -1.0 * batteryPower ); // switch sign so we know it is charging
                        inverterOutput = converter->CalculateOutput( solarPVOutput - batteryPower ); // send remaining to load
                        (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                    }
                    else { // battery requires all solar power, no solar pushed to loads
                        battery->Power( -1.0 * solarPVOutput );
                        (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed();
                    }                    
                }
                else { // no room to charge battery, just push solar to AC 
                    battery->Power( 0.0 ); // still need to do, else numbers don't update
                    inverterOutput = converter->CalculateOutput( solarPV->GetCurrentPowerOutput() );
                    (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                }
            } 
            else { // inside TOU, use battery if possible
                solarPVOutput = solarPV->GetCurrentPowerOutput();
                inverterInput = converter->CalculateInputToZeroLoad(immediateLoad->GetCurrentLoadServed());
                
                if( solarPVOutput > inverterInput ) { // use excess power to charge battery
                    double excessDC = solarPVOutput - inverterInput;
                    
                    batteryPower = battery->GetMaximumChargePower();
                    if( batteryPower > 0 ) {
                        if( excessDC > batteryPower ) { // more solar than can put into battery
                            battery->Power( -1.0 * batteryPower ); // switch sign so we know it is charging
                            inverterOutput = converter->CalculateOutput( solarPVOutput - batteryPower ); // send remaining to load
                            (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                        }
                        else { // battery requires all solar power, no solar pushed to loads
                            battery->Power( -1.0 * solarPVOutput ); // switch sign so we know it is charging
                            (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed();
                        }                    
                    }
                    else { // no room to charge battery, just push solar to AC 
                        battery->Power( 0.0 ); // still need to do, else numbers don't update
                        inverterOutput = converter->CalculateOutput( solarPVOutput );
                        (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                    }
                }
                else { // try use battery to meet load
                    double deficitDC = inverterInput - solarPVOutput;
                    
                    batteryPower = battery->GetMaximumDischargePower();
                    if( batteryPower > 0 ) {
                        if( deficitDC > batteryPower ) { // need more power than battery can deliver
                            battery->Power( batteryPower ); // positive sign means discharging
                            inverterOutput = converter->CalculateOutput( solarPVOutput + batteryPower ); // solar output + battery output
                            (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                        }
                        else { // battery can meet deficit DC that solar PV cannot
                            battery->Power( deficitDC ); // 
                            inverterOutput = converter->CalculateOutput( solarPVOutput + deficitDC ); // solar output + battery output
                            (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                        }                    
                    }
                    else { // no energy in battery to use
                        battery->Power( 0.0 ); // still need to do, else numbers don't update
                        inverterOutput = converter->CalculateOutput( solarPVOutput );
                        (*m_iterEnergyNet) = immediateLoad->GetCurrentLoadServed() - inverterOutput;
                    }
                }
            }
        }
        
        // electric vehicle simply added to net load, no conditional logic needed
        if(electricVehicle) {
            (*m_iterEnergyNet) += electricVehicle->GetCurrentChargePower();
        }
        
        UpdateToNextTimestep();
    }
}
/**
 * Adds an immediate load without any data. 
 * @return ID of the immediate load. 
 */
unsigned int EnergySystem::AddImmediateLoad() {
    ++m_currentObjectID;
    ImmediateLoad *immediateLoad = new ImmediateLoad(m_currentObjectID);
    m_immediateLoad.insert( std::make_pair( m_currentObjectID, immediateLoad ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, immediateLoad ) );
    return( m_currentObjectID );
}

unsigned int EnergySystem::AddImmediateLoad( ImmediateLoad *immediateLoad, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    immediateLoad->m_identInput.m_objectID = m_currentObjectID;
    m_immediateLoad.insert( std::make_pair( m_currentObjectID, immediateLoad ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, immediateLoad ) );
    id = m_currentObjectID;
        
    return( id );
}
unsigned int EnergySystem::AddImmediateLoad( ImmediateLoadData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    ImmediateLoad *immediateLoad = new ImmediateLoad();
    immediateLoad->SetData(data);
    immediateLoad->m_identInput.m_objectID = m_currentObjectID;
    m_immediateLoad.insert( std::make_pair( m_currentObjectID, immediateLoad ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, immediateLoad ) );
    id = m_currentObjectID;
        
    return( id );
}

/**
 * Adds a solar PV component without any data. 
 * @return ID of the solar PV component. 
 */
unsigned int EnergySystem::AddSolarPV() {
    ++m_currentObjectID;
    SolarPV *solarPV = new SolarPV(m_currentObjectID);
    m_solarPV.insert( std::make_pair( m_currentObjectID, solarPV ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarPV ) );
    return( m_currentObjectID );
}

unsigned int EnergySystem::AddSolarPV( SolarPV *solarPV, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    solarPV->m_identInput.m_objectID = m_currentObjectID;
    m_solarPV.insert( std::make_pair( m_currentObjectID, solarPV ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarPV ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddSolarPV( SolarPVData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    SolarPV *solarPV = new SolarPV();
    solarPV->SetData(data);
    solarPV->m_identInput.m_objectID = m_currentObjectID;
    m_solarPV.insert( std::make_pair( m_currentObjectID, solarPV ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarPV ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddConverter() {
    ++m_currentObjectID;
    Converter *converter = new Converter(m_currentObjectID);
    m_converter.insert( std::make_pair( m_currentObjectID, converter ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, converter ) );
    return( m_currentObjectID );
}
unsigned int EnergySystem::AddConverter( Converter *converter, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    converter->m_identInput.m_objectID = m_currentObjectID;
    m_converter.insert( std::make_pair( m_currentObjectID, converter ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, converter ) );
    id = m_currentObjectID;
        
    return( id );
}
unsigned int EnergySystem::AddConverter( ConverterData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    Converter *converter = new Converter();
    converter->SetData( data );
    converter->m_identInput.m_objectID = m_currentObjectID;
    m_converter.insert( std::make_pair( m_currentObjectID, converter ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, converter ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddBattery() {
    ++m_currentObjectID;
    Battery *battery = new Battery(m_currentObjectID);
    m_battery.insert( std::make_pair( m_currentObjectID, battery ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, battery ) );
    return( m_currentObjectID );
}

unsigned int EnergySystem::AddBattery( Battery *battery, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    battery->m_identInput.m_objectID = m_currentObjectID;
    m_battery.insert( std::make_pair( m_currentObjectID, battery ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, battery ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddBattery( BatteryData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    Battery *battery = new Battery();
    battery->SetData( data );
    battery->m_identInput.m_objectID = m_currentObjectID;
    m_battery.insert( std::make_pair( m_currentObjectID, battery ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, battery ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddElectricVehicle() {
    ++m_currentObjectID;
    ElectricVehicle *electricVehicle = new ElectricVehicle(m_currentObjectID);
    m_electricVehicle.insert( std::make_pair( m_currentObjectID, electricVehicle ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, electricVehicle ) );
    return( m_currentObjectID );
}

unsigned int EnergySystem::AddElectricVehicle( ElectricVehicle *electricVehicle, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    electricVehicle->m_identInput.m_objectID = m_currentObjectID;
    m_electricVehicle.insert( std::make_pair( m_currentObjectID, electricVehicle ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, electricVehicle ) );
    id = m_currentObjectID;
        
    return( id );
}
unsigned int EnergySystem::AddElectricVehicle( ElectricVehicleData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    ElectricVehicle *electricVehicle = new ElectricVehicle();
    electricVehicle->SetData( data );
    electricVehicle->m_identInput.m_objectID = m_currentObjectID;
    m_electricVehicle.insert( std::make_pair( m_currentObjectID, electricVehicle ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, electricVehicle ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddSolarField() {
    ++m_currentObjectID;
    SolarField *solarField = new SolarField(m_currentObjectID);
    m_solarField.insert( std::make_pair( m_currentObjectID, solarField ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarField ) );
    return( m_currentObjectID );
}

unsigned int EnergySystem::AddSolarField( SolarField *solarField, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    solarField->m_identInput.m_objectID = m_currentObjectID;
    m_solarField.insert( std::make_pair( m_currentObjectID, solarField ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarField ) );
    id = m_currentObjectID;
        
    return( id );
}
unsigned int EnergySystem::AddSolarField( SolarFieldData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    SolarField *solarField = new SolarField();
    solarField->SetData( data );
    solarField->m_identInput.m_objectID = m_currentObjectID;
    m_solarField.insert( std::make_pair( m_currentObjectID, solarField ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarField ) );
    id = m_currentObjectID;
        
    return( id );
}

/**
 * Adds a solar resource without any data. 
 * @return ID of the solar resource. 
 */
unsigned int EnergySystem::AddSolarResource() {
    ++m_currentObjectID;
    SolarResource *solarResource = new SolarResource(m_currentObjectID);
    m_solarResource.insert( std::make_pair( m_currentObjectID, solarResource ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarResource ) );
    return( m_currentObjectID );
}

unsigned int EnergySystem::AddSolarResource( SolarResource *solarResource, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    solarResource->m_identInput.m_objectID = m_currentObjectID;
    m_solarResource.insert( std::make_pair( m_currentObjectID, solarResource ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarResource ) );
    id = m_currentObjectID;
        
    return( id );
}

unsigned int EnergySystem::AddSolarResource( SolarResourceData &data, bool replaceObjectID ) {
    // TODO: overwrite unique ID for now, retaining object IDs will require us to look-up 
    // the ID in m_ESDEObjectList to see if it is taken before adding the ID to the list
    unsigned int id;
    
    ++m_currentObjectID;
    SolarResource *solarResource = new SolarResource();
    solarResource->SetData( data );
    solarResource->m_identInput.m_objectID = m_currentObjectID;
    m_solarResource.insert( std::make_pair( m_currentObjectID, solarResource ) );
    m_ESDEObjectList.insert( std::make_pair( m_currentObjectID, solarResource ) );
    id = m_currentObjectID;
        
    return( id );
}

void EnergySystem::CalculateSummaryData() {
    std::map< unsigned int, ESDEObject* >::iterator iterESDEObject;
    for( iterESDEObject=m_ESDEObjectList.begin(); iterESDEObject!=m_ESDEObjectList.end(); ++iterESDEObject ) {
        iterESDEObject->second->CalculateSummaryData();
    }
    
    // net load information
    m_totalEnergyIn = 0;
    m_totalEnergyOut = 0;
    m_totalEnergyNet = 0;
    for( unsigned int i=0; i<m_numTimesteps; ++i ) {
        if( m_energyNet.at(i) > 0 ) {
            m_totalEnergyIn += m_energyNet.at(i);
        }
        else {
            m_totalEnergyOut += -1.0 * m_energyNet.at(i);
        }
    }
    m_totalEnergyNet = m_totalEnergyIn - m_totalEnergyOut;
    
    // loop through and calculate for grid information
    std::vector< double > totalLoadServed = GetImmediateLoad()->GetLoadServed();
    for( unsigned int i=0; i<m_numTimesteps; ++i ) {
        m_allSystemsTotalLoad.at(i) = totalLoadServed.at(i) * m_numSystems;
        m_allSystemsNetLoad.at(i) = m_energyNet.at(i) * m_numSystems;
    }
    
    // other summary information
    
    // TODO: this is a hack, not sure why m_timestepHourlyFraction resets to 0.0
    m_timestepHourlyFraction = 1.0;
    
    SummaryStatistics netSummary = CalculateSummaryStatistics( m_energyNet, true );
    m_maxLoadServed = netSummary.max;
    m_avgLoadServed = netSummary.mean;
    m_totalLoadServedPeriod = netSummary.total * m_timestepHourlyFraction;
    m_totalLoadServedDay = m_totalLoadServedPeriod / 365.0;
    m_loadFactor = m_avgLoadServed / m_maxLoadServed;  
    
    // only one load, this will work for now
    double avgLoad = 0.0;
    double totalLoad = 0.0;
    if( m_immediateLoad.size() > 0 ) {
        avgLoad = m_immediateLoad.begin()->second->GetAvgLoadServed();
        totalLoad = m_immediateLoad.begin()->second->GetTotalLoadServedPeriod();
    }
    
    std::map< unsigned int, SolarPV* >::iterator iterSolarPV;
    for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) {
        iterSolarPV->second->CalculatePenetration( avgLoad );
    }
    
    std::map< unsigned int, Battery* >::iterator iterBattery;
    for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) {
        iterBattery->second->CalculateAutonomy( avgLoad );
    }
    
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) {
        iterElectricVehicle->second->CalculateLoadPortion( totalLoad );
    }
    
    // calculate financials
    // TODO: this is a hack for time being, use only net load info
    m_interconnectionCharges = m_rateSchedule.m_interconnectionCharge * 12.0;
    
    double avgEnergyCost = 0;
    double avgDemandCost = 0;
    double count = 0;
    std::map<int,Rate>::iterator iter;
    for( iter=m_rateSchedule.m_rates.begin(); iter!=m_rateSchedule.m_rates.end(); ++iter ) {
        avgEnergyCost += iter->second.m_energyPrice.begin()->first;
        avgDemandCost += iter->second.m_demandCharge.begin()->first;
        ++count;
    }
    avgEnergyCost /= count;
    avgDemandCost /= count;
    
    if( m_totalEnergyNet > 0 ) {
        m_energyCharges = m_totalEnergyNet * avgEnergyCost;  // TODO: not right, just average energy cost
    }
    else if( m_totalEnergyNet < 0 ) { // sell back at overproduction credit
        m_energyCharges = m_totalEnergyNet * m_rateSchedule.m_overproductionCredit;
        
    }
    m_demandCharges = avgDemandCost * m_maxLoadServed * 6.0; // TODO: this just equates over 1/2 of year to get approximate value... which is not right

    m_totalCharges = m_energyCharges + m_demandCharges + m_interconnectionCharges;
}

void EnergySystem::CalculateGridSummaryData() {
    std::vector< double > netLoad = GetImmediateLoad( 2 )->GetLoadServed(); // 2 = grid net load
    
    // net load information
    m_totalEnergyIn = 0;
    m_totalEnergyOut = 0;
    m_totalEnergyNet = 0;
    for( unsigned int i=0; i<m_numTimesteps; ++i ) {
        if( netLoad.at(i) > 0 ) {
            m_totalEnergyIn += netLoad.at(i);
        }
        else {
            m_totalEnergyOut += -1.0 * netLoad.at(i);
        }
    }
    m_totalEnergyNet = m_totalEnergyIn - m_totalEnergyOut;
    
    // other summary information
    
    // TODO: this is a hack, not sure why m_timestepHourlyFraction resets to 0.0
    m_timestepHourlyFraction = 1.0;
    
    SummaryStatistics netSummary = CalculateSummaryStatistics( netLoad, true );
    m_maxLoadServed = netSummary.max;
    m_avgLoadServed = netSummary.mean;
    m_totalLoadServedPeriod = netSummary.total * m_timestepHourlyFraction;
    m_totalLoadServedDay = m_totalLoadServedPeriod / 365.0;
    m_loadFactor = m_avgLoadServed / m_maxLoadServed;  
}

// data io
bool EnergySystem::OutputSummaryDataToFile() {
    OutputSummaryDataToFile(m_outputDir);
    return(true);
}

bool EnergySystem::OutputSummaryDataToFile( std::string &fname ) {
    
    // energy system summary information (single customer)
    std::string dir = fname + "GridInterconnection.txt";
    std::ofstream fout;
    fout.open( dir.c_str() );

    fout << "Energy purchased [kWh/period]: " << m_totalEnergyIn << "\n";
    fout << "Energy sold [kWh/period]: " << m_totalEnergyOut << "\n";
    fout << "Net purchases [kWh/period]: " << m_totalEnergyNet << "\n";
    fout << "\n";
    
    fout << "Peak load served [kW]: " << m_maxLoadServed << "\n";
    fout << "Average load served [kW]: " << m_avgLoadServed << "\n";
    fout << "Energy use [kWh/day]: " << m_totalLoadServedDay << "\n";
    fout << "Energy use [kWh/period]: " << m_totalLoadServedPeriod << "\n";
    fout << "Load factor [-]: " << m_loadFactor << "\n";
    fout << "\n";
            
    fout << "Total charges [$/y]: " << m_totalCharges << "\n";
    fout << "Energy charges [$/y]: " << m_energyCharges << "\n";
    fout << "Demand charges [$/y]: " << m_demandCharges << "\n";
    fout << "Interconnection charges [$/y]: " << m_interconnectionCharges << "\n";
    
    fout.close();
    
    // energy system summary information (total for all customers)
    dir = fname + "GridInterconnectionTotal.txt";
    fout.open( dir.c_str() );

    fout << "Energy purchased [kWh/period]: " << m_totalEnergyIn * m_numSystems << "\n";
    fout << "Energy sold [kWh/period]: " << m_totalEnergyOut * m_numSystems << "\n";
    fout << "Net purchases [kWh/period]: " << m_totalEnergyNet * m_numSystems << "\n";
    fout << "\n";
    
    fout << "Peak load served [kW]: " << m_maxLoadServed * m_numSystems << "\n";
    fout << "Average load served [kW]: " << m_avgLoadServed * m_numSystems << "\n";
    fout << "Energy use [kWh/day]: " << m_totalLoadServedDay * m_numSystems << "\n";
    fout << "Energy use [kWh/period]: " << m_totalLoadServedPeriod * m_numSystems << "\n";
    fout << "Load factor [-]: " << m_loadFactor << "\n";
    fout << "\n";
            
    fout << "Total charges [$/y]: " << m_totalCharges * m_numSystems << "\n";
    fout << "Energy charges [$/y]: " << m_energyCharges * m_numSystems << "\n";
    fout << "Demand charges [$/y]: " << m_demandCharges * m_numSystems << "\n";
    fout << "Interconnection charges [$/y]: " << m_interconnectionCharges * m_numSystems << "\n";
    
    fout.close();
    
    std::map< unsigned int, ImmediateLoad* >::iterator iterImmediateLoad;
    for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) {
        iterImmediateLoad->second->OutputSummaryToFile(fname);
    }
    std::map< unsigned int, SolarResource* >::iterator iterSolar;
    for( iterSolar=m_solarResource.begin(); iterSolar!=m_solarResource.end(); ++iterSolar ) {
        iterSolar->second->OutputSummaryToFile(fname);
    }
    std::map< unsigned int, SolarPV* >::iterator iterSolarPV;
    for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) {
        iterSolarPV->second->OutputSummaryToFile(fname);
    }
    std::map< unsigned int, Converter* >::iterator iterConverter;
    for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) {
        iterConverter->second->OutputSummaryToFile(fname);
    }
    std::map< unsigned int, Battery* >::iterator iterBattery;
    for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) {
        iterBattery->second->OutputSummaryToFile(fname);
    }
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) {
        iterElectricVehicle->second->OutputSummaryToFile(fname);
    }
    std::map< unsigned int, SolarField* >::iterator iterSolarField;
    for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) {
        iterSolarField->second->OutputSummaryToFile(fname);
    }
    return(true);
}

// data io
bool EnergySystem::OutputGridSummaryDataToFile() {
    OutputGridSummaryDataToFile(m_outputDir);
    return(true);
}

bool EnergySystem::OutputGridSummaryDataToFile( std::string &fname ) {
    // energy system summary information (single customer)
    std::string dir = fname + "GridInterconnection.txt";
    std::ofstream fout;
    fout.open( dir.c_str() );

    fout << "Energy purchased [kWh/period]: " << m_totalEnergyIn << "\n";
    fout << "Energy sold [kWh/period]: " << m_totalEnergyOut << "\n";
    fout << "Net purchases [kWh/period]: " << m_totalEnergyNet << "\n";
    fout << "\n";
    
    fout << "Peak load served [kW]: " << m_maxLoadServed << "\n";
    fout << "Average load served [kW]: " << m_avgLoadServed << "\n";
    fout << "Energy use [kWh/day]: " << m_totalLoadServedDay << "\n";
    fout << "Energy use [kWh/period]: " << m_totalLoadServedPeriod << "\n";
    fout << "Load factor [-]: " << m_loadFactor << "\n";
    fout << "\n";
            
    fout << "Total charges [$/y]: " << m_totalCharges << "\n";
    fout << "Energy charges [$/y]: " << m_energyCharges << "\n";
    fout << "Demand charges [$/y]: " << m_demandCharges << "\n";
    fout << "Interconnection charges [$/y]: " << m_interconnectionCharges << "\n";
    
    fout.close();
    
    // energy system summary information (total for all customers)
    dir = fname + "GridInterconnectionTotal.txt";
    fout.open( dir.c_str() );

    fout << "Energy purchased [kWh/period]: " << m_totalEnergyIn * m_numSystems << "\n";
    fout << "Energy sold [kWh/period]: " << m_totalEnergyOut * m_numSystems << "\n";
    fout << "Net purchases [kWh/period]: " << m_totalEnergyNet * m_numSystems << "\n";
    fout << "\n";
    
    fout << "Peak load served [kW]: " << m_maxLoadServed * m_numSystems << "\n";
    fout << "Average load served [kW]: " << m_avgLoadServed * m_numSystems << "\n";
    fout << "Energy use [kWh/day]: " << m_totalLoadServedDay * m_numSystems << "\n";
    fout << "Energy use [kWh/period]: " << m_totalLoadServedPeriod * m_numSystems << "\n";
    fout << "Load factor [-]: " << m_loadFactor << "\n";
    fout << "\n";
            
    fout << "Total charges [$/y]: " << m_totalCharges * m_numSystems << "\n";
    fout << "Energy charges [$/y]: " << m_energyCharges * m_numSystems << "\n";
    fout << "Demand charges [$/y]: " << m_demandCharges * m_numSystems << "\n";
    fout << "Interconnection charges [$/y]: " << m_interconnectionCharges * m_numSystems << "\n";
    
    fout.close();
    return(true);
}
bool EnergySystem::OutputTimeseriesDataToFile( bool includeHeader ) {
    std::string fname = m_outputDir;
    fname.append( "time_series_detailed.csv" );
    OutputTimeseriesDataToFile(fname);
    return(true);
}
bool EnergySystem::OutputSimpleTimeseriesDataToFile( bool includeHeader ) {
    std::string fname = m_outputDir;
    fname.append( "time_series_simple.csv" );
    OutputSimpleTimeseriesDataToFile(fname);
    return(true);
}
bool EnergySystem::OutputGridTimeseriesDataToFile( bool includeHeader ) {
    // used for aggregate grid data
    std::string fname = m_outputDir;
    fname.append( "time_series_simple.csv" );
    OutputGridTimeseriesDataToFile(fname);
    return(true);
}
bool EnergySystem::OutputTimeseriesDataToFile( std::string &fname, bool includeHeader ) {
    
    /* TODO
     o Error handling if cannot open file, etc.
     o Return false if not succeed. 
     */
    
    std::ofstream fout;
    fout.open( fname.c_str() );
    
    std::map< unsigned int, ImmediateLoad* >::iterator iterImmediateLoad;
    std::map< unsigned int, SolarResource* >::iterator iterSolarResource;
    std::map< unsigned int, SolarPV* >::iterator iterSolarPV;
    std::map< unsigned int, Converter* >::iterator iterConverter;
    std::map< unsigned int, Battery* >::iterator iterBattery;
    std::map< unsigned int, SolarField* >::iterator iterSolarField;
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    
    if( includeHeader ) {
        fout << "Time step,";

        for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) 
            iterImmediateLoad->second->OutputTimeseriesHeaderToFile(fout);
       
        for( iterSolarResource=m_solarResource.begin(); iterSolarResource!=m_solarResource.end(); ++iterSolarResource ) 
            iterSolarResource->second->OutputTimeseriesHeaderToFile(fout);        
        
        for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) 
            iterSolarPV->second->OutputTimeseriesHeaderToFile(fout);
        
        for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) 
            iterConverter->second->OutputTimeseriesHeaderToFile(fout);
        
        for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) 
            iterBattery->second->OutputTimeseriesHeaderToFile(fout);
        
        for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) 
            iterElectricVehicle->second->OutputTimeseriesHeaderToFile(fout);
        
        for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) 
            iterSolarField->second->OutputTimeseriesHeaderToFile(fout);
        
        fout << "\n"; 
    }
    
    unsigned int iTime;
    for( iTime=0; iTime<m_numTimesteps; ++iTime ) {
        fout << iTime << ",";
        
        for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) 
            iterImmediateLoad->second->OutputTimeseriesDataToFile(fout, iTime, m_numSystems);

        for( iterSolarResource=m_solarResource.begin(); iterSolarResource!=m_solarResource.end(); ++iterSolarResource ) 
            iterSolarResource->second->OutputTimeseriesDataToFile(fout, iTime);
        
        for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) 
            iterSolarPV->second->OutputTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) 
            iterConverter->second->OutputTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) 
            iterBattery->second->OutputTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) 
            iterElectricVehicle->second->OutputTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) 
            iterSolarField->second->OutputTimeseriesDataToFile(fout, iTime);

        fout << "\n";
    }
    
    fout.close();
    
    return(true);
}

bool EnergySystem::OutputSimpleTimeseriesDataToFile( std::string &fname, bool includeHeader ) {
    
    /* TODO
     o Error handling if cannot open file, etc.
     o Return false if not succeed. 
     */
    
    std::ofstream fout;
    fout.open( fname.c_str() );
    
    std::map< unsigned int, ImmediateLoad* >::iterator iterImmediateLoad;
    std::map< unsigned int, SolarResource* >::iterator iterSolarResource;
    std::map< unsigned int, SolarPV* >::iterator iterSolarPV;
    std::map< unsigned int, Converter* >::iterator iterConverter;
    std::map< unsigned int, Battery* >::iterator iterBattery;
    std::map< unsigned int, SolarField* >::iterator iterSolarField;
    std::map< unsigned int, ElectricVehicle* >::iterator iterElectricVehicle;
    
    if( includeHeader ) {
        fout << "Time step,";
        
        for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) 
            iterImmediateLoad->second->OutputSimpleTimeseriesHeaderToFile(fout);
       
        fout << m_energyNetName << ",";
        
//        for( iterSolarResource=m_solarResource.begin(); iterSolarResource!=m_solarResource.end(); ++iterSolarResource ) 
//            iterSolarResource->second->OutputSimpleTimeseriesHeaderToFile(fout);        
        
        for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) 
            iterSolarPV->second->OutputSimpleTimeseriesHeaderToFile(fout);
        
        for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) 
            iterConverter->second->OutputSimpleTimeseriesHeaderToFile(fout);
        
        for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) 
            iterBattery->second->OutputSimpleTimeseriesHeaderToFile(fout);
        
        for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) 
            iterElectricVehicle->second->OutputSimpleTimeseriesHeaderToFile(fout);
        
//        for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) 
//            iterSolarField->second->OutputSimpleTimeseriesHeaderToFile(fout);
        
        fout << "\n"; 
    }
    
    unsigned int iTime;
    for( iTime=0; iTime<m_numTimesteps; ++iTime ) {
        fout << iTime << ",";
        
        for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) 
            iterImmediateLoad->second->OutputSimpleTimeseriesDataToFile(fout, iTime, m_numSystems);

        fout << m_energyNet.at(iTime) * m_numSystems << ",";
        
//        for( iterSolarResource=m_solarResource.begin(); iterSolarResource!=m_solarResource.end(); ++iterSolarResource ) 
//            iterSolarResource->second->OutputSimpleTimeseriesDataToFile(fout, iTime);
        
        for( iterSolarPV=m_solarPV.begin(); iterSolarPV!=m_solarPV.end(); ++iterSolarPV ) 
            iterSolarPV->second->OutputSimpleTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterConverter=m_converter.begin(); iterConverter!=m_converter.end(); ++iterConverter ) 
            iterConverter->second->OutputSimpleTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterBattery=m_battery.begin(); iterBattery!=m_battery.end(); ++iterBattery ) 
            iterBattery->second->OutputSimpleTimeseriesDataToFile(fout, iTime, m_numSystems);
        
        for( iterElectricVehicle=m_electricVehicle.begin(); iterElectricVehicle!=m_electricVehicle.end(); ++iterElectricVehicle ) 
            iterElectricVehicle->second->OutputSimpleTimeseriesDataToFile(fout, iTime, m_numSystems);
        
//        for( iterSolarField=m_solarField.begin(); iterSolarField!=m_solarField.end(); ++iterSolarField ) 
//            iterSolarField->second->OutputSimpleTimeseriesDataToFile(fout, iTime);

        fout << "\n";
    }
    
    fout.close();
    
    return(true);
}

bool EnergySystem::OutputGridTimeseriesDataToFile( std::string &fname, bool includeHeader ) {
    
    /* TODO
     o Error handling if cannot open file, etc.
     o Return false if not succeed. 
     */
    
    std::ofstream fout;
    fout.open( fname.c_str() );
    
    std::map< unsigned int, ImmediateLoad* >::iterator iterImmediateLoad;
    
    if( includeHeader ) {
        fout << "Time step,";
        
        for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) 
            iterImmediateLoad->second->OutputSimpleTimeseriesHeaderToFile(fout);
       
        fout << "\n"; 
    }
    
    unsigned int iTime;
    for( iTime=0; iTime<m_numTimesteps; ++iTime ) {
        fout << iTime << ",";
        
        for( iterImmediateLoad=m_immediateLoad.begin(); iterImmediateLoad!=m_immediateLoad.end(); ++iterImmediateLoad ) 
            iterImmediateLoad->second->OutputSimpleTimeseriesDataToFile(fout, iTime);
        
        fout << "\n";
    }
    
    fout.close();
    
    return(true);
}