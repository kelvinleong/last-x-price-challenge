package kl.challenge.consumer.web;

import com.google.gson.Gson;
import kl.challenge.consumer.RestfulTestBase;
import kl.challenge.consumer.domain.Price;
import kl.challenge.consumer.dto.Result;
import kl.challenge.consumer.queue.PricePublisher;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Created by kelvinleung on 22/7/2018.
 */
public class CalculationControllerTest extends RestfulTestBase{
    @Autowired
    private PricePublisher pricePublisher;

    private List<Price> prices = new ArrayList<>();

    private void createTheQueue() {
        Gson gson = new Gson();

        Price priceMsg = new Price(System.currentTimeMillis(), 10.0);
        prices.add(priceMsg);

        priceMsg = new Price(System.currentTimeMillis(), 20.0);
        prices.add(priceMsg);

        priceMsg = new Price(System.currentTimeMillis(), 30.0);
        prices.add(priceMsg);

        prices.forEach(price -> {
            pricePublisher.publish(gson.toJson(price));
        });
    }

    private Double calPriceAvg() {
        Double sum = 0.0;
        for(Price price: prices) {
            sum += price.getPrice();
        }

        return sum/prices.size();
    }

    @Test
    public void getAverageXLastPrice() {
        createTheQueue();

        // normal case
        String requestUrl = getRequestBasePath() + CalculationController.PATH_GET_AVG_PRICE + "?number=3";
        ResponseEntity<Result> entity = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                Result.class);

        then(entity.getBody().getCode()).isEqualTo(0);
        then(entity.getBody().getMsg()).isEqualTo("OK");
        Double respAvg = (Double) entity.getBody().getResult();
        then(respAvg).isEqualTo(calPriceAvg());

        // negative case
        requestUrl = getRequestBasePath() + CalculationController.PATH_GET_AVG_PRICE + "?number=-5";
        entity = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                Result.class);
        then(entity.getBody().getCode()).isEqualTo(-1);

        // overflow
        requestUrl = getRequestBasePath() + CalculationController.PATH_GET_AVG_PRICE + "?number=10000";
        entity = this.testRestTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                Result.class);
        then(entity.getBody().getCode()).isEqualTo(-1);
    }
}
