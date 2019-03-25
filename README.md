# presto-string-decimal-math
Presto - UDF plugin for doing BigDecimal math over String columns

#### Supported functions

##### Aggregate functions:
- str_dec_math_avg
- str_dec_math_max
- str_dec_math_min
- str_dec_math_sum

##### Scalar functions:

- str_dec_math_divide
- str_dec_math_multiply

### Building jar with dependencies:

`mvn clean compile assembly:single`
