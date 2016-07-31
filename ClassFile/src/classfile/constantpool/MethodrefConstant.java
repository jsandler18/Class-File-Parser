package classfile.constantpool;

public class MethodrefConstant extends Constant {

	public static final byte TAG = 10;
	private short classIndex;
	private short nameAndTypeIndex;
	
	public MethodrefConstant(short classIdx, short nameAndTypeIdx) {
		this.classIndex = classIdx;
		this.nameAndTypeIndex = nameAndTypeIdx;
	}

	@Override
	public String toString() {
		return "ConstantMethodrefInfo :\n\tClass Index         : " + this.classIndex 
				+ "\n\tName and Type Index : " + this.nameAndTypeIndex;
	}

}
