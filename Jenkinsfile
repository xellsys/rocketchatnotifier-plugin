#!groovy

// only 20 builds
properties([
  disableConcurrentBuilds(),
  [$class: 'GithubProjectProperty', displayName: '', projectUrlStr: 'https://github.com/jenkinsci/rocketchatnotifier-plugin/'],
  buildDiscarder(logRotator(artifactNumToKeepStr: '20', numToKeepStr: '20'))
])

node('docker') {

  def buildNumber = env.BUILD_NUMBER
  def workspace = env.WORKSPACE
  def buildUrl = env.BUILD_URL

  // PRINT ENVIRONMENT TO JOB
  echo "workspace directory is $workspace"
  echo "build URL is $buildUrl"
  echo "build Number is $buildNumber"
  echo "PATH is $env.PATH"

  try {
    docker.image('cloudbees/java-build-tools').inside {
      stage('Checkout') {
        checkout scm
      }

      stage('Build') {
        sh "mvn clean install"
      }

      stage('Test') {
      }

      junit testResults: 'target/surefire-reports/TEST-*.xml'
      archiveArtifacts artifacts: 'target/*.hpi'
    }

    stage('Deploy') {
      sshagent (credentials: ['github-hypery2k']) {
        sh "mvn deploy -DskipTests=true"
      }
    }
  } catch (e) {
    mail subject: 'Error on build', to: 'github@martinreinhardt-online.de', body: "Please go to ${env.BUILD_URL}."
    throw e
  }

}
