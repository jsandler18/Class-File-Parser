package classfile;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Attr;

import classfile.Field.AccessFlag;
import classfile.attributes.Attribute;
import classfile.attributes.CodeAttribute;
import classfile.constantpool.Constant;

public class Method implements ByteCode{
	public enum AccessFlag {
		Public (0x0001),
		Private (0x0002),
		Protected (0x0004),
		Static (0x0008),
		Final (0x0010),
		Synchronized (0x0020),
		Bridge (0x0040),
		Varargs (0x0080),
		Native (0x0100),
		Abstract (0x0400),
		Strict (0x0800),
		Synthetic (0x1000);
		
		private final short flag;
		private AccessFlag (int flag) {
			this.flag = (short) flag;
		}
		
		public short flag() {
			return this.flag;
		}
	}
	
	private ArrayList<AccessFlag> accessFlags;
	private short nameIndex;
	private short descriptorIndex;
	private Attribute [] attributes;
	
	public Method (DataInputStream reader) throws IOException {
		short accessMask = reader.readShort();
		this.accessFlags = new ArrayList<AccessFlag>();
		if ((accessMask & 0x0001) != 0) {
			this.accessFlags.add(AccessFlag.Public);
		}
		if ((accessMask & 0x0002) != 0) {
			this.accessFlags.add(AccessFlag.Private);
		}
		if ((accessMask & 0x0004) != 0) {
			this.accessFlags.add(AccessFlag.Protected);
		}
		if ((accessMask & 0x0008) != 0) {
			this.accessFlags.add(AccessFlag.Static);
		}
		if ((accessMask & 0x0010) != 0) {
			this.accessFlags.add(AccessFlag.Final);
		}
		if ((accessMask & 0x0020) != 0) {
			this.accessFlags.add(AccessFlag.Synchronized);
		}
		if ((accessMask & 0x0040) != 0) {
			this.accessFlags.add(AccessFlag.Bridge);
		}
		if ((accessMask & 0x0080) != 0) {
			this.accessFlags.add(AccessFlag.Varargs);
		}
		if ((accessMask & 0x0100) != 0) {
			this.accessFlags.add(AccessFlag.Native);
		}
		if ((accessMask & 0x0400) != 0) {
			this.accessFlags.add(AccessFlag.Abstract);
		}
		if ((accessMask & 0x0800) != 0) {
			this.accessFlags.add(AccessFlag.Strict);
		}
		if ((accessMask & 0x1000) != 0) {
			this.accessFlags.add(AccessFlag.Synthetic);
		}

		this.nameIndex = reader.readShort();
		this.descriptorIndex = reader.readShort();
		short attrcount =  reader.readShort();
		this.attributes = new Attribute [attrcount];
		for (int i = 0; i < attrcount; i++) {
			this.attributes[i] = Attribute.readAttribute(reader);
		}
	}
	
	/**
	 * The most basic way to create a valid Method object
	 * @param name the name of the method
	 * @param signature the jvm signature of the method, specified in the JVM spec.
	 * 	Note that this is not the same as a normal java method signature
	 * @param code the code of the method
	 */
	public Method(AccessFlag [] flags, String name, String signature, CodeAttribute code) {
		this.nameIndex = (short) Attribute.getUTF8ConstantIndex(name);
		this.descriptorIndex = (short) Attribute.getUTF8ConstantIndex(signature);
		this.accessFlags = new ArrayList<AccessFlag>();
		for (AccessFlag flag : flags) {
			this.accessFlags.add(flag);
		}
		this.attributes = new Attribute[1];
		this.attributes[0] = code;
	}
	
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("\nMethod Info :\n");
		result.append("\tName Index       : " + this.nameIndex +"\n");
		result.append("\tDescriptor Index : " + this.descriptorIndex + "\n");
		result.append("\tAccess Modifiers : |");
		for (AccessFlag flag : this.accessFlags) {
			result.append(flag.name() + "|");
		}
		result.append("\n");
		for (Attribute attr : this.attributes) {
			result.append("\t"+attr.toString().replaceAll("\n", "\n\t"));
		}
		return result.toString();
	}
	

	@Override
	public ByteBuffer toByteCode() {
		byte [][] attributeBuffers = new byte[this.attributes.length][];
		int len = 0;
		for (int i = 0; i < this.attributes.length; i++) {
			attributeBuffers[i] = this.attributes[i].toByteCode().array();
			len += attributeBuffers[i].length;
		}
		ByteBuffer result = ByteBuffer.allocate(len + 8);
		short flags = 0;
		for (AccessFlag flag : this.accessFlags) {
			flags |= flag.flag();
		}
		//fill first 8 bytes with field info
		result.putShort(flags);
		result.putShort(this.nameIndex);
		result.putShort(this.descriptorIndex);
		result.putShort((short) this.attributes.length);
		//fill rest with attribute info
		for(byte[] a : attributeBuffers) {
			result.put(a);
		}
		return result;
	}
}
