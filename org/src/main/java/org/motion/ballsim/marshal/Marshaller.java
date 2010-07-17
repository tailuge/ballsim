package org.motion.ballsim.marshal;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * General object marshalling
 */
public interface Marshaller<T> {

	/**
	 * marshal from object in {@link OutputStream}
	 */
	void marshal(T t, OutputStream out);

	/**
	 * unmarshal from {@link InputStream} to object
	 */
	T unmarshal(InputStream in);
}
