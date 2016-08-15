package classfile.constant;

/**
 * Represents a Method's Type in the constant pool
 * @author Jake Sandler
 *
 */
public class MethodTypeConstant extends Constant{

	private UTF8Constant descriptor;
	
	/**
	 * Constructs a Method type constant 
	 * @param descriptor A valid method type descriptor
	 */
	public MethodTypeConstant(UTF8Constant descriptor) {
		super(ConstantTag.ConstantMethodType);
		this.descriptor = descriptor;
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
			return ((MethodTypeConstant) o).descriptor.equals(this.descriptor);
		}
		else {
			return false;
		}
	}
	
}
