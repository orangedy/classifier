package common.datasource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.util.FileUtil;

/**
 * 从文件获取输入数据，文件目录为train和eval
 * train下包括positive和negative两个文件夹，分别存放正样本和副样本文件，都以纯文本的方式存储数据，多类别时文件名表示类别名
 * eval下包括positive和negative两个文件夹，分别存放正样本和副样本文件，都以纯文本的方式存储数据，多类别时文件名表示类别名
 * 输出为字符串数组
 * 
 * @author orangedy
 * 
 */
public class TrainDataFromFile implements TrainDataSource {

	private File trainDataDir;

	private File evalDataDir;

	private String[] categorys;

	public String[] getCategorys() {
		if (this.categorys != null && this.categorys.length != 0) {
			return categorys;
		} else {
			return new String[0];
		}
	}

	public void setCategorys(String[] categorys) {
		this.categorys = categorys;
	}

	public TrainDataFromFile(String trainDataDir, String evalDataDir) {
		if (trainDataDir != null && trainDataDir.length() != 0) {
			this.trainDataDir = new File(trainDataDir);
			if (!this.trainDataDir.isDirectory()) {
				throw new IllegalArgumentException("训练语料库搜索失败！ [" + trainDataDir + "]");
			}
			categorys = this.trainDataDir.list();
		}
		if (evalDataDir != null && evalDataDir.length() != 0) {
			this.evalDataDir = new File(evalDataDir);
			if (!this.evalDataDir.isDirectory()) {
				throw new IllegalArgumentException("分类语料库搜索失败！ [" + evalDataDir + "]");
			}
		}
	}

	/**
	 * 根据类别名，获取该类别所有的数据文件路径
	 * 
	 * @param category
	 *            类别名
	 * @return
	 */
	public String[] getContentsByCategory(String category) {
		if (category == null || category.equals("")) {
			return new String[0];
		}
		File classDir = new File(trainDataDir.getPath() + File.separator + category);
		String[] ret = classDir.list();
		if (ret == null || ret.length <= 0) {
			return new String[0];
		}
		List<String> contents = new ArrayList<String>();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = trainDataDir.getPath() + File.separator + category + File.separator + ret[i];
			File file = new File(ret[i]);
			if (file.exists()) {
				String content = FileUtil.readFile(file);
				contents.add(content);
			}
		}
		return contents.toArray(new String[contents.size()]);
	}

	@Override
	public String[] getContents() {
		if (this.evalDataDir != null) {
			String[] ret = this.evalDataDir.list();
			List<String> contents = new ArrayList<String>();
			for (int i = 0; i < ret.length; i++) {
				ret[i] = this.evalDataDir.getPath() + File.separator + ret[i];
				File file = new File(ret[i]);
				if (file.exists()) {
					String content = FileUtil.readFile(file);
					contents.add(content);
				}
			}
			return contents.toArray(new String[contents.size()]);
		}
		return new String[0];
	}

	public static void main(String[] args) {
		TrainDataFromFile test = new TrainDataFromFile("E:\\worktemp\\SogouC.mini\\Sample",
				"E:\\worktemp\\SogouC.mini\\Sample\\C000007");
		String[] categorys = test.getCategorys();
		for (String category : categorys) {
			System.out.println(category);
			String[] contents = test.getContentsByCategory(category);
			for (String content : contents) {
				System.out.println(content);
			}
		}

		String[] evalData = test.getContents();
		for (String str : evalData) {
			System.out.println(str);
		}

	}
}
