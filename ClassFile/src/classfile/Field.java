package classfile;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import classfile.Method.AccessFlag;
import classfile.attributes.Attribute;
import classfile.constantpool.Constant;

public class Field implements ByteCode{
	public enum AccessFlag {
		Public (0x0001),
		Private (0x0002),
		Protected (0x0004),
		Static (0x0008),
		Final (0x0010),
		Volatile (0x0040),
		Transient (0x0080),
		Synthetic (0x1000),
		Enum (0x4000);
		
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
	
	public Field (DataInputStream reader) throws IOException {
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
		if ((accessMask & 0x0040) != 0) {
			this.accessFlags.add(AccessFlag.Volatile);
		}
		if ((accessMask & 0x0080) != 0) {
			this.accessFlags.add(AccessFlag.Transient);
		}
		if ((accessMask & 0x1000) != 0) {
			this.accessFlags.add(AccessFlag.Synthetic);
		}
		if ((accessMask & 0x4000) != 0) {
			this.accessFlags.add(AccessFlag.Enum);
		}
		this.nameIndex = reader.readShort();
		this.descriptorIndex = reader.readShort();
		short attrcount =  reader.readShort();
		this.attributes = new Attribute [attrcount];
		for (int i = 0; i < attrcount; i++) {
			this.attributes[i] = Attribute.readAttribute(reader);
		}
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("FieldInfo :\n");
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
