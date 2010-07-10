package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

public class UtilVector3D 
{
	
	public static Vector3D crossUp(Vector3D vec)
	{
		return Vector3D.crossProduct(vec, Vector3D.PLUS_K);
	}

	public static Vector3D rnd()
	{
		return (new Vector3D(Math.random()-0.5,Math.random()-0.5,0.0)).normalize();
	}
}
