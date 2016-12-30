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
  def projectVersion = projectVersion()
  def mavenVersion = mavenVersion()

  // PRINT ENVIRONMENT TO JOB
  echo "workspace directory is $workspace"
  echo "mavenVersion is $mavenVersion"
  echo "projectVersion is $projectVersion"
  echo "build URL is $buildUrl"
  echo "build Number is $buildNumber"
  echo "PATH is $env.PATH"

  try {
    stage('Checkout') {
      checkout scm
    }

    stage('Build') {
      sh "mvn clean install"
    }

    stage('Test') {
      sh "mvn -Pdocker clean verify"
    }

    junit testResults: 'target/surefire-reports/TEST-*.xml,target/failsafe-reports/TEST-*.xml'
    archiveArtifacts artifacts: 'target/*.hpi'

    stage('Deploy') {
      sshagent(credentials: ['github-hypery2k']) {
        if (mavenVersion && !mavenVersion.contains('SNAPSHOT')) {
          // release build
          sh "mvn deploy -DskipTests=true"
          // nightly build
        } else {
          sh "mvn deploy -DskipTests=true -DfinalName=rocketchatnotifier_${projectVersion}-alpha-build_${buildNumber}"
        }
      }
    }
  } catch (e) {
    mail subject: 'Error on build', to: 'github@martinreinhardt-online.de', body: "Please go to ${env.BUILD_URL}."
    throw e
  }

}

def mavenVersion() {
  def file = readFile('pom.xml')
  def project = new XmlSlurper().parseText(file)
  return project.version.text()
}

def projectVersion() {
  def file = readFile('pom.xml')
  def project = new XmlSlurper().parseText(file)
  return project.version.text().replaceAll("-RELEASE","").replaceAll("-SNAPSHOT","")
}
