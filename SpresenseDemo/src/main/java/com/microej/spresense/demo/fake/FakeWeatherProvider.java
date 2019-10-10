/*
 * Java
 *
 * Copyright 2019 Sony Corp. All rights reserved.
 * This Software has been designed by MicroEJ Corp and all rights have been transferred to Sony Corp.
 * Sony Corp. has granted MicroEJ the right to sub-licensed this Software under the enclosed license terms.
 */
package com.microej.spresense.demo.fake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.microej.spresense.demo.Time;

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
			try (BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(FakeWeatherProvider.class.getResourceAsStream(FAKE_FORECAST_CSV)))) {
				for (int i = 0; i < weathers.length; i++) {
					String line = bufferReader.readLine();
					int type = Integer.parseInt(line.substring(0, 2));
					int temperature = Integer.parseInt(line.substring(3, 5));
					Time sunrise = new Time(0, 0, 0, Integer.parseInt(line.substring(6, 8)),
							Integer.parseInt(line.substring(9, 11)));
					float wind = Float.parseFloat(line.substring(12, 17));
					float humidity = Float.parseFloat(line.substring(18, 23));
					float latitude = Float.parseFloat(line.substring(24, 32));
					float longitude = Float.parseFloat(line.substring(33, 40));
					weathers[i] = new FakeWeather(type, temperature, sunrise, wind, humidity, latitude, longitude);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return weathers[day * 24 + hour];
	}

}
