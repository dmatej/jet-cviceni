package cz.zcu.jet.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class CollectionUtilsTest {

  @Test
  public void sortNothing() {
    assertNull("null", CollectionUtils.sort(null));
  }

  @Test
  public void sortOnlyNullInList() {
    final Collection<String> names = Arrays.asList((String) null);
    final List<String> sorted = CollectionUtils.sort(names);
    assertNotNull("null", sorted);
    assertEquals("sorted.size", 1, sorted.size());
    assertNull("sorted[0]", sorted.get(0));
  }

  @Test
  public void sortNames() {
    final List<String> names = Arrays.asList("řízeček", null, "matějček", "ČOČKA");
    final List<String> sorted = CollectionUtils.sort(names);
    assertNotNull("null", sorted);
    assertEquals("sorted.size", 4, sorted.size());
    assertEquals("sorted[0]", null, sorted.get(0));
    assertEquals("sorted[1]", "ČOČKA", sorted.get(1));
    assertEquals("sorted[2]", "matějček", sorted.get(2));
    assertEquals("sorted[3]", "řízeček", sorted.get(3));
    assertFalse("sorted.hashcode and names.hashcode should not be equal",
        names.hashCode() == sorted.hashCode());
    assertEquals("null changed position in original list!", null, names.get(1));
  }
}
