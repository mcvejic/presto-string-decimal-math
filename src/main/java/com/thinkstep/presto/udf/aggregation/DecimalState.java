package com.thinkstep.presto.udf.aggregation;

import io.prestosql.spi.function.AccumulatorState;
import io.airlift.slice.Slice;

/**
 * Created by milan on 09/05/18.
 */
public interface DecimalState extends AccumulatorState
{
    Slice getDecimal();

    void setDecimal(Slice value);
}
