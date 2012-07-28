package com.github.geekarist.tabgoblin.client;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.geekarist.tabgoblin.server.TablatureDao;
import com.googlecode.gwt.test.GwtTestWithEasyMock;
import com.googlecode.gwt.test.utils.events.Browser;

public class TabGoblinTest extends GwtTestWithEasyMock {

	@Autowired
	TablatureDao tablatureDao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IllegalArgumentException, IOException {
		// Ouvrir la page
		TabGoblin tabGoblin = new TabGoblin();
		tabGoblin.onModuleLoad();

		// Saisir la tablature dans le champ texte
		Browser.fillText(tabGoblin.getTabContentsTextArea(), FileUtils
				.readFileToString(new File("src/test/resources/laboheme.txt")));

		// Cliquer sur submit
		Browser.click(tabGoblin.getSubmitButton());

		// La tablature est stockée par le dao des tablatures
		// Le dao est mocké par une classe qui stocke la tablature sur le disque
		Assert.assertEquals(FileUtils.readFileToString(new File(
				"target/mockDaoOutput.txt")), FileUtils
				.readFileToString(new File("src/test/resources/laboheme.txt")));
	}

	@Override
	public String getModuleName() {
		return "com.github.geekarist.tabgoblin.TabGoblin";
	}

}
