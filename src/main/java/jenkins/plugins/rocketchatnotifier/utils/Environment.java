package jenkins.plugins.rocketchatnotifier.utils;

public class Environment {


  public String getHttpProxy() {
    String value = System.getenv("http_proxy");
    return value == null ? "" : value;
  }

  public String getHttpsProxy() {
    String value = System.getenv("https_proxy");
    return value == null ? "" : value;
  }

}
