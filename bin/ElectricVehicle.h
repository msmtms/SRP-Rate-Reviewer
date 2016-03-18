/* 
 * File:   ElectricVehicle.h
 * Author: Nate
 *
 * Created on November 3, 2015, 2:37 PM
 */

#ifndef ELECTRICVEHICLE_H
#define	ELECTRICVEHICLE_H


/* TODO
 x input data
 x time series data
 x summary data
 x initialization
 x calculation of summary data
 o battery models
    o kibam
    o ...
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ElectricVehicleData.h"
#include "ESDEObject.h"


/******************************************************************************/
class ElectricVehicle : public ESDEObject, public ElectricVehicleData {
    /**
    * @class ElectricVehicle [ElectricVehicle.h]
    * @brief Electric vehicle class including relevant functions. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    ElectricVehicle();
    ElectricVehicle(unsigned int id);
    ElectricVehicle(const ElectricVehicle &orig);
    ElectricVehicle(const ElectricVehicle &orig, bool all);
    virtual ~ElectricVehicle();
    
    void Init(unsigned int numTimesteps);
    void UpdateToNextTimestep();
    void SetData( ElectricVehicleData &data );
    
    double GetMaximumChargePower();
    void CreateChargerLoadProfile();
    unsigned int CalculateChargeHours();
    double GetChargerLoad();
    
    void CalculateSummaryData();
    
private: 
    double m_currentCapacityAsEnergy;   /**< Current capacity of storage [kWh] */
};


#endif	/* ELECTRICVEHICLE_H */

