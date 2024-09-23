package com.external.calculator.chain.processor;

import com.external.calculator.chain.context.ChainContext;

public interface ChainProcessor {

    void process(ChainContext chainContext);

}