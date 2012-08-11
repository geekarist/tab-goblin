package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.io.Serializable;

import com.github.geekarist.tabgoblin.shared.TabGoblinException;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	private Environment environment;
	private EntityStore entityStore;
	private PrimaryIndex<PK, T> objectById;

	public GenericDaoImpl(File home, boolean readOnly, Class<T> persistentClass, Class<PK> primaryKeyClass) {
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
		objectById = entityStore.getPrimaryIndex(primaryKeyClass, persistentClass);
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

	public void put(T newInstance) {
		objectById.put(newInstance);
	}

	public T get(PK id) {
		return objectById.get(id);
	}

}
