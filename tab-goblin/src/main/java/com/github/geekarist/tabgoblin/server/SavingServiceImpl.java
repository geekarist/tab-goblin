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
		Tablature tab = tablatureDao.get(0);
		if (tab == null) {
			tab = new Tablature(0, tabContents);
			tablatureDao.put(tab);
		} else {
			tab.setContents(tabContents);
			tablatureDao.put(tab);
		}
		return tab.getId();
	}
	
	public String load() {
		Tablature tab = tablatureDao.get(0);
		return tab.getContents();
	}

}
