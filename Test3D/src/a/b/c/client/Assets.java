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
import gwt.g3d.client.gl2.GL2;
import gwt.g3d.client.gl2.GLDisposable;
import gwt.g3d.client.gl2.enums.ClearBufferMask;
import gwt.g3d.client.gl2.enums.TextureMagFilter;
import gwt.g3d.client.gl2.enums.TextureMinFilter;
import gwt.g3d.client.gl2.enums.TextureUnit;
import gwt.g3d.client.gl2.enums.TextureWrapMode;
import gwt.g3d.client.mesh.StaticMesh;
import gwt.g3d.client.primitive.PrimitiveFactory;
import gwt.g3d.client.shader.AbstractShader;
import gwt.g3d.client.shader.ShaderException;
import gwt.g3d.client.texture.Texture2D;
import gwt.g3d.resources.client.ExternalMeshResource;
import gwt.g3d.resources.client.ExternalTexture2DResource;
import gwt.g3d.resources.client.GenerateMipmap;
import gwt.g3d.resources.client.MagFilter;
import gwt.g3d.resources.client.MeshResource;
import gwt.g3d.resources.client.MinFilter;
import gwt.g3d.resources.client.ShaderResource;
import gwt.g3d.resources.client.Texture2DResource;
import gwt.g3d.resources.client.WrapMode;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.user.client.Window;

public class Assets implements GLDisposable {
	
	protected GL2 gl;

	protected AbstractShader shader;


	protected void initBaseImpl() {
		try {
			ShaderResource shaderResource = ((ShaderResource) getClientBundle()
					.getResource("shader"));
			shader = shaderResource.createShader(gl);
			shader.bind();
		} catch (ShaderException e) {
			Window.alert(e.getMessage());
			return;
		}
	}

	


	/**
	 * Initializes the demo.
	 * 
	 * @param gl
	 */
	public final void init(GL2 gl) {
		this.gl = gl;
		initImpl();
	}


	private StaticMesh tableMesh, cueMesh;
	private StaticMesh shadowMesh, ballMesh;
	private Texture2D moonTexture, tableTexture, shadowTexture, cueTexture;

	private final Matrix3f nMatrix = new Matrix3f();


	
	protected Assets() {
	}

	@Override
	public void dispose() {
		shadowMesh.dispose();
		ballMesh.dispose();
		moonTexture.dispose();
		tableTexture.dispose();
		shader.dispose();
		tableMesh.dispose();
		cueMesh.dispose();
	}

	public ClientBundleWithLookup getClientBundle() {
		return Resources.INSTANCE;
	}


	protected void prepareDraw() {
		gl.clear(ClearBufferMask.COLOR_BUFFER_BIT,
				ClearBufferMask.DEPTH_BUFFER_BIT);

		gl.uniform1i(shader.getUniformLocation("uUseLighting"), 1);
		gl.uniform(shader.getUniformLocation("uAmbientColor"), new Vector3f(
				0.3f, 0.3f, 0.3f));
		gl.uniform(shader.getUniformLocation("uPointLightingLocation"),
				new Vector3f(0.1f, 1.0f, 30.0f));
		gl.uniform(shader.getUniformLocation("uPointLightingColor"),
				new Vector3f(1.0f, 1.0f, 1.0f));

	}

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

	protected void placeBall(double x, double y,
			double px,double py,double pz,
			double ux,double uy,double uz,
			double cx,double cy,double cz
			) {
		if (moonTexture != null) {
			moonTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -x, (float) -y, 0);
		MODELVIEW.lookAt(
				0, 0, 0, 
				(float)-px, (float)-py, (float)-pz,
				(float)-ux, (float)-uy, (float)-uz
				);
		setMatrixUniforms();
		ballMesh.draw();
		MODELVIEW.pop();

		placeShadow(x,y);
		//debugRoll(-x,-y,-px,-py,-pz);
		//debugRoll(-x,-y,-ux,-uy,-uz);
	}

	protected void placeShadow(double x, double y)
	{
		if (shadowTexture != null) {
			shadowTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -x, (float) -y, -0.99f);
		MODELVIEW.scale(1, 1, 0.01f);
		MODELVIEW.rotateX((float)Math.PI/2);
		setMatrixUniforms();
		shadowMesh.draw();
		MODELVIEW.pop();		
	}

