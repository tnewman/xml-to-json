# xml-to-json
Converts XML to JSON based on a mapping file.

## Build
```bash
./gradlew shadowJar
```

## Run
```bash
java -jar build/libs/xml-to-json-all.jar src/test/resources/testmappings.json src/test/resources/testdata.xml test.json
```
