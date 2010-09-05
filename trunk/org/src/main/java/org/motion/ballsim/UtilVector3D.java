package org.motion.ballsim;


import org.motion.ballsim.gwtsafe.MathRuntimeException;
import org.motion.ballsim.gwtsafe.Vector3D;


public class UtilVector3D 
{
	
	public static Vector3D crossUp(Vector3D vec)
	{
		return Vector3D.crossProduct(vec, Vector3D.PLUS_K);
	}

	public static Vector3D rnd()
	{
		try {
			return (new Vector3D(Math.random()-0.5,Math.random()-0.5,0.0)).normalize();
		} catch (MathRuntimeException e) {
			// try again
			return rnd();
		}
	}

	public static Vector3D reflectAlongAxis(Vector3D arg, Vector3D axis)
	{
		if (axis.getY() == 0)
			return new Vector3D(-arg.getX(),arg.getY(),0);

		return new Vector3D(arg.getX(),-arg.getY(),0);
	}
	
	public static double projectionOnAxis(Vector3D arg, Vector3D axis)
	{
		return Vector3D.dotProduct(arg,axis);
	}

}
