<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main"/>
	<title>Drools Sample</title>
	<style type="text/css" media="screen">
	#page-body {
		margin: 2em;
	}
	p {
		line-height: 1.5;
		margin: 0.5em 0;
	}
	</style>
</head>

<body>
<div id="page-body">
	<h1>Test Results</h1>
	<g:set var="counter" value="${0}"/>
	<g:each var="result" in="${model.results}">
		<p>${result.key}: <span id="result${++counter}">${result.value}</span></p>
	</g:each>
</div>
</body>
</html>
