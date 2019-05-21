# Patient Service
This is a sample application to be run on IPS.

# Configuration
The following properties can be configured based on your environment. There are a number of Kafka properties in application.properties that are
not documented below. Only the environment specific config is provided in the table.

|| Property || Description || Default value ||
| fhir.server.root.url | Base URL of FHIR server. (Expects no authentication) Currently using publicly accessible FHIR server | https://r3.smarthealthit.org |
| spring.kafka.consumer.bootstrap-servers | Kafka consumer servers | localhost:9092 |
| spring.kafka.producer.bootstrap-servers | Kafka producer servers | localhost:9092 |
