package jenkins.plugins.rocket.model;

public abstract class Identified implements Comparable<Identified> {
  private String _id;

  public String getId() {
    return this._id;
  }

  @Override
  public boolean equals(Object obj) {
    return ((Identified) obj)._id.equals(_id);
  }

  @Override
  public int hashCode() {
    return _id.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getName() + "@" + _id;
  }

  @Override
  public int compareTo(Identified o) {
    return toString().compareTo(o.toString());
  }
}
