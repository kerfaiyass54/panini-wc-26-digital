from kafka import KafkaConsumer

from stats.nationality_stats import process_nationality_stats
from stats.nations_stats import process_nations_stats
from stats.groups_stats import process_groups_stats
from stats.continent_stats import process_continent_stats

consumer = KafkaConsumer(
    "stickers-refresh-topic",
    bootstrap_servers="localhost:29092",
    auto_offset_reset="latest",
    group_id="python-analytics"
)

process_nationality_stats()

process_nations_stats()

process_groups_stats()

process_continent_stats()

print("all analytics sent to kafka")

print("waiting for refresh events...")

while True:

    for message in consumer:

        try:

            print("refresh received")

            process_nationality_stats()

            process_nations_stats()

            process_groups_stats()

            process_continent_stats()

            print("all analytics sent to kafka")

        except Exception as e:

            print("analytics error:", e)