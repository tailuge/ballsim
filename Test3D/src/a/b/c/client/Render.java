package a.b.c.client;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;

import org.motion.ballsim.physics.gwtsafe.Vector3D;

public class Render extends Assets {

	protected void placeTable() {
		if (tableTexture != null) {
			tableTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate(0, 0, 0.0f);
		MODELVIEW.scale(1f, 1f, 1f);
		setMatrixUniforms();
		tableMesh.draw();
		MODELVIEW.pop();
	}

	protected void placeBall(int ballId, double x, double y, double px,
			double py, double pz, double ux, double uy, double uz, double cx,
			double cy, double cz) {
		if (ballTextures.containsKey(ballId)) {
			ballTextures.get(ballId).bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -x, (float) -y, 0);
		MODELVIEW.lookAt(0, 0, 0, (float) -px, (float) -py, (float) -pz,
				(float) -ux, (float) -uy, (float) -uz);
		setMatrixUniforms();
		ballMesh.draw();
		MODELVIEW.pop();

		placeShadow(x, y);
		// debugRoll(-x,-y,-px,-py,-pz);
		// debugRoll(-x,-y,-ux,-uy,-uz);
	}

	protected void placeShadow(double x, double y) {
		if (shadowTexture != null) {
			shadowTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -x, (float) -y, -0.99f);
		MODELVIEW.scale(1, 1, 0.01f);
		MODELVIEW.rotateX((float) Math.PI / 2);
		setMatrixUniforms();
		shadowMesh.draw();
		MODELVIEW.pop();
	}

	protected void placeCue(Vector3D inputPos, double inputAngle, Vector3D inputSpin, double thrust) {
		if (cueTexture != null) {
			cueTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -inputPos.getX(), (float) -inputPos.getY(), 0f);
		MODELVIEW.rotateZ((float) (-inputAngle + Math.PI));
		MODELVIEW.translate((float)inputSpin.getX(), (float) -thrust*2, 0.0f - (float)inputSpin.getY());
		MODELVIEW.scale(1, 1, 1);
		setMatrixUniforms();
		cueMesh.draw();
		MODELVIEW.pop();

		// placeCueShadow(x, y, angle);
	}

	protected void placeCueShadow(double x, double y, double angle) {
		if (shadowTexture != null) {
			shadowTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -x, (float) -y - 24f, -0.99f);
		MODELVIEW.rotateZ((float) (-angle + Math.PI));
		MODELVIEW.scale(0.25f, 20, 1);
		MODELVIEW.rotateX((float) Math.PI / 2);

		setMatrixUniforms();
		cueShadowMesh.draw();
		MODELVIEW.pop();
	}

	protected void debugRoll(double x, double y, double px, double py, double pz) {
		if (shadowTexture != null) {
			shadowTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) px, (float) py, (float) pz);
		MODELVIEW.translate((float) x, (float) y, 0);
		MODELVIEW.scale(0.2f, 0.2f, 0.2f);
		setMatrixUniforms();
		ballMesh.draw();
		MODELVIEW.pop();
	}

}
