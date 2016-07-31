package classfile.attributes;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class SourceDebugExtensionAttribute extends Attribute {
	private byte [] debugExtension;
	
	public SourceDebugExtensionAttribute(DataInputStream dataScanner) throws IOException {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		//this is gross :(
		try {
			while (true) {
				bytes.add(dataScanner.readByte());
			}
		} catch (EOFException e) {
			//continue on
		}
		Byte [] tmp = bytes.toArray(new Byte[0]);
		this.debugExtension = new byte[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			this.debugExtension[i] = tmp[i];
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n\tName            : Source Debug Extension\n\tDebug Extension :");
		for (int i = 0; i < this.debugExtension.length; i++) {
			if (i % 16 == 0) {
				result.append("\n\t\t");
			}
			result.append(String.format("%x ", this.debugExtension[i]));
		}
		
		return result.toString();
	}

}
