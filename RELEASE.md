# Steps
```
conventional-changelog -p angular -i CHANGELOG.md -s && git add CHANGELOG.md && git commit -m 'updated CHANGELOG.md'
eval "$(ssh-agent -s)"
mvn release:prepare release:perform -e
```
