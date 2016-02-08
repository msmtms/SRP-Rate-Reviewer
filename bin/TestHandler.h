/**
 * @file TestHandler.h
 * @brief Test class for unit and integration testing.   
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef TESTFUNCTIONS_H
#define	TESTFUNCTIONS_H

/* TODO
 o replace with automated unit tests
 o add logic to decern when tests results in pass or fail
 o integration tests
 */

// data model 

// system computations

/******************************************************************************/
class TestHandler {
   /**
    * @class TestHandler [TestHandler.h]
    * @brief Test class for unit and integration testing. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    
    TestHandler() {;}
    ~TestHandler() {;}
    
    bool RunTest( unsigned int testID );
    
public: // enum for tests
    
    enum {
        /**** unit tests ****/
        TEST_CUSTOM = 0,
        // loads
        TEST_IMMEDIATE_LOAD,

        // components
        TEST_SOLAR_PV,
        TEST_SOLAR_FIELD,
        TEST_POWER_TOWER,
        TEST_CONVERTER,
        TEST_BATTERY,
        TEST_ELECTRIC_VEHICLE,

        // resources
        TEST_SOLAR_RESOURCE,
        
        /**** integration tests ****/
        TEST_ENERGY_SYSTEM
    };
    
private:
    
    bool TestCustom();
    bool TestImmediateLoad();
    bool TestSolarPV();
    bool TestBattery();
    bool TestElectricVehicle();
    bool TestSolarField();
    bool TestPowerTower();
    bool TestConverter();
    bool TestSolarResource();
    bool TestEnergySystem();
    bool TestEnergySystem( std::string systemName );
};
   

#endif	/* TESTFUNCTIONS_H */

