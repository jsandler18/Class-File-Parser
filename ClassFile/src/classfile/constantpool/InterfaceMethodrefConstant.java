package classfile.constantpool;

public class InterfaceMethodrefConstant extends Constant {

	public static final byte TAG = 11;
	private short classIndex;
	private short nameAndTypeIndex;
	
	public InterfaceMethodrefConstant(short classIdx, short nameAndTypeIdx) {
		this.classIndex = classIdx;
		this.nameAndTypeIndex = nameAndTypeIdx;
	}

	@Override
	public String toString() {
		return "ConstantInterfaceMethodrefInfo :\n\tClass Index         : " + this.classIndex 
				+ "\n\tName and Type Index : " + this.nameAndTypeIndex;
	}

}
