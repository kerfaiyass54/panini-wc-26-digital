import json

from kafka import KafkaConsumer

from app.models.match_request import MatchRequest
from app.services.simulator import Simulator
from app.kafka.producer import send_result

consumer = KafkaConsumer(
    "match-request-topic",
    bootstrap_servers="localhost:9092",
    value_deserializer=lambda m:
        json.loads(m.decode("utf-8")),
    group_id="simulation-group"
)

def start_consumer():

    for message in consumer:

        request = MatchRequest(
            **message.value
        )

        result = Simulator.simulate(
            request
        )

        send_result(result)