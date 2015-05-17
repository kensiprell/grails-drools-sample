import grails.plugin.drools_sample.DroolsRule

class BootStrap {
	def init = { servletContext ->
		def classLoader = this.class.classLoader
		String rule1 = '''
rule "Applicant is over 18"
when
    $a : grails.plugin.drools_sample.Applicant(age > 18)
then
    System.out.println("Applicant is over 18.");
end
'''
		String rule2 = '''
rule "Application was made this year"
when
    $a : grails.plugin.drools_sample.Application(dateApplied > "01-Jan-2015")
then
    System.out.println("Application was made this year.");
end
'''
		String rule3 = '''
rule "Application is valid"
when
    $p : grails.plugin.drools_sample.Applicant(age > 18)
    $a : grails.plugin.drools_sample.Application(dateApplied > "01-Jan-2015")
then
    System.out.println("Application is valid.");
    $a.setValid(true);
end
'''
		new DroolsRule(ruleText: rule1, description: "Applicant is over 18", packageName: "application").save()
		new DroolsRule(ruleText: rule2, description: "Application was made this year", packageName: "application").save()
		new DroolsRule(ruleText: rule3, description: "Application is valid", packageName: "application").save()
		String drlText = classLoader.getResourceAsStream("drools-rules/application/application.drl").text
		new DroolsRule(ruleText: drlText, description: "application.drl").save()
		drlText = classLoader.getResourceAsStream("drools-rules/ticket/ticket.drl").text
		new DroolsRule(ruleText: drlText, description: "ticket.drl").save(flush: true)
	}

	def destroy = {
	}
}
