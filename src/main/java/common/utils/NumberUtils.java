package common.utils;

import java.util.Random;

public final class NumberUtils {

	public static int getRandomInt(int min, int max) {
		if (min > max) {
			int temp = min;
			min = max;
			max = temp;
		}
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	public static int getRandomInt() {
		Random random = new Random();
		return random.nextInt();
	}

}