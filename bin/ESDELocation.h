/**
 * @file ESDELocation.h
 * @brief Location data for the energy system. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ESDELOCATION_H
#define	ESDELOCATION_H

/* TODO
 o allow input as degrees
 x allow input as decimal
 o allow toggle of east/west and north/south
 o extend from ESDEObject ? 
 */

#include <utility>
#include <math.h>
#include <iostream>
#include <fstream>

/******************************************************************************/
class ESDELocation {
    /**
    * @class ESDELocation [ESDELocation.h]
    * @brief Location data for the energy system. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public: 
    ESDELocation() {;}
    ~ESDELocation() {;}

    inline void SetLatitudeAsDecimal( double latitude ) { m_latitude = latitude; }
    inline double GetLatitudeAsDecimal() const { return(m_latitude); }
    
    inline void SetLongitudeAsDecimal( double longitude ) { m_longitude = longitude; }
    inline double GetLongitudeAsDecimal() const { return(m_longitude); }
    
    inline void SetTimezone( double timezone ) { m_timezone = timezone; }
    inline double GetTimezone() const { return(m_timezone); }
    
    ESDELocation & operator=(const ESDELocation& location) {
        // no need for static cast to ESDEObjecdt at this time
        m_latitude = location.m_latitude;
        m_longitude = location.m_longitude;
        m_timezone = location.m_timezone;
        return(*this);
    }
    
private:
    double m_latitude;  /**< Latitude of the location [decimal]. Positive for north. */
    double m_longitude; /**< Longitude of the location [decimal]. Positive for east. */
    double m_timezone;  /**< Timezone of the location [hours]. Hours from GMT. */
};

#endif	/* LOCATION_H */

