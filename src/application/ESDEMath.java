package application;

import java.util.ArrayList;

public class ESDEMath {
    
    /**
     * Calculates sum of the data array.
     * @param data - data array.
     * @param numPoints - size of the data array.
     * @return sum of the data array.
     */
    public static double CalculateSum( double[] data, int numPoints ) {
        double sum=0.0;

        for( int i=0; i<numPoints; ++i )
            sum += data[i];

        return( sum );
    }
    
    /**
     * Calculates sum of the data array.
     * @param data - data array.
     * @return sum of the data array.
     */
    public static double CalculateSum( ArrayList<Double> data ) {
        double sum = 0.0; 
        int i; 
        int length = data.size();
        
        for( i=0; i<length; ++i ) {
            sum += data.get(i);
        }

        return(sum);
    }
    /**
     * Calculates mean of the data array.
     * @param data - data array.
     * @param numPoints - size of the data array.
     * @return mean of the data array.
     */
    public static double CalculateMean( double[] data, int numPoints ) {
        if( numPoints == 0 ) {
            return (0.0);
        }
        else {
            return( CalculateSum(data, numPoints) / (numPoints) );
        }
    }
    /**
     * Calculates mean of data the array.
     * @param data - data array.
     * @return mean of the data array.
     */
    public static double CalculateMean( ArrayList<Double> data  ) {
        if( data.size() == 0 ) {
            return (0.0);
        }
        else {
            return( CalculateSum(data) / (data.size()) );
        }
    }
    /**
     * Calculates minimum of the data array.
     * @param data - data array.
     * @param numPoints - size of the data array.
     * @return minimum of the data array.
     */
    public static double CalculateMin( double[] data, int numPoints ) {
        if( numPoints == 0 ) {
            return (0.0);
        }
        else {
            double minVal = Double.MAX_VALUE;
            for( int i=0; i<numPoints; ++i )
                minVal = Math.min( minVal, data[i] );
            return( minVal );
        }
    }
    /**
     * Calculates maximum of the data array.
     * @param data - data array.
     * @param numPoints - size of the data array.
     * @return maximum of the data array.
     */
    public static double CalculateMax( double[] data, int numPoints ) {
        if( numPoints == 0 ) {
            return (0.0);
        }
        else {
            double maxVal = Double.MAX_VALUE;
            for( int i=0; i<numPoints; ++i )
                maxVal = Math.max( maxVal, data[i] );
            return( maxVal );
        }
    }
    /**
     * Calculates SummaryStatistics for the data. 
     * @param data - vector of data.
     * @param includeZeros - include zeros when calculating summary statistic.
     * @return summary statistics. 
     */
    public static SummaryStatistics CalculateSummaryStatistics( ArrayList<Double> data , boolean includeZeros ) {
        
        SummaryStatistics stat = new SummaryStatistics(); 
        stat.setMean(0);
        stat.setMax(Double.MIN_VALUE);
        stat.setMin(Double.MAX_VALUE);
        stat.setTotal(0);
        stat.setOperatingTime(0);
        
        if( !includeZeros ) { // ignore zeros in calculating statistics
            int countZeros = 0;
            for( int i = 0; i < data.size(); ++i ) {
                if( data.get(i) != 0 ) {
                    stat.setMean(stat.getMean() + data.get(i));
                    stat.setMax(Math.max( stat.getMax(), data.get(i)));
                    stat.setMin(Math.min( stat.getMin(), data.get(i)));
                }
                else {
                    ++countZeros;
                }
            }
            stat.setOperatingTime(data.size() - countZeros);
            stat.setTotal(stat.getMean()); // mean is currently the total, before dividing by number of points
            stat.setMean(stat.getMean() / (double)( data.size() - countZeros ));
        }
        else { // include zeros in calculating statistics
            for( int i = 0; i < data.size(); ++i) {
            	stat.setMean(stat.getMean() + data.get(i));
                stat.setMax(Math.max( stat.getMax(), data.get(i)));
                stat.setMin(Math.min( stat.getMin(), data.get(i)));
                if( data.get(i) != 0 ) {
                    stat.setOperatingTime(stat.getOperatingTime() + 1);
                }
            }
            stat.setTotal(stat.getMean()); // mean is currently the total, before dividing by number of points
            stat.setMean(stat.getMean() / (double)( data.size()));
        }
        
        return(stat);
    }
    /**
     * Converts Kelvin to Celsius.
     * @param kelvin - temperature in Kelvin.
     * @return temperature in Celsius.
     */
    public static double ConvertKelvinToCelsius( double kelvin ) { 
        return(kelvin - 273.15); 
    }
    /**
     * Converts Celsius to Kelvin.
     * @param celsius - temperature in Celsius.
     * @return temperature in Kelvin.
     */
    public static double ConvertCelsiusToKelvin( double celsius ) { 
        return(celsius + 273.15); 
    }
    /**
     * Converts kWh to MJ. 
     * @param kWh - kilowatt-hours.
     * @return megajoules.
     */
    public static double ConvertkWhToMJ( double kWh ) { 
        return(kWh*3.6); 
    }
    /**
     * Converts MJ to kWh
     * @param MJ - megajoules.
     * @return kilowatt-hours.
     */
    public static double ConvertMJTokWh( double MJ ) { 
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
    
}
