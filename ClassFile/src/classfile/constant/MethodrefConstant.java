package classfile.constant;

/**
 * Represents an A method that belongs to a class in the constant pool
 * @author Jake Sandler
 *
 */
public class MethodrefConstant extends Constant {

	private ClassConstant containerClass;
	private NameTypeConstant nametype;
	
	/**
	 * Constructs a Methodref constant
	 * @param containerClass The class that contains this method
	 * @param nametype the name and type of the method
	 */
	public MethodrefConstant(ClassConstant containerClass, NameTypeConstant nametype) {
		super(ConstantTag.ConstantInterfaceMethodref);
		this.containerClass = containerClass;
		this.nametype = nametype;
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
			return (((MethodrefConstant) o).containerClass.equals(this.containerClass) && 
					((MethodrefConstant) o).nametype.equals(this.nametype));
		}
		else {
			return false;
		}
	}
	
}
