
package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class LocalVariableTypeTableAttribute extends Attribute {

	private class LocalVariableTypeTableEntry {
		private short startPC;
		private short length;
		private short nameIndex;
		private short signatureIndex;
		private short index;
		
		public LocalVariableTypeTableEntry(short startPC, short length, short nameIndex, short signatureIndex, short index) {
			this.startPC = startPC;
			this.length = length;
			this.nameIndex = nameIndex;
			this.signatureIndex = signatureIndex;
			this.index = index;
		}
		
	}
	

	private LocalVariableTypeTableEntry [] localVariableTypeTable;
	
	
	public LocalVariableTypeTableAttribute(DataInputStream dataScanner) throws IOException {
		short localVariableTypeTableLength = dataScanner.readShort();
		this.localVariableTypeTable = new LocalVariableTypeTableEntry[localVariableTypeTableLength];
		for (int i = 0; i < localVariableTypeTableLength; i++) {
			this.localVariableTypeTable[i] = new LocalVariableTypeTableEntry(dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName                             : Local Variable Type Table\n");
		result.append("\tLocal Variable Type Table Length : " + this.localVariableTypeTable.length + "\n" );
		result.append("\tLocal Variable Type Table        :\n");
		for (LocalVariableTypeTableEntry e : this.localVariableTypeTable){
			result.append("\t\tLocal Variable Type :\n");
			result.append("\t\t\tStart PC         : " + e.startPC + "\n");
			result.append("\t\t\tLength           : " + e.length + "\n");
			result.append("\t\t\tName Index       : " + e.nameIndex + "\n");
			result.append("\t\t\tSignature Index  : " + e.signatureIndex + "\n");
			result.append("\t\t\tIndex            : " + e.index + "\n");

		}
		result.append("\n");
		
		return result.toString();
	}

}
