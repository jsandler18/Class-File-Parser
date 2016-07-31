package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class ExceptionsAttribute extends Attribute {

	private short [] exceptionIndexTable;
	public ExceptionsAttribute(DataInputStream dataScanner) throws IOException {
		short len = dataScanner.readShort();
		this.exceptionIndexTable = new short [len];
		for (int i = 0; i < len ; i++) {
			this.exceptionIndexTable[i] = dataScanner.readShort();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo:\n");
		result.append("\tName                   : Exception Index Table\n");
		result.append("\tException Table Length : " + this.exceptionIndexTable.length + "\n");
		result.append("\tException Index Table  :\n\t\t");
		for (short s : this.exceptionIndexTable) {
			result.append(s + "\n\t\t");
		}
		result.append("\n");
		
		return result.toString();
	}

}
