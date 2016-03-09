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
#include "dirent.h"

Logger *logger;

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

	env->ReleaseStringUTFChars(in, input);

	std::cout << "Starting" << std::endl;

	std::vector<std::string> x = split(s, ',');
	/*
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
	*/

	// initialize logger
	logger = Logger::Instance();
	Logger::Instance()->setLogPath(std::string("./logger/"));

	std::cout << "Created Logger" << std::endl;

	/** Derek: here is the code for hooking your work to mine */
	ESDEDataModel myDataModel;

	// Derek: one call for solar information, do after the solar resource file is loaded
	int argc = std::atoi(x[3].c_str());
	unsigned int numTimesteps = std::atoi(x[8].c_str());

	if (argc == ID_ESDE_CALCULATE_SOLAR) {
		std::cout << "Calculating solar" << std::endl;
		// Derek --> replace these with your own data from the GUI
		SolarResourceData dataSolar = ESDELoader::LoadSolarResourceInput(std::string("./data/Location.txt"), x[2]);
		myDataModel.GetSolarResource()->SetData(dataSolar);

		// keep these here
		myDataModel.GetSolarResource()->SimulateSun();
		myDataModel.GetSolarResource()->CalculateSummaryData();

		// Derek --> now grab what you need and pass back to GUI, each vector is 12 in length for 1 per month
		std::vector<double> tmp;
		tmp = myDataModel.GetSolarResource()->GetAverageGlobalHorizontal();
		tmp = myDataModel.GetSolarResource()->GetAverageGlobalDirect();
		tmp = myDataModel.GetSolarResource()->GetAverageClearness();
	}

	// Derek: one call for all other information
	if (argc == ID_ESDE_CALCULATE_ENERGY_SYSTEMS) {
		std::cout << "Calculating Energy" << std::endl;
		EnergySystem energySystem(0, x[0]);

		int solar = std::atoi(x[4].c_str());
		int invert = std::atoi(x[5].c_str());
		int batt = std::atoi(x[6].c_str());
		int ev = std::atoi(x[7].c_str());

		// Step 1: add objects to energy system
		ImmediateLoadData loadData = ESDELoader::LoadImmediateLoadInput(std::string("./data/" + x[0] + "/input/load.txt"));
		energySystem.AddImmediateLoad(loadData);

		SolarResourceData dataSolarResource = ESDELoader::LoadSolarResourceInput(std::string("./data/Location.txt"), x[2]);
		energySystem.AddSolarResource(dataSolarResource);

		if (solar) {
			SolarPVData dataSolarPV = ESDELoader::LoadSolarPVInput(std::string("./data/" + x[0] + "/input/SolarPV.txt"));
			energySystem.AddSolarPV(dataSolarPV);
		}

		if (invert) {
			ConverterData dataConverter = ESDELoader::LoadConverterInput(std::string("./data/" + x[0] + "/input/Inverter.txt"));
			energySystem.AddConverter(dataConverter);
		}

		if (batt) {
			BatteryData dataBattery = ESDELoader::LoadBatteryInput(std::string("./data/" + x[0] + "/input/Battery.txt"));
			energySystem.AddBattery(dataBattery);
		}

		if (ev) {
			ElectricVehicleData dataElectricVehicle = ESDELoader::LoadElectricVehicleInput(std::string("./data/" + x[0] + "/input/ElectricVehicle.txt"));
			energySystem.AddElectricVehicle(dataElectricVehicle);
		}

		std::cout << "time steps: " << numTimesteps << std::endl;
		// Step 2: init energy system and all objects
		energySystem.Init(numTimesteps);

		std::cout << "Run E calc" << std::endl;
		// Step 3: run simulation
		energySystem.RunSimulation();

		std::cout << "Output TS" << std::endl;
		// Step 4: output data
		energySystem.OutputTimeseriesDataToFile();
		std::cout << "Output STS" << std::endl;
		energySystem.OutputSimpleTimeseriesDataToFile();
		std::cout << "Calc Summary" << std::endl;
		energySystem.CalculateSummaryData();
		std::cout << "Output Summary" << std::endl;
		energySystem.OutputSummaryDataToFile();
	}

	if (argc == ID_ESDE_CALCULATE_GRID) {
		std::cout << "Calculating Grid" << std::endl;
		std::string tempString;

		std::map< std::string, EnergySystem > energySystems;
		std::map< std::string, EnergySystem > tempSystem;
		std::map< std::string, EnergySystem >::iterator iterSystems;

		std::string dataDir = "./data/";
		std::string ratepayerDir = dataDir + "Ratepayers/";

		// Step 1: read in information on solar resource
		SolarResourceData dataSolarResource;

		if (std::ifstream(std::string("./data/Location.txt").c_str()).good() && std::ifstream(x[2].c_str()).good()) {
			dataSolarResource = ESDELoader::LoadSolarResourceInput(std::string("./data/Location.txt"), x[2]);
		}
		else {
			if (!(std::ifstream(std::string("./data/Location.txt").c_str()).good())) {
				Logger::Instance()->writeToLogFile(std::string("Missing \"Location.txt\" for solar resource"), Logger::PROCESS);
			}
			if (!(std::ifstream(x[2].c_str()).good())) {
				Logger::Instance()->writeToLogFile(std::string("Missing \"SolarResource.txt\" for solar resource"), Logger::PROCESS);
			}
			// still call this, just so that the proper files are created (with zeros for solar resource ... easy fix for now)
			dataSolarResource = ESDELoader::LoadSolarResourceInput(std::string("./data/Location.txt"), x[2]);
		}

		// Step 2: add information on rates
		// TODO -- read in rate data here?
		std::string rateDir = dataDir + "Rates/";
		std::map< std::string, RateSchedule > rateSchedules;

		std::vector< std::string > rateScheduleNames;
		DIR *dir;
		struct dirent *ent;
		int count = 0;
		if ((dir = opendir(rateDir.c_str())) != NULL) {
			while ((ent = readdir(dir)) != NULL) {
				std::cout << "Processing Strata..." << std::endl;
				if (count >= 2) {
					rateScheduleNames.push_back(std::string(ent->d_name));
					std::cout << "Rate " << (count-1) << ": " << ent->d_name << std::endl;
				}
				count++;
			}
			closedir(dir);
		}

		for (size_t i = 0; i<rateScheduleNames.size(); ++i) {
			rateSchedules.insert(std::make_pair(rateScheduleNames.at(i), ESDELoader::LoadRateSchedule(rateDir, rateScheduleNames.at(i))));
		}

		// Step 3: read in ratepayer names and then read in ratepayer data
		std::vector< std::string > ratepayerNames;

		std::cout << "Grabbing Strata" << std::endl;
		count = 0;
		if ((dir = opendir(ratepayerDir.c_str())) != NULL) {
			while ((ent = readdir(dir)) != NULL) {
				std::cout << "Processing Strata..." << std::endl;
				if (count >= 2){
					ratepayerNames.push_back(std::string(ent->d_name));
					std::cout << "Strata " << (count -1) << ": " << ent->d_name << std::endl;
				}
				count++;
			}
			closedir(dir);
		}
		std::cout << "Done Processing Strata" << std::endl;
		for (size_t i = 0; i<ratepayerNames.size(); ++i) {
			std::cout << "Compiling Strata..." << std::endl;
			// this adds a blank solar resource, only if there is solar PV --> shows that solar PV output is zero but still also prints informatino to file
			energySystems.insert(std::make_pair(ratepayerNames.at(i), ESDELoader::LoadEnergySystem(ratepayerDir, ratepayerNames.at(i), dataSolarResource)));

			// add rate schedule
			std::string rateScheduleName = energySystems[ratepayerNames.at(i)].GetRateScheduleName();
			energySystems[ratepayerNames.at(i)].SetRateSchedule(rateSchedules[rateScheduleName]);
		}

		// Step 4: calculate energy systems and print to file
		for (iterSystems = energySystems.begin(); iterSystems != energySystems.end(); ++iterSystems) {
			std::cout << "Processing Systems..." << std::endl;
			// init energy system and all objects (this will also reset solar resource iterators))
			iterSystems->second.Init(numTimesteps);

			// run simulation
			iterSystems->second.RunSimulation();

			// output data
			iterSystems->second.OutputTimeseriesDataToFile();
			iterSystems->second.OutputSimpleTimeseriesDataToFile();
			iterSystems->second.CalculateSummaryData();
			iterSystems->second.OutputSummaryDataToFile();
		}

		// Step 5. calculate grid information and print to file
		Logger::Instance()->writeToLogFile(std::string("Calculating grid data"), Logger::PROCESS);
		EnergySystem gridSystem;

		gridSystem.AddImmediateLoad(); // add empty container for total load
		gridSystem.GetImmediateLoad(1)->m_identTimeseries.m_objectName = std::string("Grid total load");
		gridSystem.AddImmediateLoad(); // add empty container for net load
		gridSystem.GetImmediateLoad(2)->m_identTimeseries.m_objectName = std::string("Grid net load");
		gridSystem.Init(numTimesteps);

		for (iterSystems = energySystems.begin(); iterSystems != energySystems.end(); ++iterSystems) {
			// add ratepayer total load data to grid system... 1 = total load
			gridSystem.GetImmediateLoad(1)->AddLoadToTimeseries(iterSystems->second.GetAllSystemsTotalLoad());

			// add ratepayer net load data to grid system... 2 = net load
			gridSystem.GetImmediateLoad(2)->AddLoadToTimeseries(iterSystems->second.GetAllSystemsNetLoad());

			// copy ratepayer net load data
			ImmediateLoadData loadData;
			loadData.SetLoadServed(iterSystems->second.GetAllSystemsNetLoad());
			loadData.m_identTimeseries.m_objectName = iterSystems->second.GetName() + std::string(" total load");
			gridSystem.AddImmediateLoad(loadData);

			// add cost data
			gridSystem.AddToGridTotalCharges(iterSystems->second.GetGridTotalCharges());
			gridSystem.AddToGridEnergyCharges(iterSystems->second.GetGridEnergyCharges());
			gridSystem.AddToGridDemandCharges(iterSystems->second.GetGridDemandCharges());
			gridSystem.AddToGridInerconnectionCharges(iterSystems->second.GetGridInterconnectionCharges());
		}

		// calculate grid summary data
		gridSystem.CalculateGridSummaryData();

		// output data
		Logger::Instance()->writeToLogFile(std::string("Writing grid data to file"), Logger::PROCESS);
		std::string gridDir = dataDir + std::string("Grid/output/");
		gridSystem.SetOutputDirectory(gridDir);
		gridSystem.OutputGridTimeseriesDataToFile();
		gridSystem.OutputGridSummaryDataToFile();
		// TODO: calculate grid summary information        
	}
	const char *out = input;
	jstring output = env->NewStringUTF(out);
	return output;
}
