using Microsoft.AspNetCore.Mvc;
using static sem9.WeatherForecastHolder;

namespace sem9.Controllers;

[ApiController]
[Route("api/weather")]
public class WeatherForecastController : ControllerBase
{
    private WeatherForecastHolder _forecasts;

    public WeatherForecastController(WeatherForecastHolder forecasts)
    {
        _forecasts = forecasts;
    }
    [HttpPost("add")]
    public IActionResult Add([FromQuery] DateTime date, [FromQuery] int temperature)
    {
        _forecasts.AddForecast(date, temperature);
        return Ok(_forecasts.GetForecastByDate(date));
    }
    
    [HttpPut("update")]
    public IActionResult Update([FromQuery] DateTime date, [FromQuery] int temperature)
    {
        bool result = _forecasts.UpdateForecast(date, temperature);
        if (result) return Ok(_forecasts.GetForecastByDate(date));
        else return NotFound();
    }
    
    [HttpDelete("delete")]
    public IActionResult Delete([FromQuery] DateTime date)
    {
        WeatherForecast forecast = _forecasts.GetForecastByDate(date);
        if (forecast == null) return NotFound();
        _forecasts.RemoveForecast(date);
        return Ok(forecast);
    }
    
    [HttpGet("get")]
    public IActionResult Get([FromQuery] DateTime date)
    {
        WeatherForecast forecast = _forecasts.GetForecastByDate(date);
        if (forecast == null) return NotFound();
        return Ok(forecast);
    }
    
    [HttpGet("getAll")]
    public IActionResult GetAll()
    {
        return Ok(_forecasts.GetAllForecast());
    }
}