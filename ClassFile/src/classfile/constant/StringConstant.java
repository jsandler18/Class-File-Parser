package classfile.constant;

/**
 * Represents a string literal constant in the constant pool"
 * @author Jake Sandler
 *
 */
public class StringConstant extends Constant {
	private UTF8Constant string;
	
	/**
	 * Constructs a String constant from a UTF8 constant
	 * @param string
	 */
	public StringConstant(UTF8Constant string) {
		super(ConstantTag.ConstantString);
		this.string = string;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (o.getClass().equals(this.getClass())) {
			return ((StringConstant) o).string.equals(this.string);
		}
		else {
			return false;
		}
	}
}
