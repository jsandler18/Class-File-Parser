
package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import classfile.ByteCode;
import classfile.constantpool.Constant;
import classfile.constantpool.UTF8Constant;

public class LocalVariableTableAttribute extends Attribute {

	public static class LocalVariableTableEntry implements ByteCode{
		private short startPC;
		private short length;
		private short nameIndex;
		private short descriptorIndex;
		private short index;
		
		public LocalVariableTableEntry(short startPC, short length, short nameIndex, short descriptorIndex, short index) {
			this.startPC = startPC;
			this.length = length;
			this.nameIndex = nameIndex;
			this.descriptorIndex = descriptorIndex;
			this.index = index;
		}
		
		public LocalVariableTableEntry(short startPC, short length, String name, String descriptor, short index) {
			this.startPC = startPC;
			this.length = length;
			this.nameIndex = (short) Attribute.getUTF8ConstantIndex(name);
			this.descriptorIndex = (short) Attribute.getUTF8ConstantIndex(descriptor);
			this.index = index;
		}

		@Override
		public ByteBuffer toByteCode() {
			ByteBuffer result = ByteBuffer.allocate(10);
			result.putShort(this.startPC);
			result.putShort(this.length);
			result.putShort(this.nameIndex);
			result.putShort(this.descriptorIndex);
			result.putShort(this.index);
			return result;
		}
		
	}
	

	private LocalVariableTableEntry [] localVariableTable;
	private short localVariableTableAttributeIndex;
	
	public LocalVariableTableAttribute(ArrayList <LocalVariableTableEntry> vars) {
		this.localVariableTableAttributeIndex = (short) Attribute.getUTF8ConstantIndex("LocalVariableTable");
		this.localVariableTable = new LocalVariableTableEntry [vars.size()];
		for (int i = 0; i < vars.size(); i++) {
			this.localVariableTable[i] = vars.get(i);
		}
	}
	
	public LocalVariableTableAttribute(DataInputStream dataScanner) throws IOException {
		this.localVariableTableAttributeIndex = (short) Attribute.getUTF8ConstantIndex("LocalVariableTable");
		short localVariableTableLength = dataScanner.readShort();
		this.localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
		for (int i = 0; i < localVariableTableLength; i++) {
			this.localVariableTable[i] = new LocalVariableTableEntry(dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName                        : Local Variable Table\n");
		result.append("\tLocal Variable Table Length : " + this.localVariableTable.length + "\n" );
		result.append("\tLocal Variable Table        :\n");
		for (LocalVariableTableEntry e : this.localVariableTable){
			result.append("\t\tLocal Variable :\n");
			result.append("\t\t\tStart PC         : " + e.startPC + "\n");
			result.append("\t\t\tLength           : " + e.length + "\n");
			result.append("\t\t\tName Index       : " + e.nameIndex + "\n");
			result.append("\t\t\tDescriptor Index : " + e.descriptorIndex + "\n");
			result.append("\t\t\tIndex            : " + e.index + "\n");

		}
		result.append("\n");
		
		return result.toString();
	}
	
	@Override
	public ByteBuffer toByteCode() {
		
		int idx = this.localVariableTableAttributeIndex;
		//6 bytes for tag and len, and 10 bytes for each local var entry
		ByteBuffer result = ByteBuffer.allocate(8 + this.localVariableTable.length * 10);
		result.putShort((short) idx);
		result.putInt(this.localVariableTable.length * 10 + 2);
		result.putShort((short) this.localVariableTable.length);
		for (LocalVariableTableEntry e : this.localVariableTable) {
			result.put(e.toByteCode().array());
		}
		return result;
		
	}

	public int getCount() {
		return this.localVariableTable.length;
	}

}
