package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;

public class StackMapTableAttribute extends Attribute {
	
	//try to replicate a C union
	private enum StackMapFrameOptions{
		SameFrame,
		SameLocals1StackItemFrame,
		SameLocals1StackItemFrameExtended,
		ChopFrame,
		SameFrameExtended,
		AppendFrame,
		FullFrame
	}
	private enum VerificationTypeInfoOptions {
		TopVariableInfo,
		IntegerVariableInfo,
		FloatVariableInfo,
		LongVariableInfo,
		DoubleVariableInfo,
		NullVariableInfo,
		UninitializedThisVariableInfo,
		ObjectVariableInfo,
		UninitializedVariableInfo
		
	}
	private class StackMapFrame {
		
		private StackMapFrameOptions option;
		private int frameType;
		private VerificationTypeInfo [] stack;
		private VerificationTypeInfo [] locals;
		private short offsetDelta;
		
		public StackMapFrame(DataInputStream dataScanner) throws IOException {
			
			this.frameType = Byte.toUnsignedInt(dataScanner.readByte());
			if (this.frameType < 64) {
				this.offsetDelta = (short) this.frameType;
				this.option = StackMapFrameOptions.SameFrame;
			}
			else if (this.frameType < 128) {
				this.offsetDelta = (short) (this.frameType - 64);
				this.option = StackMapFrameOptions.SameLocals1StackItemFrame;
				this.stack = new VerificationTypeInfo[1];
				stack[0] = new VerificationTypeInfo(dataScanner);
			}
			else if (this.frameType == 247 ) {
				this.option = StackMapFrameOptions.SameLocals1StackItemFrameExtended;
				this.offsetDelta = dataScanner.readShort();
				this.stack = new VerificationTypeInfo[1];
				stack[0] = new VerificationTypeInfo(dataScanner);
			}
			else if (this.frameType < 251) {
				this.option = StackMapFrameOptions.ChopFrame;
				this.offsetDelta = dataScanner.readShort();
			}
			else if (this.frameType == 251) {
				this.option = StackMapFrameOptions.SameFrameExtended;
				this.offsetDelta = dataScanner.readShort();
			}
			else if (this.frameType < 255){ 
				this.option = StackMapFrameOptions.AppendFrame;
				this.offsetDelta = dataScanner.readShort();
				this.locals = new VerificationTypeInfo [this.frameType - 251];
				for (int i = 0; i < this.locals.length; i++) {
					this.locals[i] = new VerificationTypeInfo(dataScanner);
				}
			}
			else {
				this.option = StackMapFrameOptions.FullFrame;
				this.offsetDelta = dataScanner.readShort();
				short numLocals = dataScanner.readShort();
				this.locals = new VerificationTypeInfo[numLocals];
				for (int i = 0; i < this.locals.length; i++) {
					this.locals[i] = new VerificationTypeInfo(dataScanner);
				}
				short numStackItems = dataScanner.readShort();
				this.stack = new VerificationTypeInfo[numStackItems];
				for (int i = 0; i < this.stack.length; i++) {
					this.stack[i] = new VerificationTypeInfo(dataScanner);
				}
			}
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("Stack Map Frame :\n");
			result.append("\tFrame Type                  : " + this.option.toString() + "\n");
			result.append("\tFrame Tag                   : " + this.frameType + "\n");
			result.append("\tOffset Delta                : " + this.offsetDelta + "\n");
			if (this.locals != null) {
				result.append("\tLocal Variable Verification :\n\t\t");
				for (VerificationTypeInfo local : this.locals) {
					result.append(local.toString().replaceAll("\n", "\n\t\t"));
				}
				result.append("\n");
			}
			if (this.stack != null) {
				result.append("\tOperand Stack Verification  :\n\t\t");
				for (VerificationTypeInfo item : this.stack) {
					result.append(item.toString().replaceAll("\n", "\n\t\t"));
				}
				result.append("\n");
			}
			result.append("\n");
			return result.toString();	
		}
		
	}
	private class VerificationTypeInfo {
		
		private VerificationTypeInfoOptions option;
		private short cpoolIndex;
		private short offset;
		private byte tag;
		public VerificationTypeInfo(DataInputStream dataScanner) throws IOException {
			
			this.tag = dataScanner.readByte();
			switch (this.tag) {
				case 0:
					this.option = VerificationTypeInfoOptions.TopVariableInfo;
					break;
				case 1:
					this.option = VerificationTypeInfoOptions.IntegerVariableInfo;
					break;
				case 2:
					this.option = VerificationTypeInfoOptions.FloatVariableInfo;
					break;
				case 3:
					this.option = VerificationTypeInfoOptions.DoubleVariableInfo;
					break;
				case 4: 
					this.option = VerificationTypeInfoOptions.LongVariableInfo;
					break;
				case 5:
					this.option = VerificationTypeInfoOptions.NullVariableInfo;
					break;
				case 6:
					this.option = VerificationTypeInfoOptions.UninitializedThisVariableInfo;
					break;
				case 7: 
					this.option = VerificationTypeInfoOptions.ObjectVariableInfo;
					this.cpoolIndex = dataScanner.readShort();
					break;
				case 8: 
					this.option = VerificationTypeInfoOptions.UninitializedVariableInfo;
					this.offset = dataScanner.readShort();
					break;
			}
			
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("VerificationInfo :\n");
			result.append("\tVerification Type   : " + this.option.toString() + "\n");
			result.append("\tVerification Tag    : " + this.tag + "\n");
			if (this.option == VerificationTypeInfoOptions.ObjectVariableInfo) {
				result.append("\tConstant Pool Index : " + this.cpoolIndex + "\n");
			}
			else if (this.option == VerificationTypeInfoOptions.UninitializedVariableInfo) {
				result.append("\tOffset              : " + this.offset + "\n");
			}
			return result.toString();
		}
		
	}
	
	//all that stuff ^ was for union >:(
	
	private StackMapFrame [] entries;
	private short stackMapFrameAttributeIndex;
	
	public StackMapTableAttribute(DataInputStream dataScanner) throws IOException {
		this.stackMapFrameAttributeIndex = (short) Attribute.getUTF8ConstantIndex("StackMapTable");
		short numEntries = dataScanner.readShort();
		this.entries = new StackMapFrame[numEntries];
		for (int i = 0; i <  numEntries; i++) {
			this.entries[i] = new StackMapFrame(dataScanner);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName                   : Stack Map Table\n");
		result.append("\tStack Map Table Length : " + this.entries.length + "\n");
		result.append("\tStack Map Table        :\n\t");
		for (StackMapFrame entry : this.entries) {
			result.append(entry.toString().replace("\n", "\n\t"));
		}
		result.append("\n");
		
		return result.toString();
	}

}
