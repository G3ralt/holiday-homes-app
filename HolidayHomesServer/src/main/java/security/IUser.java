package security;

import java.util.List;

public interface IUser {
  List<String>  getRolesAsStrings();
  String getUsername();
  String getPasswordHash();
}
