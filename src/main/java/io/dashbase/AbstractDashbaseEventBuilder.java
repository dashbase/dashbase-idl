package io.dashbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDashbaseEventBuilder<T, B extends AbstractDashbaseEventBuilder<T, B>> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDashbaseEventBuilder.class);

  protected Map<String, String> textCols = new HashMap<>();
  protected Map<String, Double> numberCols = new HashMap<>();
  protected Map<String, String> metaCols = new HashMap<>();
  protected Map<String, String> idCols = new HashMap<>();
  protected long timeInMillis = -1L;
  protected boolean omitPayload = false;

  public abstract T build();

  public B withTimeInMillis(long timeInMillis) {
    if (timeInMillis <= 0) {
      LOGGER.debug("timeInMillis must be positive {}", timeInMillis, new IllegalArgumentException());
      return (B) this;
    }

    this.timeInMillis = timeInMillis;
    return (B) this;
  }

  public B withOmitPayload(boolean omitPayload) {
    this.omitPayload = omitPayload;
    return (B) this;
  }

  public B withMetaColumns(Map<String, String> metaCols) {
    if (metaCols == null) {
      LOGGER.debug("metaCols cannot be null", new IllegalArgumentException());
      return (B) this;
    }
    this.metaCols = metaCols;
    return (B) this;
  }

  public B addMeta(String name, String val) {
    safeAdd(name, val, metaCols);
    return (B) this;
  }

  public B withTextColumns(Map<String, String> textCols) {
    if (textCols == null) {
      LOGGER.debug("textCols cannot be null", new IllegalArgumentException());
      return (B) this;
    }
    this.textCols = textCols;
    return (B) this;
  }

  public B addText(String name, String val) {
    safeAdd(name, val, textCols);
    return (B) this;
  }

  public B withIdColumns(Map<String, String> idCols) {
    if (idCols == null) {
      LOGGER.debug("idCols cannot be null", new IllegalArgumentException());
      return (B) this;
    }

    this.idCols = idCols;
    return (B) this;
  }

  public B addId(String name, String val) {
    safeAdd(name, val, idCols);
    return (B) this;
  }

  public B withNumberColumns(Map<String, Double> numberCols) {
    if (numberCols == null) {
      LOGGER.debug("numberCols cannot be null", new IllegalArgumentException());
      return (B) this;
    }
    this.numberCols = numberCols.entrySet().stream()
        .filter(e -> isValidDouble(e.getValue()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return (B) this;
  }

  public B addNumber(String name, double val) {
    if (name == null) {
      LOGGER.debug("name cannot be null", name, new IllegalArgumentException());
      return (B) this;
    } else if (!isValidDouble(val)) {
      LOGGER.debug("invalid value {}", val, new IllegalArgumentException());
      return (B) this;
    }
    this.numberCols.put(name, val);
    return (B) this;
  }

  private static boolean isValidDouble(double d) {
    // NaN and Infinite doubles are serialized as String "NaN" and "Infinite",
    // which causes AvroTypeException
    // https://issues.apache.org/jira/browse/AVRO-2032
    // Ignore those values for now.
    return !Double.isNaN(d) && !Double.isInfinite(d);
  }

  private void safeAdd(String name, String val, Map<String, String> map) {
    if (name != null && val != null) {
      map.put(name, val);
    } else {
      LOGGER.debug("name {} and value {} cannot be null", name, val, new IllegalArgumentException());
    }
  }
}
