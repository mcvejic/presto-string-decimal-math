package com.thinkstep.presto.udf.aggregation;

/*
  Created by milan on 09/05/18.

  Calculate sum of big decimals represented as strings
 */

import io.prestosql.spi.block.BlockBuilder;
import io.prestosql.spi.function.AggregationFunction;
import io.prestosql.spi.function.CombineFunction;
import io.prestosql.spi.function.InputFunction;
import io.prestosql.spi.function.OutputFunction;
import io.prestosql.spi.function.SqlType;
import io.prestosql.spi.type.StandardTypes;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import org.apfloat.Apfloat;

import static io.prestosql.spi.type.VarcharType.VARCHAR;

@AggregationFunction("str_dec_math_min")
public class StringDecimalMinFunction
{
    @InputFunction
    public static void input(DecimalState state, @SqlType(StandardTypes.VARCHAR) Slice value)
    {
        if (state.getDecimal() != null) {
            Apfloat stateValueAfloat = new Apfloat(state.getDecimal().toStringAscii(), 100);
            if (value != null) {
                Apfloat otherStateValueAfloat = new Apfloat(value.toStringAscii(), 100);
                if (stateValueAfloat.compareTo(otherStateValueAfloat) >= 0) {
                    state.setDecimal(
                            Slices.utf8Slice(otherStateValueAfloat.toString()));
                }
            }
        }
        else {
            if (value != null) {
                Apfloat otherStateValueAfloat = new Apfloat(value.toStringAscii(), 100);
                state.setDecimal(
                        Slices.utf8Slice(otherStateValueAfloat.toString()));
            }
        }
    }

    @CombineFunction
    public static void combine(DecimalState state, DecimalState otherState)
    {
        if (state.getDecimal() != null) {
            Apfloat stateValueAfloat = new Apfloat(state.getDecimal().toStringAscii(), 100);
            if (otherState != null) {
                Apfloat otherStateValueAfloat = new Apfloat(otherState.getDecimal().toStringAscii(), 100);
                if (stateValueAfloat.compareTo(otherStateValueAfloat) >= 0) {
                    state.setDecimal(
                            Slices.utf8Slice(otherStateValueAfloat.toString(true)));
                }
            }
        }
        else {
            if (otherState.getDecimal() != null) {
                Apfloat otherStateValueAfloat = new Apfloat(otherState.getDecimal().toStringAscii(), 100);
                state.setDecimal(
                        Slices.utf8Slice(otherStateValueAfloat.toString(true)));
            }
        }

    }

    @OutputFunction(StandardTypes.VARCHAR)
    public static void output(DecimalState state, BlockBuilder out)
    {
        if (state != null && state.getDecimal() != null && !state.getDecimal().toStringAscii().isEmpty()) {
            VARCHAR.writeString(out, state.getDecimal().toStringAscii());
        }
        else {
            out.appendNull();
        }
    }
}
