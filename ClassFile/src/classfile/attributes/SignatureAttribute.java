package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class SignatureAttribute extends Attribute {
	private short signatureIndex;
	
	public SignatureAttribute(DataInputStream dataScanner) throws IOException {
		this.signatureIndex = dataScanner.readShort();
	}
	
	@Override
	public String toString() {
		return "AttributeInfo :\n\tName           : Signature\n\tSignature Index : " + this.signatureIndex + "\n";
	}

}
