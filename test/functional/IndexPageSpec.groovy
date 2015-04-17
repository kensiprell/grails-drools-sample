import geb.spock.GebReportingSpec
import spock.lang.*

@Stepwise
class IndexPageSpec extends GebReportingSpec {

	def "IndexPage"() {
		when:
		go "http://localhost:8080/grails-drools-sample/"

		then:
		title == "Drools Sample"
	}

	@Unroll
	def "result#id should equal #text"() {
		when:
		waitFor { $("#result$id").displayed }

		then:
		$("#result$id").text() == text

		where:
		id	| text
		1	| "true"
		2	| "false"
		3	| "false"
		4	| "true"
		5	| "false"
		6	| "false"
		7	| "true"
		8	| "false"
		9	| "false"
		10	| "true"
		11	| "false"
		12	| "false"
		13	| "true"
		14	| "false"
		15	| "false"
		16	| "Escalate"
		17	| "5"
		18	| "Escalate"
		19	| "0"
		20	| "Pending"
		21	| "0"
		22	| "Escalate"
		23	| "5"
		24	| "Escalate"
		25	| "0"
		26	| "Pending"
		27	| "0"
	}
}
