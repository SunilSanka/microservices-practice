package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

public class HelloWorldBean {
  private String message;
  
  public HelloWorldBean(String msg) {
   this.message = msg;
  }
   
  public void setMessage(String msg) {
	  this.message = msg;
  }
  
  public String toString() {
	  return String.format("HelloWorldBean[]messsage=%s", message);
  }

  public String getMessage() {
	return message;
  }  
}
