#!/usr/bin/env bash
git checkout master
mvn jgitflow:release-start
mvn jgitflow:release-finish -DnoDeploy=true
git checkout master
conventional-changelog -p angular -i CHANGELOG.md -s && git add CHANGELOG.md
git commit -m "Changelog"
git push --all
