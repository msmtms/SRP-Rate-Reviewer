/**
 * @file ESDEObject.h
 * @brief All ESDE objects are derived from this parent class.
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ESDEOBJECT_H
#define	ESDEOBJECT_H

#include <iostream>

#include "ESDEObjectData.h"

/******************************************************************************/
class ESDEObject {
   /**
    * @class ESDEObject [ESDEObject.h]
    * @brief All ESDE objects are derived from this parent class.
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    ESDEObject();
    ESDEObject(const ESDEObject& orig);
    virtual ~ESDEObject();
    
    inline void SetObjectIdentification( ESDEIdentification &ident) { m_identObject = ident; }
    inline ESDEIdentification & GetObjectIdentification() { return(m_identObject); }
    
    void SetObjectType( unsigned int objectType ) { m_identObject.m_objectType = objectType; }
    unsigned int GetObjectType() { return(m_identObject.m_objectType); }
    
    void SetObjectID( unsigned int objectID ) { m_identObject.m_objectID = objectID; }
    int GetObjectID() { return(m_identObject.m_objectID); }
    
    void SetObjectName(std::string objectName) {m_identObject.m_objectName = objectName;}
    std::string GetObjectName() {return m_identObject.m_objectName;}
    
    inline void SetNumTimesteps( unsigned int numTimesteps ) { m_numTimesteps = numTimesteps; }
    inline unsigned int GetNumTimesteps() { return(m_numTimesteps); }
    
    inline void SetTimestepHourlyFraction( double timestepHourlyFraction ) { m_timestepHourlyFraction = timestepHourlyFraction; }
    inline double GetTimestepHourlyFraction() { return(m_timestepHourlyFraction); }
    
    // functions to override
    inline virtual void UpdateToNextTimestep() {;}
    
    inline virtual void CalculateSummaryData() {;}

protected:
    ESDEIdentification m_identObject;   /**< Identification information for the type of object. */
    
    unsigned int m_numTimesteps;        /**< Number of timesteps in a simulation. */
    double m_timestepHourlyFraction;    /**< Fraction of timesteps in one hour. */
};

#endif	/* ESDEOBJECT_H */

