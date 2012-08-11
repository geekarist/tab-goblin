package com.github.geekarist.tabgoblin.client;

import java.io.IOException;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.github.geekarist.tabgoblin.TabGoblinTestConstants;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.utils.events.Browser;

public class TabGoblinTest extends GwtTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSubmitWithSavingSuccessful() throws IllegalArgumentException, IOException {
		// Setup /////////
		SavingServiceAsync savingServiceMock = EasyMock.createMock(SavingServiceAsync.class);
		expectSaveAndCallbackOnSuccess(savingServiceMock, TabGoblinTestConstants.LABOHEME_TAB_CONTENTS, 943);
		EasyMock.replay(savingServiceMock);

		// Test /////////
		TabGoblin tabGoblin = new TabGoblin(savingServiceMock);
		tabGoblin.onModuleLoad();
		Browser.fillText(tabGoblin.getTabContentsTextArea(), TabGoblinTestConstants.LABOHEME_TAB_CONTENTS);
		Browser.click(tabGoblin.getSubmitButton());

		// Assert ///////////
		EasyMock.verify(savingServiceMock);
		Assert.assertEquals("Tablature has been saved with id 943.", tabGoblin.getResultMessageLabel().getText());
	}

	@Test
	public void testSubmitWithSavingKo() throws IllegalArgumentException, IOException {
		// Setup /////////
		SavingServiceAsync savingServiceMock = EasyMock.createMock(SavingServiceAsync.class);
		expectSaveAndCallbackOnFailure(savingServiceMock, TabGoblinTestConstants.LABOHEME_TAB_CONTENTS, new Exception(
				"Error message"));
		EasyMock.replay(savingServiceMock);

		// Test /////////
		TabGoblin tabGoblin = new TabGoblin(savingServiceMock);
		tabGoblin.onModuleLoad();
		Browser.fillText(tabGoblin.getTabContentsTextArea(), TabGoblinTestConstants.LABOHEME_TAB_CONTENTS);
		Browser.click(tabGoblin.getSubmitButton());

		// Assert ///////////
		EasyMock.verify(savingServiceMock);
		Assert.assertEquals("Error while saving tablature.", tabGoblin.getResultMessageLabel().getText());
	}

	@Test
	public void testLoadWithLoadingSuccessful() throws IllegalArgumentException, IOException {
		// Setup /////////
		SavingServiceAsync savingServiceMock = EasyMock.createMock(SavingServiceAsync.class);
		expectLoadAndCallbackOnSuccess(savingServiceMock, TabGoblinTestConstants.LABOHEME_TAB_CONTENTS);
		EasyMock.replay(savingServiceMock);

		// Test /////////
		TabGoblin tabGoblin = new TabGoblin(savingServiceMock);
		tabGoblin.onModuleLoad();
		Browser.click(tabGoblin.getLoadButton());

		// Assert ///////////
		EasyMock.verify(savingServiceMock);
		Assert.assertEquals("Tablature has been loaded successfully.", tabGoblin.getResultMessageLabel().getText());
		Assert.assertEquals(TabGoblinTestConstants.LABOHEME_TAB_CONTENTS, tabGoblin.getTabContentsTextArea().getText());
	}

	@Test
	public void testLoadWithLoadingKo() throws IllegalArgumentException, IOException {
		// Setup /////////
		SavingServiceAsync savingServiceMock = EasyMock.createMock(SavingServiceAsync.class);
		expectLoadAndCallbackOnFailure(savingServiceMock, new Exception("Loading error message"));
		EasyMock.replay(savingServiceMock);

		// Test /////////
		TabGoblin tabGoblin = new TabGoblin(savingServiceMock);
		tabGoblin.onModuleLoad();
		Browser.click(tabGoblin.getLoadButton());

		// Assert ///////////
		EasyMock.verify(savingServiceMock);
		Assert.assertEquals("Error while loading tablature.", tabGoblin.getResultMessageLabel().getText());
		Assert.assertEquals("", tabGoblin.getTabContentsTextArea().getText());
	}

	@SuppressWarnings("unchecked")
	private void expectLoadAndCallbackOnFailure(SavingServiceAsync savingServiceMock, final Exception exception) {
		savingServiceMock.load(EasyMock.isA(AsyncCallback.class));
		EasyMock.expectLastCall().andDelegateTo(new SavingServiceAsync() {
			public void save(String tabContents, AsyncCallback<Integer> callback) {
			}

			public void load(AsyncCallback<String> callback) {
				callback.onFailure(exception);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void expectLoadAndCallbackOnSuccess(SavingServiceAsync savingServiceMock, final String labohemeTabContents) {
		savingServiceMock.load(EasyMock.isA(AsyncCallback.class));
		EasyMock.expectLastCall().andDelegateTo(new SavingServiceAsync() {
			public void save(String tabContents, AsyncCallback<Integer> callback) {
			}

			public void load(AsyncCallback<String> callback) {
				callback.onSuccess(labohemeTabContents);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void expectSaveAndCallbackOnFailure(SavingServiceAsync savingServiceMock, String tabContents,
			final Exception exception) {
		savingServiceMock.save(EasyMock.eq(tabContents), EasyMock.isA(AsyncCallback.class));
		EasyMock.expectLastCall().andDelegateTo(new SavingServiceAsync() {
			public void save(String tabContents, AsyncCallback<Integer> callback) {
				callback.onFailure(exception);
			}

			public void load(AsyncCallback<String> callback) {
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void expectSaveAndCallbackOnSuccess(SavingServiceAsync savingServiceMock, String tabContents, final int result) {
		savingServiceMock.save(EasyMock.eq(tabContents), EasyMock.isA(AsyncCallback.class));
		EasyMock.expectLastCall().andDelegateTo(new SavingServiceAsync() {
			public void save(String tabContents, AsyncCallback<Integer> callback) {
				callback.onSuccess(result);
			}

			public void load(AsyncCallback<String> callback) {
			}
		});
	}

	@Override
	public String getModuleName() {
		return "com.github.geekarist.tabgoblin.TabGoblin";
	}

}
