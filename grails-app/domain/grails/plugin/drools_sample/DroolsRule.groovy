package grails.plugin.drools_sample

class DroolsRule {

	String ruleText
	String description
	String packageName

	static mapping = {
		ruleText type: 'text'
	}

	static constraints = {
		ruleText blank: false
		description blank: false
		packageName nullable: true, blank: true
	}
}
