package classfile.constant;

/**
 * Represents an A method that belongs to an interface, instead of 
 * a well defined class, in the constant pool
 * @author Jake Sandler
 *
 */
public class InterfaceMethodrefConstant extends Constant {

	private ClassConstant containerInterface;
	private NameTypeConstant nametype;
	
	/**
	 * Constructs an Interface Methodref constant
	 * @param containerInterface The interface that contains this method
	 * @param nametype the name and type of the method
	 */
	public InterfaceMethodrefConstant(ClassConstant containerInterface, NameTypeConstant nametype) {
		super(ConstantTag.ConstantInterfaceMethodref);
		this.containerInterface = containerInterface;
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
			return (((InterfaceMethodrefConstant) o).containerInterface.equals(this.containerInterface) && 
					((InterfaceMethodrefConstant) o).nametype.equals(this.nametype));
		}
		else {
			return false;
		}
	}
	
}
