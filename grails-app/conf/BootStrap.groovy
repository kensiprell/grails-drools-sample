import grails.plugin.drools_sample.DroolsRule

class BootStrap {

	def init = { servletContext ->
		def classLoader = new GroovyClassLoader()
		String drlText = classLoader.getResourceAsStream("rules.application.application.drl").text
		new DroolsRule(rule: drlText, description: "application.drl", packageName: "application").save(flush: true)
		drlText = classLoader.getResourceAsStream("rules.ticket.ticket.drl").text
		new DroolsRule(rule: drlText, description: "ticket.drl", packageName: "ticket").save(flush: true)
		DroolsRule.withSession { it.clear() }
	}

	def destroy = {
	}
}
