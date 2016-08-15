package classfile.constant;

/**
 * represents a UTF8 constant in the Class File constant Pool
 * @author Jake Sandler
 *
 */
public class UTF8Constant extends Constant {
	private String content;
	
	/**
	 * Constructs a UTF* constant from the given string
	 * @param content
	 */
	public UTF8Constant(String content) {
		super(ConstantTag.ConstantUTF8);
		this.content = content;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (o.getClass().equals(this.getClass())) {
			return ((UTF8Constant) o).content.equals(this.content);
		}
		else {
			return false;
		}
		
	}

}
