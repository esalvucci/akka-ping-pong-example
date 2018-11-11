# Ping Pong example

This is an example of Ping Pong using Akka Actors and Remoting to exchange either the ping and the pong from actors in different locations within the local network.

## Run

First run the Pong actor ensuring that the port in the resources/application.conf is set to 2553

The run the Ping actor. Before running the ping actor change the port in resources/application.conf to 2552
