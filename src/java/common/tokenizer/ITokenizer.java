package common.tokenizer;

/**
 * 分词类接口
 * @author orangedy
 *
 */
public interface ITokenizer {

	/**
	 * @param sinput
	 * @param encoding
	 * @return
	 */
	public String[] tokenize(String sinput, String encoding);
	
	/**
	 * @param sinput
	 * @return
	 */
	public String[] tokenize(String sinput);
	
}
