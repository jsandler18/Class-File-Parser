package classfile;

/**
 * This interface defines objects that must beable to turn themselves
 * into valid JVM Bytecode
 * @author Jake Sandler
 *
 */
public interface ByteCode {
	/**
	 * Turn the current object into its JVM bytecode representation
	 * @return the bytes of the bytecode
	 */
	public byte[] toByteCode();
}
