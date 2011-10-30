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

import java.util.HashMap;
import java.util.Map;

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

	protected StaticMesh tableMesh, cueMesh;
	protected StaticMesh cueShadowMesh, shadowMesh, ballMesh;
	final protected Map<Integer,Texture2D> ballTextures = new HashMap<Integer,Texture2D>(); 
	protected Texture2D tableTexture, shadowTexture, cueTexture;

	private final Matrix3f nMatrix = new Matrix3f();

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

		gl.activeTexture(TextureUnit.TEXTURE0);
		gl.uniform1i(shader.getUniformLocation("uSampler"), 0);

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

	protected Assets() {
	}

	@Override
	public void dispose() {
		shadowMesh.dispose();
		ballMesh.dispose();
		for(Texture2D texture : ballTextures.values())
			texture.dispose();
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
				0.4f, 0.4f, 0.4f));
		gl.uniform(shader.getUniformLocation("uPointLightingLocation"),
				new Vector3f(0.0f, 0.0f, 50.0f));
		gl.uniform(shader.getUniformLocation("uPointLightingColor"),
				new Vector3f(1.0f, 1.0f, 1.0f));

	}

	private void loadTexture(ExternalTexture2DResource textureResource, final int index)
	{
		textureResource.getTexture(
				new ResourceCallback<Texture2DResource>() {
					@Override
					public void onSuccess(Texture2DResource resource) {
						ballTextures.put(index,resource.createTexture(gl));
					}

					@Override
					public void onError(ResourceException e) {
						Window.alert("Fail to load moon image.");
					}
				});
		
	}
	protected void initImpl() {
		initBaseImpl();

		loadTexture(Resources.INSTANCE.ball0(),0);
		loadTexture(Resources.INSTANCE.ball1(),1);
		loadTexture(Resources.INSTANCE.ball2(),2);
		loadTexture(Resources.INSTANCE.ball3(),3);
		loadTexture(Resources.INSTANCE.ball4(),4);
		loadTexture(Resources.INSTANCE.ball5(),5);
		loadTexture(Resources.INSTANCE.ball6(),6);
		loadTexture(Resources.INSTANCE.ball7(),7);
		loadTexture(Resources.INSTANCE.ball8(),8);
		loadTexture(Resources.INSTANCE.ball9(),9);
		

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

		ballMesh = new StaticMesh(gl, PrimitiveFactory.makeSphere(13, 13));
		ballMesh.setPositionIndex(shader
				.getAttributeLocation("aVertexPosition"));
		ballMesh.setNormalIndex(shader.getAttributeLocation("aVertexNormal"));
		ballMesh.setTexCoordIndex(shader.getAttributeLocation("aTextureCoord"));

		shadowMesh = new StaticMesh(gl, PrimitiveFactory.makeCone(1, 15));
		shadowMesh.setPositionIndex(shader
				.getAttributeLocation("aVertexPosition"));
		shadowMesh.setNormalIndex(shader.getAttributeLocation("aVertexNormal"));
		shadowMesh.setTexCoordIndex(shader
				.getAttributeLocation("aTextureCoord"));

		cueShadowMesh = new StaticMesh(gl, PrimitiveFactory.makePlane());
		cueShadowMesh.setPositionIndex(shader
				.getAttributeLocation("aVertexPosition"));
		cueShadowMesh.setNormalIndex(shader
				.getAttributeLocation("aVertexNormal"));
		cueShadowMesh.setTexCoordIndex(shader
				.getAttributeLocation("aTextureCoord"));

		loadTable();
		loadCue();
	}

	protected void setMatrixUniforms() {
		gl.uniformMatrix(shader.getUniformLocation("uMVMatrix"),
				MODELVIEW.get());
		MODELVIEW.getInvertTranspose(nMatrix);
		gl.uniformMatrix(shader.getUniformLocation("uNMatrix"), nMatrix);
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

		@Source("images/ball0.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball0();
		
		@Source("images/ball1.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball1();

		@Source("images/ball2.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball2();

		@Source("images/ball3.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball3();

		@Source("images/ball4.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball4();

		@Source("images/ball5.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball5();

		@Source("images/ball6.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball6();

		@Source("images/ball7.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball7();

		@Source("images/ball8.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball8();

		@Source("images/ball9.png")
		@MagFilter(TextureMagFilter.LINEAR)
		@MinFilter(TextureMinFilter.LINEAR)
		@GenerateMipmap
		ExternalTexture2DResource ball9();

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

	public void loadTable() {
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
				tableMesh.setTexCoordIndex(shader
						.getAttributeLocation("aTextureCoord"));
			}

			@Override
			public void onError(ResourceException e) {
				Window.alert(e.getMessage());
			}
		});
	}

	public void loadCue() {
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
				cueMesh.setTexCoordIndex(shader
						.getAttributeLocation("aTextureCoord"));
			}

			@Override
			public void onError(ResourceException e) {
				Window.alert(e.getMessage());
			}
		});
	}
}