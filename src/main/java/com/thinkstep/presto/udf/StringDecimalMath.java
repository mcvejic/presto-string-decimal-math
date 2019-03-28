package com.thinkstep.presto.udf;

/**
 * Created by milan on 09/05/18.
 */
import java.util.Set;

import io.prestosql.spi.Plugin;
import com.google.common.collect.ImmutableSet;
import com.thinkstep.presto.udf.aggregation.StringDecimalAverageFunction;
import com.thinkstep.presto.udf.aggregation.StringDecimalMaxFunction;
import com.thinkstep.presto.udf.aggregation.StringDecimalMinFunction;
import com.thinkstep.presto.udf.aggregation.StringDecimalSumFunction;
import com.thinkstep.presto.udf.scalar.StringDecimalScalarFunctions;

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
                .add(StringDecimalScalarFunctions.class)
                .build();
    }
}
