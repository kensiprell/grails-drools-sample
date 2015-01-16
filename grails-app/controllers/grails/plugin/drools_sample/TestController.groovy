package grails.plugin.drools_sample

import org.kie.api.runtime.KieSession
import org.kie.api.runtime.StatelessKieSession

class TestController {

	def droolsService
	StatelessKieSession applicationStatelessSession
	// TODO tests for ticketStatefulSession
	KieSession ticketStatefulSession

	def index() {
		[model: model]
	}

	def getModel() {
		def model = [results:[:]]

		def applicant = new Applicant(name: "A Smith", age: 20)
		def application = new Application(dateApplied: new Date())
		Object[] facts = [applicant, application]
		applicationStatelessSession.execute(Arrays.asList(facts))
		model.results["Bean - age is 20 and application is made this year"] = application.valid

		applicant = new Applicant(name: "B Smith", age: 17)
		application = new Application(dateApplied: new Date())
		facts = [applicant, application]
		applicationStatelessSession.execute(Arrays.asList(facts))
		model.results["Bean - age is 17 and application is made this year"] = application.valid

		applicant = new Applicant(name: "C Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		facts = [applicant, application]
		applicationStatelessSession.execute(Arrays.asList(facts))
		model.results["Bean - age is over 18 and application is made last year"] = application.valid

		applicant = new Applicant(name: "A Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromFile("rules.application.application.drl", [applicant, application])
		model.results["File - age is over 18 and application is made this year"] = application.valid

		applicant = new Applicant(name: "B Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromFile("rules.application.application.drl", [applicant, application])
		model.results["File - age is 17 and application is made this year"] = application.valid

		applicant = new Applicant(name: "C Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromFile("rules.application.application.drl", [applicant, application])
		model.results["File - age is over 18 and application is made last year"] = application.valid

		def rule = DroolsRule.findByDescription("application.drl")
		applicant = new Applicant(name: "A Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase(rule.id, [applicant, application])
		model.results["Database - age is over 18 and application is made this year"] = application.valid

		applicant = new Applicant(name: "B Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase(rule.id, [applicant, application])
		model.results["Dabase: age is 17 and application is made this year"] = application.valid

		applicant = new Applicant(name: "C Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromDatabase(rule.id, [applicant, application])
		model.results["Database - age is over 18 and application is made last year"] = application.valid

		applicant = new Applicant(name: "A Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase("application", [applicant, application])
		model.results["PackageName - age is over 18 and application is made this year"] = application.valid

		applicant = new Applicant(name: "B Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase("application", [applicant, application])
		model.results["PackageName - age is 17 and application is made this year"] = application.valid

		applicant = new Applicant(name: "C Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromDatabase("application", [applicant, application])
		model.results["PackageName - age is over 18 and application is made last year"] = application.valid

		return model
	}
}
