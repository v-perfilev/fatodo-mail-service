# Fatodo mail-service
Spring app that is used email sending based on extended mongo skeleton

## Tests

### Unit tests
```
./mvnw test
```
### Integration tests
```
./mvnw verify -DskipUTs
```
### Contract tests
```
./mvnw -ntp --batch-mode -s etc/maven/settings.xml initialize test-compile failsafe:integration-test failsafe:verify 
    -Pcontracts
    -Dmaven.repo.url=URL
    -Dmaven.repo.username=USERNAME
    -Dmaven.repo.password=PASSWORD
```
