## Dashbase MessagePack

### Introduction

We can produce logs to kafka cluster in MessagePack format, and Dashbase will read from kafka and then parse the data. Here is the document about how to build a MessagePack doc that can be parsed by dashbase.

### Why MessagePack

It’s fast and small. We can use extension type to present specific type which can help dashbase organize the data.

### Extension Type

1. Sorted
2. Keyword
3. TextOffset
4. SortedOffset
5. LatLon
  Note: the type number **start from 0**, so if you want to use “Keyword” as your extension type, you should use **type code “1”**. Exp: ExtensionType(0, “test”) means the value “test” should be stored as “Sorted” in dashbase.
  To see more about schema reference, see [here](https://dashbase.atlassian.net/wiki/spaces/DK/pages/1867903/Schema+Reference).

### Data Format
- Timestamp. The first parameter we should pack is timestamp, and the type is “Long”. Exp: 1547125553
- Raw: The second parameter is a raw string. It will be mentioned later.
- Field,Value: The parser will parse the remained string repeatedly. It will first parse a string value, and set the result to “Field”. Then it will parse the next MessageFormat which is specified in org.msgpack.core.MessageFormat. 
  Format can be:
  1. FLOAT32, it will be parsed as float.
  2. FLOAT64, it will be parsed as double.
  3. STRING,  it will be parsed as raw text value.
  4. INTEGER, it will be parsed as long.
  5. EXTENSION, 
     1. Sorted, it will be parsed as Sorted value.
     2. Keyword, it will be parsed as Keyword value.
     3. TextOffset. This type actually point at the offset in raw string that mentioned in step two. The extension data that you should provide is an 8  bytes array. 
         The first 4 bytes present the start offset, and the latter 4 bytes present the end offset.   Exp: [0,0,0,0,0,0,0,4] means the start offset is 0(included), and the end offset is 4(not included). 
         And then you can use the bytes array to be the data of TextOffset type. The parser will set rawString.subString(0,4) to the value. 
         **Note: the data should be big endian.**
     4. SortedOffset. Same as TextOffset, but it will be treated as a string of Sorted type.
     5. LatLon. You can set latitude and longitude if you want. It looks same as offset, but you should provide a 16 length bytes array to present two double parameters. The parser will parse two double value sequentially and set them to lat, lon respectively.
  6. ARRAY, it will parse the array header first to get the length. And then loop to parse the each value respectively.

