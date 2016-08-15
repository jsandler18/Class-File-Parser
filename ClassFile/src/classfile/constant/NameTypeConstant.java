package classfile.constant;

/**
 * Represents the name and type of a class member without
 * holding information about the class that this member 
 * belongs to
 * @author Jake Sandler
 *
 */
public class NameTypeConstant extends Constant {

	private UTF8Constant name;
	private UTF8Constant descriptor;
	
	/**
	 * Constructs a Name and Type constant
	 * @param name Either the special method name "&lt;init&gt;" or 
	 * 	a valid, unqualified name of the member
	 * @param descriptor a valid field or type descriptor
	 */
	public NameTypeConstant(UTF8Constant name, UTF8Constant descriptor) {
		super(ConstantTag.ConstantNameAndType);
		this.name = name;
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
			return (((NameTypeConstant) o).name.equals(this.name) && 
					((NameTypeConstant) o).descriptor.equals(this.descriptor));
		}
		else {
			return false;
		}
	}
}
