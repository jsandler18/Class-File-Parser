package classfile.constantpool;

public class InvokeDynamicConstant extends Constant {

	public static final byte TAG = 18;

	private short bootstrapMethodAttrIndex;
	private short nameAndTypeIndex;
	
	public InvokeDynamicConstant(short bootstrapMethodAttrIdx, short nameAndTypeIdx) {
		this.bootstrapMethodAttrIndex = bootstrapMethodAttrIdx;
		this.nameAndTypeIndex = nameAndTypeIdx;
	}

	@Override
	public String toString() {
		return "ConstantInvokeDynamicInfo : \n\tBootstrap Method Attribute Index : " + this.bootstrapMethodAttrIndex 
				+ "\n\tName and Type Index              : " + this.nameAndTypeIndex;
	}

}
