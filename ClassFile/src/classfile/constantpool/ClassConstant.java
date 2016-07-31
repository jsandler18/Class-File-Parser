package classfile.constantpool;

import java.nio.ByteBuffer;

public class ClassConstant extends Constant {

	public static final int TAG = 7;
	private short nameIdx;
	public ClassConstant(short i) {
		this.nameIdx = i;
	}

	@Override
	public String toString() {
		return  "ConstantClassInfo :\n\tName Index : \"" + this.nameIdx + "\"";
	}
	
	@Override
	public ByteBuffer toByteCode() {
		ByteBuffer result = ByteBuffer.allocate(3);
		result.put((byte) TAG);
		result.putShort(this.nameIdx);
		return result;
	}

}
