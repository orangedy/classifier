package common.datasource;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

import common.bean.Document;

public class ValidatorDataFromFileTest extends TestCase{

	ValidatorDataFromFile dataSource;
	
	@Override
	protected void setUp() throws Exception {
		this.dataSource = new ValidatorDataFromFile("E:\\categoryworktemp\\SogouC.reduced\\Reduced");
	}
	
	@Test
	public void testHavaNext() {
		this.dataSource.init();
		boolean result = this.dataSource.haveNext();
		assertFalse(result);
	}

	@Test
	public void testGetNextDocument() {
		this.dataSource.init();
		if(this.dataSource.haveNext()){
			Document document = this.dataSource.getNextDocument();
			assertNotNull(document);
		}
	}

}
