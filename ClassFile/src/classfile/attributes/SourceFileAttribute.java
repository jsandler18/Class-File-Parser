package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceFileAttribute extends Attribute {
	private short sourcefileIndex;
	
	public SourceFileAttribute(DataInputStream dataScanner) throws IOException {
		this.sourcefileIndex = dataScanner.readShort();
	}
	
	@Override
	public String toString() {
		return "AttributeInfo :\n\tName             : Source File\n\tSource File Index : " + this.sourcefileIndex + "\n";
	}

}
