package com.github.geekarist.tabgoblin;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TabGoblinTestConstants {

	public static final Integer EXISTING_TAB_ID = 99;
	public static final String LABOHEME_TAB_CONTENTS;
	public static final String LABOHEME_TAB_NEW_CONTENTS;

	static {
		try {
			LABOHEME_TAB_CONTENTS = FileUtils.readFileToString(new File("src/test/resources/laboheme.txt"));
			LABOHEME_TAB_NEW_CONTENTS = FileUtils.readFileToString(new File("src/test/resources/laboheme2.txt"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
