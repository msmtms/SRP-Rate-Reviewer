#include <jni.h>
#include <stdio.h>
#include "application_RateReviewerProxy.h"

#include <fstream>
#include <iostream>
#include <vector>
#include <iterator>
#include <map>
#include <ctime>

#include "TestHandler.h"
#include "ESDELocation.h"
#include "ESDEDataModel.h"
#include "SolarResource.h"
#include "ESDELoader.h"
#include "SolarField.h"
#include "EnergySystem.h"
#include "Converter.h"

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT jstring JNICALL Java_application_RateReviewerProxy_initInterface (JNIEnv *env, jobject thisObj, jstring in){
	const char *input = env->GetStringUTFChars(in, NULL);
	if (input == NULL){
		return NULL;
	}

	unsigned int numTimesteps = 8760;
	EnergySystem energySystem;

	// Step 1: add objects to energy system
	ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput(std::string("./data/input/Load.txt"));
	energySystem.AddImmediateLoad(loadData);

	SolarResourceData dataSolarResource = ESDELoader::LoadSolarResourceInput(std::string("./data/input/Location.txt"), std::string("./data/input/SolarResource.txt"));
	energySystem.AddSolarResource(dataSolarResource);

	SolarPVData dataSolarPV = ESDELoader::LoadSolarPVInput(std::string("./data/input/SolarPV.txt"));
	energySystem.AddSolarPV(dataSolarPV);

	ConverterData dataConverter = ESDELoader::LoadConverterInput(std::string("./data/input/Inverter.txt"));
	energySystem.AddConverter(dataConverter);

	BatteryData dataBattery = ESDELoader::LoadBatteryInput(std::string("./data/input/Battery.txt"));
	energySystem.AddBattery(dataBattery);

	ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput(std::string("./data/input/ElectricVehicle.txt"));
	energySystem.AddElectricVehicle(dataElectricVehicle);

	// Step 2: init energy system and all objects
	energySystem.Init(numTimesteps);

	// Step 3: run simulation
	energySystem.RunSimulation();

	// Step 4: output data
	energySystem.OutputTimeseriesDataToFile();
	energySystem.OutputSimpleTimeseriesDataToFile();
	energySystem.CalculateSummaryData();
	energySystem.OutputSummaryDataToFile();

	
	const char *out = "complete";
	jstring output = env->NewStringUTF(out);
	return output;
}
