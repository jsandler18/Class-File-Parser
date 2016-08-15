package classfile.constant;

/**
 * An enumeration holding the different possible values of
 * the constant tag that every constant pool member has
 * @author Jake Sandler
 *
 */
public enum ConstantTag {
	ConstantUTF8      	       (1),
	ConstantInteger   		   (3),
	ConstantFloat     		   (4),
	ConstantLong      		   (5),
	ConstantDouble    		   (6),
	ConstantClass     		   (7),
	ConstantString    		   (8),
	ConstantFieldref  		   (9),
	ConstantMethodref 		   (10),
	ConstantInterfaceMethodref (11),
	ConstantNameAndType		   (12),
	ConstantMethodHandle	   (15),
	ConstantMethodType	   	   (16),
	ConstantInvokeDynamic	   (18);
	
	private final byte TAG;
	private ConstantTag(int tag) {
		this.TAG = (byte) tag;
	}
	
	/**
	 * Get the value of the constant tag
	 * @return
	 */
	public byte getTag() {
		return this.TAG;
	}
}
