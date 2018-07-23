package kl.challenge.consumer.domain;

import java.io.Serializable;

/**
 * Created by kelvinleung on 21/7/2018.
 */
public class Price implements Serializable{
    private static final long serialVersionUID = 3051425159590898520L;

    private Long timestamp;
    private Double price;

    public Price(Long timestamp, Double price) {
        this.timestamp = timestamp;
        this.price = price;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
