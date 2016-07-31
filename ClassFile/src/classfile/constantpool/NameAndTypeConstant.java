package classfile.constantpool;

public class NameAndTypeConstant extends Constant {

	public static final byte TAG = 12;
	private short nameIndex;
	private short descriptorIndex;
	
	public NameAndTypeConstant(short nameIdx, short descriptorIdx) {
		this.nameIndex = nameIdx;
		this.descriptorIndex = descriptorIdx;
	}

	@Override
	public String toString() {
		return "ConstantNameAndTypeInfo :\n\tName Index       : " + this.nameIndex
				+ "\n\tDescriptor Index : " + this.descriptorIndex;
	}

}
