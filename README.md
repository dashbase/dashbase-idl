# dashbase-idl
Dashbase IDL definitions

## Avro

For storing and transmitting structured data in a compact and language-indepedent way, we've created [Avro](https://avro.apache.org/) schemas [here](https://github.com/dashbase/dashbase-idl/tree/master/src/main/avro)

Currently we only have Avro support due to native-support by both the [Hadoop](http://hadoop.apache.org/) and [Kafka](https://kafka.apache.org/) projects.

## DashbaseEvent Definition

A [DashbaseEvent](https://github.com/dashbase/dashbase-idl/blob/master/src/main/avro/dashbase_event.avsc) defines a record to be inserted into Dashbase, and is composed from 3 parts:

### Time

Timestamp in milliseconds of creation of the event, defaults to 0 if not specified.

### Columns

Dashbase columns define how the record is to be stored.

* meta columns - structured data, will not be tokenized and support aggregations, e.g. topn. Examples are: host, response code etc.
* number columns - contains numeric data, will be indexed as numbers and support numeric aggregations, e.g. sum/min/max/avg. Examples are: latency, byte count etc.
* text columns - unstructured text, will be tokenized and support full-text queries. Examples are: log messages, agents etc.
* id columns - optimized for optional id information, similar to meta, will not be tokenized. Aggreagtions are not supported.

### Payload

Raw data and its storage can be configurated via:

* omitPayload - if true, raw data storage is skipped. Examples would be metrics or click data, where raw event bytes are typically not used, and storing them would be wasteful.
* usePayload - if true, will be using the payload data provided, otherwise the avro bytes are used for raw storage. This flag is ignored if ```omitPayload``` is set to true.
* payload- the raw bytes for the payload, used only if ```omitPayload``` is false AND . ```usePayload``` is true.

## Java API

[DashbaseEventBuilder](https://github.com/dashbase/dashbase-idl/blob/master/src/main/java/io/dashbase/avro/DashbaseEventBuilder.java) should be used to build a [DashbaseEvent](https://github.com/dashbase/dashbase-idl/blob/master/src/main/java/io/dashbase/avro/DashbaseEvent.java) instance.

Example:

```
DashbaseEventBuilder eventBuilder =
                new DashbaseEventBuilder()
                .withPayload(new byte[0])
                .withTimeInMillis(1234567L)
                .addMeta("tags", "green")
                .addNumber("num", 1234.0)
                .addText("text", "dashbase is cool");

DashbaseEvent event = eventBuilder.build();
```

