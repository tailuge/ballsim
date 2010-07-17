package org.motion.ballsim.marshal;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <tt>java.beans</tt> implementation of {@link Marshaller}
 * <p>
 * 
 * @see XMLDecoder
 * @see XMLEncoder
 */
public class JavaBeansMarshaller<T> implements Marshaller<T> {

	@Override
	public void marshal(T t, OutputStream out) {
		XMLEncoder e = new XMLEncoder(out);
		e.writeObject(t);
		e.close();
	}

	@Override
	public T unmarshal(InputStream in) {
		XMLDecoder d = new XMLDecoder(in);
		@SuppressWarnings("unchecked")
		T result = (T) d.readObject();
		d.close();
		return result;
	}

}
