/**
 * @file SolarFunctions.cpp
 * @brief Common functions used in  solar resource and solar generator calculations. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#include <map>
#include <cassert>
#include <math.h>
#include <iostream>
#include <fstream>

#include "SolarFunctions.h"
#include "ESDEMath.h"

/**
 * Calculates the diffuse ratio given the clearness index using Erb's correlation.
 * Eq. 2.10.1 in Duffie and Beckmann, 2nd ed.
 * @param clearnessIndex - clearness index.
 * @return diffuse ratio.
 */
double CalculateDiffuseRatio_Erbs( double clearnessIndex ) {
    double diffuseRatio;  // the ratio of diffuse radiation to global radiation

    if ( clearnessIndex < 0.0 ) // this should never happen
        diffuseRatio = 1.0;
    else if ( clearnessIndex <= 0.22 )
        diffuseRatio = 1.0 - 0.09 * clearnessIndex;
    else if ( clearnessIndex <= 0.80 )
        diffuseRatio = 0.9511 - 0.1604 * clearnessIndex + 4.388 * pow(clearnessIndex,2) 
            - 16.638 * pow(clearnessIndex,3) + 12.336 * pow(clearnessIndex,4);
    else
        diffuseRatio = 0.165;

    return(diffuseRatio);
}
/**
 * Calculates solar time. 
 * @param localTime - local time or civil time [fractional] from 0 to 24. 
 * @param timezone - timezone of the location from GMT [hours]. 
 * @param longitude - longitude of the location [decimal]. 
 * @param dayOfYear - day of year [fractional] starting at 0 for Jan 1. 
 * @return solar time [fractional] from 0 to 24. 
 */
double CalculateSolarTime( double localTime, double timezone, double longitude, double dayOfYear ) {

    double solarTime = localTime + (longitude / 15.0) - timezone;
    
    // calculate the equation of time
    double solarLocale = (2 * PI) * ( dayOfYear - 1.0 ) / 365.0; // radians
    solarTime += 3.82 * ( 0.000075 + 0.001868 * cos(solarLocale) - 0.032077 * sin(solarLocale) 
                 - 0.014615 * cos(2.0*solarLocale) - 0.04089 * sin(2.0*solarLocale) );
    
    // ensure the solar time is between zero and 24 hours
    while( solarTime < 0.0 )
        solarTime += 24.0;
    while( solarTime > 24.0)
        solarTime -= 24.0;
    
    return(solarTime);
}
/**
 * Calculates the hour angle. The hour angle equals zero at solar noon.
 * @param solarTime - solar time [fractional] from 0 to 24.
 * @return hour angle [radians]. 
 */
double CalculateHourAngle( double solarTime ) {
    double hourAngle = ( PI / 180.0 ) * ( solarTime - 12.0 ) * 15.0;
    return( hourAngle );
}
/**
 * Calculates the angle at which the sun sets.
 * Eq. 1.6.1 from Duffie and Beckman, 2nd ed.
 * @param latitude - location latitude [radians].
 * @param solarDeclination - solar declination [radians].
 * @return sunset hour angle [radians].
 */
double CalculateSunsetHourAngle( double latitude, double solarDeclination ) {
    
    double cosOfSunsetHourAngle, sunsetHourAngle;
    
    cosOfSunsetHourAngle = -1.0 * tan( latitude ) * tan( solarDeclination );
    
    // handle for special cases
    if( cosOfSunsetHourAngle >= 1.0 )
        sunsetHourAngle = 0.0;  // set to zero if sun doesn't come up (polar regions in winter)
    else if( cosOfSunsetHourAngle <= -1.0 )
        sunsetHourAngle = PI;   // sun is up all day (polar regions in summer)
    else
        sunsetHourAngle = acos(cosOfSunsetHourAngle); // all regions
    
    return( sunsetHourAngle );
}
/**
 * Calculates the solar declination, the latitude at which the sun's rays are 
 * perpendicular to the earth's surface at solar noon. Eq. 1.6.1 from Duffie and Beckman, 2nd ed.
 * @param dayOfYear - day of year [fractional] starting at 0 for Jan 1. 
 * @return solar declination [radians].
 */
double CalculateSolarDeclination( double dayOfYear ) {
    double declination = 23.45 * sin( ConvertDegreesToRadians( 360.0 * ( 284 + dayOfYear ) / 365.0 ) );
    return( ConvertDegreesToRadians( declination ) ); 
}
/**
 * Calculates the solar azimuth in degrees West of South. The equation is modified
 * from that listed on "en.wikipedia.org/wiki/Solar_azimuth_angle" because the 
 * Eq. given on Wikipedia has a convention in degrees East of North instead of West of South. 
 * @param latitude - latitude of the location [radians].
 * @param solarDeclination - solar declination [radians].
 * @param solarAltitude - solar altitude [radians].
 * @param hourAngle - hour angle [radians].
 * @return solar azimuth.
 */
