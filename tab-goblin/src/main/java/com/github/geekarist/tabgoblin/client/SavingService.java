package com.github.geekarist.tabgoblin.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("savingService")
public interface SavingService extends RemoteService {
	int save(String tabContents);
	String load();
}
