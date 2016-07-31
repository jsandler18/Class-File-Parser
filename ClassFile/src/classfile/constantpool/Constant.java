package classfile.constantpool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import classfile.ByteCode;

import java.io.DataInputStream;

public abstract class Constant implements ByteCode{

	protected static ArrayList<Constant> constantPool;
	/**
	 * Creates an instance of a subclass of ConstantPoolInfo that corresponds
	 * to the item in the constant pool whose tag starts at the given offset

	 * @param classFileBytes the byte list started at the tag of the constant pool info object.
	 * 	this parameter is modified such that the byte list starts at the first byte
	 * 	after the end of the constant pool item
	 * @return some subclass of ConstantPoolObject
	 * @throws IOException 
	 */
	public static Constant readConstant(DataInputStream reader) throws IOException{
		Constant result = null;
		byte tag = reader.readByte();
		switch (tag) {
			case UTF8Constant.TAG:
				short length = reader.readShort();
				byte [] bytes = new byte[length];
				for (int i = 0; i < length; i++) {
					bytes[i] = reader.readByte();
				}
				result = new UTF8Constant(bytes);
				break;
				
			case IntegerConstant.TAG:
				int i = reader.readInt();
				result = new IntegerConstant(i);
				break;
	
			case FloatConstant.TAG:
				float f = reader.readFloat();
				result = new FloatConstant(f);
				break;
				
			case LongConstant.TAG:
				long l = reader.readLong();
				result = new LongConstant(l);
				break;
				
			case DoubleConstant.TAG:
				double d = reader.readDouble();
				result = new DoubleConstant(d);
				break;
				
			case ClassConstant.TAG:
				short nameIdx = reader.readShort();
				result = new ClassConstant(nameIdx);
				break;
				
			case StringConstant.TAG:
				short stringIdx = reader.readShort();
				result = new StringConstant(stringIdx);
				break;
				
			case FieldConstant.TAG:
				short classIdx = reader.readShort();
				short nameAndTypeIdx = reader.readShort();
				result = new FieldConstant(classIdx,nameAndTypeIdx);
				break;
				
			case MethodrefConstant.TAG:
				classIdx = reader.readShort();
				nameAndTypeIdx = reader.readShort();
				result = new MethodrefConstant(classIdx,nameAndTypeIdx);
				break;
				
			case InterfaceMethodrefConstant.TAG:
				classIdx = reader.readShort();
				nameAndTypeIdx = reader.readShort();
				result = new InterfaceMethodrefConstant(classIdx,nameAndTypeIdx);
				break;
				
			case NameAndTypeConstant.TAG:
				nameIdx = reader.readShort();
				short descriptorIdx = reader.readShort();
				result = new NameAndTypeConstant(nameIdx,descriptorIdx);
				break;
				
			case MethodHandleCosntant.TAG:
				byte refKind = reader.readByte();
				short refIdx = reader.readShort();
				result = new MethodHandleCosntant(refKind,refIdx);
				break;
				
			case MethodTypeConstant.TAG:
				descriptorIdx = reader.readShort();
				result = new MethodTypeConstant(descriptorIdx);
				break;
				
			case InvokeDynamicConstant.TAG:
				short bootstrapMethodAttrIdx = reader.readShort();
				nameAndTypeIdx = reader.readShort();
				result = new InvokeDynamicConstant(bootstrapMethodAttrIdx,nameAndTypeIdx);
				break;
			default:
				throw new IllegalArgumentException("The tag '" + tag +"' does not exist");
		}
		
		return result;
	}
	
	public void setConstantPool (ArrayList<Constant> constantPool) {
		this.constantPool = constantPool;
	}
	
	public ByteBuffer toByteCode() {
		throw new IllegalAccessError("Not Done Yet :(");
	}
	
	public abstract String toString();
		
}
