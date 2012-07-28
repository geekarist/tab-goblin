package com.github.geekarist.tabgoblin.client;

import com.github.geekarist.tabgoblin.shared.Tablature;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("filingService")
public interface SavingService extends RemoteService {
	public int save(Tablature tab);
}
