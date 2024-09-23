package com.external.calculator.chain.processor.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.external.calculator.chain.processor.ChainProcessor;

@Component
public class ChainProcessorFactory {

    @Autowired
    Map<String, ChainProcessor> chainProcessors;

    public ChainProcessor getChainProcessor(final String processorName) {
        final ChainProcessor processor = this.chainProcessors.get(processorName);
        if (processor == null) {
            throw new IllegalStateException("No Such ChainProcessor Defined: " + processorName);
        }
        return processor;
    }
}
