import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import classfile.*;
import classfile.Method.AccessFlag;
import classfile.attributes.CodeAttribute;
import classfile.attributes.LocalVariableTableAttribute;
import classfile.attributes.LocalVariableTableAttribute.LocalVariableTableEntry;
import classfile.attributes.StackMapTableAttribute;
import classfile.attributes.StackMapTableAttribute.StackMapFrame;
import classfile.attributes.StackMapTableAttribute.VerificationTypeInfoOptions;
public class Driver extends ClassLoader {

	public static void main(String[] args) throws IOException {
		ClassFileWriter w = new ClassFileWriter("TestClass");
		/*
		 * public static int power (int base, int exp) {
		 * 		int result = 1;
		 * 		for (int i = 0; i < exp; i++) {
		 * 			result *= base;
		 * 		}
		 * 		return result;
		 * }
		 */
		byte [] bytecode = {
		/*0*/	0x04, //store 1 on operand stack
		/*1*/	0x3d, //load that 1 into variable 2 (result)
		/*2*/	0x03, //store 0 on operand stack
		/*3*/	0x3e, //load that 0 into variable 3 (i)
		/*4*/	(byte) 0xa7, 0x00,0x0e, //branch to loop condition check at 15
		/*7*/	0x1c, //load result to operand stack
		/*8*/	0x1a, //load base to operand stack
		/*9*/	0x68, //multiply result and base
		/*a*/	0x3d, //store the multiplication in result
		/*b*/	(byte) 0x84, 0x03, 0x01, //i++
		/*e*/	0x1d, //load i onto operand stack
		/*f*/	0x1b, //load exp onto operand stack
		/*10*/	(byte) 0xa1, 0x00,0x07, //if i < exp, branch to beginning of loop
		/*13*/	0x1c, //load result to operand stack
		/*14*/	(byte) 0xac //return top of operand stack
		};
		//set this based on bytecode
		short maxStack = 2;
		ArrayList<LocalVariableTableEntry> varlist = new ArrayList<LocalVariableTableEntry>();
		//add vars here
		varlist.add(new LocalVariableTableAttribute.LocalVariableTableEntry(
				(short)0, 
				(short)(21), 
				"base", 
				"I", 
				(short)0));
		varlist.add(new LocalVariableTableAttribute.LocalVariableTableEntry(
				(short)0, 
				(short)(21), 
				"exp", 
				"I", 
				(short)1));
		varlist.add(new LocalVariableTableAttribute.LocalVariableTableEntry(
				(short)1, 
				(short)(21-1), 
				"result", 
				"I", 
				(short)2));
		varlist.add(new LocalVariableTableAttribute.LocalVariableTableEntry(
				(short)3, 
				(short)(21-3), 
				"i", 
				"I", 
				(short)3));
		LocalVariableTableAttribute vars = new LocalVariableTableAttribute(varlist);
		ArrayList<StackMapFrame> framelist = new ArrayList<StackMapFrame>();
		framelist.add(new StackMapFrame(
				255,
				(short)7,//where the if statement jumps to
				new StackMapTableAttribute.VerificationTypeInfo[] {},//stack is empty
				new StackMapTableAttribute.VerificationTypeInfo[] {//2 local vars that are not params
						new StackMapTableAttribute.VerificationTypeInfo(VerificationTypeInfoOptions.IntegerVariableInfo,(short)0,(short)0),
						new StackMapTableAttribute.VerificationTypeInfo(VerificationTypeInfoOptions.IntegerVariableInfo,(short)0,(short)0)}));
		framelist.add(new StackMapFrame(
				255,
				(short)9,//??
				new StackMapTableAttribute.VerificationTypeInfo[] {},//stack is empty
				new StackMapTableAttribute.VerificationTypeInfo[] {//2 local vars that are not params
						new StackMapTableAttribute.VerificationTypeInfo(VerificationTypeInfoOptions.IntegerVariableInfo,(short)0,(short)0),
						new StackMapTableAttribute.VerificationTypeInfo(VerificationTypeInfoOptions.IntegerVariableInfo,(short)0,(short)0)}));
		StackMapTableAttribute stackmap = new StackMapTableAttribute(framelist);
		CodeAttribute code = new CodeAttribute(maxStack, bytecode, vars, stackmap);
		
		AccessFlag[] flags = new AccessFlag[2];
		flags[0] = AccessFlag.Public;
		flags[1] = AccessFlag.Static;
		
		//fill these in
		String name = "power";
		String signature = "(II)I";
	
		Method m = new Method(flags , name, signature, code);
		
		w.addMethod(m);
		byte [] classfile = w.toByteCode().array();
		
		ClassFileReader r = new ClassFileReader(new DataInputStream(new FileInputStream("/root/git/Class-File-Parser/ClassFile/bin/classfile/ClassFileReader.class")));
		System.out.println(r.toString());

		Driver loader = new Driver();
		Class c = loader.publicDefineClass("TestClass", classfile, 0, classfile.length);
		loader.publicResolveClass(c);
		
		System.out.println(Arrays.toString(c.getMethods()));
	}
	
	public Class<?> publicDefineClass(String name, byte [] b, int off, int len) {
		return super.defineClass(name, b, off, len);
	}
	
	public void publicResolveClass(Class<?> c) {
		super.resolveClass(c);
	}

}
