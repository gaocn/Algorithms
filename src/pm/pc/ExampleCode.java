package pm.pc;

import java.io.IOException;

public class ExampleCode implements Runnable {
	static String ReadLn(int maxLength) { // utility function to read from
											// stdin,
		// Provided by Programming-challenges, edit for style only
		byte line[] = new byte[maxLength];
		int length = 0;
		int input = -1;
		try {
			while (length < maxLength) {// Read untill maxlength
				input = System.in.read();
				if ((input < 0) || (input == '\n'))
					break; // or untill end of line ninput
				line[length++] += input;
			}

			if ((input < 0) && (length == 0))
				return null; // eof
			return new String(line, 0, length);
		} catch (IOException e) {
			return null;
		}
	}

	public static void main(String args[]) // entry point from OS
	{
		ExampleCode myWork = new ExampleCode(); // Construct the bootloader
		myWork.run(); // execute
	}

	public void run() {
		new ExampleCode().run();
	}
}
