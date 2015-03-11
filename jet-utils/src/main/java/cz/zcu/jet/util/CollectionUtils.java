package cz.zcu.jet.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionUtils {
  private static final Logger LOG = LoggerFactory.getLogger(CollectionUtils.class);

  public static List<String> sort(final Collection<String> words) {
    LOG.debug("sort(words={})", words);
    if (words == null) {
      return null;
    }
    return words.stream()
        .sorted((a, b) -> Objects.toString(a, "").compareToIgnoreCase(Objects.toString(b, "")))
        .collect(Collectors.toList());
  }

}
