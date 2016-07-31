package classfile.constantpool;

public class MethodTypeConstant extends Constant {

	public static final byte TAG = 16;
	
	private short descriptorIndex;

	public MethodTypeConstant(short descriptorIdx) {
		this.descriptorIndex = descriptorIdx;
	}

	@Override
	public String toString() {
		return "ConstantMethodTypeInfo :\n\tDescriptor Index : " + this.descriptorIndex;
	}

}
