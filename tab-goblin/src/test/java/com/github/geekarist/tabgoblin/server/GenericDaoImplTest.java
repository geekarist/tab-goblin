package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.fest.assertions.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.geekarist.tabgoblin.shared.TabGoblinException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class GenericDaoImplTest {

	private static final String EXISTING_TAB_STORE = "target/tab-store";
	private static final Integer EXISTING_TAB_ID = 99;
	private static final String EXISTING_TAB_CONTENTS;

	static {
		try {
			EXISTING_TAB_CONTENTS = FileUtils.readFileToString(new File("src/test/resources/laboheme.txt"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Before
	public void setUp() throws Exception {
		createBDB(EXISTING_TAB_STORE, false);
	}

	private void createBDB(String tabStore, boolean readOnly) throws IOException {
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setReadOnly(readOnly);
		environmentConfig.setAllowCreate(!readOnly);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setReadOnly(readOnly);
		storeConfig.setAllowCreate(!readOnly);

		File home = new File(tabStore);
		home.mkdir();

		Environment environment = new Environment(home, environmentConfig);
		EntityStore entityStore = new EntityStore(environment, "TabStore", storeConfig);
		PrimaryIndex<Integer, Tablature> tablatureById = entityStore.getPrimaryIndex(Integer.class, Tablature.class);

		tablatureById.put(new Tablature(EXISTING_TAB_ID, EXISTING_TAB_CONTENTS));

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
		GenericDao<Tablature, Integer> genericDaoImpl = new GenericDaoImpl(new File(EXISTING_TAB_STORE), false);
		genericDaoImpl.close();
	}

	@Test
	public void testOpenExistingStoreReadOnly() throws IOException {
		GenericDao<Tablature, Integer> genericDaoImpl = new GenericDaoImpl(new File(EXISTING_TAB_STORE), true);
		genericDaoImpl.close();
	}

	@Test(expected = TabGoblinException.class)
	public void testOpenNonExistingStore() throws IOException {
		GenericDao<Tablature, Integer> dao = null;
		try {
			dao = new GenericDaoImpl(new File("target/non-existing-store"), false);
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
	public void testReadExistingTab() throws IOException {
		GenericDao<Tablature, Integer> dao = new GenericDaoImpl(new File(EXISTING_TAB_STORE), false);
		Tablature tab = dao.read(99);
		Assert.assertEquals(EXISTING_TAB_ID, tab.getId());
		Assert.assertEquals(EXISTING_TAB_CONTENTS, tab.getContents());
		dao.close();
	}

	@Test
	public void testReadNonExistingTab() throws IOException {
		GenericDao<Tablature, Integer> dao = new GenericDaoImpl(new File(EXISTING_TAB_STORE), false);
		Tablature tab = dao.read(98);
		Assert.assertNull(tab);
		dao.close();
	}

	@Test
	public void testCreate() throws IOException {
		// TODO
	}

}
