#!groovy

// only 20 builds
properties([
  disableConcurrentBuilds(),
  buildDiscarder(logRotator(artifactNumToKeepStr: '20', numToKeepStr: '20'))
])

buildPlugin(platforms: ['linux'], jdkVersions: [7, 8], findbugs: [archive: true, unstableTotalAll: '0'], checkstyle: [run: true, archive: true])