	protected void placeCue(double x, double y)
	{
		if (cueTexture != null) {
			cueTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) -x, (float) -y, 0f);
		MODELVIEW.scale(1, 1, 1);
//		MODELVIEW.rotateX((float)Math.PI/2);
		setMatrixUniforms();
		cueMesh.draw();
		MODELVIEW.pop();		
	}

	protected void debugRoll(double x, double y,
			double px,double py,double pz
			) {
		if (moonTexture != null) {
			moonTexture.bind();
		}
		MODELVIEW.push();
		MODELVIEW.translate((float) px, (float) py, (float) pz);
		MODELVIEW.translate((float) x, (float) y, 0);
		MODELVIEW.scale(0.2f, 0.2f, 0.2f);
		setMatrixUniforms();
		ballMesh.draw();
		MODELVIEW.pop();
	}
	
	protected void setView(float x, float y) {
		PROJECTION.pushIdentity();
		PROJECTION.perspective(45, 1, .1f, 150);
		PROJECTION.lookAt(x, y, 20, 0, 0, 0, 0, 0, 1);
		gl.uniformMatrix(shader.getUniformLocation("uPMatrix"),
				PROJECTION.get());
		PROJECTION.pop();
	}

	protected void initImpl() {
		initBaseImpl();

	
		gl.activeTexture(TextureUnit.TEXTURE0);
		gl.uniform1i(shader.getUniformLocation("uSampler"), 0);

		Resources.INSTANCE.ball().getTexture(
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

		Resources.INSTANCE.tableTexture().getTexture(
				new ResourceCallback<Texture2DResource>() {
					@Override
					public void onSuccess(Texture2DResource resource) {
						tableTexture = resource.createTexture(gl);
					}

					@Override
					public void onError(ResourceException e) {
						Window.alert("Fail to load crate image.");
					}
				});

		Resources.INSTANCE.shadow().getTexture(
				new ResourceCallback<Texture2DResource>() {
					@Override
					public void onSuccess(Texture2DResource resource) {
						shadowTexture = resource.createTexture(gl);
					}

					@Override
					public void onError(ResourceException e) {
						Window.alert("Fail to load crate image.");
					}
				});

		Resources.INSTANCE.cueTextureImage().getTexture(
				new ResourceCallback<Texture2DResource>() {
					@Override
					public void onSuccess(Texture2DResource resource) {
						cueTexture = resource.createTexture(gl);
					}

					@Override
					public void onError(ResourceException e) {
						Window.alert("Fail to load crate image.");
					}
				});
		
		setView(0, 0);

		
		ballMesh = new StaticMesh(gl, PrimitiveFactory.makeSphere(10, 10));
		ballMesh.setPositionIndex(shader.getAttributeLocation("aVertexPosition"));
		ballMesh.setNormalIndex(shader.getAttributeLocation("aVertexNormal"));
		ballMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));

		shadowMesh = new StaticMesh(gl, PrimitiveFactory.makeCone(1, 15));
		shadowMesh.setPositionIndex(shader.getAttributeLocation("aVertexPosition"));
		shadowMesh.setNormalIndex(shader.getAttributeLocation("aVertexNormal"));
		shadowMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));

		loadTable();
		loadCue();
	}

	private void setMatrixUniforms() {
		gl.uniformMatrix(shader.getUniformLocation("uMVMatrix"),MODELVIEW.get());
		MODELVIEW.getInvertTranspose(nMatrix);
		gl.uniformMatrix(shader.getUniformLocation("uNMatrix"),nMatrix);
	}

	/** Resource files. */
	interface Resources extends ClientBundleWithLookup {
		Resources INSTANCE = GWT.create(Resources.class);

		@Source("models/table.obj")
		ExternalMeshResource table();

		@Source("models/cue.obj")
		ExternalMeshResource cue();

		@Source({ "shaders/lesson12.vp", "shaders/lesson12.fp" })
		ShaderResource shader();

		@Source("images/moon.gif")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball();

		@Source("images/table.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@WrapMode(TextureWrapMode.CLAMP_TO_EDGE)
		ExternalTexture2DResource tableTexture();

		@Source("images/cue.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@WrapMode(TextureWrapMode.CLAMP_TO_EDGE)
		ExternalTexture2DResource cueTextureImage();

		@Source("images/shadow.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		ExternalTexture2DResource shadow();

	}


	
	public void loadTable()
	{
		ExternalMeshResource meshResource;
		Resources resource = Resources.INSTANCE;
		meshResource = resource.table();
		   
		meshResource.getMesh(new ResourceCallback<MeshResource>() {
			@Override
			public void onSuccess(MeshResource resource) {
				tableMesh = resource.createMesh(gl);
				tableMesh.setPositionIndex(shader
						.getAttributeLocation("aVertexPosition"));
				tableMesh.setNormalIndex(-1);
				tableMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));
			}
			
			@Override
			public void onError(ResourceException e) {
				Window.alert(e.getMessage());
			}
		});
	}
	
	public void loadCue()
	{
		ExternalMeshResource meshResource;
		Resources resource = Resources.INSTANCE;
		meshResource = resource.cue();
		   
		meshResource.getMesh(new ResourceCallback<MeshResource>() {
			@Override
			public void onSuccess(MeshResource resource) {
				cueMesh = resource.createMesh(gl);
				cueMesh.setPositionIndex(shader
						.getAttributeLocation("aVertexPosition"));
				cueMesh.setNormalIndex(-1);
				cueMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));
			}
			
			@Override
			public void onError(ResourceException e) {
				Window.alert(e.getMessage());
			}
		});
	}
}