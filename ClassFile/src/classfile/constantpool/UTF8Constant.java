package classfile.constantpool;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class UTF8Constant extends Constant {

	public static final int TAG = 1;
	private byte[] bytes;
	
	/**
	 * @param bytes an array of utf8 bytes that is exactly the length
	 * specified by the classfile. 
	 */
	public UTF8Constant(byte[] bytes) {
		this.bytes = Arrays.copyOf(bytes, bytes.length);
	}
	
	public String getContent() {
		return new String(this.bytes);
	}

	@Override
	public String toString() {
		return "ConstantUTF8Info :\n\tLength  :  " + bytes.length + "\n\tContent : \"" + new String(this.bytes) + "\"";
	}

	@Override
	public ByteBuffer toByteCode() {
		ByteBuffer result = ByteBuffer.allocate(3 + this.bytes.length);
		result.put((byte)TAG);
		result.putShort((short) this.bytes.length);
		result.put(this.bytes);
		return result;
	}
}
