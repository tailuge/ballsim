package a.b.c.client;

import static gwt.g3d.client.math.MatrixStack.MODELVIEW;

import javax.vecmath.Matrix3f;

import gwt.g3d.client.Surface3D;
import gwt.g3d.client.gl2.GL2;
import gwt.g3d.client.gl2.GLDisposable;
import gwt.g3d.client.gl2.WebGLContextAttributes;
import gwt.g3d.client.gl2.enums.ClearBufferMask;
import gwt.g3d.client.gl2.enums.DepthFunction;
import gwt.g3d.client.gl2.enums.EnableCap;
import gwt.g3d.client.gl2.enums.TextureUnit;
import gwt.g3d.client.shader.AbstractShader;
import gwt.g3d.client.shader.ShaderException;
import gwt.g3d.resources.client.ShaderResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class SurfaceElement implements GLDisposable {

	final protected Surface3D surface = new Surface3D(500, 500,
			new WebGLContextAttributes() {
				{
					setStencilEnable(true);
				}
			});

	final protected GL2 gl;

	protected AbstractShader shader;

	protected final Matrix3f nMatrix = new Matrix3f();

	public SurfaceElement() {

		RootPanel.get().add(surface);
		gl = surface.getGL();
		if (gl == null) {
			Window.alert("No WebGL context found. Exiting.");
			return;
		}

		initSurface();
		initShader();
		supressContextMenu();
	}

	private void initSurface()
	{
		gl.clearColor(0.0f, 0f, 0f, 1f);
		gl.clearDepth(1);
		gl.viewport(0, 0, surface.getWidth(), surface.getHeight());

		gl.enable(EnableCap.DEPTH_TEST);
		gl.depthFunc(DepthFunction.LEQUAL);
		gl.clear(ClearBufferMask.COLOR_BUFFER_BIT,
				ClearBufferMask.DEPTH_BUFFER_BIT);		
	}

	private void initShader() {
		try {
			ShaderResource shaderResource = ((ShaderResource) getShaderClientBundle()
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

	private void supressContextMenu()
	{
		RootPanel.get().addDomHandler(new ContextMenuHandler() {

			@Override
			public void onContextMenu(ContextMenuEvent event) {
				event.preventDefault();
				event.stopPropagation();
			}
		}, ContextMenuEvent.getType());		
	}
	
	@Override
	public void dispose() {
	}

	protected void setMatrixUniforms() {
		gl.uniformMatrix(shader.getUniformLocation("uMVMatrix"),
				MODELVIEW.get());
		MODELVIEW.getInvertTranspose(nMatrix);
		gl.uniformMatrix(shader.getUniformLocation("uNMatrix"), nMatrix);
	}

	private ClientBundleWithLookup getShaderClientBundle() {
		return Resources.INSTANCE;
	}

	/** Resource files. */
	interface Resources extends ClientBundleWithLookup {
		Resources INSTANCE = GWT.create(Resources.class);

		@Source({ "shaders/lesson12.vp", "shaders/lesson12.fp" })
		ShaderResource shader();
	}
}
