package classfile.constant;

import java.util.List;
import classfile.ByteCode;


/**
 * Represents a collection of constants. Can turn itself into bytecode.
 * @author Jake Sandler
 * 
 */
public class ConstantPool implements ByteCode {
	private List<Constant> constants;
	
	@Override
	public byte[] toByteCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Gets the index of the given constant. It will add the constant
	 * to the pool if it does not already exist.
	 * @param constant A constant 
	 * @return the index of that constant in the constant pool
	 */
	public short getIndex(Constant constant) {
		//TODO
		return 0;
	}

}
