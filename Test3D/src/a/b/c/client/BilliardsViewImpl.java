/*
 * Copyright 2009 Hao Nguyen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package a.b.c.client;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;
import static gwt.g3d.client.math.MatrixStack.PROJECTION;
import gwt.g3d.client.gl2.enums.ClearBufferMask;
import gwt.g3d.client.gl2.enums.TextureMagFilter;
import gwt.g3d.client.gl2.enums.TextureMinFilter;
import gwt.g3d.client.gl2.enums.TextureUnit;
import gwt.g3d.client.mesh.StaticMesh;
import gwt.g3d.client.primitive.PrimitiveFactory;
import gwt.g3d.client.texture.Texture2D;
import gwt.g3d.resources.client.ExternalTexture2DResource;
import gwt.g3d.resources.client.GenerateMipmap;
import gwt.g3d.resources.client.MagFilter;
import gwt.g3d.resources.client.MinFilter;
import gwt.g3d.resources.client.ShaderResource;
import gwt.g3d.resources.client.Texture2DResource;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import org.motion.ballsim.physics.Table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.user.client.Window;

public class BilliardsViewImpl extends HoldingGL implements BilliardsView {
	private Texture2D moonTexture, crateTexture;
	private final Matrix3f nMatrix = new Matrix3f();

	private StaticMesh cubeMesh, moonMesh;

	private double lastTime;
	private float cubeAngle;

	protected BilliardsViewImpl() {
	}

	@Override
	public void dispose() {
		cubeMesh.dispose();
		moonMesh.dispose();
		moonTexture.dispose();
		crateTexture.dispose();
		shader.dispose();
	}

	@Override
	public ClientBundleWithLookup getClientBundle() {
		return Resources.INSTANCE;
	}


	@Override
	public void update() {
		gl.clear(ClearBufferMask.COLOR_BUFFER_BIT,
				ClearBufferMask.DEPTH_BUFFER_BIT);

		gl.uniform1i(shader.getUniformLocation("uUseLighting"), 1);
		gl.uniform(shader.getUniformLocation("uAmbientColor"), new Vector3f(
				0.3f, 0.3f, 0.3f));
		gl.uniform(shader.getUniformLocation("uPointLightingLocation"),
				new Vector3f(1.0f, 1.0f, 30.0f));
		gl.uniform(shader.getUniformLocation("uPointLightingColor"),
				new Vector3f(1.0f, 1.0f, 1.0f));


		MODELVIEW.push();

		placeBall(15, 30);
		placeBall(-15, 30);
		placeBall(-15, -30);
		placeBall(15, -30);

		placeTable();

		MODELVIEW.pop();

		setView((float) (Math.sin(cubeAngle / 100) * 50.0),
				(float) (Math.cos(cubeAngle / 100) * 50.0));

		double currTime = System.currentTimeMillis();
		float elapsed = (float) (currTime - lastTime);
		cubeAngle += 0.05f * elapsed;
		lastTime = currTime;
	}

	private void placeTable() {
		if (crateTexture != null) {
			crateTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate(0, 0, -2.0f);
		MODELVIEW.scale(17, 32, 1f);
		setMatrixUniforms();
		cubeMesh.draw();
		MODELVIEW.pop();
	}

	private void placeBall(float x, float y) {
		if (moonTexture != null) {
			moonTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate(x, y, 0);
		setMatrixUniforms();
		moonMesh.draw();
		MODELVIEW.pop();
	}

	private void setView(float x, float y) {
		PROJECTION.pushIdentity();
		PROJECTION.perspective(45, 1, .1f, 150);
		PROJECTION.lookAt(x, y, 20, 0, 0, 0, 0, 0, 1);
		gl.uniformMatrix(shader.getUniformLocation("uPMatrix"),PROJECTION.get());
		PROJECTION.pop();
	}

	@Override
	protected void initImpl() {
		super.initImpl();

		cubeAngle = 0;

		gl.activeTexture(TextureUnit.TEXTURE0);
		gl.uniform1i(shader.getUniformLocation("uSampler"), 0);

		Resources.INSTANCE.moon().getTexture(
				new ResourceCallback<Texture2DResource>() {
					@Override
					public void onSuccess(Texture2DResource resource) {
						moonTexture = resource.createTexture(gl);
					}

					@Override
					public void onError(ResourceException e) {
						Window.alert("Fail to load moon image.");
					}
				});

		Resources.INSTANCE.crate().getTexture(
				new ResourceCallback<Texture2DResource>() {
					@Override
					public void onSuccess(Texture2DResource resource) {
						crateTexture = resource.createTexture(gl);
					}

					@Override
					public void onError(ResourceException e) {
						Window.alert("Fail to load crate image.");
					}
				});

		setView(0, 0);

		moonMesh = new StaticMesh(gl, PrimitiveFactory.makeSphere(10, 10));
		moonMesh.setPositionIndex(shader.getAttributeLocation("aVertexPosition"));
		moonMesh.setNormalIndex(shader.getAttributeLocation("aVertexNormal"));
		moonMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));

		cubeMesh = new StaticMesh(gl, PrimitiveFactory.makeBox());
		cubeMesh.setPositionIndex(shader.getAttributeLocation("aVertexPosition"));
		cubeMesh.setNormalIndex(shader.getAttributeLocation("aVertexNormal"));
		cubeMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));

		lastTime = System.currentTimeMillis();
	}

	private void setMatrixUniforms() {
		gl.uniformMatrix(shader.getUniformLocation("uMVMatrix"), MODELVIEW.get());
		MODELVIEW.getInvertTranspose(nMatrix);
		gl.uniformMatrix(shader.getUniformLocation("uNMatrix"), nMatrix);
	}

	/** Resource files. */
	interface Resources extends ClientBundleWithLookup {
		Resources INSTANCE = GWT.create(Resources.class);

		@Source({ "shaders/lesson12.vp", "shaders/lesson12.fp" })
		ShaderResource shader();

		@Source("images/moon.gif")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource moon();

		@Source("images/crate.gif")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		ExternalTexture2DResource crate();
	}

	@Override
	public void showTable(Table table) {
		table.generateSequence();
	}
}