package common.tokenizer;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import ICTCLAS.I3S.AC.ICTCLAS50;

public class ICTCLASTokenizer implements ITokenizer {

	private Logger log = Logger.getLogger(ICTCLASTokenizer.class);
	private ICTCLAS50 tokenizer;
	
	public ICTCLASTokenizer() {
		super();
		tokenizer = new ICTCLAS50();
		if(tokenizer.ICTCLAS_Init(".".getBytes()) == false){
			System.out.println("init fail");
		}
	}

	public ICTCLASTokenizer(String usrdir){
		this();
		int i;
		try {
			i = tokenizer.ICTCLAS_ImportUserDictFile(usrdir.getBytes(), 0);
			log.info(i);
		} catch (Exception e) {
			log.error("init usr dir failed");
			e.printStackTrace();
		}
	}
	
	public void setPosType(int posType){
		tokenizer.ICTCLAS_SetPOSmap(posType);
	}

	@Override
	public String[] tokenize(String sinput, String encoding) {
		
		if(sinput != null || sinput.length() != 0){
			return ictclasTokenizer(sinput, encoding);
		}else{
			return new String[0];
		}
	}

	@Override
	public String[] tokenize(String sinput) {
		if(sinput != null || sinput.length() != 0){
			return ictclasTokenizer(sinput, "GB2312");
		}else{
			return new String[0];
		}
	}
	
	private String[] ictclasTokenizer(String sinput, String encoding) {
		String sOutput = null;
		if(encoding == null){
			encoding = "GB2312";
		}
		try {
			byte[] bOutput = tokenizer.ICTCLAS_ParagraphProcess(sinput.getBytes(encoding), 0, 1);
			sOutput = new String(bOutput, encoding);
		} catch (UnsupportedEncodingException e) {
			log.error("encoding error");
			e.printStackTrace();
		}
		return sOutput.split("\\s+");
	}
	
	// for test
	public static void main(String[] args) {
		ICTCLASTokenizer test = new ICTCLASTokenizer();
		String sInput = "今天下雪了";
		String[] results = test.tokenize(sInput, "GB2312");
		for(String result : results){
			System.out.println(result);
		}
	}
	
}
