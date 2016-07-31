package classfile.constantpool;

public class IntegerConstant extends Constant {

	public static final int TAG = 3;
	private int data;
	public IntegerConstant(int i) {
		this.data = i;
	}

	@Override
	public String toString() {
		return  "ConstantIntegerInfo :\n\tInteger : " + this.data;
	}

}
