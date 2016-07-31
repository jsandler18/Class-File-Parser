package classfile.constantpool;

public class LongConstant extends Constant {

	public static final int TAG = 5;
	private long data;
	public LongConstant(long l) {
		this.data = l;
	}

	@Override
	public String toString() {
		return  "ConstantLongInfo :\n\tLong : " + this.data;
	}

}
