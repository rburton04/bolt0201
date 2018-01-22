try {
   timeout(time: 20, unit: 'MINUTES') {
      def appName="openshift-bolt-sample"
      def project=""
      node {
        stage("Initialize Bolt Node") {
          project = env.PROJECT_NAME
        }
      }
      node("bolt3") {
        stage("Checkout") {
          git url: "https://rburton04@bitbucket.org/gaugeTestForSwat/gaugetest.git", branch: "demo"
        }
        stage("Run Bolt Tests") {
         // sh "curl -SsL https://downloads.getgauge.io/stable | sh -s -- --location=/usr/bin"
         // sh "gauge install java"
         // sh "mvn clean install"
          sh "mvn gauge:execute -DspecsDir=specs/conference_app/conference_app_jmeter.spec"
            //publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'reports/html-report', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            perfReport modePerformancePerTestCase: true, modeThroughput: true, sourceDataFiles: '**/results*.xml'
          
        }
      }
      
   }
} catch (err) {
   echo "in catch block"
   echo "Caught: ${err}"
   currentBuild.result = 'FAILURE'
   throw err
}