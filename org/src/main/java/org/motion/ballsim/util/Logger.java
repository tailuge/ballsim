package org.motion.ballsim.util;

public class Logger {

	  private final String name;
	  private boolean enabled = true;
	  
	  public Logger(String class_, boolean enabled_)
	  {
		  name = class_;
		  enabled = enabled_;
	  }

	  public void info(String format)
	  {
	  }
	  
	  public void info(String format, Object arg)
	  {
		  if (enabled)
		  {
			  System.out.println(name); // todo
		  }
	  }

	  public void info(String format, Object arg1, Object arg2)
	  {
	  }
	  
	  public void info(String format, Object[] argArray)
	  {
		  
	  }

}
