package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.ArrayList;
import java.util.Arrays;

import classfile.ByteCode;

public class StackMapTableAttribute extends Attribute {
	
	//try to replicate a C union
	public enum StackMapFrameOptions{
		SameFrame,
		SameLocals1StackItemFrame,
		SameLocals1StackItemFrameExtended,
		ChopFrame,
		SameFrameExtended,
		AppendFrame,
		FullFrame
	}
	public enum VerificationTypeInfoOptions {
		TopVariableInfo (0),
		IntegerVariableInfo (1),
		FloatVariableInfo (2),
		LongVariableInfo (3),
		DoubleVariableInfo (4),
		NullVariableInfo (5),
		UninitializedThisVariableInfo (6),
		ObjectVariableInfo (7),
		UninitializedVariableInfo (8);
		
		private final int tag;
		private VerificationTypeInfoOptions(int tag) {
			this.tag = tag;
		}
		
		public int getTag() {
			return this.tag;
		}
		
	}
	public static class StackMapFrame implements ByteCode{
		
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
		
		
		public StackMapFrame(int frametype, short offsetDelta, VerificationTypeInfo[] stack, VerificationTypeInfo[] locals) {
			this.frameType = frametype;
			this.offsetDelta = offsetDelta;
			this.stack = Arrays.copyOf(stack, stack.length);
			this.locals = Arrays.copyOf(locals, locals.length);
			if (frametype < 64) {
				this.option = StackMapFrameOptions.SameFrame;
			}
			else if (frametype < 128) {
				this.option = StackMapFrameOptions.SameLocals1StackItemFrame;
			}
			else if (frametype == 247) {
				this.option = StackMapFrameOptions.SameLocals1StackItemFrameExtended;
			}
			else if (frametype > 247 && frametype < 251) {
				this.option = StackMapFrameOptions.ChopFrame;
			}
			else if (frametype == 251) {
				this.option = StackMapFrameOptions.SameFrameExtended;
			}
			else if (frametype > 251 && frametype <255) {
				this.option = StackMapFrameOptions.AppendFrame;
			}
			else if (frametype == 255) {
				this.option = StackMapFrameOptions.FullFrame;
			}
			else {
				throw new IllegalArgumentException("The tag is bad");
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
		
		public int getSize() {
			int size = 0;
			switch (this.option) {
			case SameFrame:
				return 1;
			case SameLocals1StackItemFrame:
				return (1 + this.stack[0].getSize());
			case SameLocals1StackItemFrameExtended:
				return (1 + 2 + this.stack[0].getSize());
			case ChopFrame:
			case SameFrameExtended:
				return 3;
			case AppendFrame:
				for (VerificationTypeInfo ver : this.locals) {
					size += ver.getSize();
				}
				return (1 + 2 + size);
			case FullFrame:
				for (VerificationTypeInfo ver : this.locals) {
					size += ver.getSize();
				}
				for (VerificationTypeInfo ver : this.stack) {
					size += ver.getSize();
				}
				return (1 + 2 + 2 + 2 + size);
			}
			return 0;
		}

		@Override
		public ByteBuffer toByteCode() {
			ByteBuffer result = null;
			switch (this.option) {
				case SameFrame:
					result = ByteBuffer.allocate(this.getSize());
					result.put((byte) this.frameType);
					break;
				case SameLocals1StackItemFrame:
					result = ByteBuffer.allocate(this.getSize());
					result.put((byte) this.frameType);
					result.put(this.stack[0].toByteCode().array());
					break;
				case SameLocals1StackItemFrameExtended:
					result = ByteBuffer.allocate(this.getSize());
					result.put((byte) this.frameType);
					result.putShort(this.offsetDelta);
					result.put(this.stack[0].toByteCode().array());
					break;
				case ChopFrame:
				case SameFrameExtended:
					result = ByteBuffer.allocate(this.getSize());
					result.put((byte) this.frameType);
					result.putShort(offsetDelta);
					break;
				case AppendFrame:

					result = ByteBuffer.allocate(this.getSize());
					result.put((byte) this.frameType);
					result.putShort(this.offsetDelta);
					for (VerificationTypeInfo ver : this.locals) {
						result.put(ver.toByteCode().array());
					}
				case FullFrame:
					result = ByteBuffer.allocate(this.getSize());
					result.put((byte) this.frameType);
					result.putShort(offsetDelta);
					result.putShort((short) this.locals.length);
					for (VerificationTypeInfo ver : this.locals) {
						result.put(ver.toByteCode().array());
					}
					result.putShort((short) this.stack.length);
					for (VerificationTypeInfo ver : this.stack) {
						result.put(ver.toByteCode().array());
					}
					
			}
			return result;
		}
		
	}
	public static class VerificationTypeInfo implements ByteCode{
		
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
		
		/**
		 * 
		 * @param option which type this verifies
		 * @param offset indicates the offset, in the code array of the Code attribute that contains this StackMapTable attribute,
		 *  of the new instruction that created the object being stored in the location. only required for UninitializedVariable, else doesn't matter
		 * @param cpoolindex index into the constant pool of the object this verifies. only reauired for ObjectVariable, else doesn't matter
		 */
		public VerificationTypeInfo(VerificationTypeInfoOptions option, short offset, short cpoolindex) {
			this.option = option;
			this.offset = offset;
			this.cpoolIndex = cpoolindex;
		}
		
		public int getSize() {
			if (this.option == VerificationTypeInfoOptions.UninitializedThisVariableInfo || this.option == VerificationTypeInfoOptions.ObjectVariableInfo) {
				return 3;
			}
			else {
				return 1;
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

		@Override
		public ByteBuffer toByteCode() {
			ByteBuffer result = ByteBuffer.allocate(this.getSize());
			result.put((byte) this.option.getTag());
			if (this.option == VerificationTypeInfoOptions.UninitializedThisVariableInfo) {
				result.putShort(this.offset);
			}
			else if (this.option == VerificationTypeInfoOptions.ObjectVariableInfo) {
				result.putShort(this.cpoolIndex);
			}
			return result;
		}
		
	}
	
	//all that stuff ^ was for union >:(
	
	private ArrayList<StackMapFrame> entries;
	private short stackMapFrameAttributeIndex;
	
	public StackMapTableAttribute(DataInputStream dataScanner) throws IOException {
		this.stackMapFrameAttributeIndex = (short) Attribute.getUTF8ConstantIndex("StackMapTable");
		short numEntries = dataScanner.readShort();
		this.entries = new ArrayList<StackMapFrame>(numEntries);
		for (int i = 0; i <  numEntries; i++) {
			 this.entries.add(new StackMapFrame(dataScanner));
		}
	}
	
	public StackMapTableAttribute(ArrayList<StackMapFrame> entries) {
		this.stackMapFrameAttributeIndex = (short) Attribute.getUTF8ConstantIndex("StackMapTable");
		this.entries = (ArrayList<StackMapFrame>) entries.clone();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName                   : Stack Map Table\n");
		result.append("\tStack Map Table Length : " + this.entries.size() + "\n");
		result.append("\tStack Map Table        :\n\t");
		for (StackMapFrame entry : this.entries) {
			result.append(entry.toString().replace("\n", "\n\t"));
		}
		result.append("\n");
		
		return result.toString();
	}
	
	@Override
	public ByteBuffer toByteCode() {
		int size = 0;
		for (StackMapFrame entry : this.entries) {
			size += entry.getSize();
		}
		ByteBuffer result = ByteBuffer.allocate(size + 2 + 4 + 2);
		result.putShort(this.stackMapFrameAttributeIndex);
		result.putInt(size + 2); //stack map frame table + the length of it
		result.putShort((short) this.entries.size());
		for (StackMapFrame entry : this.entries) {
			result.put(entry.toByteCode().array());
		}
		
		return result;
		
	}

}
