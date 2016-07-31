package classfile.constantpool;

public class DoubleConstant extends Constant {

	public static final int TAG = 6;
	private double data;
	public DoubleConstant(double d) {
		this.data = d;
	}

	@Override
	public String toString() {
		return  "ConstantDoubleInfo :\n\tDouble : " + this.data ;
	}

}
