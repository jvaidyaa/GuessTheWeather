package main.java.com.hack.guess;

public class WeatherObject {

	private String temperature;
	private String feelTemperature;
	private String condition;
	private String humidity;
	private String dewPoint;
	private String windSpeed;
	private String windDirection;
	private String pressure;
	private String visibility;
	private String uvIndex;

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getFeelTemperature() {
		return feelTemperature;
	}

	public void setFeelTemperature(String feelTemperature) {
		this.feelTemperature = feelTemperature;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getDewPoint() {
		return dewPoint;
	}

	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getUvIndex() {
		return uvIndex;
	}

	public void setUvIndex(String uvIndex) {
		this.uvIndex = uvIndex;
	}

	@Override
	public String toString() {
		return "WeatherObject [temperature=" + temperature
				+ ", feelTemperature=" + feelTemperature + ", condition="
				+ condition + ", humidity=" + humidity + ", dewPoint="
				+ dewPoint + ", windSpeed=" + windSpeed + ", windDirection="
				+ windDirection + ", pressure=" + pressure + ", visibility="
				+ visibility + ", uvIndex=" + uvIndex + "]";
	}

}
