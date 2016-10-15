#!groovy

// only 20 builds
properties([buildDiscarder(logRotator(artifactNumToKeepStr: '20', numToKeepStr: '20'))])

node('docker') {
    deleteDir()

    def buildNumber = env.BUILD_NUMBER
    def workspace = env.WORKSPACE
    def buildUrl = env.BUILD_URL


    // PRINT ENVIRONMENT TO JOB
    echo "workspace directory is $workspace"
    echo "build URL is $buildUrl"
    echo "build Number is $buildNumber"
    echo "PATH is $env.PATH"

    docker.image('cloudbees/java-build-tools').inside {
      try {
        stage('Checkout') { 
           checkout scm
        }

        stage('Build') {
           sh "mvn package"
        }

        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        step([$class: 'ArtifactArchiver', artifacts: '*/target/*.hpi'])

        }
    } catch (e){
       mail subject: 'Error on build', to: 'contact@martinreinhardt-online.de'
       throw e
    }

}
