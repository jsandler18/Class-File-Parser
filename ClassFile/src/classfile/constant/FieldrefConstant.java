package classfile.constant;

/**
 * Represents a field in the constant pool that is a member
 * of a class thatis also in the constant pool
 * @author Jake Sandler
 *
 */
public class FieldrefConstant extends Constant{

	private ClassConstant containerClass;
	private NameTypeConstant nametype;

	/**
	 * Constructs a Fieldref Constant 
	 * @param containerClass The class this field is a member of
	 * @param nametype the name and type of this field
	 */
	public FieldrefConstant(ClassConstant containerClass, NameTypeConstant nametype) {
		super(ConstantTag.ConstantFieldref);
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
			return (((FieldrefConstant) o).containerClass.equals(this.containerClass) && 
					((FieldrefConstant) o).nametype.equals(this.nametype));
		}
		else {
			return false;
		}
	}

}
