package classfile.attributes;

import java.util.Arrays;


public class UnknownAttribute extends Attribute {

	private byte [] data;

	public UnknownAttribute(byte [] bytes) {
		this.data = Arrays.copyOf(bytes, bytes.length);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("AttributeInfo :\n");
		result.append("\tName        : Unknown\n");
		result.append("\tData Length : " + this.data.length + "\n");
		result.append("\tData        :");
		for (int i = 0; i < this.data.length; i++) {
			if (i % 16 == 0) {
				result.append("\n\t\t");
			}
			result.append(String.format("%02x ",this.data[i]));
		}
		result.append("\n");
		return result.toString();
	}

}
