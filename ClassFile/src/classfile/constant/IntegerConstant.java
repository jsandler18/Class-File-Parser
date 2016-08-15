package classfile.constant;

/**
 * Represents an Integer Constant in the constant pool
 * @author Jake Sandler
 *
 */
public class IntegerConstant extends Constant {

	private int bytes;
	
	/**
	 * Constructs an Integer constant
	 * @param bytes a 4 byte value representing this integer
	 */
	public IntegerConstant(int bytes) {
		super(ConstantTag.ConstantInteger);
		this.bytes = bytes;
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
			return ((IntegerConstant) o).bytes == (this.bytes);
		}
		else {
			return false;
		}
		
	}
}
