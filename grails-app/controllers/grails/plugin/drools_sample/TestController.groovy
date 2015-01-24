package grails.plugin.drools_sample

import org.kie.api.runtime.KieSession
import org.kie.api.runtime.StatelessKieSession

class TestController {

	def droolsService
	StatelessKieSession applicationStatelessSession
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

		def t1 = new Ticket(1, new Customer("Jack", "Gold"))
		def t2 = new Ticket(2, new Customer("Tom", "Silver"))
		def t3 = new Ticket(3, new Customer("Bill", "Bronze"))
		facts = [t1, t1.customer, t2, t2.customer, t3, t3.customer]
		for (fact in facts) {
			ticketStatefulSession.insert fact
		}
		ticketStatefulSession.fireAllRules()
		ticketStatefulSession.dispose()
		model.results["ticketStatefulSession - t1.status is Escalate"] = t1.status
		model.results["ticketStatefulSession - t1.customer.discount is 5"] = t1.customer.discount
		model.results["ticketStatefulSession - t2.status is Escalate"] = t2.status
		model.results["ticketStatefulSession - t2.customer.discount is 0"] = t2.customer.discount
		model.results["ticketStatefulSession - t3.status is Pending"] = t3.status
		model.results["ticketStatefulSession - t3.customer.discount is 0"] = t3.customer.discount

		return model
	}
}
