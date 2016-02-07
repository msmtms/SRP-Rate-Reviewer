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


std::vector<std::string> &split(const std::string &s, char delim, std::vector<std::string> &elems) {
	std::stringstream ss(s);
	std::string item;
	while (std::getline(ss, item, delim)) {
		elems.push_back(item);
	}
	return elems;
}


std::vector<std::string> split(const std::string &s, char delim) {
	std::vector<std::string> elems;
	split(s, delim, elems);
	return elems;
}

// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT jstring JNICALL Java_application_RateReviewerProxy_initInterface (JNIEnv *env, jobject thisObj, jstring in){
	const char *input = env->GetStringUTFChars(in, NULL);
	std::string s(input);
	if (input == NULL){
		return NULL;
	}

	std::vector<std::string> x = split(s, ',');

	unsigned int numTimesteps = 8760;
	EnergySystem energySystem(0, x[0]);

	// Step 1: add objects to energy system
	ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput("./data/" + x[0] + "/input/load.txt");
	energySystem.AddImmediateLoad(loadData);

	SolarResourceData dataSolarResource = ESDELoader::LoadSolarResourceInput(std::string("./data/Location.txt"), x[2]);
	energySystem.AddSolarResource(dataSolarResource);

	SolarPVData dataSolarPV = ESDELoader::LoadSolarPVInput(std::string("./data/" + x[0] + "/input/SolarPV.txt"));
	energySystem.AddSolarPV(dataSolarPV);

	ConverterData dataConverter = ESDELoader::LoadConverterInput(std::string("./data/" + x[0] + "/input/Inverter.txt"));
	energySystem.AddConverter(dataConverter);

	BatteryData dataBattery = ESDELoader::LoadBatteryInput(std::string("./data/" + x[0] + "/input/Battery.txt"));
	energySystem.AddBattery(dataBattery);

	ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput(std::string("./data/" + x[0] + "/input/ElectricVehicle.txt"));
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

	
	const char *out = input;
	jstring output = env->NewStringUTF(out);
	return output;
}
