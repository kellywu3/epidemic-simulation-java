Epidemic Simulation
===================
Introduction:
-------------

The Epidemic Simulation simulates the progression of an epidemic. Users can control various factors of the epidemic.

<img src="./docs/epidemic-simulation-demo.gif" width="500" alt="Epidemic Simulation Demo Image" title="Epidemic Simulation Demo">


This program was inspired by 3Blue1Brown's YouTube video,  ['Simulating an Epidemic'](https://www.youtube.com/watch?v=gxAaO2rsdIs).

Epidemic Simulation includes features that can be controled by the user, such as turning on and off 'destination' that allows subjects to move to the center of its own or other communities, 'communities', that divides the subjects evenly into different, assigned communities, and 'quarantine', that puts the infected subjects into a quarantied area after a certain amount of time. The total number of susceptible (blue), infected (red), and removed (grey) subjects are listed in a graph and chart. The simulation can be paused and reset as well.

This is still a work in progress. Additional features are listed in 'To Do'.

Instructions:
-------------
* Status Panel: Located at the bottom of the window.
    * Check boxes turn off and on destination, communities, quarantine, and subject description options
    * Buttons pause, restart, or reset default values
* Simulation Control Panel: Located at the lower left column of the window.
    * Input and enter values that change the simulation accordingly
    * Includes 
the number, mass, and friction factor of subjects, infection radius, number of community rows/columns, odds of traveling to destination (when the destination option is enabled), odds of infection transmission, min/max time of infection/destination stay, and delay before quarantining. 
* Susceptible, Infected, Removed (SIR) Chart: Located at the middle left column of the window.
    * Displays the graphed trend of all the subject's health statuses
* Susceptible, Infected, Removed (SIR) Panel: Located at the upper left column of the window.
    * Displays the time and the number of subjects at each health status
    * Includes
time index, number of susceptible, infected, and removed subjects, the max number of infected subjets, and the time of the epidemic's eradication
* Simulation Display: Located at the center right of the window.
    * Displays the simulation of subjects and transmission

Reuqirements:
-------------
* Java - 1.8 and up
* Maven - 3.6.3 and up

Build and Run:
--------------
```shell
mvn install

java -jar target/epidemic-simulation-java-*-jar-with-dependencies.jar
```

To Do's:
-------
* Section dividers in simulation control panel
* Adjust the time scale to ticks per day

Known Bugs:
-----------
* Subjects are sometimes initialized in the corner
* Subjects get stuck in their community

Screenshots:
------------

