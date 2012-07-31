package com.github.geekarist.tabgoblin.server;

import com.github.geekarist.tabgoblin.client.SavingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SavingServiceImpl extends RemoteServiceServlet implements SavingService {

	private static final long serialVersionUID = -4754291908887731795L;
	
	private GenericDao<Tablature, Integer> tablatureDao;

	protected SavingServiceImpl(GenericDao<Tablature, Integer> tablatureDao) {
		this.tablatureDao = tablatureDao;
	}

	public int save(String tabContents) {
		Tablature tab = tablatureDao.read(0);
		int id;
		if (tab == null) {
			tab = new Tablature(0, tabContents);
			id = tablatureDao.create(tab);
		} else {
			id = tab.getId();
			tab.setContents(tabContents);
			tablatureDao.update(tab);
		}
		return id;
	}
	
	public String load() {
		Tablature tab = tablatureDao.read(0);
		return tab.getContents();
	}

}
