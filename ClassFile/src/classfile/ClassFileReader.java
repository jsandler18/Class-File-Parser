package classfile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import classfile.attributes.Attribute;
import classfile.constantpool.Constant;

public class ClassFileReader {
	
	
	private int magic;
	private short minor;
	private short major;
	private short constantPoolCount;
	private ArrayList<Constant> constantPoolList;
	private ArrayList<ClassAccessFlag> accessFlags;
	private short thisClass;
	private short superClass;
	private short interfaceCount;
	private int [] interfaces;
	private short fieldsCount;
	private Field [] fields;
	private short methodsCount;
	private Method [] methods;
	private short attributeCount;
	private Attribute [] attributes;
	
	public ClassFileReader(String filename) throws IOException {
		this(new DataInputStream(new FileInputStream(filename)));
	}
	
	public ClassFileReader(DataInputStream reader) throws IOException {
		this.magic = reader.readInt();
		this.minor = reader.readShort();
		this.major = reader.readShort();
		this.constantPoolCount = (short) (reader.readShort() - 1);
		this.constantPoolList = new ArrayList<Constant>(this.constantPoolCount);
		Attribute.setConstantPoolList(constantPoolList);
		for (int i = 0; i < this.constantPoolCount; i++) {
			this.constantPoolList.add(Constant.readConstant(reader));
		}
		short flags = reader.readShort();
		this.accessFlags = new ArrayList<ClassAccessFlag>();
		if ((flags & 0x0001) != 0) {
			this.accessFlags.add(ClassAccessFlag.Public);
		}
		if ((flags & 0x0010) != 0) {
			this.accessFlags.add(ClassAccessFlag.Final);
		}
		if ((flags & 0x0020) != 0) {
			this.accessFlags.add(ClassAccessFlag.Super);
		}
		if ((flags & 0x0200) != 0) {
			this.accessFlags.add(ClassAccessFlag.Interface);
		}
		if ((flags & 0x0400) != 0) {
			this.accessFlags.add(ClassAccessFlag.Abstract);
		}
		if ((flags & 0x1000) != 0) {
			this.accessFlags.add(ClassAccessFlag.Synthetic);
		}
		if ((flags & 0x2000) != 0) {
			this.accessFlags.add(ClassAccessFlag.Annotation);
		}
		if ((flags & 0x4000) != 0) {
			this.accessFlags.add(ClassAccessFlag.Enum);
		}
		this.thisClass = reader.readShort();
		this.superClass = reader.readShort();
		this.interfaceCount = reader.readShort();
		this.interfaces = new int[this.interfaceCount];
		for (int i = 0; i < this.interfaceCount; i++) {
			this.interfaces[i] = reader.readShort();
		}
		this.fieldsCount = reader.readShort();
		this.fields = new Field[this.fieldsCount];
		for (int i = 0; i < this.fieldsCount; i++) {
			this.fields[i] = new Field(reader);
		}
		this.methodsCount = reader.readShort();
		this.methods = new Method[this.methodsCount];
		for (int i = 0; i < this.methodsCount; i++) {
			this.methods[i] = new Method(reader);
		}
		this.attributeCount = reader.readShort();
		this.attributes = new Attribute[this.attributeCount];
		for (int i = 0; i < this.attributeCount; i++) {
			this.attributes[i] = Attribute.readAttribute(reader);
		}
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("ClassFile :\n");
		result.append(String.format("\tMagic Number   : %x\n", this.magic));
		result.append(String.format("\tVersion Number : %d.%d\n",this.major ,this.minor));
		result.append("\tConstant Pool  :");
		for (int i = 0; i < this.constantPoolCount; i++) {
			result.append(String.format("\n\t\tIndex : %d\n\t\t", i+1));
			result.append(this.constantPoolList.get(i).toString().replaceAll("\n", "\n\t\t"));
		}
		result.append("\n\tAccess Flags :\n\t\t|");
		for (ClassAccessFlag flag : this.accessFlags) {
			result.append(String.format("%s|", flag.toString()));
		}
		result.append("\n\tThis Class Index  : " + this.thisClass);
		result.append("\n\tSuper Class Index : " + this.superClass);
		result.append("\n\tImplemented Interface Indices : ");
		for (int idx : this.interfaces) {
			result.append(idx + " " );
		}
		result.append("\n\tFields :\n\t\t");
		for (Field f : this.fields) {
			result.append(f.toString().replaceAll("\n", "\n\t\t"));
		}
		result.append("\n\tMethods :\n\t\t");
		for (Method f : this.methods) {
			result.append(f.toString().replaceAll("\n", "\n\t\t"));
		}
		result.append("\n\tAttributes :\n\t\t");
		for (Attribute f : this.attributes) {
			result.append(f.toString().replaceAll("\n", "\n\t\t"));
		}
		result.append("\n");
		
		
		return result.toString();
	}
	
	public static int test (String t) {
		int i = t.length();
		float j = (float) (1.0f / i);
		short k = 0;
		k = (short) Math.floor(j + (float) Math.PI);
		System.out.println(k);
		return Short.toUnsignedInt(k);
	}
}
