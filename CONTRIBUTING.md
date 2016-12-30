# Contributing to RocketChat Jenkins Notifier Plugin

We'd love for you to contribute to our source code and to make this package even better than it is
today! Here are the guidelines we'd like you to follow:

 - [Issues and Bugs](#issue)
 - [Feature Requests](#feature)
 - [Coding Rules](#rules)
 - [Commit Message Guidelines](#commit)

## <a name="issue"></a> Found an Issue?

If you find a bug in the source code or a mistake in the documentation, you can help us by
submitting an issue to our [JIRA](https://issues.jenkins-ci.org/browse/JENKINS-39690?jql=project%20%3D%20JENKINS%20AND%20component%20%3D%20rocket-chat-notifier-plugin). Even better you can submit a Pull Request
with a fix.

First search if the issue is already described!

If not create a new issue:

* Tell about your environment:
  * operating system and version
  * Jenkins version
  * Java version
  * RocketChat version
* Describe your issue
  * describe your steps leading to the issue
  * attach error logs or screenshots
  * if possible provide test case or screenshots

## <a name="feature"></a> Want a Feature?

You can request a new feature by submitting an issue in [JIRA](https://issues.jenkins-ci.org/browse/JENKINS-39690?jql=project%20%3D%20JENKINS%20AND%20component%20%3D%20rocket-chat-notifier-plugin).

## <a name="rules"></a> Coding Rules

To ensure consistency throughout the source code, keep these rules in mind as you are working:

* All features or bug fixes **must be tested** by one or more uni test.
* All public API methods **must be documented** with JavaDoc.

## <a name="commit"></a> Git Commit Guidelines

We're using [Angular Commit Guidelines](https://github.com/angular/angular.js/blob/master/CONTRIBUTING.md#-git-commit-guidelines)


## <a name="debug"></a> Debug plugin

To debug the plugin in jenkins:
```
export MAVEN_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n"
mvn hpi:run
```

Now you can debug the plugin via port 8000
