package bbsmart.rpn2go;

class Tokenizer {
	protected final String line;
	protected final int len;

	protected int pos;
	protected String token;

	Tokenizer(String nline) {
		line = nline;
		len = line.length();
		pos = 0;
		token = "";
	}

	boolean advance() {
		while (pos < len && line.charAt(pos) == ' ')
			pos++;

		int sublen = 0;
		while (pos < len && line.charAt(pos) != ' ') {
			pos++;
			sublen++;
		}

		token = line.substring(pos - sublen, pos);
		return sublen > 0;
	}

	String current() {
		return token;
	}
}
