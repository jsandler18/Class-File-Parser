package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantValueAttribute extends Attribute {

	private short constantIndex;
	public ConstantValueAttribute(DataInputStream dataScanner) throws IOException {
		this.constantIndex = dataScanner.readShort();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("AttributeInfo :\n");
		result.append("\tName                 : Constant Value\n");
		result.append("\tData Length          : 2\n");
		result.append("\tConstant Value Index : " + this.constantIndex + "\n");
		return result.toString();
	}

}
