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




## Running the tests

Explain how to run the automated tests for this system

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
* Gauge
* JMeter
* Java
* RedHat
* Additional Resources
