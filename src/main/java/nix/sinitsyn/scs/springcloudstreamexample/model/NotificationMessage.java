package nix.sinitsyn.scs.springcloudstreamexample.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class NotificationMessage {

  private String message;
  private Date date;
}
