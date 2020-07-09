package com.iceloof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import com.iceloof.library.Database;
import com.iceloof.model.Data;
import com.iceloof.model.User;

public class UserTest {

  @Test
  public void testUserLogin() throws SQLException {
    Database db = Mockito.mock(Database.class);
    Data data = new Data(db);
    List<List<Object>> expected_db = new ArrayList<List<Object>>();
    List<Object> expected_row = new ArrayList<Object>();
    expected_row.add(Double.parseDouble("1.0"));
    expected_row.add("test");
    expected_row.add("test@test.com");
    expected_row.add("User");
    expected_db.add(expected_row);
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    ResultSet rs = Mockito.mock(ResultSet.class);
    Mockito.when(connection.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
    Mockito.when(stmt.executeQuery()).thenReturn(rs);
    Mockito.when(db.get()).thenReturn(expected_db);
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    User expected_user = new User(1, "test", "test@test.com", "User");
    User actual_user = (User)data.loginByName("test", "testpassword", request, response);
    assertEquals(expected_user.toSring(), actual_user.toSring());
  }
}