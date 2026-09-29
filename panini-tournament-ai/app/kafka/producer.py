import json

from kafka import KafkaProducer

producer = KafkaProducer(
    bootstrap_servers="localhost:29092",
    value_serializer=lambda v:
        json.dumps(v).encode("utf-8")
)

def send_result(result):

    producer.send(
        "match-result-topic",
        result.model_dump()
    )

    producer.flush()