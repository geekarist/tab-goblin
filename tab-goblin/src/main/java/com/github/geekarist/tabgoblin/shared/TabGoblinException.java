package com.github.geekarist.tabgoblin.shared;

import java.io.IOException;

public class TabGoblinException extends RuntimeException {

	public TabGoblinException(String string, IOException e) {
		super(string, e);
	}

	private static final long serialVersionUID = 5697290692231104812L;

}
