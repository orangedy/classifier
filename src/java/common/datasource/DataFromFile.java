package common.datasource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 从文件获取输入数据，文件目录为train和eval
 * train下包括positive和negative两个文件夹，分别存放正样本和副样本文件，都以纯文本的方式存储数据，多类别时文件名表示类别名
 * eval下包括positive和negative两个文件夹，分别存放正样本和副样本文件，都以纯文本的方式存储数据，多类别时文件名表示类别名
 * 输出为字符串数组
 * @author orangedy
 *
 */
public class DataFromFile implements DataSource {

	private File trainDataDir;
	
	//默认训练数据目录
	private static String defaultPath = "./sample";

	private String[] categorys;
	
	public String[] getCategorys() {
		return categorys;
	}

	public void setCategorys(String[] categorys) {
		this.categorys = categorys;
	}

	public DataFromFile(String fileDir) {
		String dir;
		if(fileDir == null || fileDir.length() == 0){
			dir = defaultPath;
		}else{
			dir = fileDir;
		}
		trainDataDir = new File(dir);
		if(!trainDataDir.isDirectory()){
			throw new IllegalArgumentException("训练语料库搜索失败！ [" + fileDir + "]");
		}
		categorys = trainDataDir.list();
		List<String> list = new ArrayList<String>();
		for(String category : this.categorys){
			File classDir = new File(trainDataDir.getPath() + File.separator + category);
			if(classDir.isDirectory()){
				list.add(category);
			}
		}
		categorys = (String[]) list.toArray();
	}
	
	/**
	 * 根据类别名，获取该类别所有的数据文件路径
	 * @param category 类别名
	 * @return
	 */
	public String[] getContentDirByCategory(String category){
		if (category == null || category.equals(""))
			return trainDataDir.list();
		File classDir = new File(trainDataDir.getPath() + File.separator + category);
		String[] ret = classDir.list();
		if (ret == null || ret.length <= 0)
			return new String[0];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = trainDataDir.getPath() + File.separator + category + File.separator + ret[i];
		}
		return ret;
	}
}
