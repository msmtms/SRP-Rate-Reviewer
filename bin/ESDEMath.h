/**
 * @file ESDEMath.h
 * @brief Math class. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef ESDEMATH_H
#define	ESDEMATH_H

#include <math.h>
#include <float.h>
#include <vector>

/******************************************************************************/
#define PI (4.0 * atan(1.0))
/******************************************************************************/
struct SummaryStatistics {
    SummaryStatistics() {mean=-1; min=-1; max=-1; total=-1; operatingTime=0;}
    
    double mean;
    double min;
    double max;
    double total;
    unsigned int operatingTime; // non-zero values in array denote operating time
};
/******************************************************************************/
inline double CalculateSum( const float* data, const unsigned int numPoints );
inline double CalculateSum( const std::vector<double> &data );
inline double CalculateMean( const double* data, const unsigned int numPoints );
inline double CalculateMean( const std::vector<double> &data );
inline double CalculateMax( const double* data, const unsigned int numPoints );
inline double CalculateMin( const double* data, const unsigned int numPoints );
inline SummaryStatistics CalculateSummaryStatistic( std::vector< double > &data, bool ignoreZeros );
inline float InterpolateExtrapolateFromTwoPoints( float x1, float y1, float x2, float y2, float x3 );

inline double ConvertKelvinToCelsius( double kelvin );
inline double ConvertCelsiusToKelvin( double celsius );
inline double ConvertkWhToMJ( double kWh );
inline double ConvertMJTokWh( double MJ );

inline float ConvertDegreesToRadians( float degrees ) { return( degrees / 180.0 * PI ); }
inline float ConvertRadiansToDegrees( float radians ) { return( radians / PI * 180.0 ); }
/******************************************************************************/
inline int GetFirstHourOfMonth(int iMonth);
inline int GetLastHourOfMonth(int iMonth);
inline int GetFirstDayOfMonth(int iMonth);
inline int GetDaysInMonth(int iMonth);
/******************************************************************************/
/**
 * Calculates sum of the data array.
 * @param data - data array.
 * @param numPoints - size of the data array.
 * @return sum of the data array.
 */
inline double CalculateSum( const double* data, const unsigned int numPoints ) {
    double sum=0.0;

    for( unsigned int i=0; i<numPoints; ++i )
        sum += data[i];

    return( sum );
}
/**
 * Calculates sum of the data array.
 * @param data - data array.
 * @return sum of the data array.
 */
inline double CalculateSum( const std::vector<double> &data ) {
    double sum = 0.0; 
    unsigned int i; 
    unsigned int length = data.size();
    
    for( i=0; i<length; ++i ) {
        sum += data.at(i);
    }

    return(sum);
}
/**
 * Calculates mean of the data array.
 * @param data - data array.
 * @param numPoints - size of the data array.
 * @return mean of the data array.
 */
inline double CalculateMean( const double *data, const unsigned int numPoints ) {
    if( numPoints == 0 ) {
        return (0.0);
    }
    else {
        return( CalculateSum(data, numPoints) / double(numPoints) );
    }
}
/**
 * Calculates mean of data the array.
 * @param data - data array.
 * @return mean of the data array.
 */
inline double CalculateMean( const std::vector<double> &data ) {
    if( data.size() == 0 ) {
        return (0.0);
    }
    else {
        return( CalculateSum(data) / double(data.size()) );
    }
}
/**
 * Calculates minimum of the data array.
 * @param data - data array.
 * @param numPoints - size of the data array.
 * @return minimum of the data array.
 */
inline double CalculateMin( const double *data, const unsigned int numPoints ) {
    if( numPoints == 0 ) {
        return (0.0);
    }
    else {
        double minVal = DBL_MAX;
        for( unsigned int i=0; i<numPoints; ++i )
            minVal = fmin( minVal, data[i] );
        return( minVal );
    }
}
/**
 * Calculates maximum of the data array.
 * @param data - data array.
 * @param numPoints - size of the data array.
 * @return maximum of the data array.
 */
inline double CalculateMax( const double *data, const unsigned int numPoints ) {
    if( numPoints == 0 ) {
        return (0.0);
    }
    else {
        float maxVal = DBL_MIN;
        for( unsigned int i=0; i<numPoints; ++i )
            maxVal = fmax( maxVal, data[i] );
        return( maxVal );
    }
}
/**
 * Calculates SummaryStatistics for the data. 
 * @param data - vector of data.
 * @param includeZeros - include zeros when calculating summary statistic.
 * @return summary statistics. 
 */
