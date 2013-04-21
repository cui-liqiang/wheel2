package testutil;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.SystemLogChute;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

public class TemplateUtil {
    public static Template getTemplateFromString(String tplString) throws Exception {
        Velocity.setProperty(Velocity.RESOURCE_LOADER, "string");
        Velocity.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        Velocity.addProperty("string.resource.loader.modificationCheckInterval", "1");
        Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, SystemLogChute.class.getName());
        Velocity.init();

        StringResourceRepository repo = StringResourceLoader.getRepository();
        repo.putStringResource("testTpl", tplString);

        return Velocity.getTemplate("testTpl");
    }
}
