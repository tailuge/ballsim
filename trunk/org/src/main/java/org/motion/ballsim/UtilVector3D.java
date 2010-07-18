package org.motion.ballsim;

import org.apache.commons.math.geometry.Vector3D;

import com.google.common.base.Function;

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
	
	public static Function<Vector3D,Double> getX = new Function<Vector3D, Double>() {
		
		public Double apply(Vector3D arg) {
			return arg.getX();
		}
	};

	public static Function<Vector3D,Double> getY = new Function<Vector3D, Double>() {
		
		public Double apply(Vector3D arg) {
			return arg.getY();
		}
	};
	
	public static Function<Vector3D,Vector3D> reflectX = new Function<Vector3D, Vector3D>() {	

		public Vector3D apply(Vector3D arg) {
			return new Vector3D(-arg.getX(),arg.getY(),0);
		}
	};

	public static Function<Vector3D,Vector3D> reflectY = new Function<Vector3D, Vector3D>() {	

		public Vector3D apply(Vector3D arg) {
			return new Vector3D(arg.getX(),-arg.getY(),0);
		}
	};

}
