package com.github.geekarist.tabgoblin.server;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
	PK create(T newInstance);

	T read(PK id);

	List<T> readAll();

	void update(T transientObject);

	void delete(T persistentObject);
	
	void close();
}