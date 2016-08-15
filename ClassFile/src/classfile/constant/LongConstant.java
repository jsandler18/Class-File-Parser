package classfile.constant;

/**
 * Represents a long constant in the cosntant pool
 * @author Jake Sandler
 *
 */
public class LongConstant extends Constant {

	private long bytes;
	
	/**
	 * Constructs a Long constant
	 * @param bytes an 8 byte value representing the long
	 */
	public LongConstant(long bytes) {
		super(ConstantTag.ConstantLong);
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
			return ((LongConstant) o).bytes == (this.bytes);
		}
		else {
			return false;
		}
		
	}
}
