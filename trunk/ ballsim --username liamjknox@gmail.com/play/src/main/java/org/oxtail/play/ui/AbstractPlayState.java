package org.oxtail.play.ui;

import java.io.BufferedReader;
import java.io.IOException;


public abstract class AbstractPlayState {

	protected abstract String prompt();

	protected abstract AbstractPlayState execute(String cmd);

	protected abstract String readInput(BufferedReader in) throws IOException;
}
