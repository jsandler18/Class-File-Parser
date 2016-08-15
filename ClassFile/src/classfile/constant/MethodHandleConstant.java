package classfile.constant;

/**
 * Represents a method handle in the constant pool
 * @author Jake Sandler
 *
 */
public class MethodHandleConstant extends Constant {
	private ReferenceKind kind;
	private Constant reference;
	
	/**
	 * Constructs a Method handle constant
	 * @param kind The kind of method handle this is
	 * @param reference One of several possible Constants, depending on the kind
	 */
	public MethodHandleConstant(ReferenceKind kind, Constant reference) {
		super(ConstantTag.ConstantMethodHandle);
		this.kind = kind;
		this.reference = reference;
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
			return (((MethodHandleConstant) o).kind.getKind() == (this.kind.getKind()) && 
					((MethodHandleConstant) o).reference.equals(this.reference));
		}
		else {
			return false;
		}
	}
}
