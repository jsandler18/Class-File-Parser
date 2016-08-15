package classfile.constant;

/**
 * Represents a Double constant in the constant pool
 * @author Jake Sandler
 *
 */
public class DoubleConstant extends Constant {
	private long bytes;
	
	/**
	 * Constructs a Double constant
	 * @param bytes an 8 byte value representing this double
	 */
	public DoubleConstant(long bytes) {
		super(ConstantTag.ConstantDouble);
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
			return ((DoubleConstant) o).bytes == (this.bytes);
		}
		else {
			return false;
		}
		
	}
	
}
