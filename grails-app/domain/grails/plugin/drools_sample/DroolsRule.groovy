package grails.plugin.drools_sample

class DroolsRule {

	//String ruleText
	String rule
	String description
	String packageName

	static mapping = {
		//ruleText type: 'text'
		rule type: 'text'
	}

	static constraints = {
		//ruleText blank: false
		rule blank: false
		description blank: false
		packageName nullable: true, blank: true
	}
}
