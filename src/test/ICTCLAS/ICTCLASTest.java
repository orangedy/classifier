package ICTCLAS;

import ICTCLAS.I3S.AC.ICTCLAS50;

public class ICTCLASTest {
	public static void main(String[] args) {
		try{
			String sInput = "我们都是中国人";
			ICTCLAS50 testSpliter = new ICTCLAS50();
			String argu = ".";
			if(testSpliter.ICTCLAS_Init(argu.getBytes("GB2312")) == false){
				System.out.println("init fail");
				return;
			}
			testSpliter.ICTCLAS_SetPOSmap(3);
			byte[] bOutput = testSpliter.ICTCLAS_ParagraphProcess(sInput.getBytes("UTF-8"), 3, 1);
			System.out.println(bOutput.length);
			String sOutput = new String(bOutput, "UTF-8");
			System.out.println(sOutput);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
