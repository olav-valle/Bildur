package no.ntnu.bildur.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class PhotoTag implements Serializable {
  private String value;

  public PhotoTag(String value){
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
