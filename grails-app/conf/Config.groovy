grails.plugin.drools.droolsRuleDomainClass = "grails.plugin.drools_sample.DroolsRule"

grails.project.groupId = appName

grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
	all          : '*/*',
	atom         : 'application/atom+xml',
	css          : 'text/css',
	csv          : 'text/csv',
	form         : 'application/x-www-form-urlencoded',
	html         : ['text/html', 'application/xhtml+xml'],
	js           : 'text/javascript',
	json         : ['application/json', 'text/json'],
	multipartForm: 'multipart/form-data',
	rss          : 'application/rss+xml',
	text         : 'text/plain',
	hal          : ['application/hal+json', 'application/hal+xml'],
	xml          : ['text/xml', 'application/xml']
]

grails.views.default.codec = "html"

grails.controllers.defaultScope = 'singleton'

grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml'
			codecs {
				expression = 'html'
				scriptlet = 'html'
				taglib = 'none'
				staticparts = 'none'
			}
		}
	}
}

grails.converters.encoding = "UTF-8"

grails.scaffolding.templates.domainSuffix = 'Instance'

grails.json.legacy.builder = false

grails.enable.native2ascii = true

grails.spring.bean.packages = []

grails.web.disable.multipart = false

grails.exceptionresolver.params.exclude = ['password']

grails.hibernate.cache.queries = false

grails.hibernate.pass.readonly = false

grails.hibernate.osiv.readonly = false

environments {
	development {
		grails.logging.jul.usebridge = true
	}
	production {
		grails.logging.jul.usebridge = false
	}
}

def log4jConversionPattern = '%d{yyyy-MMM-dd HH:mm:ss,SSS} %p [%t] %c %x - %F %L - %m%n'
log4j = {
	appenders {
		console name: 'stdout',
			layout: pattern(conversionPattern: log4jConversionPattern)
		file name: 'file',
			append: false,
			file: (System.getProperty('catalina.base') ?: 'target') + '/logs/grails-drools-sample.log',
			layout: pattern(conversionPattern: log4jConversionPattern)
	}
	root {
		error 'stdout', 'file'
	}
	info file: [
		'org.drools',
		'org.kie',
		'org.grails.plugins.drools'
	]
	error file: [
		'org.codehaus.groovy.grails.web.servlet',        // controllers
		'org.codehaus.groovy.grails.web.pages',          // GSP
		'org.codehaus.groovy.grails.web.sitemesh',       // layouts
		'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
		'org.codehaus.groovy.grails.web.mapping',        // URL mapping
		'org.codehaus.groovy.grails.commons',            // core / classloading
		'org.codehaus.groovy.grails.plugins',            // plugins
		'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
		'org.springframework',
		'org.hibernate'
	]
}
