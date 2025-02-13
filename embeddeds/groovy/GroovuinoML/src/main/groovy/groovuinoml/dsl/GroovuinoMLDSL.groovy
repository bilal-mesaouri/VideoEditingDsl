package main.groovy.groovuinoml.dsl;

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

class GroovuinoMLDSL {
	private GroovyShell shell
	private CompilerConfiguration configuration
	private GroovuinoMLBinding binding
	private GroovuinoMLBasescript basescript
	
	GroovuinoMLDSL() {
		binding = new GroovuinoMLBinding()
		binding.setGroovuinoMLModel(new GroovuinoMLModel(binding));
		configuration = getDSLConfiguration()
		configuration.setScriptBaseClass("main.groovy.groovuinoml.dsl.GroovuinoMLBasescript")
		shell = new GroovyShell(configuration)

	}
	
	private static CompilerConfiguration getDSLConfiguration() {
		def secure = new SecureASTCustomizer()
		secure.with {
			//disallow closure creation
			closuresAllowed = false
			//disallow method definitions
			methodDefinitionAllowed = true
			//empty white list => forbid imports
			importsWhitelist = [
				'java.lang.*',
				'java.math.BigDecimal'
			]
			staticImportsWhitelist = []
			staticStarImportsWhitelist= []
			//language tokens disallowed
//			tokensBlacklist= []
			//language tokens allowed
			tokensWhitelist= []
			//types allowed to be used  (including primitive types)
			constantTypesClassesWhiteList= [
				int, Integer, Number, Integer.TYPE, String, Object,float, Float, double, Double,BigDecimal
			]
			//classes who are allowed to be receivers of method calls
			receiversClassesWhiteList= [
				int, Number, Integer, String, Object, float, Float, double, Double, BigDecimal
			]
		}
		
		def configuration = new CompilerConfiguration()
		configuration.addCompilationCustomizers(secure)
		
		return configuration
	}
	
	void eval(File scriptFile) {
		Script script = shell.parse(scriptFile)
		
		binding.setScript(script)
		script.setBinding(binding)
		
		script.run()
	}
}
