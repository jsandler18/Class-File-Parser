
package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class LineNumberTableAttribute extends Attribute {

	private class LineNumberTableEntry {
		private short startPC;
		private short lineNumber;
		public LineNumberTableEntry(short startPC, short lineNumber) {
			this.startPC = startPC;
			this.lineNumber = lineNumber;
		}
	}
	

	private LineNumberTableEntry [] lineNumberTable;
	
	
	public LineNumberTableAttribute(DataInputStream dataScanner) throws IOException {
		short lineNumberTableLength = dataScanner.readShort();
		this.lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
		for (int i = 0; i < lineNumberTableLength; i++) {
			this.lineNumberTable[i] = new LineNumberTableEntry(dataScanner.readShort(), dataScanner.readShort());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName                     : Line Number Table\n");
		result.append("\tLine Number Table Length : " + this.lineNumberTable.length + "\n" );
		result.append("\tLine Number Table        :\n");
		for (LineNumberTableEntry e : this.lineNumberTable){
			result.append("\t\tLine Number Entry :\n");
			result.append("\t\t\tStart PC        : " + e.startPC + "\n");
			result.append("\t\t\tLine Number     : " + e.lineNumber + "\n");

		}
		result.append("\n");
		
		return result.toString();
	}

}
