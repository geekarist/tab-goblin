package com.github.geekarist.tabgoblin.server;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.github.geekarist.tabgoblin.client.SavingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SavingServiceImpl extends RemoteServiceServlet implements SavingService {

	private static final long serialVersionUID = -4754291908887731795L;

	public int save(String tabContents) {
		try {
			File file = new File("target/savedTablatureContents.txt");
			FileUtils.writeStringToFile(file, tabContents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String load() {
		File file = new File("src/test/resources/laboheme.txt");
		try {
			return FileUtils.readFileToString(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
