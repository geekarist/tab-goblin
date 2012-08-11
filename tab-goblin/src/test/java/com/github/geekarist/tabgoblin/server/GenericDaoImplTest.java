package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.fest.assertions.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.geekarist.tabgoblin.TabGoblinTestConstants;
import com.github.geekarist.tabgoblin.shared.TabGoblinException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class GenericDaoImplTest {

	private static final String EXISTING_TAB_STORE = "target/tab-store";
	private static final File EXISTING_TAB_STORE_HOME = new File(EXISTING_TAB_STORE);

	@Before
	public void setUp() throws Exception {
		EntityStore entityStore = openStore();

		PrimaryIndex<Integer, Tablature> tablatureById = entityStore.getPrimaryIndex(Integer.class, Tablature.class);
		tablatureById.put(new Tablature(TabGoblinTestConstants.EXISTING_TAB_ID, TabGoblinTestConstants.LABOHEME_TAB_CONTENTS));

		closeStore(entityStore);
	}

	private EntityStore openStore() {
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setReadOnly(false);
		environmentConfig.setAllowCreate(!false);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setReadOnly(false);
		storeConfig.setAllowCreate(!false);

		File home = new File(EXISTING_TAB_STORE);
		home.mkdirs();

		Environment environment = new Environment(home, environmentConfig);
		EntityStore entityStore = new EntityStore(environment, "TabStore", storeConfig);
		return entityStore;
	}

	private void closeStore(EntityStore entityStore) {
		Environment environment = entityStore.getEnvironment();
		entityStore.close();
		environment.close();
	}

	@After
	public void tearDown() throws IOException {
		deleteBDB(EXISTING_TAB_STORE);
	}

	private void deleteBDB(String existingTabStore) throws IOException {
		FileUtils.deleteDirectory(new File(existingTabStore));
	}

	@Test
	public void testOpenExistingStore() {
		GenericDao<Tablature, Integer> genericDaoImpl = //
		new GenericDaoImpl<Tablature, Integer>( //
				EXISTING_TAB_STORE_HOME, false, Tablature.class, Integer.class);
		genericDaoImpl.close();
	}

	@Test
	public void testOpenExistingStoreReadOnly() throws IOException {
		GenericDao<Tablature, Integer> genericDaoImpl = //
		new GenericDaoImpl<Tablature, Integer>( //
				EXISTING_TAB_STORE_HOME, true, Tablature.class, Integer.class);
		genericDaoImpl.close();
	}

	@Test(expected = TabGoblinException.class)
	public void testOpenNonExistingStore() throws IOException {
		GenericDao<Tablature, Integer> dao = null;
		try {
			dao = new GenericDaoImpl<Tablature, Integer>( //
					new File("target/non-existing-store"), false, Tablature.class, Integer.class);
		} catch (TabGoblinException e) {
			Assert.assertEquals("Error while creating dao environment", e.getMessage());
			Throwable cause = e.getCause();
			Assertions.assertThat(cause).isInstanceOf(IllegalArgumentException.class);
			String message = cause.getMessage();
			Assert.assertEquals("Environment home target\\non-existing-store doesn't exist", message);
			throw e;
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
	}

	@Test
	public void testGetExistingTab() throws IOException {
		GenericDao<Tablature, Integer> dao = new GenericDaoImpl<Tablature, Integer>( //
				EXISTING_TAB_STORE_HOME, false, Tablature.class, Integer.class);
		Tablature tab = dao.get(99);
		Assert.assertEquals(TabGoblinTestConstants.EXISTING_TAB_ID, tab.getId());
		Assert.assertEquals(TabGoblinTestConstants.LABOHEME_TAB_CONTENTS, tab.getContents());
		dao.close();
	}

	@Test
	public void testGetNonExistingTab() throws IOException {
		GenericDao<Tablature, Integer> dao = new GenericDaoImpl<Tablature, Integer>( //
				EXISTING_TAB_STORE_HOME, false, Tablature.class, Integer.class);
		Tablature tab = dao.get(98);
		Assert.assertNull(tab);
		dao.close();
	}

	@Test
	public void testPutNewTab() throws IOException {
		GenericDaoImpl<Tablature, Integer> dao = new GenericDaoImpl<Tablature, Integer>( //
				EXISTING_TAB_STORE_HOME, false, Tablature.class, Integer.class);
		dao.put(new Tablature(TabGoblinTestConstants.EXISTING_TAB_ID, TabGoblinTestConstants.LABOHEME_TAB_CONTENTS));
		dao.close();

		Tablature actual = loadTab(TabGoblinTestConstants.EXISTING_TAB_ID);
		Assert.assertEquals(TabGoblinTestConstants.EXISTING_TAB_ID, actual.getId());
		Assert.assertEquals(TabGoblinTestConstants.LABOHEME_TAB_CONTENTS, actual.getContents());
	}

	@Test
	public void testPutModifiedExistingTab() throws IOException {
		GenericDaoImpl<Tablature, Integer> dao = new GenericDaoImpl<Tablature, Integer>( //
				EXISTING_TAB_STORE_HOME, false, Tablature.class, Integer.class);

		Tablature tab = loadTab(TabGoblinTestConstants.EXISTING_TAB_ID);
		tab.setContents(TabGoblinTestConstants.LABOHEME_TAB_NEW_CONTENTS);
		dao.put(tab);
		dao.close();

		Tablature actual = loadTab(TabGoblinTestConstants.EXISTING_TAB_ID);
		Assert.assertEquals(TabGoblinTestConstants.EXISTING_TAB_ID, actual.getId());
		Assert.assertEquals(TabGoblinTestConstants.LABOHEME_TAB_NEW_CONTENTS, actual.getContents());
	}

	private Tablature loadTab(Integer existingTabId) {
		EntityStore entityStore = openStore();
		PrimaryIndex<Integer, Tablature> primaryIndex = entityStore.getPrimaryIndex(Integer.class, Tablature.class);
		Tablature tablature = primaryIndex.get(existingTabId);
		closeStore(entityStore);
		return tablature;
	}

}
