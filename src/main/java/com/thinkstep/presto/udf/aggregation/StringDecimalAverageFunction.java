package com.thinkstep.presto.udf.aggregation;

/*
  Created by milan on 09/05/18.

  Calculate average of big decimals represented as strings
 */

import io.prestosql.spi.block.BlockBuilder;
import io.prestosql.spi.function.AggregationFunction;
import io.prestosql.spi.function.CombineFunction;
import io.prestosql.spi.function.InputFunction;
import io.prestosql.spi.function.OutputFunction;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;
import static io.prestosql.spi.type.VarcharType.VARCHAR;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apfloat.Apfloat;

@AggregationFunction("str_dec_math_avg")
public class StringDecimalAverageFunction
{
    @InputFunction
    public static void input(LongAndDecimalState state, @SqlType(StandardTypes.VARCHAR) Slice value)
    {
        state.setLong(state.getLong() + 1);
        Apfloat stateValueAfloat = new Apfloat(0.0);
        Apfloat otherStateValueAfloat = new Apfloat(0.0);

        if (state.getDecimal() != null) {
            stateValueAfloat = new Apfloat(state.getDecimal().toStringAscii(), 100);
        }

        if (value != null) {
            otherStateValueAfloat = new Apfloat(value.toStringAscii(), 100);
        }

        state.setDecimal(
                Slices.utf8Slice(stateValueAfloat.add(otherStateValueAfloat).toString()));
    }

    @CombineFunction
    public static void combine(LongAndDecimalState state, LongAndDecimalState otherState)
    {
        state.setLong(state.getLong() + otherState.getLong());
        Apfloat stateValueAfloat = new Apfloat(0.0);
        Apfloat otherStateValueAfloat = new Apfloat(0.0);

        if (state.getDecimal() != null) {
            stateValueAfloat = new Apfloat(state.getDecimal().toStringAscii(), 100);
        }

        if (otherState.getDecimal() != null) {
            otherStateValueAfloat = new Apfloat(otherState.getDecimal().toStringAscii(), 100);
        }

        state.setDecimal(
                Slices.utf8Slice(stateValueAfloat.add(otherStateValueAfloat).toString()));

    }

    @OutputFunction(StandardTypes.VARCHAR)
    public static void output(LongAndDecimalState state, BlockBuilder out)
    {
        long count = state.getLong();
        if (count == 0) {
            out.appendNull();
        }
        else {
            if (state.getDecimal() != null && !state.getDecimal().toStringAscii().isEmpty()) {
                Apfloat value = new Apfloat(state.getDecimal().toStringAscii(), 100);
                VARCHAR.writeString(out, value.divide(new Apfloat(count, 100)).toString(true));
            }
            else {
                out.appendNull();
            }
        }
    }
}
