package com.github.geekarist.tabgoblin.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TabGoblin implements EntryPoint {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final SavingServiceAsync savingService = GWT.create(SavingService.class);

	private final Messages messages = GWT.create(Messages.class);

	private TextArea tabContentsTextArea;
	private Label resultMessageLabel;
	private Button submitButton;
	private Button loadButton;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DockPanel dockPanel = new DockPanel();

		submitButton = new Button(messages.submitButton());
		loadButton = new Button(messages.loadButton());
		tabContentsTextArea = new TextArea();
		resultMessageLabel = new Label();

		registerSubmit();
		registerLoad();

		dockPanel.add(loadButton, DockPanel.NORTH);
		dockPanel.add(tabContentsTextArea, DockPanel.CENTER);
		dockPanel.add(submitButton, DockPanel.SOUTH);
		dockPanel.add(resultMessageLabel, DockPanel.SOUTH);
		RootPanel.get().add(dockPanel);
	}

	private void registerLoad() {
		loadButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				savingService.load(new AsyncCallback<String>() {

					public void onSuccess(String result) {
						tabContentsTextArea.setText(result);
						resultMessageLabel.setText(messages.resultLoadOk());
					}

					public void onFailure(Throwable caught) {
						resultMessageLabel.setText(messages.resultLoadKo());
						GWT.log("ko", caught);
					}
				});
			}
		});
	}

	private void registerSubmit() {
		submitButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent arg0) {
				savingService.save(tabContentsTextArea.getText(), new AsyncCallback<Integer>() {

					public void onSuccess(Integer result) {
						resultMessageLabel.setText(messages.resultOk("" + result));
					}

					public void onFailure(Throwable caught) {
						Window.alert("ko");
						GWT.log("ko", caught);
					}
				});
			}
		});
	}

	protected TextArea getTabContentsTextArea() {
		return tabContentsTextArea;
	}

	protected Button getSubmitButton() {
		return submitButton;
	}

	protected Label getResultMessageLabel() {
		return resultMessageLabel;
	}

	public Button getLoadButton() {
		return loadButton;
	}
}
