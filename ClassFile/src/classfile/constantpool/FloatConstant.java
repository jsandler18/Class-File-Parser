package classfile.constantpool;

public class FloatConstant extends Constant {

	public static final int TAG = 4;
	private float data;
	public FloatConstant(float f) {
		this.data = f;
	}

	@Override
	public String toString() {
		return  "ConstantFloatInfo :\n\tFloat : " + this.data;
	}

}
