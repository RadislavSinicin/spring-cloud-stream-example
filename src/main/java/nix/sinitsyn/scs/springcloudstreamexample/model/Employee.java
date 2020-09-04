package nix.sinitsyn.scs.springcloudstreamexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

  private String name;
  private int age;
}
