package com.github.geekarist.tabgoblin.client;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.utils.WidgetUtils;
import com.googlecode.gwt.test.utils.events.Browser;

public class TabGoblinTest extends GwtTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSubmit() throws IllegalArgumentException, IOException {
		new File("target/savedTablatureContents.txt").delete();

		TabGoblin tabGoblin = new TabGoblin();
		tabGoblin.onModuleLoad();

		Assert.assertTrue(tabGoblin.getTabContentsTextArea().isVisible());
		Browser.fillText(tabGoblin.getTabContentsTextArea(),
				FileUtils.readFileToString(new File("src/test/resources/laboheme.txt")));

		Assert.assertTrue(tabGoblin.getSubmitButton().isVisible());
		Browser.click(tabGoblin.getSubmitButton());

		Label resultMessageLabel = tabGoblin.getResultMessageLabel();
		Assert.assertTrue(WidgetUtils.isWidgetVisible(resultMessageLabel));
		Assert.assertEquals("Tablature has been saved with id 0.", resultMessageLabel.getText());
		Assert.assertEquals( //
				FileUtils.readFileToString(new File("src/test/resources/laboheme.txt")), //
				FileUtils.readFileToString(new File("target/savedTablatureContents.txt")));
	}

	@Test
	public void testLoad() throws IllegalArgumentException, IOException {
		TabGoblin tabGoblin = new TabGoblin();
		tabGoblin.onModuleLoad();

		// Check that text area is empty
		Assert.assertTrue(tabGoblin.getTabContentsTextArea().isVisible());
		Assert.assertEquals("", tabGoblin.getTabContentsTextArea().getText());
		
		// Click load button
		Widget loadButton = tabGoblin.getLoadButton();
		Assert.assertTrue(loadButton.isVisible());
		Browser.click(loadButton);

		// Check that status message is printed
		Label resultMessageLabel = tabGoblin.getResultMessageLabel();
		Assert.assertTrue(WidgetUtils.isWidgetVisible(resultMessageLabel));
		Assert.assertEquals("Tablature has been loaded successfully.", resultMessageLabel.getText());

		// Check that tablature content gets loaded when button is clicked
		Assert.assertEquals( //
				FileUtils.readFileToString(new File("src/test/resources/laboheme.txt")), //
				tabGoblin.getTabContentsTextArea().getText());

	}

	@Override
	public String getModuleName() {
		return "com.github.geekarist.tabgoblin.TabGoblin";
	}

}
