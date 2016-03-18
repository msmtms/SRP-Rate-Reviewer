/**
 * @file SolarField.h
 * @brief Sub-system of concentrating solar power system. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef SOLARFIELD_H
#define	SOLARFIELD_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObject.h"
#include "SolarResource.h"
#include "SolarFieldData.h"

/******************************************************************************/
class SolarField : public ESDEObject, public SolarFieldData {
    /**
    * @file SolarField [SolarField.h]
    * @brief Sub-system of concentrating solar power system.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
    
public:
    SolarField();
    SolarField(unsigned int id);
    SolarField(const SolarField& orig);
    SolarField(const SolarField &orig, bool all);
    virtual ~SolarField();
    
    void Init(unsigned int numTimesteps);
    void UpdateToNextTimestep();
    void SetData( SolarFieldData &data );
    
    void SetIdentification( ESDEIdentification &ident );
    
    void Simulate();
    
    void CalculateSummaryData();
    
public: // accessors
    void SetSolarResource( SolarResource *solarResource ) { m_solarResource = solarResource; }
    SolarResource * GetSolarResource() { return(m_solarResource); }
    
protected:
    SolarResource *m_solarResource;     /**< Solar resource. */
};


#endif	/* SOLARFIELD_H */

