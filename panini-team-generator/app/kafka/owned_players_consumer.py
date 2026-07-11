import json

from kafka import KafkaConsumer

from app.elastic.elastic_client import (
    es,
    INDEX_NAME
)

consumer = KafkaConsumer(
    "owned-players-response-topic",
    bootstrap_servers="localhost:9092",
    auto_offset_reset="earliest",
    enable_auto_commit=True,
    value_deserializer=lambda x:
    json.loads(x.decode("utf-8"))
)


def start_consumer():

    print(
        "Listening to owned-players-response-topic..."
    )

    for message in consumer:

        data = message.value

        email = data["email"]

        document = {
            "email": email,
            "players": data["players"]
        }

        es.index(
            index=INDEX_NAME,
            id=email,
            document=document
        )

        print(
            f"Saved players for {email}"
        )