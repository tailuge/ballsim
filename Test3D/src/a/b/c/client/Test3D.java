package a.b.c.client;

import org.motion.ballsim.physics.Table;
import org.motion.ballsim.physics.util.Rack;

import gwt.g2d.client.util.FpsTimer;
import gwt.g3d.client.Surface3D;
import gwt.g3d.client.gl2.GL2;
import gwt.g3d.client.gl2.WebGLContextAttributes;
import gwt.g3d.client.gl2.enums.ClearBufferMask;
import gwt.g3d.client.gl2.enums.DepthFunction;
import gwt.g3d.client.gl2.enums.EnableCap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test3D implements EntryPoint {

	private Surface3D surface = new Surface3D(500, 500,
			new WebGLContextAttributes() {
				{
					setStencilEnable(true);
				}
			});

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel.get().add(surface);

		final GL2 gl = surface.getGL();
		if (gl == null) {
			Window.alert("No WebGL context found. Exiting.");
			return;
		}

		gl.clearColor(0.0f, 0f, 0f, 1f);
		gl.clearDepth(1);
		gl.viewport(0, 0, surface.getWidth(), surface.getHeight());

		gl.enable(EnableCap.DEPTH_TEST);
		gl.depthFunc(DepthFunction.LEQUAL);
		gl.clear(ClearBufferMask.COLOR_BUFFER_BIT,
				ClearBufferMask.DEPTH_BUFFER_BIT);

		Rack.rack(table, "2", "");
		table.generateSequence();
		
		runDemo(gl);
	}

	Table table = new Table(true);

	/**
	 * Initializes and runs the demo.
	 */
	private void runDemo(final GL2 gl) {
		final BilliardsViewImpl demo = new BilliardsViewImpl();
		
		demo.init(gl);

		FpsTimer timer = new FpsTimer(30) {
			double startTime = System.currentTimeMillis();
			@Override
			public void update() {
				demo.plotAtTime(table, (System.currentTimeMillis() - startTime)/1000.0);
				
				if (((System.currentTimeMillis() - startTime)/1500.0)>10)
				{
					startTime = System.currentTimeMillis();
				}
			}
		};
		timer.start();
	}

}
