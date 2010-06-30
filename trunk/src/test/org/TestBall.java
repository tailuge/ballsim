package motion.org;

import org.junit.Assert;
import org.junit.Test;

public class TestBall {

	@Test
	public final void testObject() 
	{
		Ball b = new Ball();
		Assert.assertNotNull("object ok", b);		
	}

	@Test
	public final void testAdvance() 
	{
		Ball b = new Ball();
		b.advance(0.0);
	}

}
