package cz.zcu.jet.util;

import java.text.Collator;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities to simplify a work with collections.
 *
 * @author David Matějček
 */
public class CollectionUtils {
  private static final Logger LOG = LoggerFactory.getLogger(CollectionUtils.class);

  /**
   * Czech case insensitive null-safe sort.
   *
   * @param words
   * @return new sorted list
   */
  public static List<String> sort(final Collection<String> words) {
    LOG.debug("sort(words={})", words);
    if (words == null) {
      return null;
    }
    final Collator collator = Collator.getInstance(new Locale("cs", "CZ"));
    return words.stream()
        .sorted((a, b) -> collator.compare(Objects.toString(a, ""), Objects.toString(b, "")))
        .collect(Collectors.toList());
  }
}
