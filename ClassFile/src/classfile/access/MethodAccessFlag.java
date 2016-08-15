package classfile.access;

/**
 * Holds the possible access flags for a JVM Class Method
 * @author Jake Sandler
 *
 */
public enum MethodAccessFlag implements AccessFlag {
	Public       (0x0001),
	Private      (0x0002),
	Protected    (0x0004),
	Static       (0x0008),
	Final        (0x0010),
	Synchronized (0x0020),
	Bridge       (0x0040),
	Varargs      (0x0080),
	Native       (0x0100),
	Abstract     (0x0400),
	Strict       (0x0800),
	Synthetic    (0x1000);

	private final short flag;
    private MethodAccessFlag(int flag) {
		this.flag = (short) flag;
	}
	
	@Override
	public short getFlag() {
		return flag;
	}
}