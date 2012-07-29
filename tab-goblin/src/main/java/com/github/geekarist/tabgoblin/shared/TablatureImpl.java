package com.github.geekarist.tabgoblin.shared;

public class TablatureImpl implements Tablature {

	private String contents;

	public TablatureImpl(String text) {
		contents = text;
	}

	public String getContents() {
		return contents;
	}

}
