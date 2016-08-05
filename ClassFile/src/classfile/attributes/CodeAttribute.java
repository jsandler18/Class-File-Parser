
package classfile.attributes;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import classfile.ByteCode;
import classfile.constantpool.Constant;
import classfile.constantpool.UTF8Constant;

public class CodeAttribute extends Attribute {

	public static class ExceptionTableEntry implements ByteCode {
		private short startPC;
		private short endPC;
		private short handlerPC;
		private short catchType;
		public ExceptionTableEntry(short startPC, short endPC, short handlerPC, short catchType) {
			super();
			this.startPC = startPC;
			this.endPC = endPC;
			this.handlerPC = handlerPC;
			this.catchType = catchType;
		}
		@Override
		public ByteBuffer toByteCode() {
			ByteBuffer result = ByteBuffer.allocate(8);
			result.putShort(this.startPC);
			result.putShort(this.endPC);
			result.putShort(this.handlerPC);
			result.putShort(this.catchType);
			return result;
		}
	}
	
	private short codeAttributeIndex;
	private short maxStack;
	private short maxLocals;
	private byte [] code;
	private ExceptionTableEntry [] exceptionTable;
	private Attribute [] attributes;
	
	/**
	 * Constructor for Attribute.createAttribute to use
	 * @param dataScanner a binary scanner of the data segment of this attribute
	 * @throws IOException
	 */
	public CodeAttribute(DataInputStream dataScanner) throws IOException {
		this.codeAttributeIndex = (short) Attribute.getUTF8ConstantIndex("Code");
		this.maxStack = dataScanner.readShort();
		this.maxLocals = dataScanner.readShort();
		int codeLength = dataScanner.readInt();
		this.code = new byte[codeLength];
		for (int i = 0; i < codeLength; i++) {
			this.code[i] = dataScanner.readByte();
		}
		short exceptionTableLength = dataScanner.readShort();
		this.exceptionTable = new ExceptionTableEntry[exceptionTableLength];
		for (int i = 0; i < exceptionTableLength; i++) {
			this.exceptionTable[i] = new ExceptionTableEntry(dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort(), dataScanner.readShort());
		}
		short attributesCount = dataScanner.readShort();
		this.attributes = new Attribute [attributesCount];
		for (int i = 0; i < attributesCount; i++) {
			this.attributes[i] = Attribute.readAttribute(dataScanner);
		}
	}
	
	/**
	 * The most minimal constructor for creating a code attribute from scratch
	 * @param maxStack maximum depth of the operand stack
	 * @param code the bytecode
	 * @param vars the local variables used in the bytecode
	 */
	public CodeAttribute(short maxStack, byte [] code, LocalVariableTableAttribute vars, StackMapTableAttribute stackmap) {
		this.codeAttributeIndex = (short) Attribute.getUTF8ConstantIndex("Code");
		this.maxStack = maxStack;
		this.maxLocals = (short) vars.getCount();
		this.code = Arrays.copyOf(code, code.length);
		this.exceptionTable = new ExceptionTableEntry[0];
		this.attributes = new Attribute[2];
		this.attributes[0] = vars;
		this.attributes[1] = stackmap;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("AttributeInfo :\n");
		result.append("\tName                   : Code\n");
		result.append("\tMax Stack Depth        : " + this.maxStack + "\n");
		result.append("\tLocal Variable Count   : " + this.maxLocals + "\n");
		result.append("\tCode Length            : " + this.code.length + "\n");
		result.append("\tCode                   :" );
		for (int i = 0; i < this.code.length; i++) {
			if (i % 16 == 0) {
				result.append("\n\t\t");
			}
			result.append(String.format("%02x ",this.code[i]));
		}
		result.append("\n\tException Table Length : " + this.exceptionTable.length + "\n" );
		result.append("\tException Table        :\n");
		for (ExceptionTableEntry e : this.exceptionTable){
			result.append("\t\tException :\n");
			result.append("\t\t\tStart PC   : " + e.startPC + "\n");
			result.append("\t\t\tEnd PC     : " + e.endPC + "\n");
			result.append("\t\t\tHandler PC : " + e.handlerPC + "\n");
			result.append("\t\t\tCatch Type : " + e.catchType + "\n");
		}
		result.append("\tAttributes             :\n\t\t");
		for (Attribute f : this.attributes) {
			result.append(f.toString().replaceAll("\n", "\n\t\t"));
		}
		result.append("\n");
		
		return result.toString();
	}
	
	@Override
	public ByteBuffer toByteCode () {
		//search for "Code" in the constant pool for its index. add it if not already there
		int idx = this.codeAttributeIndex;
		byte [][] attributeBuffers = new byte[this.attributes.length][];
		int len = 0;
		//get bytecode of each attribute
		for (int i = 0; i < this.attributes.length; i++) {
			attributeBuffers[i] = this.attributes[i].toByteCode().array();
			len += attributeBuffers[i].length;
		}
		//len = len of all attribute bytecodes + the sizes of the other stuff
		len += 2 + 2 + 4 + this.code.length + 2 + 8*this.exceptionTable.length + 2;
		//buffer has room for len bytes + a short tag and an int that is the value of len
		ByteBuffer result = ByteBuffer.allocate(len + 6);
		result.putShort((short) idx);
		result.putInt(len);
		result.putShort(this.maxStack);
		result.putShort(this.maxLocals);
		result.putInt(this.code.length);
		result.put(this.code);
		result.putShort((short) this.exceptionTable.length);
		for (ExceptionTableEntry e : this.exceptionTable) {
			result.put(e.toByteCode().array());
		}
		result.putShort((short) this.attributes.length);
		for (Attribute a : this.attributes) {
			result.put(a.toByteCode().array());
		}
		return result;
		
	}

}
