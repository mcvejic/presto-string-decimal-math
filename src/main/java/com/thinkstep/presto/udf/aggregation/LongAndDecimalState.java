package com.thinkstep.presto.udf.aggregation;

import com.facebook.presto.spi.function.AccumulatorState;
import io.airlift.slice.Slice;

/**
 * Created by milan on 09/05/18.
 */
public interface LongAndDecimalState extends AccumulatorState
{
    long getLong();

    void setLong(long value);

    Slice getDecimal();

    void setDecimal(Slice value);
}
