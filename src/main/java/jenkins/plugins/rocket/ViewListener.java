package jenkins.plugins.rocket;

import hudson.model.Result;
import hudson.model.View;

public interface ViewListener {

  void fireViewChanged(View view, Result oldResult, Result newResult);

}
