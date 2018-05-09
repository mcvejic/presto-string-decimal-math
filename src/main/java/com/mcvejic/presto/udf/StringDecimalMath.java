package com.mcvejic.presto.udf;

/**
 * Created by milan on 09/05/18.
 */
import java.util.Set;

import com.facebook.presto.spi.Plugin;
import com.google.common.collect.ImmutableSet;
import com.mcvejic.presto.udf.aggregation.StringDecimalAverageFunction;
import com.mcvejic.presto.udf.aggregation.StringDecimalMaxFunction;
import com.mcvejic.presto.udf.aggregation.StringDecimalMinFunction;
import com.mcvejic.presto.udf.aggregation.StringDecimalSumFunction;

public class StringDecimalMath
        implements Plugin
{
    @Override
    public Set<Class<?>> getFunctions()
    {
        return ImmutableSet.<Class<?>>builder()
                .add(StringDecimalAverageFunction.class)
                .add(StringDecimalSumFunction.class)
                .add(StringDecimalMinFunction.class)
                .add(StringDecimalMaxFunction.class)
                .build();
    }
}
