#!/usr/bin/env bash
rm -r gemfire-server1/ gemfire-server2/ locator1/
gfsh run --file=startServer.gfsh

#gfsh -e "start server --name=server1 --J=-Dgfsh.log-level=fine"
#
#gfsh -e "debug --state=on" -e "start server --name=server1"
#gfsh -e "set variable --name=gfsh.log-level --value=fine" -e "start server --name=server1"
#
#debug --state=on
