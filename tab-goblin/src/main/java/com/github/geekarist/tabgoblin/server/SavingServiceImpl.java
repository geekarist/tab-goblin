package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.github.geekarist.tabgoblin.client.SavingService;
import com.github.geekarist.tabgoblin.shared.TabGoblinException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SavingServiceImpl extends RemoteServiceServlet implements SavingService {

	private static final String SOURCE_FILE = "src/test/resources/laboheme.txt";
	private static final String TARGET_FILE = "target/savedTablatureContents.txt";
	
	private static final long serialVersionUID = -4754291908887731795L;

	public int save(String tabContents) {
		try {
			File file = new File(TARGET_FILE);
			FileUtils.writeStringToFile(file, tabContents);
		} catch (IOException e) {
			throw new TabGoblinException("Error while saving tablature contents", e);
		}
		return 0;
	}
	
	public String load() {
		File file = new File(SOURCE_FILE);
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			throw new TabGoblinException("Error while loading tablature contents", e);
		}
	}

}
