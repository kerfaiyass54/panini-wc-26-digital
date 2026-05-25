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

print("waiting for refresh events...")

while True:

    for message in consumer:

        try:

            value = message.value.decode("utf-8")

            # example:
            # "refresh for: yassine@gmail.com"

            email = (
                value
                .replace("refresh for:", "")
                .replace('"', "")
                .strip()
            )

            print(f"refresh received for {email}")

            process_nations_stats(email)

            process_groups_stats(email)

            process_continent_stats(email)

            print("all analytics sent to kafka")

        except Exception as e:

            print("analytics error:", e)