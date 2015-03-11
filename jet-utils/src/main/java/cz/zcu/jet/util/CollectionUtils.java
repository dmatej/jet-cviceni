package cz.zcu.jet.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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

  public static void JDBCbastlFujTajxl() throws NamingException, SQLException {
    final InitialContext ctx = new InitialContext();
    final DataSource ds = (DataSource) ctx.lookup("jdbc/jetDb");
    final Connection connection = ds.getConnection();
    final PreparedStatement statement = connection
        .prepareStatement("select everything from database " + 256);
    final ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
      throw new RuntimeException();
    }
  }

}
