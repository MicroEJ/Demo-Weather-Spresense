/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.example.hello.fake;

import java.io.IOException;
import java.io.InputStream;

import com.microej.example.hello.Time;

/**
 *
 */
public class FakeWeatherProvider {

	private static final String FAKE_FORECAST_CSV = "/com/microej/example/hello/FakeForecast.csv";
	private static FakeWeather[] weathers;

	private FakeWeatherProvider() {
	}

	public static FakeWeather getWeather(int day, int hour) {
		if (weathers == null) {
			weathers = new FakeWeather[7 * 24];
			byte[] line = new byte[41];
			try (InputStream inputStream = FakeWeatherProvider.class.getResourceAsStream(FAKE_FORECAST_CSV)) {
				for (int i = 0; i < weathers.length; i++) {
					inputStream.read(line);

					int type = Integer.valueOf(new String(line, 0, 2)).intValue();
					int temperature = Integer.valueOf(new String(line, 3, 2)).intValue();
					Time sunrise = new Time(0, 0, 0, Integer.valueOf(new String(line, 6, 2)).intValue(),
							Integer.valueOf(new String(line, 9, 2)).intValue());
					float wind = Float.valueOf(new String(line, 12, 5)).floatValue();
					float humidity = Float.valueOf(new String(line, 18, 5)).floatValue();
					float latitude = Float.valueOf(new String(line, 24, 8)).floatValue();
					float longitude = Float.valueOf(new String(line, 33, 7)).floatValue();
					weathers[i] = new FakeWeather(type, temperature, sunrise, wind, humidity, latitude, longitude);
					if (i != weathers.length - 1) {
						inputStream.read();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("FakeWeatherProvider.getWeather() " + day + " hour=" + hour);
		return weathers[day * 24 + hour];
	}

}
