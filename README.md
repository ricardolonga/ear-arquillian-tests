ear-arquillian-tests
====================

This is and example project showing how to test method invocation on an EJB inside an EAR from another EAR (deployed on the same application server) using the Java 8 Lamda feature and Arquillian.

### Why Lamdas? ###

I had to create an Integration Test using the wonderful Arquillian framework, which had to call an EJB inside an existing EAR. It was desired to be able to test:

1. Calls to the EJB using a standalone EJB client
2. Calls to the EJB from withing another deployment on the same application server

The first required test is easy to implement using standard EJB invocation mechanisms. I implemented the second test by creating a test EAR using arquillian, which gets deployed together with the target ear onto an application server. I had to create a Wrapper EJB, which contained methods for each test invocation on the target bean and returned the result to the client. 

This approach had some disadvantages:

1. The test code is not inside the test case, but inside the Wrapper EJB
2. I is neccessary to create a wrapper method for each method of the EJB I had to test.

The Lamda feature introduced with Java 8 comes here to the rescue. Using Lamdas it is possible to have the Target EJB method invocation coded inside the test case and passed to the Test EAR, which will do the actual invocation using the target EJB object. The Test EAR EJB therefore only needs to have 2 methods, independant how much methods the EJB I have to test has. One to register the lamda and one to execute the lamda method.

### Used technologies and Frameworks ###

The project was built using the versions below:
- maven: 3.1.1
- jdk: 1.8.0_05
- wildfly: 8.0.0.Final

The vesions of arquillian and all other dependencies are defined inside the pom.xml files.
Wildfly must be downloaded and extracted in order to be able to run the tests.

The project is a multi-module maven project with the following modules:
- api => the API describing the EJB 
- impl => the EJB implementation
- ear => the configuration to create an EAR containing the EJB
- test => the arquillian tests calling the EJB inside the EAR

To build it and run the test, the following steps must be done:
1. change the _arquillian.xml_ to point to the extracted wildfly you just downloaded
2. execute _mvn -Dmaven.settingsFile='path to your maven settings-xml' install_ from the project root or run the test cases using Eclipse (you have to set the maven.settingsFile property in Eclipse using "Run configurations..")
