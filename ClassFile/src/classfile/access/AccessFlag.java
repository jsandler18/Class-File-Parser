package classfile.access;

/**
 * This interface defines the requirements for a single access flag
 * that can be used in the AccessFlags class. 
 * @author Jake Sandler
 *
 */
public interface AccessFlag {
	/**
	 * Gets the value of this one flag, with the intention of being 
	 * bitwise or'ed with other flags
	 * @return a 2 byte value representing the one flag
	 */
	public short getFlag();
}
