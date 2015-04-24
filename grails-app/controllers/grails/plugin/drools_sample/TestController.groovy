package grails.plugin.drools_sample

import org.joda.time.DateTime
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
		DateTime dateTime = new DateTime(application.dateApplied)
		model.results["applicationStatelessSession - A Smith age is $applicant.age and application was made in $dateTime.year (true)"] = application.valid

		applicant = new Applicant(name: "B Smith", age: 17)
		application = new Application(dateApplied: new Date())
		facts = [applicant, application]
		applicationStatelessSession.execute(Arrays.asList(facts))
		dateTime = new DateTime(application.dateApplied)
		model.results["applicationStatelessSession - B Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "C Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		facts = [applicant, application]
		applicationStatelessSession.execute(Arrays.asList(facts))
		dateTime = new DateTime(application.dateApplied)
		model.results["applicationStatelessSession - C Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "D Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromFile("drools-rules/application/application.drl", [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromFile - D Smith age is $applicant.age and application was made in $dateTime.year (true)"] = application.valid

		applicant = new Applicant(name: "E Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromFile("drools-rules/application/application.drl", [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromFile - E Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "F Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromFile("drools-rules/application/application.drl", [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromFile - F Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		def rule = DroolsRule.findByDescription("application.drl")
		applicant = new Applicant(name: "G Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase(rule.id, [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromDatabase rule.id - G Smith age is $applicant.age and application was made in $dateTime.year (true)"] = application.valid

		applicant = new Applicant(name: "H Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase(rule.id, [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromDatabase rule.id - H Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "I Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromDatabase(rule.id, [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromDatabase rule.id - I Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "J Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase("application", [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromDatabase packageName - J Smith age is $applicant.age and application was made in $dateTime.year (true)"] = application.valid

		applicant = new Applicant(name: "K Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromDatabase("application", [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromDatabase packageName - K Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "L Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromDatabase("application", [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromDatabase packageName - L Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		def classLoader = new GroovyClassLoader()
		String text = classLoader.getResourceAsStream("drools-rules/application/application.drl").text
		applicant = new Applicant(name: "M Smith", age: 20)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromText(text, [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromText - M Smith age is $applicant.age and application was made in $dateTime.year (true)"] = application.valid

		applicant = new Applicant(name: "N Smith", age: 17)
		application = new Application(dateApplied: new Date())
		droolsService.executeFromText(text, [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromText - N Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		applicant = new Applicant(name: "O Smith", age: 20)
		application = new Application(dateApplied: new Date(114, 0, 1))
		droolsService.executeFromText(text, [applicant, application])
		dateTime = new DateTime(application.dateApplied)
		model.results["executeFromText - O Smith age is $applicant.age and application was made in $dateTime.year (false)"] = application.valid

		def t1 = new Ticket(1, new Customer("Greg", "Gold"))
		def t2 = new Ticket(2, new Customer("Sam", "Silver"))
		def t3 = new Ticket(3, new Customer("Bill", "Bronze"))
		List newFacts = [t1, t1.customer, t2, t2.customer, t3, t3.customer]
		for (fact in newFacts) {
			ticketStatefulSession.insert fact
		}
		ticketStatefulSession.fireAllRules()

		model.results["ticketStatefulSession - t1.status (Escalate)"] = t1.status
		model.results["ticketStatefulSession - t1.customer.discount (5)"] = t1.customer.discount
		model.results["ticketStatefulSession - t2.status (Escalate)"] = t2.status
		model.results["ticketStatefulSession - t2.customer.discount (0)"] = t2.customer.discount
		model.results["ticketStatefulSession - t3.status (Pending)"] = t3.status
		model.results["ticketStatefulSession - t3.customer.discount (0)"] = t3.customer.discount

		text = classLoader.getResourceAsStream("drools-rules/ticket/ticket.drl").text
		droolsService.fireFromText(text, newFacts)
		
		model.results["fireFromText - t1.status (Escalate)"] = t1.status
		model.results["fireFromText - t1.customer.discount (5)"] = t1.customer.discount
		model.results["fireFromText - t2.status (Escalate)"] = t2.status
		model.results["fireFromText - t2.customer.discount (0)"] = t2.customer.discount
		model.results["fireFromText - t3.status (Pending)"] = t3.status
		model.results["fireFromText - t3.customer.discount (0)"] = t3.customer.discount

		return model
	}
}
