/* 
 * File:   RateSchedule.h
 * Author: Nate
 *
 * Created on February 8, 2016, 2:29 AM
 */

#ifndef RATESCHEDULE_H
#define	RATESCHEDULE_H

#include <iostream>
#include <fstream>
#include <vector>
#include <iterator>
#include <map>

/******************************************************************************/
struct Rate {
    
    Rate() {
        m_name = "";
        m_id = -1;
    }
    std::string m_name;
    int m_id;
    
    std::map<double,double> m_energyPrice;
    std::map<double,double> m_feedinTariff;
    std::map<double,double> m_demandCharge;
    
    // TODO: create lookup to calculate from tiered data
};

struct RateSchedule {

    RateSchedule() {
        m_name = "";
        m_netMeteringID = 0;
        m_overproductionCredit = 0;
        m_interconnectionCharge = 0;
    }
    
    std::string m_name;
    std::map<int, Rate> m_rates;    // list of all rates
    // TODO: map to all rates
    
    unsigned int m_netMeteringID;   // 0 = none, 1 = monthly, 2 = annual
    double m_overproductionCredit;  // applies when net metering is enabled (ID = 1 or 2) [$/kWh]
    double m_interconnectionCharge; // basic connection fee to grid [$/mo]
};


#endif	/* RATESCHEDULE_H */

