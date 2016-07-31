package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InnerClassesAttribute extends Attribute {

	private enum AccessFlag {
		Public,
		Private,
		Protected,
		Static,
		Final,
		Interface,
		Abstract,
		Synthetic,
		Annotation,
		Enum
	}
	private class InnerClass {
		private short innerClassInfoIndex;
		private short outerClassInfoIndex;
		private short innerNameIndex;
		private ArrayList<AccessFlag> innerAccessFlags;
		public InnerClass(short innerClassInfoIndex, short outerClassInfoIndex, short innerNameIndex, short accessFlags) {
			this.innerClassInfoIndex = innerClassInfoIndex;
			this.outerClassInfoIndex = outerClassInfoIndex;
			this.innerNameIndex = innerNameIndex;
			this.innerAccessFlags = new ArrayList<AccessFlag>();
			if ((accessFlags & 0x0001) != 0) {
				this.innerAccessFlags.add(AccessFlag.Public);
			}
			if ((accessFlags & 0x0002) != 0) {
				this.innerAccessFlags.add(AccessFlag.Private);
			}
			if ((accessFlags & 0x0004) != 0) {
				this.innerAccessFlags.add(AccessFlag.Protected);
			}
			if ((accessFlags & 0x0008) != 0) {
				this.innerAccessFlags.add(AccessFlag.Static);
			}
			if ((accessFlags & 0x0010) != 0) {
				this.innerAccessFlags.add(AccessFlag.Final);
			}
			if ((accessFlags & 0x0200) != 0) {
				this.innerAccessFlags.add(AccessFlag.Interface);
			}
			if ((accessFlags & 0x0400) != 0) {
				this.innerAccessFlags.add(AccessFlag.Abstract);
			}
			if ((accessFlags & 0x1000) != 0) {
				this.innerAccessFlags.add(AccessFlag.Synthetic);
			}
			if ((accessFlags & 0x2000) != 0) {
				this.innerAccessFlags.add(AccessFlag.Annotation);
			}
			if ((accessFlags & 0x4000) != 0) {
				this.innerAccessFlags.add(AccessFlag.Enum);
			}
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("InnerClass :\n");
			result.append("\tInner Class Index : " + this.innerClassInfoIndex + "\n");
			result.append("\tOuter Class Index : " + this.outerClassInfoIndex + "\n");
			result.append("\tInner Name Index  : " + this.innerNameIndex + "\n");
			result.append("\tAccess Flags      : |");
			for (AccessFlag flag : this.innerAccessFlags) {
				result.append(flag.toString() + "|");
			}
			result.append("\n");
			return result.toString();
		}
		
		
	}
	
	private InnerClass [] classes;
	
	public InnerClassesAttribute(DataInputStream dataScanner) throws IOException {
		short len = dataScanner.readShort();
		this.classes = new InnerClass [len];
		for (int i = 0; i < len; i++) {
			this.classes[i] = new InnerClass(dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName             : Inner Classes\n");
		result.append("\tInner Class List :\n\t\t");
		for (InnerClass c : this.classes) {
			result.append(c.toString().replace("\n", "\n\t\t"));
		}
		result.append("\n");
		
		return result.toString();
	}

}
