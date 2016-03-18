/**
 * @file ImmediateLoad.h
 * @brief Immediate load class including relevant functions. 
 * @author Nathan Johnson
 * @date 2014-09-29
 * @version 0.0.1 - initial release
 */

#ifndef IMMEDIATELOAD_H
#define	IMMEDIATELOAD_H

/* TODO
 o properties data
 x time series data
 o curtailed and unmet load
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

#include "ESDEObject.h"
#include "ImmediateLoadData.h"

/******************************************************************************/
class ImmediateLoad : public ESDEObject, public ImmediateLoadData {
    /**
    * @class ImmediateLoad [ImmediateLoad.h]
    * @brief Immediate load class including relevant functions. 
    * @author Nathan Johnson
    * @date 2014-09-29
    * @version 0.0.1 - initial release
    */
public:
    ImmediateLoad();
    ImmediateLoad(unsigned int id);
    ImmediateLoad(const ImmediateLoad &orig);
    ImmediateLoad(const ImmediateLoad &orig, bool all);
    virtual ~ImmediateLoad();
    
    void Init(unsigned int numTimesteps);
    
    void SetData( ImmediateLoadData &data );
    
    void UpdateToNextTimestep();

    void CalculateSummaryData();
    
private: 
    
};

#endif	/* IMMEDIATELOAD_H */

