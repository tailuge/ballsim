package org.motion.ballsimapp.client.view3d;

import static gwt.g3d.client.math.MatrixStack.PROJECTION;
import gwt.g3d.client.gl2.GL2;
import gwt.g3d.client.shader.AbstractShader;

import org.motion.ballsim.physics.gwtsafe.Vector3D;

public class Camera {

	final protected GL2 gl;
	final protected AbstractShader shader;

	public Camera(GL2 gl, AbstractShader shader) {
		this.gl = gl;
		this.shader = shader;
	}

	public void setAimView(Vector3D pos, Vector3D dir) {
		Vector3D eye = pos.add(-27, dir);
		PROJECTION.pushIdentity();
		PROJECTION.perspective(45, 1, .1f, 120);
		PROJECTION.lookAt((float) -eye.getX(), (float) -eye.getY(), 15f,
				(float) -pos.getX(), (float) -pos.getY(), 5f, 0, 0, 1);
		gl.uniformMatrix(shader.getUniformLocation("uPMatrix"),
				PROJECTION.get());
		PROJECTION.pop();
	}
	
	public void setSpectatorView(Vector3D pos, Vector3D dir) {
		Vector3D eye = Vector3D.ZERO.add(-35, dir);
		PROJECTION.pushIdentity();
		PROJECTION.perspective(45, 1, .1f, 120);
		PROJECTION.lookAt((float) -eye.getX(), (float) -eye.getY(), 75f,
				(float) -pos.getX()/10, (float) -pos.getY()/10, 1f, 0, 0, 1);
		gl.uniformMatrix(shader.getUniformLocation("uPMatrix"),
				PROJECTION.get());
		PROJECTION.pop();
	}

}
