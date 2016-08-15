package access;

/**
 * Holds the possible access flags for a Nested Class
 * @author Jake Sandler
 *
 */
public enum NestedClassAccessFlag implements AccessFlag {
	Public     (0x0001),
	Private    (0x0002),
	Protected  (0x0004),
	Static     (0x0008),
	Final      (0x0010),
	Interface  (0x0200),
	Abstract   (0x0400),
	Synthetic  (0x1000),
	Annotation (0x2000),
	Enum       (0x4000);

	private final short flag;
    private NestedClassAccessFlag(int flag) {
		this.flag = (short) flag;
	}
	
	@Override
	public short getFlag() {
		return flag;
	}

}
