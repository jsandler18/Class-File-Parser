package classfile;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import classfile.attributes.Attribute;
import classfile.constantpool.ClassConstant;
import classfile.constantpool.Constant;
import classfile.constantpool.UTF8Constant;

public class ClassFileWriter implements ByteCode{
	private static final int magic = 0xCAFEBABE;
	private short major;
	private short minor;
	private ArrayList<Constant> constantPool;
	private ClassAccessFlag [] accessFlags;
	private short thisClassIndex;
	private short superClassIndex;
	private ArrayList<Short> interfaces;
	private ArrayList<Field> fields;
	private ArrayList<Method> methods;
	private ArrayList<Attribute> attributes;
	
	public ClassFileWriter(String className) {
		this.major = 52;
		this.minor = 0;
		this.constantPool = new ArrayList<Constant>();
		Attribute.setConstantPoolList(constantPool);
		this.constantPool.add(new ClassConstant((short) 2));
		this.constantPool.add(new UTF8Constant(className.getBytes()));
		this.constantPool.add(new ClassConstant((short) 4));
		this.constantPool.add(new UTF8Constant("java/lang/Object".getBytes()));
		this.accessFlags = new ClassAccessFlag [1];
		this.accessFlags[0] = ClassAccessFlag.Public;
		this.thisClassIndex = 1;
		this.superClassIndex = 3;
		this.interfaces = new ArrayList<Short>();
		this.fields = new ArrayList<Field>();
		this.methods = new ArrayList<Method>();
		this.attributes = new ArrayList<Attribute>();
	}
	
	public void addMethod(Method m) {
		this.methods.add(m);
	}

	@Override
	public ByteBuffer toByteCode() {
		int size = 0;
		
		size = 4 		//magic
				+ 2 	//minor
				+ 2  	//major
				+ 2 	//Constant Pool count
				+ 2		//Access flags
				+ 2 	//this class index
				+ 2 	//super class index
				+ 2 	//interface count
				+ 2 * this.interfaces.size() //interfaces
				+ 2		//methods count
				+ 2 	//fields count
				+ 2		//attributes count
				;
		ByteBuffer [] constants = new ByteBuffer[this.constantPool.size()];
		ByteBuffer [] methods = new ByteBuffer[this.methods.size()];
		ByteBuffer [] fields = new ByteBuffer[this.fields.size()];
		ByteBuffer [] attributes = new ByteBuffer[this.attributes.size()];
		
		for (int i = 0; i < this.constantPool.size(); i++) {
			constants[i] = this.constantPool.get(i).toByteCode();
			size += constants[i].array().length;
		}
		
		for (int i = 0; i < this.methods.size(); i++) {
			methods[i] = this.methods.get(i).toByteCode();
			size += methods[i].array().length;
		}
		
		for (int i = 0; i < this.fields.size(); i++) {
			fields[i] = this.fields.get(i).toByteCode();
			size += fields[i].array().length;
		}
		
		for (int i = 0; i < this.attributes.size(); i++) {
			attributes[i] = this.attributes.get(i).toByteCode();
			size += attributes[i].array().length;
		}
		
		ByteBuffer result = ByteBuffer.allocate(size);
		
		result.putInt(magic);
		result.putShort(this.minor);
		result.putShort(this.major);
		result.putShort((short) this.constantPool.size());
		for (ByteBuffer c : constants) {
			result.put(c);
		}
		
		short flags = 0;
		for (ClassAccessFlag f : this.accessFlags) {
			flags |= f.flag();
		}
		
		result.putShort(flags);
		result.putShort(this.thisClassIndex);
		result.putShort(this.superClassIndex);
		result.putShort((short) this.interfaces.size());
		
		for (Short s : this.interfaces) {
			result.putShort(s);
		}
		
		result.putShort((short) this.fields.size());
		
		for (ByteBuffer f : fields) {
			result.put(f);
		}
		
		result.putShort((short) this.methods.size());
		
		for (ByteBuffer m : methods) {
			result.put(m);
		}
		
		result.putShort((short) this.attributes.size());
		
		for (ByteBuffer a : attributes) {
			result.put(a);
		}
		
		System.out.println(this.methods.toString());
		byte [] tmp = result.array();
		for (int i = 0; i < tmp.length; i++) {
			if (i%16 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%02x ",tmp[i]));
		}
		return result;
	}
	
	
	
	
}
