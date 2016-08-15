package classfile;

import java.util.List;

import classfile.access.AccessFlags;
import classfile.attribute.AttributePool;
import classfile.constant.ClassConstant;
import classfile.constant.ConstantPool;

/**
 * This class represents all of the parts of a valid class file.
 * It contains mehtods for turning itself into bytecode and for getting
 * ClassFile objects from bytes
 * @author Jake Sandler
 *
 */
public class ClassFile implements ByteCode{

	private final static int MAGIC = 0xCAFEBABE;
	private short majorVersion;
	private short minorVersion;
	private ConstantPool constants;
	private AccessFlags flags;
	private ClassConstant thisClass;
	private ClassConstant superClass;
	private List<ClassConstant> interfaces;
	private List<Field> fields;
	private List<Method> methods;
	private AttributePool attributes;
	
	@Override
	public byte[] toByteCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Reads a Class File from a byte array
	 * @param bytes The bytes of a class file
	 * @return A class file object that represents the given bytes
	 */
	public static ClassFile readClassFile(byte [] bytes) {
		//TODO
		return null;
	}

}
