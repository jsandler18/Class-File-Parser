package classfile.constant;

/**
 * This is the parent class for all constants that can go 
 * in the constant pool
 * @author Jake Sandler
 *
 */
public abstract class Constant {
	/**
	 * Holds the Constant tag for a given constant class
	 */
	private final ConstantTag TAG;
	
	/**
	 * Constant class constructor for assigning the constant tag
	 * @param tag a constant tag
	 */
	public Constant(ConstantTag tag) {
		this.TAG = tag;
	}
	
	/**
	 * gets this constant's constant tag
	 * @return a constant tag
	 */
	public ConstantTag getTag() {
		return this.TAG;
	}
	
	/**
	 * Checks if this constant has valid entries
	 * @return true if it is valid, false if not
	 */
	public abstract boolean validate();
}