inline SummaryStatistics CalculateSummaryStatistics( std::vector< double > &data, bool includeZeros ) {
    
    SummaryStatistics stat; 
    stat.mean = 0;
    stat.max = DBL_MIN;
    stat.min = DBL_MAX;
    stat.total = 0;
    stat.operatingTime = 0;
    
    std::vector< double >::iterator iter;
    
    if( !includeZeros ) { // ignore zeros in calculating statistics
        unsigned int countZeros;
        for( iter = data.begin(); iter != data.end(); ++iter ) {
            if( *iter != 0 ) {
                stat.mean += *iter;
                stat.max = fmax( stat.max, *iter );
                stat.min = fmin( stat.min, *iter );
            }
            else {
                ++countZeros;
            }
        }
        stat.operatingTime = data.size() - countZeros;
        stat.total = stat.mean; // mean is currently the total, before dividing by number of points
        stat.mean /= (double)( data.size() - countZeros );
    }
    else { // include zeros in calculating statistics
        for( iter = data.begin(); iter != data.end(); ++iter ) {
            stat.mean += *iter;
            stat.max = fmax( stat.max, *iter );
            stat.min = fmin( stat.min, *iter );
            if( *iter != 0 ) {
                stat.operatingTime += 1;
            }
        }
        stat.total = stat.mean; // mean is currently the total, before dividing by number of points
        stat.mean /= (double)data.size();
    }
    
    return(stat);
}
/**
 * Converts Kelvin to Celsius.
 * @param kelvin - temperature in Kelvin.
 * @return temperature in Celsius.
 */
inline double ConvertKelvinToCelsius( double kelvin ) { 
    return(kelvin - 273.15); 
}
/**
 * Converts Celsius to Kelvin.
 * @param celsius - temperature in Celsius.
 * @return temperature in Kelvin.
 */
inline double ConvertCelsiusToKelvin( double celsius ) { 
    return(celsius + 273.15); 
}
/**
 * Converts kWh to MJ. 
 * @param kWh - kilowatt-hours.
 * @return megajoules.
 */
inline double ConvertkWhToMJ( double &kWh ) { 
    return(kWh*3.6); 
}
/**
 * Converts MJ to kWh
 * @param MJ - megajoules.
 * @return kilowatt-hours.
 */
inline double ConvertMJTokWh( double &MJ ) { 
    return(MJ/3.6); 
}



// Nate edit below

/**
 * InterpolateExtrapolateFromTwoPoints:
 *   Interpolates or extrapolates from two 2D points.
 * @param [in] float x1
 * @param [in] float y1
 * @param [in] float x2
 * @param [in] float y2
 * @param [in] float x3
 * @return float  - dependent variable corresponding to x3.
 */
float InterpolateExtrapolateFromTwoPoints( float x1, float y1, float x2, float y2, float x3 ) {
    float dx3to1 = x3 - x1;
    float slope = (y2 - y1) / (x2 - x1);
    
    return(y1 + slope * dx3to1);
}

int GetFirstHourOfMonth(int iMonth) { 
    return(24 * GetFirstDayOfMonth(iMonth));
}
int GetLastHourOfMonth(int iMonth) { 
    return(GetFirstHourOfMonth(iMonth) + 24 * GetDaysInMonth(iMonth) - 1);
}
int GetFirstDayOfMonth(int iMonth) {
    // not setup to do multiple years
    if( iMonth == 0 )
        return (0);
    else if( iMonth == 1 )
        return(31);
    else if( iMonth == 2 ) // no leap year
        return(59);
    else if( iMonth == 3 )
        return(90);
    else if( iMonth == 4 )
        return(120);
    else if( iMonth == 5 )
        return(151);
    else if( iMonth == 6 )
        return(181);
    else if( iMonth == 7 )
        return(212);
    else if( iMonth == 8 )
        return(243);
    else if( iMonth == 9 )
        return(273);
    else if( iMonth == 10 )
        return(304);
    else if( iMonth == 11 )
        return(334);
    else
        return(-1);
}

int GetDaysInMonth(int iMonth) {
    if( iMonth == 0 )
        return(31);
    else if( iMonth == 1 )
        return(28);
    else if( iMonth == 2 ) // no leap year
        return(31);
    else if( iMonth == 3 )
        return(30);
    else if( iMonth == 4 )
        return(31);
    else if( iMonth == 5 )
        return(3);
    else if( iMonth == 6 )
        return(31);
    else if( iMonth == 7 )
        return(31);
    else if( iMonth == 8 )
        return(30);
    else if( iMonth == 9 )
        return(31);
    else if( iMonth == 10 )
        return(30);
    else if( iMonth == 11 )
        return(31);
    else
        return(-1);
}

#endif	/* ESDEMATH_H */

