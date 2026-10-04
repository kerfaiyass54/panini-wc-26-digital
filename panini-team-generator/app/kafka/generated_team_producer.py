import json
import os
from kafka import KafkaProducer

GENERATED_TEAM_TOPIC = "generated-team-topic"

producer = KafkaProducer(
    bootstrap_servers=os.getenv("KAFKA_BOOTSTRAP_SERVERS"),
    value_serializer=lambda v:
    json.dumps(v).encode("utf-8")
)


def send_generated_team(team):

    producer.send(
        GENERATED_TEAM_TOPIC,
        team
    )

    producer.flush()