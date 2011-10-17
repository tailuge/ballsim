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

import gwt.g3d.client.Surface3D;
import gwt.g3d.client.gl2.GL2;
import gwt.g3d.client.gl2.GLDisposable;
import gwt.g3d.client.shader.AbstractShader;
import gwt.g3d.client.shader.ShaderException;
import gwt.g3d.resources.client.ShaderResource;

import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.user.client.Window;

public class HoldingGL implements GLDisposable {
	protected GL2 gl;
	private Surface3D surface;

	protected AbstractShader shader;

	protected void initImpl() {
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
	 * Sets the GL surface.
	 * 
	 * @param surface
	 */
	public void setSurface(Surface3D surface) {
		this.surface = surface;
	}

	/**
	 * Gets the GL surface.
	 */
	public Surface3D getSurface() {
		return surface;
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

	/**
	 * Updates the frame. Override this method to perform update per frame.
	 */
	public void update() {
	}

	public void dispose() {
	}

	/**
	 * Gets the client bundle for loading resources.
	 */
	public ClientBundleWithLookup getClientBundle() {
		return null;
	}
}
