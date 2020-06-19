# sustain-kNN

Primary purpose of this project is to read required query conditions from a property file
named config.properties and connect to sustain-dht. Next, extract relevant sketches, format them
in required format and upload to the MongoDB.

Currently, config.properties file can hold below properties:

* host=lattice-80.cs.colostate.edu
* port=9091
* dataset=noaa_2015_jan
* geohashes=9x, 9y8b9
* fromTimestamp=2015-01-01 00:00
* toTimestamp=2015-01-08 23:59
* intermediate.output.file=./data/

Adding any new properties will require code changes in sustain.kNN.utility.PropertyLoader class.
Further, the values of these properties will be used to populate the query
to extract sketches from the storage.
