package com.iceloof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.iceloof.library.Result;
import com.iceloof.model.Data;
import com.iceloof.model.User;
import com.iceloof.model.Error;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

  @Test
  @Order(1)
  public void testUserCreateSuccess() throws SQLException {
    Database db = Mockito.mock(Database.class);
    Data data = new Data(db);
    List<Object> expected_db = new ArrayList<Object>();
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    ResultSet rs = Mockito.mock(ResultSet.class);
    Mockito.when(connection.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
    Mockito.when(db.update()).thenReturn(expected_db);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    Result expected_result = new Result("200 OK", "User created");
    Result actual_result = (Result)data.createUser("test", "test@test.com", "testpassword", "User", response);
    assertEquals(expected_result.toString(), actual_result.toString());
  }

  @Test
  @Order(2)
  public void testUserCreateConflict() throws SQLException {
    Database db = Mockito.mock(Database.class);
    Data data = new Data(db);
    List<Object> expected_db = new ArrayList<Object>();
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    Mockito.when(connection.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
    Mockito.when(db.update()).thenReturn("Duplicate entry 'test' for key 'name'");
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    Error expected_result = new Error("CONFLICT", "User name or email is exist");
    Error actual_result = (Error)data.createUser("test", "test@test.com", "testpassword", "User", response);
    assertEquals(expected_result.toString(), actual_result.toString());
  }

  @Test
  @Order(3)
  public void testUserCreateFail() throws SQLException {
    Database db = Mockito.mock(Database.class);
    Data data = new Data(db);
    List<Object> expected_db = new ArrayList<Object>();
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    Mockito.when(connection.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
    Mockito.when(db.update()).thenReturn("Insert fail");
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    Error expected_result = new Error("BAD_REQUEST", "400 Bad Request");
    Error actual_result = (Error)data.createUser("test", "test@test.com", "testpassword", "User", response);
    assertEquals(expected_result.toString(), actual_result.toString());
  }

  @Test
  @Order(4)
  public void testUserLoginByName() throws SQLException {
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
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    User expected_user = new User(1, "test", "test@test.com", "User");
    User actual_user = (User)data.loginByName("test", "testpassword", response, session);
    assertEquals(expected_user.toString(), actual_user.toString());
  }

  @Test
  @Order(5)
  public void testUserLoginByNameFail() throws SQLException {
    Database db = Mockito.mock(Database.class);
    Data data = new Data(db);
    List<Object> expected_db = new ArrayList<Object>();
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    ResultSet rs = Mockito.mock(ResultSet.class);
    Mockito.when(connection.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
    Mockito.when(stmt.executeQuery()).thenReturn(rs);
    Mockito.when(db.get()).thenReturn(expected_db);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    Error expected_result = new Error("AUTH_FAILED", "Either username or password was incorrect");
    Error actual_result = (Error)data.loginByName("test", "testpassword", response, session);
    assertEquals(expected_result.toString(), actual_result.toString());
  }

  @Test
  @Order(6)
  public void testUserLoginByEmail() throws SQLException {
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
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    User expected_user = new User(1, "test", "test@test.com", "User");
    User actual_user = (User)data.loginByEmail("test@test.com", "testpassword", response, session);
    assertEquals(expected_user.toString(), actual_user.toString());
  }

  @Test
  @Order(7)
  public void testUserLoginByEmailFail() throws SQLException {
    Database db = Mockito.mock(Database.class);
    Data data = new Data(db);
    List<Object> expected_db = new ArrayList<Object>();
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    ResultSet rs = Mockito.mock(ResultSet.class);
    Mockito.when(connection.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
    Mockito.when(stmt.executeQuery()).thenReturn(rs);
    Mockito.when(db.get()).thenReturn(expected_db);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    Error expected_result = new Error("AUTH_FAILED", "Either email or password was incorrect");
    Error actual_result = (Error)data.loginByEmail("test@test.com", "testpassword", response, session);
    assertEquals(expected_result.toString(), actual_result.toString());
  }

}