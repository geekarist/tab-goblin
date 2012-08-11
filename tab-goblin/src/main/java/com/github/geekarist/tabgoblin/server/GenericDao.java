package com.github.geekarist.tabgoblin.server;

import java.io.Serializable;

public interface GenericDao<T, PK extends Serializable> {
	void put(T newInstance);

	T get(PK id);

	void close();
}