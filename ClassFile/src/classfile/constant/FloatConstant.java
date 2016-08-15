package classfile.constant;

/**
 * Represents a Float constant in the Constant pool
 * @author Jake Sandler
 *
 */
public class FloatConstant extends Constant {

	private int bytes;
	
	/**
	 * Constructs a float Constant
	 * @param bytes a 4 byte value representing the float
	 */
	public FloatConstant(int bytes) {
		 super(ConstantTag.ConstantFloat);
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
			return ((FloatConstant) o).bytes == (this.bytes);
		}
		else {
			return false;
		}
		
	}
}
