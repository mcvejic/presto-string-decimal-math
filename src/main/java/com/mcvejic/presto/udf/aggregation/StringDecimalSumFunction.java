package com.mcvejic.presto.udf.aggregation;

/*
  Created by milan on 09/05/18.

  Calculate sum of big decimals represented as strings
 */

import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.function.AggregationFunction;
import com.facebook.presto.spi.function.CombineFunction;
import com.facebook.presto.spi.function.InputFunction;
import com.facebook.presto.spi.function.OutputFunction;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apfloat.Apfloat;

import static com.facebook.presto.spi.type.VarcharType.VARCHAR;

@AggregationFunction("str_dec_math_sum")
public class StringDecimalSumFunction
{
    @InputFunction
    public static void input(DecimalState state, @SqlType(StandardTypes.VARCHAR) Slice value)
    {
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
    public static void combine(DecimalState state, DecimalState otherState)
    {
        Apfloat stateValueAfloat = new Apfloat(0.0);
        Apfloat otherStateValueAfloat = new Apfloat(0.0);

        if (state.getDecimal() != null) {
            stateValueAfloat = new Apfloat(state.getDecimal().toStringAscii(), 100);
        }

        if (otherState.getDecimal() != null) {
            otherStateValueAfloat = new Apfloat(otherState.getDecimal().toStringAscii(), 100);
        }

        state.setDecimal(
                Slices.utf8Slice(stateValueAfloat.add(otherStateValueAfloat).toString(true)));

    }

    @OutputFunction(StandardTypes.VARCHAR)
    public static void output(DecimalState state, BlockBuilder out)
    {
        VARCHAR.writeString(out, state.getDecimal().toStringAscii());
    }
}
