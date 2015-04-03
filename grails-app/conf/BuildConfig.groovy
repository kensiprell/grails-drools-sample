grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.fork = [
	test   : false,
	run    : false,
	war    : false,
	console: false
]

grails.plugin.drools.drlFileLocation = "drools-rules"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
	inherits("global") {
	}
	log "error"
	checksums true
	legacyResolve false

	repositories {
		mavenRepo "http://localhost:8081/artifactory/plugins-snapshot-local/"
		mavenRepo "http://localhost:8081/artifactory/plugins-release-local/"

		grailsPlugins()
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
	}

	dependencies {
		test "org.gebish:geb-spock:0.10.0"
		test "org.seleniumhq.selenium:selenium-chrome-driver:2.41.0"
		test "org.seleniumhq.selenium:selenium-firefox-driver:2.41.0"
		test "org.seleniumhq.selenium:selenium-support:2.41.0"
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
		test "org.grails:grails-datastore-test-support:1.0.2-grails-2.4"
	}

	plugins {
		build ":tomcat:8.0.21"

		compile ":drools:1.0.0"
		compile ":scaffolding:2.1.2"
		compile ':cache:1.1.8'
		compile ":asset-pipeline:2.1.5"

		runtime ":hibernate4:4.3.6.1"
		runtime ":database-migration:1.4.0"
		runtime ":jquery:1.11.1"

		test ":geb:0.10.0"
	}
}
