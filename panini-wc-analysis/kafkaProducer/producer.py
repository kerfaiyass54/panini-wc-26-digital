from kafka import KafkaProducer

import json

from config.settings import (
    KAFKA_BOOTSTRAP_SERVERS
)

producer = KafkaProducer(

    bootstrap_servers=
        KAFKA_BOOTSTRAP_SERVERS,

    value_serializer=lambda value:
        json.dumps(value).encode("utf-8")
)

def publish(topic: str, payload: dict):

    producer.send(
        topic,
        payload
    )

    producer.flush()

    print(
        f"[KAFKA] sent to {topic}"
    )