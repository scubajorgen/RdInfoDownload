## RDInfo Download
### RD Info
[RD Info](https://www.pdok.nl/introductie/-/article/rdinfo) is a dataset of PDOK consisting of more than 4000 reference points. It is available as WFS and WMS service, freely to use.

### Usage
It took me quite some time for me to find out how it worked. The 'RD Info' set contains two data sets: 'punten' and 'stations'. A 'punten' member is a named reference point, for example 'O.L. Vrouwetoren Amersfoort'. 

For each reference point (in '**punten**') there are multiple coordinates in the surrounding exactly measured, which are in the second data set '**stations**', for example 'Stang boven knop'. The relation between a '**punten**' member and the corresponding '**stations**' members is by the fields 'blad' and 'punt'. It is important to note that the coordinates in the '**punten**' data set is the average of the coordinates of the corresponding stations, rounded to 1 m!! The  For example, the 'OLV Toren in Amersfoort', the origin of RD, is at [155029, 463001].  Of course the tower itself is at [155000, 463000], by definition.


### This application
I wrote this software that downloads both data sets and creates a set of reference points (as Ozi waypoint file) consisting of the reference points to which the coordinates have been added of the associated station with the lowest 'station' field value (this usually is the reference point itself). The name of the Ozi waypoint is '[punt name] - [station name] - [year of measurement station]', for example 'O.L. vrouwetoren Amersfoort - Stang boven knop - 2012'

The reference points are written to a file **waypoints.wpt** in the directory from which the software is called.

### Dependencies
This program uses [MapDatumConvert version 1.0](https://github.com/scubajorgen/MapDatumConvert), so you have to download and build this first prior to building RdInfoDownload.