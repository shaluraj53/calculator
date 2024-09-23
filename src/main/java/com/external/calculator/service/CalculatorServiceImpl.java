package com.external.calculator.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.context.ChainContext;
import com.external.calculator.chain.manager.ChainManager;
import com.external.calculator.dto.ExchangeRate;
import com.external.calculator.dto.ExchangeRateAPIResponse;
import com.external.calculator.dto.Order;
import com.external.calculator.dto.OrderPriceInput;
import com.external.calculator.helper.PricingHelper;
import com.external.calculator.utils.RestUtils;

@Component
public class CalculatorServiceImpl implements CalculatorService {

    private ChainManager chainManager;
    private PricingHelper pricingHelper;
    private RestUtils restUtils;
    private Set<ExchangeRate> exchangeRates;

    @Value("${service.configs.exchangeRateEndpoint}")
    private String exchangeRateBaseURL;
    @Value("${service.configs.exchangeRateEndpointKey}")
    private String exchangeRateAPIKey;

    @Autowired
    public CalculatorServiceImpl(PricingHelper pricingHelper, ChainManager chainManager, RestUtils restUtils) {
        this.pricingHelper = pricingHelper;
        this.chainManager = chainManager;
        this.restUtils = restUtils;
        exchangeRates = new HashSet<>();
    }

    @Override
    public Order calculateOrderPrice(OrderPriceInput orderPriceInput) {
        Order order = pricingHelper.convertPriceInputToOrder(orderPriceInput);
        ExchangeRate exchangeRate = getExchangeRate(orderPriceInput.getBaseCurrency());
        if (order != null) {
            populateChainContext(orderPriceInput, order);
            ChainContext chainContext = populateChainContext(orderPriceInput, order);

            Double conversionRate = exchangeRate != null &&
                    exchangeRate.getConversionRates() != null
                            ? exchangeRate.getConversionRates().get(orderPriceInput.getForeignCurrency())
                            : 1;
            chainContext.setConversionRate(conversionRate);

            chainManager.processChain(chainContext);
            return chainContext.getOrder();
        }
        return null;
    }

    @Override
    public ExchangeRate getExchangeRate(String baseCurrency) {
        Optional<ExchangeRate> exchangeRateOptnl = exchangeRates.stream()
                .filter(e -> StringUtils.equalsIgnoreCase(e.getBaseCurrency(), baseCurrency)).findFirst();
        if (exchangeRateOptnl.isPresent() &&
                exchangeRateOptnl.get().getUpdatedOn().isEqual(LocalDate.now())) {
            return exchangeRateOptnl.get();
        }
        if (!exchangeRateOptnl.isPresent() ||
                exchangeRateOptnl.get().getUpdatedOn().isBefore(LocalDate.now())) {
            String erAPIURL = new StringBuilder(exchangeRateBaseURL).append(exchangeRateAPIKey)
                    .append("/latest/").append(baseCurrency).toString();
            ExchangeRateAPIResponse erAPIResponse = restUtils.get(erAPIURL, ExchangeRateAPIResponse.class);
            if (erAPIResponse != null && StringUtils.equalsIgnoreCase(erAPIResponse.getResult(), "success")) {
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setBaseCurrency(baseCurrency);
                exchangeRate.setUpdatedOn(LocalDate.now());
                exchangeRate.setConversionRates(erAPIResponse.getConversionRates());
                exchangeRates.add(exchangeRate);
                return exchangeRate;
            }
        }

        return null;
    }

    private ChainContext populateChainContext(OrderPriceInput orderPriceInput, Order order) {
        ChainContext chainContext = new ChainContext(order);
        BeanUtils.copyProperties(orderPriceInput, chainContext);
        return chainContext;
    }
}
