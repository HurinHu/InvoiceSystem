package com.iceloof.library;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class Tools {
  public Tools() {
  }

  public long currentTimestamp() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return timestamp.getTime();
  }

  public String currentTime(String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    Date date = new Date();
    return dateFormat.format(date);
  }

  public String formatTime(long timestamp, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    Date date = new Date();
    date.setTime(timestamp);
    return dateFormat.format(date);
  }

  public String md5(String str) {
    String md5 = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes());
      byte[] digest = md.digest();
      md5 = DatatypeConverter.printHexBinary(digest).toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return md5;
  }

  public String hash(String passwordToHash, String saltstr){
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte[] salt = saltstr.getBytes();
      md.update(salt);
      byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++){
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPassword;
  }

  public boolean checkPassword(String hash, String attempt, String saltstr){
    String generatedHash = hash(attempt, saltstr);
    return hash.equals(generatedHash);
  }

}