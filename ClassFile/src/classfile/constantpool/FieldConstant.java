package classfile.constantpool;

public class FieldConstant extends Constant {

	public static final byte TAG = 9;
	private short classIndex;
	private short nameAndTypeIndex;
	
	public FieldConstant(short classIdx, short nameAndTypeIdx) {
		this.classIndex = classIdx;
		this.nameAndTypeIndex = nameAndTypeIdx;
	}

	@Override
	public String toString() {
		return "ConstantFieldrefInfo :\n\tClass Index         : " + this.classIndex 
				+ "\n\tName and Type Index : " + this.nameAndTypeIndex;
	}

}
