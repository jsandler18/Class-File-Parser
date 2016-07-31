package classfile.constantpool;

public class StringConstant extends Constant {

	public static final int TAG = 8;
	private short stringIndex;
	public StringConstant(short stringIdx) {
		this.stringIndex = stringIdx;
	}

	@Override
	public String toString() {
		return "ConstantStringInfo :\n\tString Index : " + this.stringIndex;
	}

}
