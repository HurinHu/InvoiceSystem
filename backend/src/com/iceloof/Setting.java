package com.iceloof;

public class Setting {
  public String db = "invoicesystem";
  public String db_url = "jdbc:mysql://invoicesystem_db:3306";
  public String db_user = "root";
  public String db_pwd = "root";
  public String[] public_access = {"/", ".*.(html|js|css|json|png|jpg|ico|txt)", "^/static/.*$", "/api/register", "/api/auth", "/api/logout"};
  public String table_user = "users";
  public String user_id = "id";
  public String user_name = "name";
  public String user_email = "email";
  public String user_password = "password";
  public String user_role = "role";
  public String created_time = "created_time";
}