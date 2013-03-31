package common.datasource;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

import common.bean.Document;

public class ValidatorDataFromFileTest extends TestCase{

	ValidatorDataFromFile dataSource;
	
	@Override
	protected void setUp() throws Exception {
		this.dataSource = new ValidatorDataFromFile("E:\\worktemp\\Sample");
	}
	
	@Test
	public void testHavaNext() {
		this.dataSource.init();
		boolean result = this.dataSource.havaNext();
		assertFalse(result);
	}

	@Test
	public void testGetNextDocument() {
		this.dataSource.init();
		Document document = this.dataSource.getNextDocument();
		assertNotNull(document);
	}

}
