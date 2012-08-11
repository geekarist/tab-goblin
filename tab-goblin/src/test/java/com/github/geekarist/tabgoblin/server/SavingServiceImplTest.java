package com.github.geekarist.tabgoblin.server;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.github.geekarist.tabgoblin.TabGoblinTestConstants;

public class SavingServiceImplTest {

	private Tablature TEST_TAB;
	private Tablature TEST_NEW_TAB;

	@Before
	public void setUp() throws Exception {
		TEST_TAB = new Tablature(0, TabGoblinTestConstants.LABOHEME_TAB_CONTENTS);
		TEST_NEW_TAB = new Tablature(0, TabGoblinTestConstants.LABOHEME_TAB_NEW_CONTENTS);
	}

	@Test
	public void testLoad() {
		// Setup
		@SuppressWarnings("unchecked")
		GenericDao<Tablature, Integer> daoMock = EasyMock.createMock(GenericDao.class);
		expectGet(daoMock, 0, TEST_TAB);
		EasyMock.replay(daoMock);

		// Test
		SavingServiceImpl savingServiceImpl = new SavingServiceImpl(daoMock);
		String tabContents = savingServiceImpl.load();

		// Assert
		EasyMock.verify(daoMock);
		Assert.assertEquals(TabGoblinTestConstants.LABOHEME_TAB_CONTENTS, tabContents);
	}

	@Test
	public void testCreateWhenTabIsNew() {
		// Setup
		@SuppressWarnings("unchecked")
		GenericDao<Tablature, Integer> daoMock = EasyMock.createMock(GenericDao.class);
		expectGet(daoMock, 0, null);
		expectCreate(daoMock, TEST_TAB);
		EasyMock.replay(daoMock);

		// Test
		SavingServiceImpl savingServiceImpl = new SavingServiceImpl(daoMock);
		int resultId = savingServiceImpl.save(TabGoblinTestConstants.LABOHEME_TAB_CONTENTS);

		// Assert
		EasyMock.verify(daoMock);
		Assert.assertEquals(0, resultId);
	}

	@Test
	public void testUpdateWhenTabExists() {
		// Setup
		@SuppressWarnings("unchecked")
		GenericDao<Tablature, Integer> daoMock = EasyMock.createMock(GenericDao.class);
		expectGet(daoMock, 0, TEST_TAB);
		expectPut(daoMock, TEST_NEW_TAB);
		EasyMock.replay(daoMock);

		// Test
		SavingServiceImpl savingServiceImpl = new SavingServiceImpl(daoMock);
		int resultId1 = savingServiceImpl.save(TabGoblinTestConstants.LABOHEME_TAB_NEW_CONTENTS);

		// Assert
		EasyMock.verify(daoMock);
		Assert.assertEquals(0, resultId1);
	}

	private void expectPut(GenericDao<Tablature, Integer> daoMock, Tablature tab) {
		daoMock.put(EasyMock.eq(tab));
		EasyMock.expectLastCall();
	}

	private void expectCreate(GenericDao<Tablature, Integer> daoMock, Tablature tab) {
		daoMock.put(EasyMock.eq(tab));
		EasyMock.expectLastCall();
	}

	private void expectGet(GenericDao<Tablature, Integer> daoMock, int id, Tablature tab) {
		daoMock.get(EasyMock.eq(id));
		EasyMock.expectLastCall().andReturn(tab);
	}

}
