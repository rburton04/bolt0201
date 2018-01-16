# BOLT Test Example

This is an example project for doing web automation testing with [BOLT Test](http://www.boltiq.io).

There are some default spec files to start from and some demo specs to gain a better understanding of how it works.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

1. Java 8 [Install instructions](https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html)
2. Intellij (community edition) [Install instructions](https://www.jetbrains.com/idea/download/)
3.	Gauge [Install instructions](https://gauge.org/get-started.html)
4.	Add the Gauge plugin to Intellij [Install instructions](https://docs.gauge.org/using.html#intellij-idea)


### Installing

A step by step guide to run BOLT Test locally

1. Clone the BOLT Test sample project: {future link}
2.	Open Intellij and create a new project from an existing source.
3.	Select the BOLT Test sample project folder as the source.
4.	Select “Import project from external model” and select “Maven”, then click “Next”.
5.	Continue clicking “Next” until prompted to select the project SDK. At this point, if “1.8” is not already displayed, click the “+” in the top left corner, navigate to where java was installed, and select the folder for java 8 (1.8). Once that is complete, click “Next” and then “Finish”.
6.	At this point, it will take a few minutes for Intellij to download all of the maven dependencies. 
7.	Once that is completed, go to the pom.xml file, find the com.swatsolutions.bolt dependency, and add your username and password. It is recommended to use a hashed version of the password for security reasons. [Hashing instructions](https://maven.apache.org/guides/mini/guide-encryption.html)
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

## Running the tests

Tests can be run in two ways, through command line or through IntelliJ (or whichever IDE is being used). To run a test via the IDE, the run button can be selected for either a
complete spec file, or for just a single test in a given spec file. To run via command line, navigate to the home directory for BOLT Test. To determine the exact command, use the
following table:

Command/configuration	    Description
---------------------------|------------------
mvn gauge:execute	        Primary Command
-DspecsDir=	                A specific spec or folder of specs can be specified to run
-DinParallel=	            “true or false”, specifies to run in parallel or not.
-Dnodes=	                The maximum number of nodes to use. Uses all nodes if not included.
-Dtags=	                    “tag1 & tag2” Run tests only with the given tag(s).
-Denv=	                    How to specify what environment to run the tests against.
-Ddir=	                    Working directory for gauge. Default is project.base.dir
-Dflags=””	                Add additional flags to the execution



### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

This system is designed for use with a CI/CD development process to aid in testing software and catching bugs before software is released. BOLT Test is able to run locally or on a fully automated CI/CD pipeline.

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing
N/A

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags).

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
