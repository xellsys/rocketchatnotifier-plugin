#!/usr/bin/env bash
git checkout develop
mvn jgitflow:release-start && \
 mvn jgitflow:release-finish -DnoDeploy=true && \
 git checkout master && \
 conventional-changelog -p angular -i CHANGELOG.md -s -r 0 && \
 git add CHANGELOG.md && \
 git commit -m "Changelog" && \
 git push --all && \
 git checkout develop
