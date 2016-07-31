package classfile.attributes;

import java.io.DataInputStream;

public class DepricatedAttribute extends Attribute {

	public DepricatedAttribute(DataInputStream dataScanner) {
		//do nothing, this is just so this class can be created 
		//by Attribute.createAttribute
	}
	
	@Override
	public String toString() {
		return "AttributeInfo :\n\tName : Depricated\n";
	}

}
