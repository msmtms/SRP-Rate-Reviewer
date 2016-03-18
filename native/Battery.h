/**
 * @file Battery.h
 * @brief Battery class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef BATTERY_H
#define	BATTERY_H

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

#include "BatteryData.h"
#include "ESDEObject.h"


/******************************************************************************/
class Battery : public ESDEObject, public BatteryData {
    /**
    * @class Battery [Battery.h]
    * @brief Battery class including relevant functions. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    Battery();
    Battery(unsigned int id);
    Battery(const Battery &orig);
    Battery(const Battery &orig, bool all);
    virtual ~Battery();
    
    void Init(unsigned int numTimesteps);
    void UpdateToNextTimestep();
    void SetData( BatteryData &data );
    
    double GetMaximumChargePower();
    double GetMaximumDischargePower();
    void Power( double power );
    
    void CalculateSummaryData();
    
private: 
    double m_currentCapacityAsEnergy;   /**< Current capacity of battery [kWh] */
};


#endif	/* BATTERY_H */

