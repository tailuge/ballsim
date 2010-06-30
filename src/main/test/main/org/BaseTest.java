package main.org;
import org.junit.Assert;
import org.junit.Test;



public class BaseTest 
{

	 @Test
     public final void testObject() 
     {    
             Assert.assertNotNull("object ok", 1);           
     }

	 @Test
     public final void testState() 
     {    
             Assert.assertFalse(State.Rolling == State.Sliding);           
     }

}
