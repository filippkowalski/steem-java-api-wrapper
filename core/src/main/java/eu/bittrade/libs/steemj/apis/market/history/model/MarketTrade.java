package eu.bittrade.libs.steemj.apis.market.history.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * This class represents a Steem "market_trade" object of the
 * "market_history_plugin".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketTrade {
    private TimePointSec date;
    @JsonProperty("current_pays")
    private Asset currentPays;
    @JsonProperty("open_pays")
    private Asset openPays;

    /**
     * @return the date
     */
    public TimePointSec getDate() {
        return date;
    }

    /**
     * @return the currentPays
     */
    public Asset getCurrentPays() {
        return currentPays;
    }

    /**
     * @return the openPays
     */
    public Asset getOpenPays() {
        return openPays;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
