package classfile.attributes;

import java.io.DataInputStream;

public class SyntheticAttribute extends Attribute {

	public SyntheticAttribute(DataInputStream dataScanner) {
		//do nothing, this is just so this class can be created 
		//by Attribute.createAttribute
	}
	
	@Override
	public String toString() {
		return "AttributeInfo :\n\tName : Synthetic\n";
	}

}
