pipeline {

    agent {

        docker {

            image 'rburton04/bolt-build' 

            args '-v /root/.m2:/root/.m2' 

        }

    }

    stages {

        stage('BOLT Dev API TESTS') { 

            steps {
                //sh 'mvn clean install'
                sh 'mvn gauge:execute -DspecsDir=specs/conference_app/conference_app_jmeter.spec -Denv=dev' 
 
           
           // publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'reports/html-report', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])

            // perfReport modePerformancePerTestCase: true, modeThroughput: true, sourceDataFiles: '**/results*.xml'


        }

      }
      
      stage('BOLT QA API TESTS') { 

            steps {

                sh 'mvn gauge:execute -DspecsDir=specs/conference_app/conference_app_jmeter.spec -Denv=qa' 
 
           
           // publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'reports/html-report', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])

             perfReport modePerformancePerTestCase: true, modeThroughput: true, sourceDataFiles: '**/results*.xml'


        }

      }
        stage('BOLT PreProd UI TESTS') { 

            steps {

                sh 'mvn gauge:execute -DspecsDir=specs/conference_app/UserFeedback.spec -Denv=pre_prod' 
 
           
             publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'reports/html-report', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])

             //perfReport modePerformancePerTestCase: true, modeThroughput: true, sourceDataFiles: '**/results*.xml'


        }

      }
    }
  
}