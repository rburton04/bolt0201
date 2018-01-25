# BOLT Test Example

This is an example project for starting web automation testing with [BOLT Test](http://www.boltiq.io).

There are some default spec files to start from and some demo specs to gain a better understanding of how it works.

## Features

* Pre-Built library of selenium actions
* Pre-Built library of test steps
* Pre-Built spec files
* Support for adding custom actions and steps
* Property files for different environments
* Support for negative testing
* Organized way to manage element definitions if needed

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

1. Java 8 [Install instructions](https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html)
2. Intellij (community edition) [Install instructions](https://www.jetbrains.com/idea/download/)
3.	Gauge [Install instructions](https://gauge.org/get-started.html)
4.	Gauge plugin for Intellij [Install instructions](https://docs.gauge.org/using.html#intellij-idea)


### Installing

A step by step guide to run BOLT Test locally

1. Clone the BOLT Test sample project: {future link}
2.	Open Intellij and create a new project from an existing source.
3.	Select the BOLT Test sample project folder as the source.
4.	Select “Import project from external model” and select “Maven”, then click “Next”.
5.	Continue clicking “Next” until prompted to select the project SDK. At this point, if “1.8” is not already displayed, click the “+” in the top left corner, navigate to where java was
installed, and select the folder for java 8 (1.8). Once that is complete, click “Next” and then “Finish”.
6.	At this point, it will take a few minutes for Intellij to download all of the maven dependencies. 
7.	Once that is completed, go to the pom.xml file, find the com.swatsolutions.bolt dependency, and add your username and password. It is recommended to use a hashed version of the password
for security reasons. [Hashing instructions](https://maven.apache.org/guides/mini/guide-encryption.html)
Say what the step will be


## Writing tests

Tests are written in files called specification (spec) files. A spec file will contain the tests for either a single page or a single feature. To cover negative tests,
a second spec should be written for each page or feature and the title should end with "-NEGATIVE" to identify it as a negative test. This will disable much of the default
validation that is built into BOLT Test to help prevent unintended test failures.

Often, there will be a series of steps that is the same for many different tests. To reduce the risk for boredom, carpal tunnel, and to reduce the number of steps, there are
a few ways that this repetition can be reduced. The first is by using setup and teardown steps. Setup steps are written before the first test in a spec file. These steps will
be run before every test in that spec. Teardown steps are written at the end of a spec file after a line containing only "---" and these steps will be run after every test in
the given spec. In addition to setup and teardown steps, multiple steps can be combined using a concept file. A concept file can contain many groupings of steps within one file.
Concepts can take in variables and need to be given unique names. When using a concept in a test case, simply start writing the step as any other and just use the unique name
for the concept instead of a step.

When writing tests, tags can be added to a spec file, or to just a specific test to mark it as a particular type of test. When running tests via command line, a single (or multiple)
tags can be defined and only tests with the given tag(s) will be run. This can be very helpful for marking tests as regression tests, smoke tests, performance tests, etc.

To get a better understanding of how to write tests, please look at the demo spec files designed to show all of these features.

## Running the tests

Tests can be run in two ways, through command line or through IntelliJ (or whichever IDE is being used). To run a test via the IDE, the run button can be selected for either a
complete spec file, or for just a single test in a given spec file. To run via command line, navigate to the home directory for BOLT Test. To determine the exact command, use the
following table:

|Command/configuration	    |Description
|---------------------------|------------------
|mvn gauge:execute	        |Primary Command
|-DspecsDir=	            |A specific spec or folder of specs can be specified to run
|-DinParallel=	            |“true or false”, specifies to run in parallel or not.
|-Dnodes=	                |The maximum number of nodes to use. Uses all nodes if not included.
|-Dtags=	                |“tag1 & tag2” Run tests only with the given tag(s).
|-Denv=	                    |How to specify what environment to run the tests against.
|-Ddir=	                    |Working directory for gauge. Default is project.base.dir
|-Dflags=””	                |Add additional flags to the execution


## Runtime Report
While BOLT Test is executing, there is a flash report that can be monitored to see where the tests are and how they have run so far. It does not contain the detailed information
of the HTML Report, but it can provide an idea of what stage the tests are at and how they are running.

## HTML Report
After each execution of tests on BOLT Test is completed, an HTML report is generated with the results. This report opens to a summary page showing the number of specs that
passed or had failures. By selecting a spec, the step-by-step results can be observed for each test. In the case of data-driven tests, a table will be displayed and by selecting
each row, the step-by-step results can be observed. When a step fails, a screenshot will be attached to the failed step along with the reason for the failure.

## Building custom actions

BOLT Test is designed to be expanded and customized by each client as it is impossible to predict every situation that needs to be tested. If an action does not currently
exist in BOLT Test, a custom action can easily be built to do anything. There is a folder named "CustomSteps" which contains a starting point/example for adding custom actions
and steps.

At the start of the new Java class, two objects will need to be created. The first object is a "Driver" object to get the webdriver for intacting with the browser. The second
object to be created is a "SeleniumSmartActions" object to gain access to all of the existing building blocks for Selenium.

//TODO this could also be built to extend SelemniumSmartActions (probably a better idea)

From this point, new methods can be created that use the webdriver and existing methods from SeleniumSmartActions to complete any task required.

## Building custom steps

BOLT Test is designed to be expanded upon and customized for each client as it is impossible to predict every situation that needs to be tested. If a step is needed that does
not already exist, a custom step can easily be built for the task. There is a folder named "CustomSteps" which contains a starting point/example for adding custom steps.

At the start of the new Java class, an object will need to be created for either "SeleniumSmartActions" and/or whatever custom action classes which have been built. From this point,
a method for the new step can be built to complete whatever task is needed. As a note, it is not recommended to use webdriver in this class. All direct interactions with the browser
using Selenium webdriver should be contained within an existing or custom action method/class. After creating the method to complete the task that is needed, the method needs to be
turned into a step. This is done by adding "@Step("insert step here with a <variable>")" directly before the method. Variables are surrounded by '<>' and are passed into the
method. It is recommended to keep the same variable names and order from the step definition and the method parameters.

## Deployment

This system is designed for use with a CI/CD development process to aid in testing software and catching bugs before software is released. BOLT Test is able to run locally or on a fully automated CI/CD pipeline.

## Built With

* [Gauge](http://gauge.org) - The test building framework
* [Selenium](http://www.seleniumhq.org) - The tool to directly interact with a browser
* [JMeter](http://jmeter.apache.org) - The primary API and Performance testing tool
* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing
N/A

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use a private Maven server for versioning. To pull the BOLT Test Library from this server, there is a username and password already provided for the free version.
To use the full version, please go to the [BOLT Website](http://www.boltiq.io) for purchasing information.

## Authors

* **Swat Solutions** - *Built and maintains*

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the ... For the full version of BOLT Test, please refer to the [BoltIQ](www.boltiq.io) website for pricing information.

## Acknowledgments
* Hat tip to anyone who's code was used
* Inspiration
* etc

* Selenium
* [Gauge](https://gauge.org)
* JMeter
* Java
* RedHat
* Additional Resources
