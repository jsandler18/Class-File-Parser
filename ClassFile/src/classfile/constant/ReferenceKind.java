package classfile.constant;

/**
 * An enumeration representing all the possible refernce kinds
 * of method handles
 * @author Jake Sandler
 *
 */
public enum ReferenceKind {
	GetField  	     (1),
	GetStatic 	     (2),
	PutField         (3),
	PutStatic        (4),
	InvokeVirtual    (5),
	InvokeStatic     (6),
	InvokeSpecial    (7),
	NewInvokeSpecial (8),
	InvokeInterface  (9)
	;
	
	private final byte KIND;
	private ReferenceKind(int kind) {
		this.KIND = (byte) kind;
	}
	
	/**
	 * Gets the kind value for this tag
	 * @return a 1 byte kind value
	 */
	public short getKind() {
		return this.getKind();
	}
}
