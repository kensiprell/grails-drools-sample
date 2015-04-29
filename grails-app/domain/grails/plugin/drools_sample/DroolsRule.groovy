package grails.plugin.drools_sample

class DroolsRule {

	String rule
	String description
	String packageName

	static mapping = {
		rule type: 'text'
	}

	static constraints = {
		rule blank: false
		description blank: false
		packageName nullable: true, blank: true
	}
}
