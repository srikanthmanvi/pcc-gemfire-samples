# bootstrap
### Spinning up a GemFire Cluster locally 

This module helps with spinning up a GemFire Cluster locally.
 
#### Spinning up a cluster locally.

Execute _startServers.sh_ from the root of this module by running the command `./startServers.sh`.

#### Connecting to the cluster

You can use the GemFire CLI _gfsh_ to explore the cluster. The version of _gfsh_ should be the 
same version of GemFire cluster you created. _gfsh_ can be started as described [here](http://gemfire.docs.pivotal.io/97/geode/tools_modules/gfsh/starting_gfsh.html)
Once you are in _gfsh_ just run the command `connect` which should connect you to the cluster.
You can then run commands like `list members`, `list regions`, `describe region --name=Customer`.

#### Notes:
-  _startServers.sh_ executes _gfsh_ commands from _bootstrap/scripts/startServers.sh_
which by defaults creates a cluster of 1 locator and 3 servers running on localhost. Servers are
configured as per the files in _bootstrap/resources/_ director.

- Servers and locators create files (logs, diskstore files etc) under _bootstart/tmp_ directory 
which gets cleaned when you execute _startServers.sh_.
