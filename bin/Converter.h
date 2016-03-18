/* 
 * File:   Converter.h
 * Author: Nate
 *
 * Created on November 2, 2015, 1:30 AM
 */

#ifndef CONVERTER_H
#define	CONVERTER_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObject.h"
#include "ConverterData.h"

/******************************************************************************/
class Converter : public ESDEObject, public ConverterData {

public:
    Converter();
    Converter(unsigned int id);
    Converter(const Converter &orig);
    Converter(const Converter &orig, bool all);
    virtual ~Converter();
    
    void Init(unsigned int numTimesteps);
    void UpdateToNextTimestep();
    
    double CalculateOutputPossible( double input );
    double CalculateOutput( double input );
    double CalculateInputToZeroLoad( double output ) { return(output/m_efficiency); }
    
    void SetData( ConverterData &data );
    
    void CalculateSummaryData();
    
};

#endif	/* CONVERTER_H */

