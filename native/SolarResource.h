/**
 * @file SolarResource.h
 * @brief Solar resource class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef SOLARRESOURCE_H
#define	SOLARRESOURCE_H

/* TODO
 o input data
 o calculate synthetic solar
 o compute kt in another function
 o calculate global solar components
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObject.h"
#include "ESDELocation.h"
#include "SolarResourceData.h"

/******************************************************************************/
class SolarResource : public ESDEObject, public SolarResourceData {
    /**
    * @class SolarResource [SolarResource.h]
    * @brief Solar resource class including relevant function. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    SolarResource();
    SolarResource(unsigned int id);
    SolarResource(const SolarResource &orig);
    SolarResource(const SolarResource &orig, bool all);
    virtual ~SolarResource();
    
    void Init();
    void UpdateToNextTimestep();
    void SetData( SolarResourceData &data );
    void SetIdentification( ESDEIdentification &ident );
    
    void CalculateSummaryData();
    
    void Simulate();
    void SimulateGlobalHorizontalRadiation();
    void SimulateSun();
    
public: // calculations
    void CalculateClearnessIndex(); 
    void CalculateSolarComponents();
    
private: 

};


#endif	/* SOLARRESOURCE_H */

