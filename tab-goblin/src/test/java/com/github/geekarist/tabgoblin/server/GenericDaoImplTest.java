package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GenericDaoImplTest {

	private static final int EXPECTED_TAB_ID = 99;
	private static final String EXPECTED_TAB_CONTENTS;

	static {
		try {
			EXPECTED_TAB_CONTENTS = FileUtils.readFileToString(new File("src/test/resources/laboheme.txt"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Before
	public void setUp() throws Exception {
		createBDB();
		insertExpectedTab(EXPECTED_TAB_ID, EXPECTED_TAB_CONTENTS);
	}

	private void insertExpectedTab(int expectedTabId, String expectedTabContents) {
		// TODO Auto-generated method stub

	}

	private void createBDB() {
		// TODO Auto-generated method stub

	}

	@Test
	@Ignore
	public void testRead() throws IOException {
		GenericDao<Tablature, Integer> dao = new GenericDaoImpl();
		Tablature tab = dao.read(99);
		Assert.assertEquals(EXPECTED_TAB_ID, tab.getId());
		Assert.assertEquals(EXPECTED_TAB_CONTENTS, tab.getContents());
	}

	@After
	public void tearDown() {
		deleteBDB();
	}

	private void deleteBDB() {
		// TODO Auto-generated method stub

	}

}
