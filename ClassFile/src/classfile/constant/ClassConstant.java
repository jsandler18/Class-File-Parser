package classfile.constant;

/**
 * Represents a Class in the Constant Pool
 * @author Jake Sandler
 *
 */
public class ClassConstant extends Constant{
	private UTF8Constant name;
	
	/**
	 * Constructs a Constant Class 
	 * @param name the binary name of this class
	 */
	public ClassConstant(UTF8Constant name) {
		super(ConstantTag.ConstantClass);
		this.name = name;
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
			return ((ClassConstant) o).name.equals(this.name);
		}
		else {
			return false;
		}
	}
}
