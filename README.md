ear-arquillian-tests
====================

I had to create an Integration Test using the wonderful Arquillian framework, which had to call an EJB inside an existing EAR. It was desired to be able to test:
Calls to the EJB using a standalone EJB client
Calls to the EJB from withing another deployment on the same application server
It took some time to get it working, therefore I decided to post an example project showing how I got it working - hoping it might help some people.

The project was built using the versions below:

- maven: 3.1.1
- jdk: 1.7.0_51

The application server I used is wildfly 8.0.0.Final - it must be downloaded and extracted in order to be able to run the tests.

The project is a multi-module maven project with the following modules:

- api: the API describing the EJB 
- impl: the EJB implementation
- ear: the configuration to create an EAR containing the EJB
- test: the arquillian tests calling the EJB inside the EAR

To build it and run the test, the following steps must be done:

1. change the _arquillian.xml_ to point to the extracted wildfly you just downloaded
2. change the _AbstractGreeterImplInsideEarTest.java_ class to use your maven settings file
3. execute mvn install from the project root or run the test cases using Eclipse 
