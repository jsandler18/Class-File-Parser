package classfile.attributes;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.BinaryRefAddr;
import javax.naming.OperationNotSupportedException;

import classfile.ByteCode;
import classfile.constantpool.Constant;
import classfile.constantpool.UTF8Constant;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public abstract class Attribute implements ByteCode{
	
	//abstract constructor
	private static ArrayList<Constant> constantPoolList;
	public static void setConstantPoolList (ArrayList<Constant> constantPools) {
		constantPoolList = constantPools;
	}
	
	/**
	 * gets the index of UTF8Constant with the given content
	 * If it isn't present, add it
	 * @param content the string to look for
	 * @return the 1-indexed location of this string in the constant pool
	 */
	public static int getUTF8ConstantIndex (String content) {
		int idx;
		for (idx = 1; idx <= constantPoolList.size(); idx ++) {
			if (constantPoolList.get(idx-1) instanceof UTF8Constant && ((UTF8Constant) constantPoolList.get(idx-1)).getContent().equals(content)) {
				break;
			}
		}
		if (idx > constantPoolList.size()) {
			constantPoolList.add(new UTF8Constant("LocalVariableTable".getBytes()));
		}
		return idx;
	}
	
	public static Attribute readAttribute(DataInputStream reader) throws IOException {
		short nameIdx = reader.readShort();
		int len = reader.readInt();
		byte [] data = new byte [len];
		for (int i = 0; i < len; i++) {
			data[i] = reader.readByte();
		}
		
		if (!(constantPoolList.get(nameIdx - 1) instanceof Constant)) {
			throw new java.lang.ClassFormatError("Could not read an attribute's name because its name index was not pointing to a utf-8 string");
		}
		
		String name = ((UTF8Constant) constantPoolList.get(nameIdx - 1)).getContent();
		Attribute result = null;
		DataInputStream dataScanner = new DataInputStream(new ByteArrayInputStream(data));
		//try to make the class by name, if unsuccessful, make an unkown attribute class
		try {
			result = (Attribute) Class.forName("classfile.attributes." +name+ "Attribute").getConstructor(dataScanner.getClass()).newInstance(dataScanner);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			result = new UnknownAttribute(data);
		}
		
		
		return result;
	}
	
	public abstract String toString();
	public ByteBuffer toByteCode () {
		throw new IllegalAccessError("Not Done Yet :(");
	}

}
