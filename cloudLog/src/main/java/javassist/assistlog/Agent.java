package assistlog;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Agent {

	public static Properties props = new Properties();
	public static String userDir = System.getProperty("user.dir");
	public static Set<String> includePatterns = new HashSet<String>(16);
	
	private static void loadProps() throws Exception {

		String file = userDir+File.separator+"config"+File.separator+"agent.properties";
		InputStream is = new FileInputStream(file);
		props.load(is);
		is.close();
		
		String logPath = props.getProperty("logPath");
		if (logPath == null || logPath.trim().length() == 0) {
			logPath = userDir + File.separator + "output";
		}
		props.setProperty("logPath", logPath);

		String includeClasses = props.getProperty("includePatterns");
		if (includeClasses != null && !includeClasses.isEmpty()) {
			Collections.addAll(includePatterns, includeClasses.split(","));
		}
	}

	public static void premain(String agentArgs, Instrumentation inst) {
		try {
			System.out.println("========== call agent start ==========");
			loadProps();
//			appendBootstrapJar(inst);
			inst.addTransformer(new Transformer());
			
			System.out.println("========== call agent end ==========");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
}
	