double CalculateSolarAzimuth( double latitude, double solarDeclination, double solarAltitude, double hourAngle ) {
    
    // TODO: verify if this equations from this function provides correct results
    // --> is this because solar azimuth here is 0 to the east instead of with 
    //     PV azimuth is 0 to the south? 
    // --> I believe this should be between -90 (set) and 90 (about to come up)
    // --> yeah, the first two statements are never hit TRUE, that is wrong
    // --> update the comments accordingly
    // calculate two angle measures to identify quadrant of unit circle
    double sinOfAzimuth, cosOfAzimuth;
    sinOfAzimuth = ( sin(hourAngle) * cos(solarDeclination) ) / cos(solarAltitude);
    
    // now find out the quadrant, and calculate the final value to be between -180 and +180
    double solarAzimuth, principalValueOfTheta;
    if( sinOfAzimuth >= 1.0 ) { // sun preparing to come up in the East
        solarAzimuth = PI / 2.0; // 90 degrees
    }
    else if ( sinOfAzimuth <= -1.0 ) { // sun has set in the West
        solarAzimuth = -PI / 2.0; // -90 degrees
    }
    else { // between -180 and +180... but we need to find out which quadrant
        principalValueOfTheta = asin(sinOfAzimuth); // between -90 and +90
        
        double cosOfAzimuth = ( cos(hourAngle) * cos(solarDeclination) * sin(latitude) - sin(solarDeclination) * cos(latitude) ) / cos(solarAltitude);

        if( cosOfAzimuth < 0.0 ) { // between 90 and 270
            if( sinOfAzimuth > 0.0) { // between 0 and +180 
                solarAzimuth = PI - principalValueOfTheta; // 180 - [0,90]... between +90 and +180
            }
            else { // between -180 and 0
                solarAzimuth = -PI - principalValueOfTheta; // -180 - [-90,0]... between -180 and -90
            }
        }
        else { // between -90 and 90
            solarAzimuth = principalValueOfTheta; // between -90 and +90
        }
    }
    
    return( solarAzimuth );
}
/**
 * Calculates the solar altitude from the zenith angle.
 * @param zenithAngle - zenith angle [radians].
 * @return solar altitude [radians].
 */
double CalculateSolarAltitude( double zenithAngle ) {
    return( fmax( PI/2.0 - zenithAngle, 0.0 ) );
}
/**
 * Calculates the zenith angle, the angle of deviation between the normal of a 
 * flat surface on the earth to the sun's beam component.
 * @param latitude - location latitude [radians].
 * @param solarDeclination - solar declination [radians].
 * @param hourAngle - hour angle [radians].
 * @return zenith angle [radians].
 */
double CalculateZenithAngle( double latitude, double solarDeclination, double hourAngle ) {
    double cosThetaZ = sin( solarDeclination ) * sin( latitude )
                     + cos( solarDeclination ) * cos( latitude ) * cos( hourAngle );

    return( acos( cosThetaZ ) );
}
/**
 * Calculates incident angle, the angle of deviation between the normal of a 
 * tilted surface to the sun's beam component.
 * @param latitude - location latitude [radians].
 * @param solarDeclination - solar declination [radians].
 * @param hourAngle - hour angle [radians].
 * @param slope - slope of incident surface [radians].
 * @param azimuth - azimuth of incident surface [radians].
 * @return incident angle [radians].
 */
double CalculateIncidentAngle( double latitude, double solarDeclination, double hourAngle, double slope, double azimuth ) {    
    double cosTheta = sin( solarDeclination ) * sin( latitude ) * cos( slope )
                    - sin( solarDeclination ) * cos( latitude ) * sin( slope ) * cos( azimuth )
                    + cos( solarDeclination ) * cos( latitude ) * cos( slope ) * cos( hourAngle )
                    + cos( solarDeclination ) * sin( latitude ) * sin( slope ) * cos( azimuth ) * cos( hourAngle )
                    + cos( solarDeclination ) * sin( slope ) * sin( azimuth ) * sin( hourAngle );
    
    return( acos( cosTheta ) );
}
/**
 * Calculates the tilt ratio, a relationship between the incident angle of the 
 * tilted surface and the zenith angle.
 * @param incidentAngle - angle of deviation of a sloped surface [radians].
 * @param zenithAngle - zenith angle [radians]. 
 * @return diffuse ratio.
 */
double CalculateTiltRatio( double incidentAngle, double zenithAngle ) {
    double Rb = cos(incidentAngle) / cos(zenithAngle);
    
    if( Rb < 0.0 ) {
        Rb = 0.0; // no negative values
    }
    else if( Rb > 5.0 ) {
        Rb = 5.0; // no unrealistic high values
    }

    return(Rb);
}

//return W/m2
double CalculateExtraterrestrialNormalRadiation( double dayOfYear ) {
    return( 1.367 * ( 1.0 + 0.033 * cos( ConvertDegreesToRadians( 360.0 * dayOfYear / 365.0 ) ) ) * 1000.0);
}

double CalculateExtraterrestrialHorizontalRadiationOverTimestep( double latitude, double dayOfYear, double solarTime, double sunsetHourAngle, double solarDeclination, double timestepHourlyFraction ) {
    
    double G_ext_h; 
    double G_ext_n = CalculateExtraterrestrialNormalRadiation( dayOfYear );
    
    double hourAngle1 = CalculateHourAngle( solarTime - timestepHourlyFraction / 2.0 );
    double hourAngle2 = CalculateHourAngle( solarTime + timestepHourlyFraction / 2.0 );

    // don't compute before sunrise or after sunset
    if ( sunsetHourAngle != PI/2.0 ) {
        if( hourAngle1 < 0.0 ) {
            hourAngle1 = fmax(hourAngle1, -sunsetHourAngle);
        }
        else {
            hourAngle1 = fmin(hourAngle1, sunsetHourAngle);
        }
        if( hourAngle2 < 0.0 ) {
            hourAngle2 = fmax(hourAngle2, -sunsetHourAngle);
        }
        else {
            hourAngle2 = fmin(hourAngle2, sunsetHourAngle);;
        }
    }

    // units of kW/m2 using timestepHourlyFraction, 
    G_ext_h = ( 12.0 / PI ) * G_ext_n * ( cos( latitude ) * cos( solarDeclination ) 
                                        * ( sin( hourAngle2 ) - sin( hourAngle1 ) ) 
                                        + ( hourAngle2 - hourAngle1 ) * sin( latitude ) 
                                        * sin( solarDeclination ) ) / timestepHourlyFraction;

    return( fmax( G_ext_h, 0.0f ) );
}