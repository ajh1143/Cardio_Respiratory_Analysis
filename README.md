# Cardio_Respiratory_Analysis

Research software for parsing electrophysiological data recorded from live specimen.
Takes in raw data, exports a csv table for use in excel/R/Python etc to allow further data analysis. 

Accepts header-less data, catergorizes the data, computes respiratory periods and bins cardiac output within each period based
on time. 

Algorithm for binning heart beats within respiratory periods(Inspiration/Expiration). Allows easy export to describing latency between beats and total frequency. Useful for determining physiological and chemical treatment conditions and their effects on overall cardiorespiratory rhythm.  

