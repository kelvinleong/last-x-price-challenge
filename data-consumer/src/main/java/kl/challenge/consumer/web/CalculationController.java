package kl.challenge.consumer.web;

import io.swagger.annotations.*;
import kl.challenge.consumer.dto.Result;
import kl.challenge.consumer.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@RestController
public class CalculationController {
    @Autowired
    private CalculationService calculationService;

    public final static String PATH_GET_AVG_PRICE = "/getAvgPrice";

    @ResponseBody
    @ApiOperation(value = "Get Average Price of last X prices",
                notes = "value of X must be positive integer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "number", value = "Number of Last Prices", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.GET, path = PATH_GET_AVG_PRICE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Result.class, responseContainer = "Map")})
    public MappingJacksonValue getAvgPrice(@RequestParam(required = true) Long number) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(calculationService.getAvgPriceOf(number));
        return mappingJacksonValue;
    }
}
