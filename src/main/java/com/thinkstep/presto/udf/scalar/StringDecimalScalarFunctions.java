package com.thinkstep.presto.udf.scalar;

/**
 * Created by milan on 11/05/18.
 */

import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlNullable;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import io.airlift.slice.Slice;
import org.apfloat.Apfloat;

import static io.airlift.slice.Slices.utf8Slice;
public class StringDecimalScalarFunctions
{
    @Description("Divides two decimals stored as strings")
    @ScalarFunction("str_dec_math_divide")
    @SqlType(StandardTypes.VARCHAR)
    @SqlNullable
    public static Slice str_dec_math_divide(@SqlNullable @SqlType(StandardTypes.VARCHAR) Slice dividend, @SqlNullable @SqlType(StandardTypes.VARCHAR) Slice divisor)
    {
        if (dividend == null || dividend.toStringAscii().isEmpty() || divisor == null || divisor.toStringAscii().isEmpty()) {
            return null;
        }
        else {
            return utf8Slice(new Apfloat(dividend.toStringAscii(), 100).divide(new Apfloat(divisor.toStringAscii(), 100)).toString(true));
        }
    }

    @Description("Multiply two decimals stored as strings")
    @ScalarFunction("str_dec_math_multiply")
    @SqlType(StandardTypes.VARCHAR)
    @SqlNullable
    public static Slice str_dec_math_multiply(@SqlNullable @SqlType(StandardTypes.VARCHAR) Slice multiplicand, @SqlNullable @SqlType(StandardTypes.VARCHAR) Slice multiplier)
    {
        if (multiplicand == null || multiplicand.toStringAscii().isEmpty() || multiplier == null || multiplier.toStringAscii().isEmpty()) {
            return null;
        }
        else {
            return utf8Slice(new Apfloat(multiplicand.toStringAscii(), 100).multiply(new Apfloat(multiplier.toStringAscii(), 100)).toString(true));
        }
    }
}
