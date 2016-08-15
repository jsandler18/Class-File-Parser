package access;

/**
 * Holds the possible access flags for a JVM Class Field
 * @author Jake Sandler
 *
 */
public enum FieldAccessFlag implements AccessFlag {
	Public    (0x0001),
	Private   (0x0002),
	Protected (0x0004),
	Static    (0x0008),
	Final     (0x0010),
	Volatile  (0x0040),
	Transient (0x0080),
	Synthetic (0x1000),
	Enum      (0x4000);

	private final short flag;
    private FieldAccessFlag(int flag) {
		this.flag = (short) flag;
	}
	
	@Override
	public short getFlag() {
		return flag;
	}
}
