package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class EnclosingMethodAttribute extends Attribute {
	private short classIndex;
	private short methodIndex;
	
	public EnclosingMethodAttribute(DataInputStream dataScanner) throws IOException {
		this.classIndex = dataScanner.readShort();
		this.methodIndex = dataScanner.readShort();
	}
	
	@Override
	public String toString() {
		return "AttributeInfo :\n\tName       : Enclosing Method\n\tClass Index : " 
	+ this.classIndex + "\n\tMethod Index : " + this.methodIndex + "\n";
	}

}
