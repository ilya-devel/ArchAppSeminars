namespace sem9;

public class WeatherForecastHolder
{
    static List<WeatherForecast> _forecasts = new List<WeatherForecast>();

    /*
    public WeatherForecastHolder()
    {
        _forecasts = new List<WeatherForecast>();
    }
    */

    public void AddForecast(DateTime date, int temperatureC)
    {
        WeatherForecast tmp = GetForecastByDate(date);
        if (tmp != null)
        {
            UpdateForecast(date, temperatureC);
        }
        else
        {
            WeatherForecast weatherForecast = new WeatherForecast();
            weatherForecast.Date = date;
            weatherForecast.TemperatureC = temperatureC;
            _forecasts.Add(weatherForecast);
        }
    }

    public bool UpdateForecast(DateTime date, int temperatureC)
    {
        foreach (var item in _forecasts)
        {
            if (item.Date == date)
            {
                item.TemperatureC = temperatureC;
                return true;
            }
        }
        return false;
    }
    
    public bool RemoveForecast(DateTime date)
    {
        foreach (var item in _forecasts)
        {
            if (item.Date == date)
            {
                _forecasts.Remove(item);
                return true;
            }
        }
        return false;
    }
    
    public List<WeatherForecast> GetAllForecast() { return _forecasts; }

    public WeatherForecast GetForecastByDate(DateTime date)
    {
        foreach (var item in _forecasts)
        {
            if (item.Date == date)
            {
                return item;
            }
        }
        return null;
    }
}