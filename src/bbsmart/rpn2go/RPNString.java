package bbsmart.rpn2go;

/** A Reverse Polish Notation string */
public final class RPNString {
	protected final String string;

	private RPNString(String nstring) {
		string = nstring;
	}

	private int apply(int c, int i, int key) {
		RPNStack state = new RPNStack();

		Tokenizer tokens = new Tokenizer(string);
		while (tokens.advance())
			state.apply(tokens.current(), c, i, key);

		if (state.size() == 0)
			return 0;

		return state.pop();
	}

	private String apply(String id) {
		id = toTen(id);

		int key = 0;

		for (int i = 0; i < id.length(); i++) {
			int c = id.charAt(i) & 0xFF;
			key = apply(c, i, key);
		}

		return padInt(key & 0xFFFF);
	}

	private static String toTen(String id) {
		int len = id.length();

		if (len <= 10)
			return id;

		String left = id.substring(0, 5);
		String right = id.substring(len - 5, len);
		return left + right;
	}

	private static String padInt(int val) {
		if (val == 0)
			return "00000";

		char[] out = new char[5];
		for (int i = 4; i >= 0; i--) {
			out[i] = (char) ('0' + (val % 10));
			val /= 10;
		}

		return new String(out);
	}

	/**
	 * Calculates an activation key based on an RPN string and a device ID. 
	 * 
	 * @param string
	 *            RPN string to use
	 * @param id
	 *            Device ID
	 * @return Padded key string
	 */
	public static String apply(String string, String id) {
		return new RPNString(string).apply(id);
	}
}
