/**
 * @file SolarFunctions.h
 * @brief Common functions used in  solar resource and solar generator calculations. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef SOLARFUNCTIONS_H
#define	SOLARFUNCTIONS_H

#include <vector>

/* calculations for solar components */
double CalculateDiffuseRatio_Erbs( double clearnessIndex );
    
/* time angles for the sun */
double CalculateSolarTime( double localTime, double timezone, double longitude, double dayOfYear );
double CalculateHourAngle( double solarTime );
double CalculateSunsetHourAngle( double latitude, double solarDeclination );

/* solar angles for the sun's positioning */
double CalculateSolarDeclination( double dayOfYear );
double CalculateSolarAzimuth( double latitude, double solarDeclination, double solarAltitude, double hourAngle );
double CalculateSolarAltitude( double zenithAngle );

/* incident angles on global horizontal or tilted surfaces */
double CalculateZenithAngle( double latitude, double solarDeclination, double hourAngle );
double CalculateIncidentAngle( double latitude, double solarDeclination, double hourAngle, double slope, double azimuth );
double CalculateTiltRatio( double incidentAngle, double zenithAngle );

/* series of calculations for radiation data */
double CalculateExtraterrestrialNormalRadiation( double dayOfYear );
double CalculateExtraterrestrialHorizontalRadiationOverTimestep( double latitude, double dayOfYear, double solarTime, double sunsetHourAngle, double solarDeclination, double timestepHourlyFraction );
//double CalculateExtraterrestrialHorizontalRadiationOverOneDay() {return(0.0);}
//double CalculateExtraterrestrialHorizontalRadiationOverOneMonth() {return(0.0);}
//double CalculateExtraterrestrialHorizontalRadiationOverOneYear() {return(0.0);}

#endif	/* SOLARFUNCTIONS_H */

