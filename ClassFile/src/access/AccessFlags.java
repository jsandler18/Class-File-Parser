package access;

import java.util.ArrayList;

/**
 * 
 * @author Jake Sandler
 * This class represents a set of Access Flags for any class, 
 * method, field, attribute, etc. that has a 2 byte access flag in it.
 * It can convert itself to this 2 byte access flag format
 */
public class AccessFlags {
	private ArrayList<AccessFlag> flags;
	
	/**
	 * Gets the 2 byte access flag from this class
	 * @return the access flags
	 */
	public short getFlags(){
		short result = 0;
		for (AccessFlag flag : flags) {
			result |= flag.getFlag();
		}
		return result;
	}
	
	/**
	 * Adds a single flag to this set of flags
	 * @param flag the flag to add
	 */
	public void addFlag(AccessFlag flag) {
		flags.add(flag);
	}
}
