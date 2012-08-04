package com.github.geekarist.tabgoblin.server;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.github.geekarist.tabgoblin.client.TabGoblinTest;

public class SavingServiceImplTest {

	private Tablature TEST_TAB;
	private Tablature TEST_NEW_TAB;

	@Before
	public void setUp() throws Exception {
		TEST_TAB = new Tablature(0, TabGoblinTest.LABOHEME_TAB_CONTENTS);
		TEST_NEW_TAB = new Tablature(0, TabGoblinTest.LABOHEME_TAB_NEW_CONTENTS);
	}

	@Test
	public void testLoad() {
		// Setup
		@SuppressWarnings("unchecked")
		GenericDao<Tablature, Integer> daoMock = EasyMock.createMock(GenericDao.class);
		expectRead(daoMock, 0, TEST_TAB);
		EasyMock.replay(daoMock);

		// Test
		SavingServiceImpl savingServiceImpl = new SavingServiceImpl(daoMock);
		String tabContents = savingServiceImpl.load();

		// Assert
		EasyMock.verify(daoMock);
		Assert.assertEquals(TabGoblinTest.LABOHEME_TAB_CONTENTS, tabContents);
	}

	@Test
	public void testCreateWhenTabIsNew() {
		// Setup
		@SuppressWarnings("unchecked")
		GenericDao<Tablature, Integer> daoMock = EasyMock.createMock(GenericDao.class);
		expectRead(daoMock, 0, null);
		expectCreate(daoMock, TEST_TAB, 0);
		EasyMock.replay(daoMock);

		// Test
		SavingServiceImpl savingServiceImpl = new SavingServiceImpl(daoMock);
		int resultId = savingServiceImpl.save(TabGoblinTest.LABOHEME_TAB_CONTENTS);

		// Assert
		EasyMock.verify(daoMock);
		Assert.assertEquals(0, resultId);
	}

	@Test
	public void testUpdateWhenTabExists() {
		// Setup
		@SuppressWarnings("unchecked")
		GenericDao<Tablature, Integer> daoMock = EasyMock.createMock(GenericDao.class);
		expectRead(daoMock, 0, TEST_TAB);
		expectUpdate(daoMock, TEST_NEW_TAB);
		EasyMock.replay(daoMock);

		// Test
		SavingServiceImpl savingServiceImpl = new SavingServiceImpl(daoMock);
		int resultId1 = savingServiceImpl.save(TabGoblinTest.LABOHEME_TAB_NEW_CONTENTS);

		// Assert
		EasyMock.verify(daoMock);
		Assert.assertEquals(0, resultId1);
	}

	private void expectUpdate(GenericDao<Tablature, Integer> daoMock, Tablature tab) {
		daoMock.update(EasyMock.eq(tab));
		EasyMock.expectLastCall();
	}

	private void expectCreate(GenericDao<Tablature, Integer> daoMock, Tablature tab, int id) {
		daoMock.create(EasyMock.eq(tab));
		EasyMock.expectLastCall().andReturn(id);
	}

	private void expectRead(GenericDao<Tablature, Integer> daoMock, int id, Tablature tab) {
		daoMock.read(EasyMock.eq(id));
		EasyMock.expectLastCall().andReturn(tab);
	}

}
