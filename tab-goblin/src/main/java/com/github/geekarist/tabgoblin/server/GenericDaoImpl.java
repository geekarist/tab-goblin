package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.util.List;

import com.github.geekarist.tabgoblin.shared.TabGoblinException;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class GenericDaoImpl implements GenericDao<Tablature, Integer> {

	private Environment environment;
	private EntityStore entityStore;
	private PrimaryIndex<Integer, Tablature> tablatureById;

	public GenericDaoImpl(File home, boolean readOnly) {
		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setReadOnly(readOnly);
		environmentConfig.setAllowCreate(!readOnly);

		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setReadOnly(readOnly);
		storeConfig.setAllowCreate(!readOnly);
		
		try {
			environment = new Environment(home, environmentConfig);
		} catch (IllegalArgumentException e) {
			throw new TabGoblinException("Error while creating dao environment", e);
		}
		entityStore = new EntityStore(environment, "TabStore", storeConfig);
		tablatureById = entityStore.getPrimaryIndex(Integer.class, Tablature.class);
	}

	public void close() {
		if (entityStore != null) {
			try {
				entityStore.close();
			} catch (DatabaseException e) {
				throw new TabGoblinException("Error while closing tablature store", e);
			}
		}
		if (environment != null) {
			try {
				environment.close();
			} catch (DatabaseException e) {
				throw new TabGoblinException("Error while closing tablature environment", e);
			}
		}
	}

	public Integer create(Tablature newInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	public Tablature read(Integer id) {
		return tablatureById.get(id);
	}

	public List<Tablature> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Tablature transientObject) {
		// TODO Auto-generated method stub

	}

	public void delete(Tablature persistentObject) {
		// TODO Auto-generated method stub

	}

}
