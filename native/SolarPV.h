/**
 * @file SolarPV.h
 * @brief Solar PV class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

/* TODO
 o input data
 x time series data
 x summary data
 x initialization
 x calculation of summary data
 o include derating factor
 o temperature effects
 o power output calculations
    o flat panel
    o single axis tracking
    o dual axis tracking
 */

#ifndef SOLARPV_H
#define	SOLARPV_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObject.h"
#include "SolarPVData.h"
#include "SolarResource.h"


/******************************************************************************/
class SolarPV : public ESDEObject, public SolarPVData {
    /**
    * @class SolarPV [SolarPV.h]
    * @brief Solar PV class including relevant functions. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    SolarPV();
    SolarPV(unsigned int id);
    SolarPV(const SolarPV &orig);
    SolarPV(const SolarPV &orig, bool all);
    virtual ~SolarPV();
    
    void Init(unsigned int numTimesteps);
    void UpdateToNextTimestep();
    
    void SetData( SolarPVData &data );
    
    void Simulate();
    
    void CalculateSummaryData();
    
public: // accessors
    void SetSolarResource( SolarResource *solarResource ) { m_solarResource = solarResource; }
    SolarResource * GetSolarResource() { return(m_solarResource); }
    
protected:
    SolarResource *m_solarResource;     /**< Solar resource. */
    
private:
    double dayOfYear;
    double timestepInDays;
    
};

#endif	/* SOLARPV_H */

