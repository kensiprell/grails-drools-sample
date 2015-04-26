import grails.plugin.drools_sample.DroolsRule

class BootStrap {
	def init = { servletContext ->
		def classLoader = this.class.classLoader
		def applicationRules = DroolsRule.findAllByPackageName("application")
		log.error "BOOTSTRAP before applicationRules: ${applicationRules.size()}"
		log.error "BOOTSTRAP before DroolsRule: ${DroolsRule.getAll().size()}"
		String drlText = classLoader.getResourceAsStream("drools-rules/application/application.drl").text
		new DroolsRule(rule: drlText, description: "application.drl", packageName: "application").save(flush: true)
		drlText = classLoader.getResourceAsStream("drools-rules/ticket/ticket.drl").text
		new DroolsRule(rule: drlText, description: "ticket.drl", packageName: "ticket").save(flush: true)
		applicationRules = DroolsRule.findAllByPackageName("application")
		log.error "BOOTSTRAP after applicationRules: ${applicationRules.size()}"
		log.error "BOOTSTRAP after DroolsRule: ${DroolsRule.getAll().size()}"
		log.error "BLANK LINE"
	}

	def destroy = {
	}
}
