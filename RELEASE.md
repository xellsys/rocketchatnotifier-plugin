# Release new version
```
mvn jgitflow:release-start
conventional-changelog -p angular -i CHANGELOG.md -s && git add CHANGELOG.md
git commit --amend
mvn jgitflow:release-finish -DskipITs=true
git push --all
```
