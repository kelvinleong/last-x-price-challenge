package kl.challenge.consumer.service;

import kl.challenge.consumer.dao.PriceRepository;
import kl.challenge.consumer.domain.Price;
import kl.challenge.consumer.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Created by kelvinleung on 21/7/2018.
 */
@Service
public class CalculationService {
    @Value("${price.queue.name}")
    private String queueName;

    @Autowired
    private PriceRepository priceRepository;

    public Result<Double> getAvgPriceOf(Long number) {
        Result<Double> result = new Result<>();

        if(number <= 0) {
            result.setCode(-1);
            result.setMsg(String.format("Number [%d] must be a positive integer", number));
            return result;
        }

        Long priceQueueSize = priceRepository.size(queueName);
        if(number > priceQueueSize) {
            result.setCode(-1);
            result.setMsg(String.format("Number [%d] exceeds size of price list [%d]", number, priceQueueSize));
            return result;
        }

        List<Price> prices = priceRepository.getLatestXPrices(queueName, number - 1);
        OptionalDouble average = prices
                .stream()
                .mapToDouble(price -> price.getPrice())
                .average();
        DecimalFormat df = new DecimalFormat("#.##");
        result.setResult(Double.valueOf(df.format(average.getAsDouble())));

        return result;
    }
}
