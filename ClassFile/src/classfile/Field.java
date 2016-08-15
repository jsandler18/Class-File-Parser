package classfile;

import classfile.access.AccessFlags;
import classfile.attribute.AttributePool;
import classfile.constant.UTF8Constant;

/**
 * Represents a field in a Class File
 * @author Jake Sandler
 *
 */
public class Field implements ByteCode {
	private AccessFlags flags;
	private UTF8Constant name;
	private UTF8Constant descriptor;
	private AttributePool attriubtes;
	
	@Override
	public byte[] toByteCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
