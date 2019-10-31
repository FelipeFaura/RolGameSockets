package com.example.animarol;

public class AsyncTest  {
    public enum ServerActions{
        none,
        Hello
    }
    public static ServerActions serverAction = ServerActions.none;

  public void testMethodHellowServer(){
      serverAction = ServerActions.Hello;
  }

  public void ResetActions(){
      serverAction = ServerActions.none;
  }
}
