package org.oxtail.play.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.oxtail.play.Board;
import org.oxtail.play.Move;

public abstract class AbstractBoardLoader {

	private final int width;
	private final int height;

	protected AbstractBoardLoader(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Board loadFromFile(String filePath) {
		Path path = FileSystems.getDefault().getPath(".", filePath);
		try {
			List<String> lines = Files.readAllLines(path,
					Charset.defaultCharset());
			return loadFromString(linesToString(lines));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + filePath, e);
		}
	}

	private String linesToString(List<String> lines) {
		StringBuilder sb = new StringBuilder();
		for (String s : lines)
			sb.append(s);
		return sb.toString();
	}

	public Board loadFromString(String lines) {
		Board board = new Board(width, height);
		for (int y = 0; y < height; ++y)
			for (int x = 0; x < width; ++x) {
				int value = fromChar(lines.charAt((y * width) + x));
				if (value != 0)
					board = board.doMove(new Move(x, y, value));
			}
		return board;
	}

	protected abstract int fromChar(char charAt);

}
