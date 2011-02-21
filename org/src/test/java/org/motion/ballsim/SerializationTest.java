package org.motion.ballsim;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.motion.ballsim.gwtsafe.Vector3D;
import org.motion.ballsim.physics.Ball;
import org.motion.ballsim.physics.Event;
import org.motion.ballsim.physics.Table;
import org.motion.ballsim.util.UtilEvent;


public class SerializationTest {
	
	@Test
	public final void testSerialiseVector() throws IOException, ClassNotFoundException 
	{
		Vector3D t = new Vector3D(0.5,1,2);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(out);
	    oos.writeObject(t);
	    oos.close();
	    System.out.println(out.toByteArray().length+" bytes");
	    
	    assertTrue(out.toByteArray().length > 0);
	    
	    Object o = deserialize(out);
	    Vector3D copy = (Vector3D) o;
	    
	    assertTrue(copy.getX()==t.getX());
	    
	}

	@Test
	public final void testSerialiseEvent() throws IOException, ClassNotFoundException 
	{
		Event t = UtilEvent.stationary(new Vector3D(1,2,3));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(out);
	    oos.writeObject(t);
	    oos.close();
	    System.out.println(out.toByteArray().length+" bytes");
	    
	    assertTrue(out.toByteArray().length > 0);
	    
	    Object o = deserialize(out);
	    Event copy = (Event) o;
	    
	    assertTrue(copy.pos.getX()==t.pos.getX());
	    
	}

	@Test
	public final void testSerialiseBall() throws IOException, ClassNotFoundException 
	{
		Ball t = new Ball(3);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(out);
	    oos.writeObject(t);
	    oos.close();
	    System.out.println(out.toByteArray().length+" bytes");
	    
	    assertTrue(out.toByteArray().length > 0);
	    
	    Object o = deserialize(out);
	    Ball copy = (Ball) o;
	    
	    assertTrue(copy.id==t.id);	    
	}

	@Test
	public final void testSerialiseTable() throws IOException, ClassNotFoundException 
	{
		Table t = new Table();
		t.ball(1).setFirstEvent(UtilEvent.stationary(new Vector3D(1,2,3)));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(out);
	    oos.writeObject(t);
	    oos.close();
	    System.out.println(out.toByteArray().length+" bytes");
	    
	    assertTrue(out.toByteArray().length > 0);
	    
	    //deserialize
	    Object o = deserialize(out);
	    Table copy = (Table) o;
	    
	    assertTrue(copy.ball(1).id == t.ball(1).id );	    
	}


	
	private Object deserialize(ByteArrayOutputStream out) throws IOException,
			ClassNotFoundException {
		byte[] pickled = out.toByteArray();
	    InputStream in = new ByteArrayInputStream(pickled);
	    ObjectInputStream ois = new ObjectInputStream(in);
	    Object o = ois.readObject();
		return o;
	}

}
