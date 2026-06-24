NPM=node_modules/.bin

.PHONY: install clean test test-advanced

$(NPM):
	npm install --no-save @percy/cli@^1.31.13

install: $(NPM)
	mvn dependency:resolve

clean:
	rm -rf target node_modules

test test-advanced: install
	$(NPM)/percy app:exec -- mvn test -DsuiteXmlFile=src/test/resources/testng.xml
