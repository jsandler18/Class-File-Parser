package classfile.constantpool;

public class MethodHandleCosntant extends Constant {

	public static final byte TAG = 15;

	private byte referenceKind;
	private short refernceIndex;
	public MethodHandleCosntant(byte refKind, short refIdx) {
		this.referenceKind = refKind;
		this.refernceIndex = refIdx;
	}

	@Override
	public String toString() {
		return "ConstantMethodHandleInfo :\n\tReference Kind  : " + this.referenceKind 
				+ "\n\tReference Index : " + this.refernceIndex;
	}

}
