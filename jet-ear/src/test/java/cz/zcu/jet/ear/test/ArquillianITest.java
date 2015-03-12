/**
 *
 */
package cz.zcu.jet.ear.test;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.glassfish.embeddable.CommandResult;
import org.glassfish.embeddable.CommandRunner;
import org.glassfish.embeddable.GlassFishException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public abstract class ArquillianITest {

  private static final Logger LOG = LoggerFactory.getLogger(ArquillianITest.class);

  public static final int ID_PERSON_XTST_ECIS_FO = 29630992;

  public static final int ID_PERSON_XTST_ECIS_LEGAL_REPRESENTATIVE = 29630993;

  public static final int ID_PERSON_XTST_ECIS = 24321541;

  public static final int ID_PERSON_WITH_PARENTS = 29627883;

  public static final int ID_PERSON_FATHER = 29631006;

  public static final int ID_PERSON_MOTHER = 29631007;

  public static final int ID_PERSON_MORE_IDENTITIES = 24638782;

  public static final int ID_IDENTITY_MORE_IDENTITIES = 27707832;

  public static final int ID_PERSON_ADOPTION = 29625464;

  public static final int ID_PERSON_ADR_ADDRESS = 23769825;

  public static final int ID_IDENTITY_XTST_DITE = 27709356;

  /**
   * <em>Nepoužívat!</em> - tuto osobu používá Dan Mareš k testování
   * a tudíž jí často mění. Uvedené ID proto slouží pouze k orientačnímu
   * nástřelu testu, a proto je nutno je provádět na speciální testovací osobě.
   */
  public static final int ID_PERSON_GOLOVKA = 27955924;

  private static EnterpriseArchive ear;
  private static boolean alreadyInitialized;

  /**
   * Initialize test class.
   *
   * @return {@link JavaArchive} to deploy to the container.
   * @throws Exception exception
   */
  @Deployment
  public static EnterpriseArchive init() throws Exception {
    if (alreadyInitialized) {
      return ear;
    }
    // do not try it again - if it fails, it should fail again.
    alreadyInitialized = true;

    runCommand("set", "configs.config.server-config.jms-service.type=EMBEDDED");
    runCommand("set",
        "configs.config.server-config.jms-service.jms-host.default_JMS_host.port=17676");
    runCommand("set",
        "configs.config.server-config.iiop-service.iiop-listener.orb-listener-1.port=18080");
    runCommand("set", "configs.config.server-config.iiop-service.iiop-listener.SSL.port=18081");
    runCommand("set",
        "configs.config.server-config.iiop-service.iiop-listener.SSL_MUTUALAUTH.port=18082");

    JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "ecis-ejb.jar");
    jar = jar.addPackages(true, "cz.zcu.jet.ws");
    LOG.info("{}", jar.toString(true));

    ear = ShrinkWrap.create(EnterpriseArchive.class, "jet-ear.ear").addAsModule(jar);
    return ear;
  }


  /**
   * Execute the command with parameters and return a result.
   *
   * @param command
   * @param parameters
   * @return result of the command
   * @throws GlassFishException - cannot communicate with the instance
   * @throws IllegalStateException - invalid parameters or command
   */
  public static CommandResult runCommand(final String command, final String... parameters) throws GlassFishException {
    LOG.debug("runCommand(command={}, parameters={})", command, parameters);
    try {
      final InitialContext ctx = new InitialContext();
      final CommandRunner runner = (CommandRunner) ctx.lookup("org.glassfish.embeddable.CommandRunner");
      if (runner == null) {
        throw new IllegalStateException("No command runner instance found in initial context!");
      }

      final CommandResult result = runner.run(command, parameters);
      checkCommandResult(command, result);
      return result;
    } catch (final NamingException e) {
      throw new IllegalStateException("Cannot run command " + command, e);
    }
  }


  private static void checkCommandResult(final String cmd, final CommandResult result) {
    LOG.info("Command: {}\n  Result.status:\n  {}\n  Result.out:\n  {}\n  Result.failCause:\n  {}\n", new Object[] {
        cmd, result.getExitStatus(), result.getOutput(), result.getFailureCause()});

    if (result.getExitStatus().ordinal() != 0) {
      throw new IllegalStateException("Command '" + cmd + "' was unsuccessful: " + result.getOutput(),
          result.getFailureCause());
    }
  }


  @Rule
  public TestRule watcher = new TestWatcher() {

    @Override
    protected void starting(final Description description) {
      LOG.info("Starting test: {}", description);
    }


    @Override
    protected void finished(final Description description) {
      LOG.info("Finished test: {}", description);
    }
  };
}
